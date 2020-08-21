package com.jkrm.education.ui.activity;

import android.content.res.Configuration;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.widgets.SuperExpandableListView;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.CoursePlayResultBean;
import com.jkrm.education.bean.result.MicroLessonResultBean;
import com.jkrm.education.bean.result.WatchLogBean;
import com.jkrm.education.bean.result.WatchResultBean;
import com.jkrm.education.bean.rx.RxCostomDownType;
import com.jkrm.education.bean.rx.RxDownCourseType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.db.DaoCatalogueBean;
import com.jkrm.education.db.DaoMicroLessonBean;
import com.jkrm.education.db.DaoVideoBean;
import com.jkrm.education.db.util.DaoUtil;
import com.jkrm.education.mvp.presenters.CourseBroadcastPresent;
import com.jkrm.education.mvp.views.CourseBroadcastView;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.widgets.CourseDialogFramgment;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.GSYVideoProgressListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import org.greenrobot.eventbus.EventBus;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CourseBroadcastActivity extends AwMvpActivity<CourseBroadcastPresent> implements CourseBroadcastView.View, CourseDialogFramgment.ConfirmListener {

    @BindView(R.id.video_player)
    StandardGSYVideoPlayer mVideoPlayer;
    @BindView(R.id.tv_course_title)
    TextView mTvCourseTitle;
    @BindView(R.id.tv_course_num)
    TextView mTvCourseNum;
    @BindView(R.id.iv_down)
    ImageView mIvDown;
    @BindView(R.id.epv)
    SuperExpandableListView mEpv;

    private OrientationUtils orientationUtils;
    private boolean isPlay;
    private boolean isPause;
    private MicroLessonResultBean mMicroLessonResultBean;
    private List<CoursePlayResultBean> mGroupValues = new ArrayList<>();
    private List<List<CoursePlayResultBean.VideoListBean>> mChildValues = new ArrayList<>();
    private CoursePlayAdapter mCoursePlayAdapter;
    private String mWhetherBuy;//  1 购买  0 未购买
    private String mStrWatchState = "0";//是否观看完毕  0 未完成  1 完成
    private Timer mTimer;//计时器
    private String mStrVideoId;//视频id
    private int mWatchTime = 0;//观看时长
    private int mVideoTime = 0;//视频进度
    private int mTotalTime = 0;//视频总时长
    private int mGroupPos = 0;
    private int mChildPos = 0;
    private List<CoursePlayResultBean.VideoListBean> mPlayList = new ArrayList<>();
    private String mStrUnFinishedVideoTime = "", mStrUnFinishedVideoId = "";


    @Override
    protected CourseBroadcastPresent createPresenter() {
        return new CourseBroadcastPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_course_broadcast;
    }


    @OnClick(R.id.iv_down)
    public void onViewClicked() {
      /*  if ("0".equals(mWhetherBuy)) {
            showMsg("您暂未购买课程，不能下载");
            return;
        }*/
        //点击下载弹出下载框
        CourseDialogFramgment courseDialogFramgment = new CourseDialogFramgment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Extras.KEY_COURSE_LIST, (Serializable) mGroupValues);
        courseDialogFramgment.setArguments(bundle);
        courseDialogFramgment.show(getSupportFragmentManager(), "");

    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        mGroupValues = (List<CoursePlayResultBean>) getIntent().getSerializableExtra(Extras.KEY_COURSE_LIST);
        for (int i = 0; i < mGroupValues.size(); i++) {
            mChildValues.add(mGroupValues.get(i).getVideoList());
            mPlayList.addAll(mGroupValues.get(i).getVideoList());
        }
        mMicroLessonResultBean = (MicroLessonResultBean) getIntent().getSerializableExtra(Extras.KEY_COURSE_BEAN);
        mWhetherBuy = mMicroLessonResultBean.getWhetherBuy();//  1 购买  0 未购买
        mTvCourseTitle.setText(mMicroLessonResultBean.getMlessonName());
        mTvCourseNum.setText("共" + mMicroLessonResultBean.getMlessonCount() + "个课时");
        mCoursePlayAdapter = new CoursePlayAdapter(mGroupValues, mChildValues);
        mEpv.setAdapter(mCoursePlayAdapter);
        for (int i = 0; i < mCoursePlayAdapter.getGroupCount(); i++) {
            mEpv.expandGroup(i);
        }

    }

    @Override
    protected void initData() {
        super.initData();
        mGroupPos = getIntent().getIntExtra(Extras.VIDEO_GROUP_PRO, 0);
        mChildPos = getIntent().getIntExtra(Extras.VIDEO_CHILD_PRO, 0);
        if (null != mGroupValues && mGroupValues.size() > 0 && null != mGroupValues.get(0)) {
            initVideo(mGroupValues.get(mGroupPos).getVideoList().get(mChildPos).getUrl(), mChildValues.get(mGroupPos).get(mChildPos).getVideoId());
        }
        mPresenter.getWatchList(RequestUtil.getWatchListBody(mMicroLessonResultBean.getId()));
    }

    @Override
    protected void initListener() {
        super.initListener();
        mEpv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                saveWatchLog();
                //1 已购买 所有视频都可观看  校园通  vip
                if ("1".equals(mWhetherBuy) || "1".equals(mMicroLessonResultBean.getWhetherBuySch()) || "1".equals(mMicroLessonResultBean.getWhetherVip())) {
                    mGroupPos = i;
                    mChildPos = i1;
                    initVideo(mChildValues.get(mGroupPos).get(mChildPos).getUrl(), mChildValues.get(mGroupPos).get(mChildPos).getVideoId());
                } else if ("0".equals(mWhetherBuy)) {
                   /* // 0 未购买  只能观看每个目录下第一个视频
                    if (i1 == 0) {
                        mGroupPos=i;
                        mChildPos=i1;
                        initVideo(mChildValues.get(mGroupPos).get(mChildPos).getUrl(), mChildValues.get(mGroupPos).get(mChildPos).getVideoId());
                    } else {
                        showMsg("您暂未购买课程，只能观看试看视频");
                    }*/
                    CoursePlayResultBean.VideoListBean videoListBean = mChildValues.get(i).get(i1);
                    if ("0".equals(videoListBean.getWhetherFree())) {
                        mGroupPos = i;
                        mChildPos = i1;
                        initVideo(mChildValues.get(mGroupPos).get(mChildPos).getUrl(), mChildValues.get(mGroupPos).get(mChildPos).getVideoId());

                    } else {
                        showMsg("您暂未购买课程，只能观看试看视频");

                    }
                }
                return true;
            }
        });

    }

    @Override
    public void onClickComplete(List<CoursePlayResultBean.VideoListBean> videoList) {
        ArrayList<DaoVideoBean> daoVideoBeanArrayList = new ArrayList<>();
        //数据库记录课程信息
        Gson microGson = new Gson();
        DaoMicroLessonBean daoMicroLessonBean = microGson.fromJson(microGson.toJson(mMicroLessonResultBean), DaoMicroLessonBean.class);
        DaoUtil.getInstance().insertMicro(daoMicroLessonBean);//插入数据
        for (CoursePlayResultBean groupValue : mGroupValues) {
            Gson courseGson = new Gson();
            DaoCatalogueBean daoCatalogueBean = courseGson.fromJson(courseGson.toJson(groupValue), DaoCatalogueBean.class);
            DaoUtil.getInstance().insertCatalogue(daoCatalogueBean);
        }
        for (CoursePlayResultBean.VideoListBean videoListBean : videoList) {
            Gson daoVideoGson = new Gson();
            DaoVideoBean daoVideoBean = daoVideoGson.fromJson(daoVideoGson.toJson(videoListBean), DaoVideoBean.class);
            daoVideoBean.setFileName("JBY" + videoListBean.getId() + videoListBean.getUrl().substring(videoListBean.getUrl().lastIndexOf("/")).replace("/", "").trim());
            daoVideoBean.setFilePath(Extras.FILE_PATH + "/" + daoVideoBean.getFileName());
            DaoUtil.getInstance().insertVideoBean(daoVideoBean);
            //数据库查询是否下载过
            DaoVideoBean daoVideoBean1 = DaoUtil.getInstance().queryVideoByUrl(daoVideoBean);
            if (null != daoVideoBean1) {
                daoVideoBeanArrayList.add(daoVideoBean);
            }
        }
       /* ArrayList<String> downUrlList = new ArrayList<>();
        for (CoursePlayResultBean.VideoListBean videoListBean : videoList) {
            downUrlList.add(videoListBean.getUrl());
        }*/
        EventBus.getDefault().post(new RxCostomDownType(daoVideoBeanArrayList));
        toClass(CourseCacheNewActivity.class, false);
    }

    private void saveWatchLog() {
        if (null != mTimer) {
            mTimer.cancel();
        }
        mVideoTime = mVideoPlayer.getCurrentPlayer().getCurrentPositionWhenPlaying();//获取当前播放进度
        mTotalTime = mVideoPlayer.getCurrentPlayer().getDuration();//获取总时长
        mPresenter.saveWatchLog(RequestUtil.getSaveWatchLogBody(mMicroLessonResultBean.getId(), mMicroLessonResultBean.getMlessonName(), mMicroLessonResultBean.getMlessonUrl(), mStrVideoId, mWatchTime + "", mVideoTime + "", mTotalTime + "", mStrWatchState));
    }

    @Override
    public void onSaveWatchLogSuccess(WatchLogBean request) {
        mVideoTime = 0;
        mTotalTime = 0;
        mWatchTime = 0;
        mStrWatchState = "0";
    }

    @Override
    public void onSaveWatchLogFail(String msg) {
        showMsg(msg);
        mStrWatchState = "0";
    }

    @Override
    public void getWatchListSuccess(WatchResultBean resultBean) {
        //已播完集合
        for (WatchResultBean.FinishedListBean finishedListBean : resultBean.getFinishedList()) {
            for (CoursePlayResultBean groupValue : mGroupValues) {
                for (CoursePlayResultBean.VideoListBean videoListBean : groupValue.getVideoList()) {
                    if (videoListBean.getVideoId().equals(finishedListBean.getVideoId())) {
                        videoListBean.setWatchStatus("1");
                    }
                }
            }
        }
        mCoursePlayAdapter.notifyDataSetChanged();
        //未播完集合中
        if (null != resultBean.getUnfinishedList() && !resultBean.getUnfinishedList().isEmpty() && null != resultBean.getUnfinishedList().get(0)) {
            WatchResultBean.UnfinishedListBean unfinishedListBean = resultBean.getUnfinishedList().get(0);
            //上次播放记录
            for (int i = 0; i < mChildValues.size(); i++) {
                for (int j = 0; j < mChildValues.get(i).size(); j++) {
                    if (unfinishedListBean.getVideoId().equals(mChildValues.get(i).get(j).getVideoId())) {
                        mStrUnFinishedVideoTime = unfinishedListBean.getVideoTime();
                        mStrUnFinishedVideoId = unfinishedListBean.getVideoId();
                        mGroupPos = i;
                        mChildPos = j;
                        initVideo(mChildValues.get(mGroupPos).get(mChildPos).getUrl(), mChildValues.get(mGroupPos).get(mChildPos).getVideoId());
                        break;
                    }
                }
            }

        }
    }

    @Override
    public void getWatchListFail(String msg) {
        showMsg(msg);
    }


    class CoursePlayAdapter extends BaseExpandableListAdapter {

        private List<CoursePlayResultBean> mGroupValues;
        private List<List<CoursePlayResultBean.VideoListBean>> mChildValues;

        public CoursePlayAdapter(List<CoursePlayResultBean> groupValues, List<List<CoursePlayResultBean.VideoListBean>> childValues) {
            mGroupValues = groupValues;
            mChildValues = childValues;
        }

        @Override
        public int getGroupCount() {
            return mGroupValues.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return mGroupValues.get(i).getVideoList().size();
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
            view = View.inflate(CourseBroadcastActivity.this, R.layout.couse_cache_dialog_group_item_layout, null);
            TextView textView = view.findViewById(R.id.tv_title);
            textView.setText(mGroupValues.get(i).getTitle());
            return view;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            view = View.inflate(CourseBroadcastActivity.this, R.layout.course_child_item_layout, null);
            CoursePlayResultBean.VideoListBean videoListBean = mChildValues.get(i).get(i1);
            TextView tvName = view.findViewById(R.id.tv_name);
            tvName.setText(mChildValues.get(i).get(i1).getName());
            TextView tvShow = view.findViewById(R.id.tv_show);
            ImageView iv_lock = view.findViewById(R.id.iv_lock);

            // 已购买  vip   校园通 都可以观看
           /*if ("1".equals(mWhetherBuy)||"1".equals(mMicroLessonResultBean.getWhetherVip())||"1".equals(mMicroLessonResultBean.getWhetherBuySch())) {
                tvShow.setText(mChildValues.get(i).get(i1).getTimes());
                // tvShow.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            } else {
                if (i1 != 0) {
                    tvShow.setVisibility(View.GONE);
                }
            }*/
            if ("1".equals(videoListBean.getWatchStatus())) {
                tvShow.setText("已学完");
                // tvShow.setTextColor(getResources().getColor(R.color.color_C8CCD4));
            }

            if (videoListBean.isPlaying()) {
                tvShow.setText("播放中");
                // tvShow.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
            //已购买
            if ("1".equals(mChildValues.get(i).get(i1).getWhetherBuy())) {
                tvShow.setVisibility(View.GONE);
                iv_lock.setVisibility(View.GONE);
            } else {
                //免费
                if ("0".equals(mChildValues.get(i).get(i1).getWhetherFree())) {
                    tvShow.setText("试看");
                    iv_lock.setVisibility(View.GONE);
                    //不免费
                } else {
                    tvShow.setVisibility(View.GONE);
                    iv_lock.setVisibility(View.VISIBLE);
                }
            }


            TextView tv_time = view.findViewById(R.id.tv_time);
            String times = mChildValues.get(i).get(i1).getTimes();
            tv_time.setText(times);
            return view;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }

    }

    private void initVideo(String url, String videoId) {
        initTimer();
        mStrVideoId = videoId;//视频id
        for (CoursePlayResultBean groupValue : mGroupValues) {
            for (CoursePlayResultBean.VideoListBean videoListBean : groupValue.getVideoList()) {
                if (videoListBean.getVideoId().equals(mStrVideoId)) {
                    videoListBean.setPlaying(true);
                } else {
                    videoListBean.setPlaying(false);
                }
            }
        }
        mCoursePlayAdapter.notifyDataSetChanged();
        mStrWatchState = "0";//播放状态
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

                    @Override
                    public void onAutoComplete(String url, Object... objects) {
                        super.onAutoComplete(url, objects);
                        showMsg("播放完成");
                        mStrWatchState = "1";
                        saveWatchLog();
                        //播放完毕后更新状态
                        for (CoursePlayResultBean groupValue : mGroupValues) {
                            for (CoursePlayResultBean.VideoListBean videoListBean : groupValue.getVideoList()) {
                                if (videoListBean.getVideoId().equals(mStrVideoId)) {
                                    videoListBean.setWatchStatus("1");
                                }
                            }
                        }
                        mCoursePlayAdapter.notifyDataSetChanged();
                        doNext();
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
        //快进至
        if (videoId.equals(mStrVideoId) && !TextUtils.isEmpty(mStrUnFinishedVideoTime)) {
            mVideoPlayer.getCurrentPlayer().setSeekOnStart(Integer.parseInt(mStrUnFinishedVideoTime));
        }
    }

    private void doNext() {
        if ("0".equals(mWhetherBuy) && "0".equals(mMicroLessonResultBean.getWhetherBuySch()) && "0".equals(mMicroLessonResultBean.getWhetherVip())) {
            return;
        }
        mChildPos++;//二级列表位置+1

        if (mChildPos < mChildValues.get(mGroupPos).size()) {
            //子列表播放完成后 进入下目录 播放第一个
        } else {
            mGroupPos++;
            mChildPos = 0;
        }
        //目录播放完毕后  播放第一个
        if (mGroupPos >= mChildValues.size()) {
            mGroupPos = 0;
            mChildPos = 0;
        }
        //此列表下无视频进入下一列表
        if (null == mGroupValues.get(mGroupPos).getVideoList() || mGroupValues.get(mGroupPos).getVideoList().isEmpty()) {
            doNext();
            return;
        }
        initVideo(mChildValues.get(mGroupPos).get(mChildPos).getUrl(), mChildValues.get(mGroupPos).get(mChildPos).getVideoId());
    }

    //初始化定时器
    private void initTimer() {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mWatchTime += 1000;
            }
        }, 0, 1000);
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
        saveWatchLog();
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
