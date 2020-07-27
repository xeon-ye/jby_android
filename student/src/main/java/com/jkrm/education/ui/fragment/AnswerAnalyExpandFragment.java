package com.jkrm.education.ui.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.AnswerAnalyExpendAdapter;
import com.jkrm.education.bean.BatchBean;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.AnswerAnalyExpandPresent;
import com.jkrm.education.mvp.views.AnswerAnalyExpandView;
import com.jkrm.education.ui.activity.ErrorQuestionActivity;
import com.jkrm.education.ui.activity.OnlineAnswerActivity;
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

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/11 14:00
 */

public class AnswerAnalyExpandFragment extends AwMvpFragment<AnswerAnalyExpandPresent> implements AnswerAnalyExpandView.View {

    @BindView(R.id.video_player)
    StandardGSYVideoPlayer mVideoPlayer;
    @BindView(R.id.tv_num)
    TextView mTvNum;
    @BindView(R.id.rcv_child_data)
    RecyclerView mRcvChildData;
    @BindView(R.id.btn_start)
    Button mBtnStart;
    Unbinder unbinder;
    @BindView(R.id.ll_of_video)
    LinearLayout mLlOfVideo;
    @BindView(R.id.ll_of_expand)
    LinearLayout mLlOfExpand;
    Unbinder unbinder1;
    private String mVideoUrl;
    private String mQuestionID;
    List<SimilarResultBean> mList = new ArrayList<>();
    AnswerAnalyExpendAdapter mAnswerAnalyExpendAdapter;
    private OrientationUtils orientationUtils;
    private boolean isPlay;
    private boolean isPause;
    private BatchBean mBatchBean;


    @Override
    protected int getLayoutId() {
        return R.layout.include_analytic_expansion_layout;
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView() {
        super.initView();
        mBatchBean = (BatchBean) getArguments().getSerializable(Extras.BATCHBEAN);

        showView(mLlOfVideo,"1".equals(mBatchBean.getIsNoVideo()));
        if(!AwDataUtil.isEmpty(mBatchBean.getQuestionVideo())){
            mLlOfVideo.setVisibility(View.VISIBLE);
            initVideo(mBatchBean.getQuestionVideo(), "");
        }
        mAnswerAnalyExpendAdapter = new AnswerAnalyExpendAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(getActivity(), mRcvChildData, mAnswerAnalyExpendAdapter, false);
        mPresenter.getSimilar(mBatchBean.getQuestionId());

    }

    @Override
    protected void initData() {
        super.initData();
        if (!AwDataUtil.isEmpty(mQuestionID)) {
            mPresenter.getSimilar(mQuestionID);
        }
    }

    @Override
    protected AnswerAnalyExpandPresent createPresenter() {
        return new AnswerAnalyExpandPresent(this);
    }


    @OnClick(R.id.btn_start)
    public void onViewClicked() {
        if (!AwDataUtil.isEmpty(mList)) {
            toClass(OnlineAnswerActivity.class, true, Extras.KEY_SIMILAR_LIST, (Serializable) mList, Extras.COURSE_ID, ErrorQuestionActivity.mCourseId);
        }
    }

    @Override
    public void getSimilarSuccess(List<SimilarResultBean> result) {
        if (!AwDataUtil.isEmpty(result)) {
            mList.addAll(result);
            mAnswerAnalyExpendAdapter.addAllData(mList);
            mTvNum.setText("类题加练(共" + mList.size() + "题)");
        }
    }

    @Override
    public void getSimilarFail(String msg) {
        mAnswerAnalyExpendAdapter.clearData();
        mRcvChildData.removeAllViews();
        mAnswerAnalyExpendAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(getActivity(), MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BatchBean batchBean) {
        System.out.println("BatchBean" + batchBean.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }
}
