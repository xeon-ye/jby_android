package com.jkrm.education.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.hzw.baselib.widgets.SuperExpandableListView;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.CoursePlayResultBean;
import com.jkrm.education.bean.result.MicroLessonResultBean;
import com.jkrm.education.bean.rx.RxCostomDownType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.UrlConstant;
import com.jkrm.education.db.DaoCatalogueBean;
import com.jkrm.education.db.DaoMicroLessonBean;
import com.jkrm.education.db.DaoVideoBean;
import com.jkrm.education.db.util.DaoUtil;
import com.jkrm.education.mvp.presenters.CoursePurchasedPresent;
import com.jkrm.education.mvp.views.CoursePurchasedView;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.ShareSDKUtils;
import com.jkrm.education.widgets.CourseDialogFramgment;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.IndicatorView;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.kexanie.library.MathView;

/**
 * 已购课程
 */
public class CoursePurchasedActivity extends AwMvpActivity<CoursePurchasedPresent> implements CoursePurchasedView.View ,CourseDialogFramgment.ConfirmListener{


    @BindView(R.id.start_now)
    TextView mStartNow;
    @BindView(R.id.toolbar_custom)
    AwViewCustomToolbar mToolbarCustom;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.iv_img)
    ImageView mIvImg;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.epv)
    SuperExpandableListView mEpv;
    @BindView(R.id.nsv_mulv)
    NestedScrollView mNsvMulv;
    @BindView(R.id.iv_intro)
    ImageView mIvIntro;
    @BindView(R.id.tv_des)
    TextView mTvDes;
    @BindView(R.id.nsv_jieshao)
    NestedScrollView mNsvJieshao;
    @BindView(R.id.viewpageer)
    ViewPager mViewpageer;
    @BindView(R.id.math_view)
    MathView mMathView;

    private MicroLessonResultBean mMicroLessonResultBean;
    private List<CoursePlayResultBean> mGroupValues = new ArrayList<>();
    private List<List<CoursePlayResultBean.VideoListBean>> mChildValues = new ArrayList<>();
    private CoursePlayAdapter mCoursePlayAdapter;

    @Override
    protected CoursePurchasedPresent createPresenter() {
        return new CoursePurchasedPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_course_purchased;
    }

    @Override
    protected void initView() {
        super.initView();
        mMicroLessonResultBean = (MicroLessonResultBean) getIntent().getSerializableExtra(Extras.KEY_COURSE_BEAN);
        setToolbarWithBackImg(mMicroLessonResultBean.getTypeName(), new AwViewCustomToolbar.OnLeftClickListener() {
            @Override
            public void onLeftTextClick() {
                // toClass(ConfirmAnOrderActivity.class,false);
                finish();
            }
        });
        mToolbar.showRightView();
        mToolbar.setOnRightClickListener(new AwViewCustomToolbar.OnRightClickListener() {
            @Override
            public void onRightTextClick() {
                showMsg("此功能正紧锣密鼓开发中");
                //share();
            }
        });
        mToolbar.setRightImg(R.mipmap.share);
        mTabLayout.addTab(mTabLayout.newTab().setText("课程目录"));
        mTabLayout.addTab(mTabLayout.newTab().setText("课程介绍"));
        mTabLayout.setupWithViewPager(mViewpageer);


    }


    private void share() {
        new ShareAction(mActivity).setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        if (share_media == SHARE_MEDIA.WEIXIN) {
                            ShareSDKUtils.shareWX(mActivity, UrlConstant.COURSE_SHARE_URL, "金榜苑", "微课视频");
                        } else if (share_media == SHARE_MEDIA.QQ) {
                            ShareSDKUtils.shareQQ(mActivity, UrlConstant.COURSE_SHARE_URL, "金榜苑", "微课视频");
                        } else if (share_media == SHARE_MEDIA.WEIXIN_CIRCLE) {
                            ShareSDKUtils.shareWXC(mActivity, UrlConstant.COURSE_SHARE_URL, "金榜苑", "微课视频");
                        } else if (share_media == SHARE_MEDIA.QZONE) {
                            ShareSDKUtils.shareQQZone(mActivity, UrlConstant.COURSE_SHARE_URL, "金榜苑", "微课视频");
                        }
                    }
                }).open();
    }

    @Override
    protected void initData() {
        super.initData();
        Glide.with(mActivity).load(mMicroLessonResultBean.getMlessonUrl()).into(mIvImg);
        Glide.with(mActivity).load(mMicroLessonResultBean.getMlessonUrl()).into(mIvIntro);
        mTvDes.setText(mMicroLessonResultBean.getMlessonExplain());
        mMathView.setText(mMicroLessonResultBean.getMlessonExplain());
        mPresenter.getCoursePlayList(RequestUtil.getCoursePlayListBody(mMicroLessonResultBean.getId(), mMicroLessonResultBean.getPcvId()));
    }

    @Override
    public void getCoursePlayListSuccess(List<CoursePlayResultBean> list) {
        if (list.isEmpty()) {
            return;
        }
        mGroupValues = list;
        for (int i = 0; i < list.size(); i++) {
            List<CoursePlayResultBean.VideoListBean> videoList = list.get(i).getVideoList();
            mChildValues.add(videoList);
        }
        mCoursePlayAdapter = new CoursePlayAdapter(mGroupValues, mChildValues);
        mEpv.setAdapter(mCoursePlayAdapter);
        for (int i = 0; i < mCoursePlayAdapter.getGroupCount(); i++) {
            mEpv.expandGroup(i);
        }
    }

    @Override
    public void getCoursePlayListFail(String msg) {
        showMsg(msg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.start_now, R.id.iv_img,R.id.iv_down})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start_now:
                //toClass(CourseBroadcastActivity.class, false, Extras.KEY_COURSE_LIST, (Serializable) mGroupValues, Extras.KEY_COURSE_BEAN, mMicroLessonResultBean);
                toClass(CourseBroadcastActivity.class, false, Extras.KEY_COURSE_LIST, (Serializable) mGroupValues, Extras.KEY_COURSE_BEAN, mMicroLessonResultBean, Extras.VIDEO_GROUP_PRO, 0, Extras.VIDEO_CHILD_PRO, 0);

                break;
            case R.id.iv_img:
                // toClass(CourseBroadcastActivity.class, false, Extras.KEY_COURSE_LIST, (Serializable) mGroupValues, Extras.KEY_COURSE_BEAN, mMicroLessonResultBean);
                toClass(CourseBroadcastActivity.class, false, Extras.KEY_COURSE_LIST, (Serializable) mGroupValues, Extras.KEY_COURSE_BEAN, mMicroLessonResultBean, Extras.VIDEO_GROUP_PRO, 0, Extras.VIDEO_CHILD_PRO, 0);

                break;
            case R.id.iv_down:
                //点击下载弹出下载框
                CourseDialogFramgment courseDialogFramgment = new CourseDialogFramgment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Extras.KEY_COURSE_LIST, (Serializable) mGroupValues);
                courseDialogFramgment.setArguments(bundle);
                courseDialogFramgment.show(getSupportFragmentManager(), "");
                courseDialogFramgment.setConfirmListener(new CourseDialogFramgment.ConfirmListener() {
                    @Override
                    public void onClickComplete(List<CoursePlayResultBean.VideoListBean> mChildValues) {

                    }
                });
                break;
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (0 == tab.getPosition()) {
                    mNsvMulv.setVisibility(View.VISIBLE);
                    mNsvJieshao.setVisibility(View.GONE);
                } else {
                    mNsvMulv.setVisibility(View.GONE);
                    mNsvJieshao.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mEpv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                toClass(CourseBroadcastActivity.class, false, Extras.KEY_COURSE_LIST, (Serializable) mGroupValues, Extras.KEY_COURSE_BEAN, mMicroLessonResultBean, Extras.VIDEO_GROUP_PRO, i, Extras.VIDEO_CHILD_PRO, i1);

                //toClass(CourseBroadcastActivity.class, false, Extras.KEY_COURSE_LIST, (Serializable) mGroupValues, Extras.KEY_COURSE_BEAN, mMicroLessonResultBean);
                return true;
            }
        });
        mIvImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //toClass(CourseBroadcastActivity.class, false, Extras.KEY_COURSE_LIST, (Serializable) mGroupValues, Extras.KEY_COURSE_BEAN, mMicroLessonResultBean);
            }
        });

    }

    @Override
    public void onClickComplete(List<CoursePlayResultBean.VideoListBean> mChildValues) {
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
        for (CoursePlayResultBean.VideoListBean videoListBean : mChildValues) {
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
            view = View.inflate(CoursePurchasedActivity.this, R.layout.couse_cache_dialog_group_item_layout, null);
            TextView textView = view.findViewById(R.id.tv_title);
            textView.setText(mGroupValues.get(i).getTitle());
            return view;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            view = View.inflate(CoursePurchasedActivity.this, R.layout.course_child_item_layout, null);
            TextView tvName = view.findViewById(R.id.tv_name);
            tvName.setText(mChildValues.get(i).get(i1).getName());
            TextView tvShow = view.findViewById(R.id.tv_show);
            //.setText(mChildValues.get(i).get(i1).getTimes());
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
}
