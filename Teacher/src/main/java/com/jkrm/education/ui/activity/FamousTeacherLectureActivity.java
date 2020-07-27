package com.jkrm.education.ui.activity;

import android.content.res.Configuration;
import android.view.View;
import android.widget.RelativeLayout;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.MyDateUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.VideoResultBean;
import com.jkrm.education.constants.Extras;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import butterknife.BindView;

/**
 * Created by hzw on 2019/5/14.
 */

public class FamousTeacherLectureActivity extends AwMvpActivity {

    @BindView(R.id.ic_noData)
    RelativeLayout mIcNoData;

    private StandardGSYVideoPlayer videoPlayer;
    private OrientationUtils orientationUtils;
    private boolean isPlay;
    private boolean isPause;

    private VideoResultBean mBean;

    @Override
    protected AwCommonPresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_famous_teacher_lecture;
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
        mBean = (VideoResultBean) getIntent().getSerializableExtra(Extras.KEY_BEAN_VIDEO_RESULT);
        if(mBean == null || mBean.getQuestionVideo() == null || AwDataUtil.isEmpty(mBean.getQuestionVideo().getAccessUrl())) {
            showView(mIcNoData, true);
            return;
        }
        showView(mIcNoData, false);
        initVideo(mBean.getQuestionVideo().getAccessUrl(), MyDateUtil.getFileName(mBean.getQuestionVideo().getFilename()));
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
//                        showFull();
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
