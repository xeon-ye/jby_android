package com.jkrm.education.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.FileUtils;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.CourseInProActAdapter;

import com.jkrm.education.bean.rx.RxCostomDownType;
import com.jkrm.education.bean.rx.RxDownCourseType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.db.DaoCatalogueBean;
import com.jkrm.education.db.DaoVideoBean;
import com.jkrm.education.db.util.DaoUtil;
import com.jkrm.education.download.DownloadLimitManager;
import com.jkrm.education.download.DownloadThreadManager;
import com.jkrm.education.mvp.presenters.CourseCacheInProPresent;
import com.jkrm.education.mvp.views.CourseCacheInProView;
import com.sobot.chat.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 缓存中
 */
public class CourseCacheInProActivity extends AwMvpActivity<CourseCacheInProPresent> implements CourseCacheInProView.View {
    private static final String TAG = "CourseCacheInProActivit";
    private String MICROLESS_ID;

    @BindView(R.id.toolbar_custom)
    AwViewCustomToolbar mToolbarCustom;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.cb_all)
    CheckBox mCbAll;
    @BindView(R.id.btn_delete)
    Button mBtnDelete;
    @BindView(R.id.ll_of_setting)
    LinearLayout mLlOfSetting;
    @BindView(R.id.tv_pause_all)
    TextView mTvPauseAll;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;

    private CourseInProActAdapter mCourseInProActAdapter;
    private CopyOnWriteArrayList<DaoVideoBean> mDaoVideoBeans = new CopyOnWriteArrayList<DaoVideoBean>();

    @Override
    protected CourseCacheInProPresent createPresenter() {
        return new CourseCacheInProPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_course_cache_in_pro;
    }


    @Override
    protected void initView() {
        super.initView();
        setToolbar("缓存中", new AwViewCustomToolbar.OnLeftClickListener() {
            @Override
            public void onLeftTextClick() {
                finish();
            }
        });
        mToolbarCustom.setRightText("编辑");


        mCourseInProActAdapter = new CourseInProActAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mCourseInProActAdapter, false);
        mRcvData.setItemAnimator(null);
        //取消闪烁动画
        //((DefaultItemAnimator)mRcvData.getItemAnimator()).setSupportsChangeAnimations(false);

    }

    @Override
    protected void initData() {
        super.initData();
        MICROLESS_ID = getIntent().getStringExtra(Extras.MICROLESS_ID);
        List<DaoCatalogueBean> daoCatalogueBeans = DaoUtil.getInstance().queryCatalogueListByQueryBuilder(MICROLESS_ID);
        //根据课程 目录 查找对应视频
        for (DaoCatalogueBean daoCatalogueBean : daoCatalogueBeans) {
            List<DaoVideoBean> daoVideoBeans = DaoUtil.getInstance().queryVideoListByQueryBuilder(daoCatalogueBean.getId());
            mDaoVideoBeans.addAll(daoVideoBeans);
        }

        mCourseInProActAdapter.addAllData(mDaoVideoBeans);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mToolbarCustom.setOnRightClickListener(new AwViewCustomToolbar.OnRightClickListener() {
            @Override
            public void onRightTextClick() {
                if ("编辑".equals(mToolbarCustom.getRightText().toString())) {
                    mTvPauseAll.setVisibility(View.GONE);
                    mLlOfSetting.setVisibility(View.VISIBLE);
                    mToolbarCustom.setRightText("完成");
                    mCourseInProActAdapter.setChose(true);
                } else if ("完成".equals(mToolbarCustom.getRightText().toString())) {
                    mTvPauseAll.setVisibility(View.VISIBLE);
                    mLlOfSetting.setVisibility(View.GONE);
                    mToolbarCustom.setRightText("编辑");
                    mCourseInProActAdapter.setChose(false);
                }
            }
        });
        mCourseInProActAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                DaoVideoBean daoVideoBean = mDaoVideoBeans.get(position);
                switch (view.getId()) {
                    //暂停
                    case R.id.iv_pause:
                        DownloadThreadManager.getInstance().pauseOrStopDown(daoVideoBean);
                        //
                       /* DownloadLimitManager.getInstance().pauseDownload(daoVideoBean.getUrl());
                        DaoUtil.getInstance().updateVideoProgress(daoVideoBean);
                        DaoVideoBean daoVideoBean1 = DaoUtil.getInstance().queryVideoByUrl(daoVideoBean);
                        long pro = daoVideoBean1.getProgress();*/
                        break;
                    //继续
                    case R.id.iv_con:
                        daoVideoBean.setDownloadStatus(DaoVideoBean.DOWNLOAD);
                        DownloadThreadManager.getInstance().downVideo(daoVideoBean);
                        EventBus.getDefault().post(daoVideoBean);
                        //
                       // DownloadLimitManager.getInstance().download(daoVideoBean.getUrl());
                        break;

                    case R.id.tv_title:
                       // ToastUtil.showLongToast(CourseCacheInProActivity.this, daoVideoBean.getFileName());
                        break;
                }

            }
        });
        mCbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                for (DaoVideoBean daoVideoBean : mDaoVideoBeans) {
                    daoVideoBean.setIsCheck(b);
                }
                mCourseInProActAdapter.notifyDataSetChanged();
            }
        });

    }

    @OnClick({R.id.btn_delete, R.id.tv_pause_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_delete:
                ArrayList<DaoVideoBean> daoVideoBeans = new ArrayList<>();
                for (DaoVideoBean daoVideoBean : mDaoVideoBeans) {
                    if (daoVideoBean.getIsCheck()) {
                        daoVideoBeans.add(daoVideoBean);
                        DownloadLimitManager.getInstance().cancelDownload(daoVideoBean);//取消下载
                        DaoUtil.getInstance().deleteVideoBean(daoVideoBean);//删除数据库信息
                        FileUtils.deleteSingleFile(getApplicationContext(), daoVideoBean.getFilePath());//删除文件
                    }
                }
                break;
            case R.id.tv_pause_all:
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
                } else if ("全部开始".equals(mTvPauseAll.getText().toString())) {
                    for (DaoVideoBean daoVideoBean : mDaoVideoBeans) {
                        if (!DaoVideoBean.DOWNLOAD_OVER.equals(daoVideoBean.getDownloadStatus())) {
                            daoVideoBean.setDownloadStatus(DaoVideoBean.DOWNLOAD);
                            EventBus.getDefault().post(daoVideoBean);
                        }
                    }
                    EventBus.getDefault().post(new RxCostomDownType(mDaoVideoBeans));
                    mTvPauseAll.setText("全部暂停");
                }

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(DaoVideoBean info) {
        if (DaoVideoBean.DOWNLOAD.equals(info.getDownloadStatus())) {
            mCourseInProActAdapter.updateProgress(info);
        } else if (DaoVideoBean.DOWNLOAD_OVER.equals(info.getDownloadStatus())) {
            mCourseInProActAdapter.updateProgress(info);
        } else if (DaoVideoBean.DOWNLOAD_PAUSE.equals(info.getDownloadStatus())) {
            mCourseInProActAdapter.updateProgress(info);
            // Toast.makeText(this,"下载暂停",Toast.LENGTH_SHORT).show();
        } else if (DaoVideoBean.DOWNLOAD_CANCEL.equals(info.getDownloadStatus())) {
            mCourseInProActAdapter.updateProgress(info);
            DaoUtil.getInstance().deleteVideoBean(info);
        } else if (DaoVideoBean.DOWNLOAD_WAIT.equals(info.getDownloadStatus())) {
            mCourseInProActAdapter.updateProgress(info);
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
    protected void onDestroy() {
        super.onDestroy();
       // DaoUtil.getInstance().updateVideoProgressList(mDaoVideoBeans);
    }
}
