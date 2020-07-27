package com.jkrm.education.ui.fragment;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.util.AwMathViewUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.RegexUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.AnswerAnalyChoiceAdapter;
import com.jkrm.education.adapter.AnswerAnalyExpendAdapter;
import com.jkrm.education.bean.BatchBean;
import com.jkrm.education.bean.QuestionOptionBean;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.bean.result.WatchLogBean;
import com.jkrm.education.bean.rx.RxLastBean;
import com.jkrm.education.bean.rx.RxNextpageType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.AnswerAnalysisPresent;
import com.jkrm.education.mvp.views.AnswerAnalysisView;
import com.jkrm.education.ui.activity.AnswerAnalysisActivity;
import com.jkrm.education.ui.activity.ErrorQuestionActivity;
import com.jkrm.education.ui.activity.OnlineAnswerActivity;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.github.kexanie.library.MathView;

/**
 * @Description:
 * @Author: xiangqian
 * @CreateDate: 2020/05/28
 */

public class AnswerAnalyChoiceFragment extends AwMvpFragment<AnswerAnalysisPresent> implements AnswerAnalysisView.View {

    private static final String TAG = "AnswerAnalyChoiceFragme";

    @BindView(R.id.cb_collect)
    CheckBox mCbCollect;
    @BindView(R.id.btn_next)
    Button mBtnNext;
    @BindView(R.id.math_view_title)
    MathView mMathViewTitle;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.tv_answer_state)
    TextView mTvAnswerState;
    @BindView(R.id.tv_answer)
    TextView mTvAnswer;
    @BindView(R.id.tv_analysis)
    TextView mTvAnalysis;
    @BindView(R.id.math_view_analysis)
    MathView mMathViewAnalysis;
    @BindView(R.id.ll_of_collect)
    LinearLayout mLlofCollec;
    @BindView(R.id.rcv_child_data)
    RecyclerView mRcvChildData;

    @BindView(R.id.video_player)
    StandardGSYVideoPlayer mVideoPlayer;
    @BindView(R.id.tv_num)
    TextView mTvNum;
    @BindView(R.id.btn_start)
    Button mBtnStart;
    @BindView(R.id.ll_of_analytic_expansion)
    LinearLayout mLlOfAnalyticExpansion;
    @BindView(R.id.ll_of_video)
    LinearLayout mLlOfVideo;
    @BindView(R.id.ll_of_expand)
    LinearLayout mLlOfExpand;
    Unbinder unbinder;

    private BatchBean mBatchBean;
    private List<QuestionOptionBean> mQuestionOptionBeanList;
    private AnswerAnalyChoiceAdapter mQuestionBasketOptionsAdapter;
    private String mQuestionType = "";
    List<SimilarResultBean> mList=new ArrayList<>();
    AnswerAnalyExpendAdapter mAnswerAnalyExpendAdapter;


    private OrientationUtils orientationUtils;
    private boolean isPlay;
    private boolean isPause;

    @Override
    protected AnswerAnalysisPresent createPresenter() {
        return new AnswerAnalysisPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_answeranaly_choice_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        mAnswerAnalyExpendAdapter=new AnswerAnalyExpendAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(getActivity(),mRcvChildData,mAnswerAnalyExpendAdapter,false);
    }

    @Override
    protected void initData() {
        super.initData();
       // EventBus.getDefault().register(this);
        mBatchBean = (BatchBean) getArguments().getSerializable(Extras.BATCHBEAN);
        mQuestionType = getArguments().getString(Extras.KEY_QUESTION_TYPE);
        if (null == mBatchBean) {
            return;
        }
        //错题 隐藏下一题
        if (!AwDataUtil.isEmpty(mBatchBean.getErrorTypeName())) {
            showView(mLlofCollec, false);
            showView(mLlOfAnalyticExpansion, true);
            initVideo(mBatchBean.getQuestionVideo(),"");
            if("0".equals(mBatchBean.getIsNoVideo())|| AwDataUtil.isEmpty(mBatchBean.getQuestionVideo())){
                showView(mLlOfVideo,false);
            }
            if("0".equals(mBatchBean.getSimilar())){
                showView(mLlOfExpand,false);
            }else{
                //有类题 请求数据
                mPresenter.getSimilar(mBatchBean.getQuestionId());
            }
        }
        //题干
        mMathViewTitle.setText(mBatchBean.getContent());
        AwMathViewUtil.setImgScan(mMathViewTitle);
        //解析
        mMathViewAnalysis.setText(mBatchBean.getAnswerExplanation());
        AwMathViewUtil.setImgScan(mMathViewAnalysis);
        if(AwDataUtil.isEmpty(mBatchBean.getAnswerExplanation())||mBatchBean.getAnswerExplanation().isEmpty()){
            showView(mTvAnalysis,false);
        }
        if (AwDataUtil.isEmpty(mBatchBean.getStudAnswer())) {
            mTvAnswerState.setText("未作答");
            mTvAnswerState.setTextColor(getResources().getColor(R.color.red));
            //正确答案
            mTvAnswer.setText("正确答案：" + Html.fromHtml(mBatchBean.getAnswer()));
        } else {
            int allRight = RegexUtil.isAllRight(Html.fromHtml(mBatchBean.getAnswer()).toString(), mBatchBean.getStudAnswer());
            if (0 == allRight) {
                mTvAnswerState.setText("回答正确");
                mTvAnswerState.setTextColor(getResources().getColor(R.color.black));
                //正确答案
                mTvAnswer.setText("正确答案：" + Html.fromHtml(mBatchBean.getAnswer()));
            } else if (1 == allRight) {
                //半对
                mTvAnswerState.setText("回答不全");
                mTvAnswerState.setTextColor(getResources().getColor(R.color.red));
                //正确答案
                mTvAnswer.setText("正确答案：" + Html.fromHtml(mBatchBean.getAnswer()) + "  我的答案：" + mBatchBean.getStudAnswer());
            } else if (2 == allRight) {
                //错误
                mTvAnswerState.setText("回答错误");
                mTvAnswerState.setTextColor(getResources().getColor(R.color.red));
                //正确答案
                mTvAnswer.setText("正确答案：" + Html.fromHtml(mBatchBean.getAnswer()) + "  我的答案：" + mBatchBean.getStudAnswer());
            }

        }

        mQuestionBasketOptionsAdapter = new AnswerAnalyChoiceAdapter();
        if (mBatchBean.getType() != null && "2".equals(mBatchBean.getType().getIsOption()) && null != mBatchBean.getOptions()) {
            mRcvData.setVisibility(View.VISIBLE);
            AwRecyclerViewUtil.setRecyclerViewLinearlayout((Activity) mActivity, mRcvData, mQuestionBasketOptionsAdapter, false);
            setChoiceListData(mBatchBean, mQuestionBasketOptionsAdapter, mRcvData);
        } else {
            mRcvData.setVisibility(View.GONE);
        }
        mRcvData.setItemAnimator(null);
        changeAnswerState();


        //取消闪烁动画
        // ((DefaultItemAnimator)mRcvData.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    /**
     * 选择题选项列表数据填充.
     */
    private void setChoiceListData(BatchBean bean, AnswerAnalyChoiceAdapter adapter, RecyclerView rcvData) {
        Map<String, String> mOptionsMap = (Map<String, String>) bean.getOptions();
        mQuestionOptionBeanList = new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, String> entry : mOptionsMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            if (AwDataUtil.isEmpty(entry.getValue())) {
                continue;
            }
            mQuestionOptionBeanList.add(new QuestionOptionBean(QuestionOptionBean.SERIAL_NUMS[index], entry.getValue(), false));
            index++;
        }
        if (AwDataUtil.isEmpty((Serializable) mQuestionOptionBeanList)) {
            adapter.clearData();
            rcvData.removeAllViews();
            adapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
            mRcvData.setVisibility(View.GONE);
        } else {
            adapter.addAllData(mQuestionOptionBeanList);
            adapter.loadMoreComplete();
            adapter.setEnableLoadMore(false);
            adapter.disableLoadMoreIfNotFullPage(rcvData);
        }
    }

    private void changeAnswerState() {
        if(!AwDataUtil.isEmpty(mQuestionOptionBeanList)){
            char[] right = Html.fromHtml(mBatchBean.getAnswer()).toString().toCharArray();//html 读取 因为有新罗马字体
            if (!AwDataUtil.isEmpty(mBatchBean.getStudAnswer())) {
                char[] custom = mBatchBean.getStudAnswer().toCharArray();
                for (char c : custom) {
                    String s = String.valueOf(c);
                    for (QuestionOptionBean questionOptionBean : mQuestionOptionBeanList) {
                        if (s.equals(questionOptionBean.getSerialNum())) {
                            questionOptionBean.setChoose(1);
                        }
                    }
                }
            }

            mQuestionBasketOptionsAdapter.notifyDataSetChanged();
            for (char c : right) {
                String s = String.valueOf(c);
                for (QuestionOptionBean questionOptionBean : mQuestionOptionBeanList) {
                    if (s.equals(questionOptionBean.getSerialNum())) {
                        questionOptionBean.setChoose(2);
                    }
                }
            }
            mQuestionBasketOptionsAdapter.notifyDataSetChanged();
            if (!AwDataUtil.isEmpty(mBatchBean.getIsNoCollect())) {
                //是否收藏
                mCbCollect.setChecked("1".equals(mBatchBean.getIsNoCollect()));
            }
        }

    }

    @Override
    protected void initListener() {
        super.initListener();
        if(AnswerAnalysisActivity.mStrLastQueID.equals(mBatchBean.getId())){
            mBtnNext.setEnabled(false);
            mBtnNext.setText("已是最后一题");
        }
        mCbCollect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mPresenter.collectQuestion(RequestUtil.getCollectBody(UserUtil.getAppUser().getStudId(), mBatchBean.getCourseId(), mBatchBean.getQuestionId(), "2"));
                } else {
                    mPresenter.removeCollectQuestion(RequestUtil.getRemoveCollectBody(UserUtil.getAppUser().getStudId(), mBatchBean.getQuestionId()));
                }
            }
        });
       /* mQuestionBasketOptionsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<QuestionOptionBean> mQuestionOptionBeanList = (List<QuestionOptionBean>) adapter.getData();
                for (int i = 0; i < mQuestionOptionBeanList.size(); i++) {
                    if (i == position) {
                        mQuestionOptionBeanList.get(i).setSelect(true);
                        mAnswer=mQuestionOptionBeanList.get(i).getSerialNum();
                    } else {
                        mQuestionOptionBeanList.get(i).setSelect(false);
                    }
                }
                mQuestionBasketOptionsAdapter.notifyDataSetChanged();
                //作答
                EventBus.getDefault().post(new RxQuestionBean(mBean.getId(),true,mAnswer,mBean.getParentId()));
                EventBus.getDefault().post(new RxTurnpageType(true,mBean.getId()));
            }
        });*/

    }


    @OnClick({R.id.cb_collect, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cb_collect:
                break;
            case R.id.btn_next:
                EventBus.getDefault().post(new RxNextpageType(true, mBatchBean.getId()));
                break;
        }
    }


    @Override
    public void collectQuestionSuccess(WatchLogBean request) {
        showMsg("收藏成功");
    }

    @Override
    public void collectQuestionFail(String msg) {
        showMsg("收藏失败");
    }

    @Override
    public void removeCollectQuestionSuccess(WatchLogBean request) {
        showMsg("移除收藏成功");
    }

    @Override
    public void removeCollectQuestionFail(String msg) {
        showMsg("移除收藏失败");
    }

    @Override
    public void readOverQuestionSuccess(WatchLogBean request) {
        showMsg("批阅成功");
    }

    @Override
    public void readOverQuestionFail(String msg) {
        showMsg("批阅失败");
    }

    @Override
    public void getSimilarSuccess(List<SimilarResultBean> result) {
        if(!AwDataUtil.isEmpty(result)){
            mList.addAll(result);
            mAnswerAnalyExpendAdapter.addAllData(mList);
            mTvNum.setText("类题加练(共"+mList.size()+"题)");
        }else{
            mAnswerAnalyExpendAdapter.clearData();
            mRcvChildData.removeAllViews();
            mAnswerAnalyExpendAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(getActivity(), MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));

        }
    }

    @Override
    public void getSimilarFail(String msg) {
        mAnswerAnalyExpendAdapter.clearData();
        mRcvChildData.removeAllViews();
        mAnswerAnalyExpendAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(getActivity(), MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));

    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onLastPage(RxLastBean rxLastBean) {
        if (null != rxLastBean) {
            if (rxLastBean.isLast()) {
                mBtnNext.setEnabled(false);
                mBtnNext.setText("已是最后一题");
            } else {
                mBtnNext.setEnabled(true);
                mBtnNext.setText("下一题");
            }
        }
    }


    @OnClick(R.id.btn_start)
    public void onViewClicked() {
        if(!AwDataUtil.isEmpty(mList)){
            toClass(OnlineAnswerActivity.class,true,Extras.KEY_SIMILAR_LIST,(Serializable)mList,Extras.COURSE_ID, ErrorQuestionActivity.mCourseId);
        }
    }

    private void initVideo(String url, String title) {
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(getActivity(), mVideoPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        if (mVideoPlayer.getFullscreenButton() != null) {
            mVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showFull();
                }
            });
        }
        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption
                //                .setThumbImageView(imageView)
                .setUrl(url)
                .setCacheWithPlay(true)
                .setVideoTitle("")
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(false)//打开动画
                .setNeedLockFull(true)
                .setSeekRatio(1)
                .setLooping(false)
                .setAutoFullWithSize(false)
                .setStartAfterPrepared(false)//设置不默认播放, 需点击后播放
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        //开始播放了才能旋转和全屏
                        orientationUtils.setEnable(true);
                        isPlay = true;
                        //                        videoPlayer.startAfterPrepared(); //设置不默认播放, 需点击后播放
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        if (orientationUtils != null) {
                            // setStatusBlack();
                            orientationUtils.backToProtVideo();
                        }
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        //setStatusTransparent();
                        super.onEnterFullscreen(url, objects);
                    }
                })
                .build(mVideoPlayer);

        //增加title
        mVideoPlayer.getTitleTextView().setVisibility(View.GONE);
        //设置返回键
        mVideoPlayer.getBackButton().setVisibility(View.GONE);
        //设置返回按键功能
        mVideoPlayer.startPlayLogic();
    }

    public void showFull() {
        if (orientationUtils.getIsLand() != 1) {
            //直接横屏
            orientationUtils.resolveByClick();
        }
        //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
        mVideoPlayer.startWindowFullscreen(mActivity, true, true);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (isPlay && !isPause) {
            mVideoPlayer.onConfigurationChanged(mActivity, newConfig, orientationUtils, true, true);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        mVideoPlayer.getCurrentPlayer().onVideoPause();
        isPause = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        mVideoPlayer.getCurrentPlayer().onVideoResume();
        isPause = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (isPlay) {
            if (null != mVideoPlayer) {
                mVideoPlayer.getCurrentPlayer().release();
            }
        }
        if (orientationUtils != null){
            orientationUtils.releaseListener();
        }

    }




}
