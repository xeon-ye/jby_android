package com.jkrm.education.ui.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.util.AwArraysUtil;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwMathViewUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.AnswerAnalyExpendAdapter;
import com.jkrm.education.adapter.AnswerAnalyImgAdapter;
import com.jkrm.education.adapter.CustomGridAdapter;
import com.jkrm.education.bean.BatchBean;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.bean.result.WatchLogBean;
import com.jkrm.education.bean.rx.RxLastBean;
import com.jkrm.education.bean.rx.RxNextpageType;
import com.jkrm.education.bean.rx.RxReviewStatusType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.AnswerAnalysisPresent;
import com.jkrm.education.mvp.views.AnswerAnalysisView;
import com.jkrm.education.ui.activity.AnswerAnalysisActivity;
import com.jkrm.education.ui.activity.ErrorQuestionActivity;
import com.jkrm.education.ui.activity.ImgActivity;
import com.jkrm.education.ui.activity.OnlineAnswerActivity;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.github.kexanie.library.MathView;

/**
 * @Description: 非组题 主观题
 * @Author: xiangqian
 * @CreateDate: 2020/4/29 16:17
 */

public class AnswerAnalyNonGroupSubjectiveQuestionsFragment extends AwMvpFragment<AnswerAnalysisPresent> implements AnswerAnalysisView.View {


    @BindView(R.id.cb_collect)
    CheckBox mCbCollect;
    @BindView(R.id.btn_next)
    Button mBtnNext;
    @BindView(R.id.math_view_title)
    MathView mMathViewTitle;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.tv_analysis)
    TextView mTvAnalysis;
    @BindView(R.id.math_view_analysis)
    MathView mMathViewAnalysis;
    Unbinder unbinder;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.tv_wrong)
    TextView mTvWrong;
    @BindView(R.id.tv_answer_state)
    TextView mTvAnswerState;
    @BindView(R.id.ll_of_collect)
    LinearLayout mLlofCollec;
    @BindView(R.id.ll_of_analytic_expansion)
    LinearLayout mLlOfAnalyticExpansion;
    @BindView(R.id.video_player)
    StandardGSYVideoPlayer mVideoPlayer;
    @BindView(R.id.ll_of_video)
    LinearLayout mLlOfVideo;
    @BindView(R.id.tv_num)
    TextView mTvNum;
    @BindView(R.id.rcv_child_data)
    RecyclerView mRcvChildData;
    @BindView(R.id.btn_start)
    Button mBtnStart;
    @BindView(R.id.ll_of_expand)
    LinearLayout mLlOfExpand;
    Unbinder unbinder1;
    @BindView(R.id.tv_hint)
    TextView mTvHint;
    @BindView(R.id.ll_of_hint)
    LinearLayout mLlOfHint;
    @BindView(R.id.tv_answer)
    MathView mTvAnswer;
    //是否作答
    private boolean isAnswer;

    private SimilarResultBean mBean;
    private List<SimilarResultBean> mList = new ArrayList<>();
    private List<String> mImgList = new ArrayList<>();
    private CustomGridAdapter mCustomGridAdapter;
    private String mUrl;
    private BatchBean mBatchBean;
    private AnswerAnalyImgAdapter mAnswerAnalyImgAdapter;
    AnswerAnalyExpendAdapter mAnswerAnalyExpendAdapter;

    private OrientationUtils orientationUtils;
    private boolean isPlay;
    private boolean isPause;
    //是否正确
    private boolean isRight;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_answeranaly_nonchoice_layout;
    }

    @Override
    protected void initData() {
        super.initData();
        mBatchBean = (BatchBean) getArguments().getSerializable(Extras.BATCHBEAN);
        if (null == mBatchBean) {
            return;
        }
        if(mBatchBean.isAnswerSituation()){
            showView(mTvHint,false);
            showView(mLlOfHint,false);
        }
        //错题进来的 隐藏下一题  展示类题
        if (!AwDataUtil.isEmpty(mBatchBean.getErrorTypeName())) {
            showView(mLlofCollec, false);
            showView(mLlOfAnalyticExpansion, true);
            showView(mTvHint, false);
            showView(mLlOfHint, false);

        }
        //错题 隐藏下一题
        if (!AwDataUtil.isEmpty(mBatchBean.getErrorTypeName())) {
            showView(mLlofCollec, false);
            showView(mLlOfAnalyticExpansion, true);
            initVideo(mBatchBean.getQuestionVideo(), "");
            if ("0".equals(mBatchBean.getIsNoVideo()) || AwDataUtil.isEmpty(mBatchBean.getQuestionVideo())) {
                showView(mLlOfVideo, false);
            }
            if ("0".equals(mBatchBean.getSimilar())) {
                showView(mLlOfExpand, false);
            } else {
                //有类题 请求数据
                mPresenter.getSimilar(mBatchBean.getQuestionId());
            }
        }
        if (AwDataUtil.isEmpty(mBatchBean.getStudAnswer())) {
            mTvAnswerState.setText("未作答");
            mTvAnswerState.setTextColor(getResources().getColor(R.color.red));
            showView(mTvHint, false);
            showView(mLlOfHint, false);

        }
        //解析
        mMathViewAnalysis.setText(mBatchBean.getAnswerExplanation());
        if (AwDataUtil.isEmpty(mBatchBean.getAnswerExplanation())) {
            showView(mTvAnalysis, false);
        }
        if(!AwDataUtil.isEmpty(mBatchBean.getAnswer())){
            mTvAnswer.setText("答案\n\t"+mBatchBean.getAnswer());
            AwMathViewUtil.setImgScan(mTvAnswer);
        }
        //题干
        mMathViewTitle.setText(mBatchBean.getContent());
        AwMathViewUtil.setImgScan(mMathViewTitle);
        AwMathViewUtil.setImgScan(mMathViewAnalysis);
        //图片
        mAnswerAnalyImgAdapter = new AnswerAnalyImgAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvData, mAnswerAnalyImgAdapter, 3);
        //拆分数据
        if (!AwDataUtil.isEmpty(mBatchBean.getStudAnswer())) {
            ArrayList<String> strings = new ArrayList<>(AwArraysUtil.stringToList(mBatchBean.getStudAnswer()));
            ArrayList<String> emptyList = new ArrayList<>();
            for (String s : strings) {
                if (AwDataUtil.isEmpty(s)) {
                    emptyList.add(s);
                }
            }
            strings.removeAll(emptyList);
            mAnswerAnalyImgAdapter.addAllData(strings);
        }
        //是否可以点击
        if ("1".equals(mBatchBean.getQuestStatus())) {
            //正确
            mTvRight.setSelected(true);
        } else if ("3".equals(mBatchBean.getQuestStatus())) {
            mTvWrong.setSelected(true);
        } else if ("5".equals(mBatchBean.getQuestStatus())) {
            isAnswer = true;
            //可以批阅
        }
        //是否收藏
        mCbCollect.setChecked("1".equals(mBatchBean.getIsNoCollect()));
        mAnswerAnalyExpendAdapter = new AnswerAnalyExpendAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(getActivity(), mRcvChildData, mAnswerAnalyExpendAdapter, false);
    }


    @Override
    protected void initListener() {
        super.initListener();
        if (AnswerAnalysisActivity.mStrLastQueID.equals(mBatchBean.getId())) {
            mBtnNext.setEnabled(false);
            mBtnNext.setText("已是最后一题");
        }
        mAnswerAnalyImgAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<String> imgList = adapter.getData();
                toClass(ImgActivity.class, false, Extras.IMG_URL, imgList.get(position));
            }
        });
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
    }


    @Override
    protected AnswerAnalysisPresent createPresenter() {
        return new AnswerAnalysisPresent(this);
    }


    @Override
    protected void initView() {
        super.initView();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        unbinder1.unbind();
    }


    @OnClick({R.id.tv_right, R.id.tv_wrong, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                if (isAnswer) {
                    mTvRight.setSelected(true);
                    mTvWrong.setSelected(false);
                    mPresenter.readOverQuestion(RequestUtil.getReadOverBody(mBatchBean.getBatchId(), "1", mBatchBean.getQuestionId()));
                    isRight = true;
                }

                break;
            case R.id.tv_wrong:
                if (isAnswer) {
                    mTvWrong.setSelected(true);
                    mTvRight.setSelected(false);
                    mPresenter.readOverQuestion(RequestUtil.getReadOverBody(mBatchBean.getBatchId(), "0", mBatchBean.getQuestionId()));
                    isRight = false;
                }

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
        String id = mBatchBean.getId();
        EventBus.getDefault().post(new RxReviewStatusType(mBatchBean.getId(), isRight));
    }

    @Override
    public void readOverQuestionFail(String msg) {
        showMsg("批阅失败");
    }

    @Override
    public void getSimilarSuccess(List<SimilarResultBean> result) {
        if (!AwDataUtil.isEmpty(result)) {
            mList.addAll(result);
            mAnswerAnalyExpendAdapter.addAllData(mList);
            mTvNum.setText("类题加练(共" + mList.size() + "题)");
        } else {
            mAnswerAnalyExpendAdapter.clearData();
            mRcvChildData.removeAllViews();
            mAnswerAnalyExpendAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(getActivity(), MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));

        }
    }

    @Override
    public void getSimilarFail(String msg) {
        showMsg(msg);
        mAnswerAnalyExpendAdapter.clearData();
        mRcvChildData.removeAllViews();
        mAnswerAnalyExpendAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(getActivity(), MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.btn_start)
    public void onViewClicked() {
        if (!AwDataUtil.isEmpty(mList)) {
            toClass(OnlineAnswerActivity.class, true, Extras.KEY_SIMILAR_LIST, (Serializable) mList, Extras.COURSE_ID, ErrorQuestionActivity.mCourseId);
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
        if (orientationUtils != null) {
            orientationUtils.releaseListener();
        }

    }
}
