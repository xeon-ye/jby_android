package com.jkrm.education.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.widgets.SuperExpandableListView;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.CoursePlayResultBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.db.DaoCatalogueBean;
import com.jkrm.education.db.DaoMicroLessonBean;
import com.jkrm.education.db.DaoVideoBean;
import com.jkrm.education.db.util.DaoUtil;
import com.jkrm.education.mvp.presenters.StudyCoursePresent;
import com.jkrm.education.mvp.views.StudycourseView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudyCourseActivity extends AwMvpActivity<StudyCoursePresent> implements StudycourseView.View {
    String MICROLESS_ID = "";
    @BindView(R.id.tv_course_title)
    TextView tvCourseTitle;
    @BindView(R.id.tv_course_num)
    TextView tvCourseNum;
    @BindView(R.id.iv_down)
    ImageView ivDown;
    @BindView(R.id.epv)
    SuperExpandableListView epv;
    @BindView(R.id.video_player)
    StandardGSYVideoPlayer mVideoPlayer;
    private List<List<DaoVideoBean>> mDaoVideoBeans = new ArrayList<>();
    private List<DaoCatalogueBean> mDaoCatalogueBeans=new ArrayList<>();
    private CoursePlayAdapter mCoursePlayAdapter;

    private OrientationUtils orientationUtils;
    private boolean isPlay;
    private boolean isPause;
    private int coure_num=0;
    private String mFileName;

    @Override
    protected StudyCoursePresent createPresenter() {
        return new StudyCoursePresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_study_course;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();

    }

    @Override
    protected void initData() {
        super.initData();
        MICROLESS_ID = getIntent().getStringExtra(Extras.MICROLESS_ID);
        mFileName = getIntent().getStringExtra(Extras.FILE_NAME);
        DaoMicroLessonBean daoMicroLessonBean = DaoUtil.getInstance().queryMicro(MICROLESS_ID);
        mDaoCatalogueBeans = DaoUtil.getInstance().queryCatalogueListByQueryBuilder(MICROLESS_ID);
        //根据课程 目录 查找对应视频
        for (DaoCatalogueBean daoCatalogueBean : mDaoCatalogueBeans) {
            List<DaoVideoBean> daoVideoBeans = DaoUtil.getInstance().queryVideoListByQueryBuilder(daoCatalogueBean.getId());
            mDaoVideoBeans.add(daoVideoBeans);
            coure_num+=daoVideoBeans.size();
        }
        mCoursePlayAdapter=new CoursePlayAdapter(mDaoVideoBeans,mDaoCatalogueBeans);
        epv.setAdapter(mCoursePlayAdapter);
        for (int i = 0; i < mCoursePlayAdapter.getGroupCount(); i++) {
            epv.expandGroup(i);
        }
        if(null!=mDaoCatalogueBeans&&null!=mDaoCatalogueBeans.get(0)){
            initVideo(mFileName,"");
        }
        tvCourseTitle.setText(daoMicroLessonBean.getMlessonName());
        tvCourseNum.setText("共"+coure_num+"节");

    }

    @Override
    protected void initListener() {
        super.initListener();
        epv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
               initVideo(mDaoVideoBeans.get(i).get(i1).getFilePath(),"");
                return true;
            }
        });
    }

    class CoursePlayAdapter extends BaseExpandableListAdapter {

        private List<List<DaoVideoBean>> mChildValues = new ArrayList<>();
        private List<DaoCatalogueBean> mGroupValues=new ArrayList<>();

        public CoursePlayAdapter(List<List<DaoVideoBean>> mChildValues, List<DaoCatalogueBean> mGroupValues) {
            this.mChildValues = mChildValues;
            this.mGroupValues = mGroupValues;
        }

        @Override
        public int getGroupCount() {
            return mGroupValues.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return mChildValues.get(i).size();
        }

        @Override
        public Object getGroup(int i) {
            return null;
        }

        @Override
        public Object getChild(int i, int i1) {
            return null;
        }

        @Override
        public long getGroupId(int i) {
            return 0;
        }

        @Override
        public long getChildId(int i, int i1) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }


        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            view = View.inflate(StudyCourseActivity.this,R.layout.couse_cache_dialog_group_item_layout, null);
            TextView textView = view.findViewById(R.id.tv_title);
            textView.setText(mGroupValues.get(i).getTitle());
            return view;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            view = View.inflate(StudyCourseActivity.this, R.layout.course_child_item_layout, null);
            TextView tvName = view.findViewById(R.id.tv_name);
            TextView tv_time = view.findViewById(R.id.tv_time);
            tvName.setText(mChildValues.get(i).get(i1).getName());
            TextView tvShow = view.findViewById(R.id.tv_show);
           // tvShow.setText(mChildValues.get(i).get(i1).getTimes());
            String times = mChildValues.get(i).get(i1).getTimes();
            String[] split = times.split(":");
            tv_time.setText(split[0]+"分");
            return view;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }

    }

    private void initVideo(String url, String title) {
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, mVideoPlayer);
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
        mVideoPlayer.getBackButton().setVisibility(View.VISIBLE);
        //设置返回按键功能
        mVideoPlayer.getBackButton().setOnClickListener(v -> finish());
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
    protected void onPause() {
        super.onPause();
        mVideoPlayer.getCurrentPlayer().onVideoPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVideoPlayer.getCurrentPlayer().onVideoResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isPlay) {
            if (null != mVideoPlayer) {
                mVideoPlayer.getCurrentPlayer().release();
            }
        }
        if (orientationUtils != null)
            orientationUtils.releaseListener();
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
