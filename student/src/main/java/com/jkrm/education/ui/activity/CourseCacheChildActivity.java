package com.jkrm.education.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwBaseActivity;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.FileUtils;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.CourseCacheChildAdapter;
import com.jkrm.education.bean.rx.RxCostomDownType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.db.DaoCatalogueBean;
import com.jkrm.education.db.DaoVideoBean;
import com.jkrm.education.db.util.DaoUtil;
import com.jkrm.education.download.DownloadLimitManager;
import com.jkrm.education.download.DownloadThreadManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseCacheChildActivity extends AwBaseActivity {
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
    private String MICROLESS_ID, MICROLESS_NAME;
    private CourseCacheChildAdapter mCourseCacheChildAdapter;
    private CopyOnWriteArrayList<DaoVideoBean> mDaoVideoBeans = new CopyOnWriteArrayList<DaoVideoBean>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_course_cache_child;
    }

    @Override
    protected void initView() {
        super.initView();
        mCourseCacheChildAdapter = new CourseCacheChildAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mCourseCacheChildAdapter, false);
        MICROLESS_NAME = getIntent().getStringExtra(Extras.MICROLESS_NAME);
        setToolbarWithBackImgAndRightView(MICROLESS_NAME, "编辑", null);
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
                toClass(StudyCourseActivity.class, false, Extras.MICROLESS_ID, MICROLESS_ID, Extras.FILE_NAME, daoVideoBean.getFilePath());
            }
        });
        mCbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                for (DaoVideoBean daoVideoBean : mDaoVideoBeans) {
                    daoVideoBean.setIsCheck(b);
                }
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
}
