package com.jkrm.education.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hzw.baselib.base.AwBaseActivity;
import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.FileUtils;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.CourseCacheChildAdapter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.result.CoursePlayResultBean;
import com.jkrm.education.bean.rx.RxCostomDownType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.db.DaoCatalogueBean;
import com.jkrm.education.db.DaoMicroLessonBean;
import com.jkrm.education.db.DaoVideoBean;
import com.jkrm.education.db.util.DaoUtil;
import com.jkrm.education.download.DownloadLimitManager;
import com.jkrm.education.download.DownloadThreadManager;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.widgets.CourseDialogFramgment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CourseCacheChildActivity extends AwBaseActivity implements CourseDialogFramgment.ConfirmListener {
    @BindView(R.id.toolbar_custom)
    AwViewCustomToolbar mToolbarCustom;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.tv_pause_all)
    TextView mTvPauseAll;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.cb_all)
    CheckBox mCbAll;
    @BindView(R.id.btn_delete)
    Button mBtnDelete;
    @BindView(R.id.ll_of_setting)
    LinearLayout mLlOfSetting;
    @BindView(R.id.rl_down_more)
    RelativeLayout mRlDownMore;
    @BindView(R.id.tv_all)
    TextView mTvAll;
    @BindView(R.id.tv_free_size)
    TextView mTvFreeSize;
    private String MICROLESS_ID, MICROLESS_NAME;
    private CourseCacheChildAdapter mCourseCacheChildAdapter;
    private CopyOnWriteArrayList<DaoVideoBean> mDaoVideoBeans = new CopyOnWriteArrayList<DaoVideoBean>();
    private String MICROLESS_PVC_ID;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_course_cache_child;
    }

    @Override
    protected void initView() {
        super.initView();
        mCourseCacheChildAdapter = new CourseCacheChildAdapter();
        mCourseCacheChildAdapter.setActivity(CourseCacheChildActivity.this);
        mCourseCacheChildAdapter.setTvAll(mTvAll);
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mCourseCacheChildAdapter, false);
        MICROLESS_NAME = getIntent().getStringExtra(Extras.MICROLESS_NAME);
        setToolbarWithBackImgAndRightView(MICROLESS_NAME, "编辑", null);

    }

    @Override
    protected void initData() {
        super.initData();
        MICROLESS_ID = getIntent().getStringExtra(Extras.MICROLESS_ID);
        MICROLESS_PVC_ID = getIntent().getStringExtra(Extras.MICROLESS_PVC_ID);
        List<DaoCatalogueBean> daoCatalogueBeans = DaoUtil.getInstance().queryCatalogueListByQueryBuilder(MICROLESS_ID);
        //根据课程 目录 查找对应视频
        for (DaoCatalogueBean daoCatalogueBean : daoCatalogueBeans) {
            List<DaoVideoBean> daoVideoBeans = DaoUtil.getInstance().queryVideoListByQueryBuilder(daoCatalogueBean.getId());
            mDaoVideoBeans.addAll(daoVideoBeans);
        }
        mCourseCacheChildAdapter.addAllData(mDaoVideoBeans);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mToolbarCustom.setOnRightClickListener(new AwViewCustomToolbar.OnRightClickListener() {
            @Override
            public void onRightTextClick() {
                if ("编辑".equals(mToolbarCustom.getRightText())) {
                    mToolbarCustom.setRightText("完成");
                    mCourseCacheChildAdapter.setChose(true);
                    showView(mLlOfSetting, true);
                } else if ("完成".equals(mToolbarCustom.getRightText())) {
                    mToolbarCustom.setRightText("编辑");
                    mCourseCacheChildAdapter.setChose(false);
                    showView(mLlOfSetting, false);
                }
                mCourseCacheChildAdapter.notifyDataSetChanged();

            }
        });
        mCourseCacheChildAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DaoVideoBean daoVideoBean = (DaoVideoBean) adapter.getData().get(position);
                if(daoVideoBean.getDownloadStatus().equals(DaoVideoBean.DOWNLOAD_OVER)){
                    toClass(StudyCourseActivity.class, false, Extras.MICROLESS_ID, MICROLESS_ID, Extras.FILE_NAME, daoVideoBean.getFilePath());
                }else{
                    showMsg("视频下载中");
                }
            }
        });
        mCbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                for (DaoVideoBean daoVideoBean : mDaoVideoBeans) {
                    daoVideoBean.setIsCheck(b);
                }
                getChoseNum();
                mCourseCacheChildAdapter.notifyDataSetChanged();
            }
        });
        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<DaoVideoBean> daoVideoBeans = new ArrayList<>();
                for (DaoVideoBean daoVideoBean : mDaoVideoBeans) {
                    if (daoVideoBean.getIsCheck()) {
                        daoVideoBeans.add(daoVideoBean);
                        DownloadLimitManager.getInstance().cancelDownload(daoVideoBean);//取消下载
                        DaoUtil.getInstance().deleteVideoBean(daoVideoBean);//删除数据库信息
                        FileUtils.deleteSingleFile(getApplicationContext(), daoVideoBean.getFilePath());//删除文件
                    }
                }
                mDaoVideoBeans.removeAll(daoVideoBeans);
                mCourseCacheChildAdapter.setChose(false);
                mCourseCacheChildAdapter.notifyDataSetChanged();
                mToolbarCustom.setRightText("编辑");
                showView(mLlOfSetting, false);
            }
        });
        mTvPauseAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("全部暂停".equals(mTvPauseAll.getText().toString())) {
                    DownloadThreadManager.getInstance().removeDownList(mDaoVideoBeans);
                    //更新状态
                    ArrayList<DaoVideoBean> needPauseVideos = new ArrayList<>();
                    for (DaoVideoBean daoVideoBean : mDaoVideoBeans) {
                        if (!DaoVideoBean.DOWNLOAD_OVER.equals(daoVideoBean.getDownloadStatus())) {
                            needPauseVideos.add(daoVideoBean);
                            daoVideoBean.setDownloadStatus(DaoVideoBean.DOWNLOAD_PAUSE);
                            EventBus.getDefault().post(daoVideoBean);
                        }
                    }
                    mTvPauseAll.setText("全部开始");
                    Drawable drawable = getResources().getDrawable(R.mipmap.load_start_blue);
                    mTvPauseAll.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                } else if ("全部开始".equals(mTvPauseAll.getText().toString())) {
                    for (DaoVideoBean daoVideoBean : mDaoVideoBeans) {
                        if (!DaoVideoBean.DOWNLOAD_OVER.equals(daoVideoBean.getDownloadStatus())) {
                            daoVideoBean.setDownloadStatus(DaoVideoBean.DOWNLOAD);
                            EventBus.getDefault().post(daoVideoBean);
                        }
                    }
                    EventBus.getDefault().post(new RxCostomDownType(mDaoVideoBeans));
                    mTvPauseAll.setText("全部暂停");
                    Drawable drawable = getResources().getDrawable(R.mipmap.load_pause_blue);
                    mTvPauseAll.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                }
            }
        });
        mRlDownMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitClient.builderRetrofit()
                        .create(APIService.class)
                        .getCoursePlayList(RequestUtil.getCoursePlayListBody(MICROLESS_ID, MICROLESS_PVC_ID))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new AwApiSubscriber(new AwApiCallback<List<CoursePlayResultBean>>() {
                            @Override
                            public void onStart() {
                            }

                            @Override
                            public void onSuccess(List<CoursePlayResultBean> data) {
                                //点击下载弹出下载框
                                CourseDialogFramgment courseDialogFramgment = new CourseDialogFramgment();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable(Extras.KEY_COURSE_LIST, (Serializable) data);
                                courseDialogFramgment.setArguments(bundle);
                                courseDialogFramgment.show(getSupportFragmentManager(), "");
                            }


                            @Override
                            public void onFailure(int code, String msg) {

                            }

                            @Override
                            public void onCompleted() {
                            }
                        }));
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(DaoVideoBean info) {
        if (DaoVideoBean.DOWNLOAD.equals(info.getDownloadStatus())) {
            mCourseCacheChildAdapter.updateProgress(info);
        } else if (DaoVideoBean.DOWNLOAD_OVER.equals(info.getDownloadStatus())) {
            mCourseCacheChildAdapter.updateProgress(info);
        } else if (DaoVideoBean.DOWNLOAD_PAUSE.equals(info.getDownloadStatus())) {
            mCourseCacheChildAdapter.updateProgress(info);
            // Toast.makeText(this,"下载暂停",Toast.LENGTH_SHORT).show();
        } else if (DaoVideoBean.DOWNLOAD_CANCEL.equals(info.getDownloadStatus())) {
            mCourseCacheChildAdapter.updateProgress(info);
            DaoUtil.getInstance().deleteVideoBean(info);
        } else if (DaoVideoBean.DOWNLOAD_WAIT.equals(info.getDownloadStatus())) {
            mCourseCacheChildAdapter.updateProgress(info);
        } else if (DaoVideoBean.DOWNLOAD_ERROR.equals(info.getDownloadStatus())) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onClickComplete(List<CoursePlayResultBean.VideoListBean> mChildValues) {
        ArrayList<DaoVideoBean> daoVideoBeanArrayList = new ArrayList<>();
        //数据库记录课程信息

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
        ArrayList<String> downUrlList = new ArrayList<>();
        for (CoursePlayResultBean.VideoListBean videoListBean : mChildValues) {
            downUrlList.add(videoListBean.getUrl());
        }
        EventBus.getDefault().post(new RxCostomDownType(daoVideoBeanArrayList));
        mCourseCacheChildAdapter.notifyDataSetChanged();
        mCourseCacheChildAdapter.clearData();
        initData();
    }

    private void getChoseNum() {
        int num = 0;
        for (DaoVideoBean daoVideoBean : mDaoVideoBeans) {
            if(daoVideoBean.getIsCheck()){
                num++;
            }
        }
        mTvAll.setText("全选（" + num + "）");
    }
}
