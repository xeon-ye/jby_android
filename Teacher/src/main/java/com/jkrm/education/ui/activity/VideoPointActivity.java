package com.jkrm.education.ui.activity;

import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.MyDateUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.mark.MarkHomeworkFamousTeacherAdapter;
import com.jkrm.education.adapter.mark.VideoPointAdapter;
import com.jkrm.education.bean.result.VideoPointResultBean;
import com.jkrm.education.bean.result.VideoResultBean;
import com.jkrm.education.bean.result.VideoResultBean.CataVideosBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.zzhoujay.richtext.RichText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 对点微课
 * Created by hzw on 2019/5/14.
 */

public class VideoPointActivity extends AwMvpActivity {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;

    private VideoPointAdapter mAdapter;
    private StandardGSYVideoPlayer videoPlayer;
    private OrientationUtils orientationUtils;
    private boolean isPlay;
    private boolean isPause;
    /**
     * 对点微课列表
     */
    private List<VideoPointResultBean> mVideoPointResultBeanList = new ArrayList<>();

    @Override
    protected AwCommonPresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_point;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusBlack();
        videoPlayer =  (StandardGSYVideoPlayer)findViewById(R.id.video_player);
    }

    @Override
    protected void initData() {
        super.initData();
        mAdapter = new VideoPointAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mAdapter, false);
        mRcvData.setFocusable(false);

        mVideoPointResultBeanList = (List<VideoPointResultBean>) getIntent().getSerializableExtra(Extras.KEY_BEAN_VIDEO_RESULT);
        if(AwDataUtil.isEmpty(mVideoPointResultBeanList)) {
            showDialogToFinish(getString(R.string.hint_no_video_point));
            mAdapter.clearData();
            mRcvData.removeAllViews();
            mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
            return;
        }
        mAdapter.addAllData(mVideoPointResultBeanList);
        mAdapter.loadMoreComplete();
        mAdapter.setEnableLoadMore(false);
        mAdapter.disableLoadMoreIfNotFullPage(mRcvData);

        initVideo(mVideoPointResultBeanList.get(0).getUrl(), MyDateUtil.getFileName(mVideoPointResultBeanList.get(0).getName()));
        setText(mTvTitle, MyDateUtil.getFileName(mVideoPointResultBeanList.get(0).getName()));
        if(!AwDataUtil.isEmpty(mVideoPointResultBeanList.get(0).getBrief())) {
            RichText.from("").bind(mActivity).into(mTvDesc);
            RichText.from(mVideoPointResultBeanList.get(0).getBrief()).bind(mActivity).into(mTvDesc);
        } else {
            setText(mTvDesc, "暂无简介");
        }


    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            VideoPointResultBean bean = (VideoPointResultBean) adapter.getData().get(position);
            AwLog.d("点击的微课名称是: " + MyDateUtil.getFileName(bean.getName()));
            initVideo(bean.getUrl(), MyDateUtil.getFileName(bean.getName()));
            setText(mTvTitle, MyDateUtil.getFileName(bean.getName()));

            if(!AwDataUtil.isEmpty(bean.getBrief())) {
                RichText.from("").bind(mActivity).into(mTvDesc);
                RichText.from(bean.getBrief()).bind(mActivity).into(mTvDesc);
            } else {
                setText(mTvDesc, "暂无简介");
            }
        });
    }

    private void initVideo(String url, String title) {
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, videoPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        if (videoPlayer.getFullscreenButton() != null) {
            videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showFull();
                }
            });
        }
        //增加封面
        //        ImageView imageView = new ImageView(this);
        //        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //        MyDateUtil.setThumbnailToImageView(url, imageView);
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
                .setStartAfterPrepared(true)//设置不默认播放, 需点击后播放
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
                            setStatusBlack();
                            orientationUtils.backToProtVideo();
                        }
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        setStatusTransparent();
                        super.onEnterFullscreen(url, objects);
                    }
                })
                .build(videoPlayer);

        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.GONE);
        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        //设置返回按键功能
        videoPlayer.getBackButton().setOnClickListener(v -> finish());
        videoPlayer.startPlayLogic();
    }

    public void showFull() {
        if (orientationUtils.getIsLand() != 1) {
            //直接横屏
            orientationUtils.resolveByClick();
        }
        //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
        videoPlayer.startWindowFullscreen(mActivity, true, true);

    }

    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.getCurrentPlayer().onVideoPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.getCurrentPlayer().onVideoResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isPlay) {
            videoPlayer.getCurrentPlayer().release();
        }
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (isPlay && !isPause) {
            videoPlayer.onConfigurationChanged(mActivity, newConfig, orientationUtils, true, true);
        }
    }

    @Override
    public void onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }
}
