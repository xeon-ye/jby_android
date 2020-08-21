package com.jkrm.education.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.jkrm.education.adapter.CourseCacheAdapter;
import com.jkrm.education.bean.rx.RxCostomDownType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.db.DaoCatalogueBean;
import com.jkrm.education.db.DaoMicroLessonBean;
import com.jkrm.education.db.DaoVideoBean;
import com.jkrm.education.db.util.DaoUtil;
import com.jkrm.education.download.DownloadLimitManager;
import com.jkrm.education.download.DownloadThreadManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/8/3 14:14
 */

public class CourseCacheNewActivity extends AwBaseActivity {
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
    private List<DaoMicroLessonBean> mDaoMicroLessonBeans = new ArrayList<>();
    private Set<DaoMicroLessonBean> microLessonBeanSet = new HashSet<>();
    private CourseCacheAdapter mCourseCacheAdapter;
    private Handler mHnadler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    mToolbarCustom.setRightText("当前网速： " + msg.obj.toString());
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_course_cache_new;
    }

    @Override
    protected void initView() {
        super.initView();
        setToolbarWithBackImgAndRightView("课程缓存", "编辑", new AwViewCustomToolbar.OnRightClickListener() {
            @Override
            public void onRightTextClick() {

            }
        });
        mCourseCacheAdapter = new CourseCacheAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mCourseCacheAdapter, false);
    }

    @Override
    protected void initData() {
        super.initData();
        mDaoMicroLessonBeans.clear();
        microLessonBeanSet.clear();
        List<DaoMicroLessonBean> daoMicroLessonBeans = DaoUtil.getInstance().queryMicro();
        for (DaoMicroLessonBean mDaoMicroLessonBean : daoMicroLessonBeans) {
            List<DaoCatalogueBean> daoCatalogueBeans = DaoUtil.getInstance().queryCatalogueListByQueryBuilder(mDaoMicroLessonBean.getId());
            for (DaoCatalogueBean daoCatalogueBean : daoCatalogueBeans) {
                List<DaoVideoBean> daoVideoBeans = DaoUtil.getInstance().queryVideoListByQueryBuilder(daoCatalogueBean.getId());
                microLessonBeanSet.add(mDaoMicroLessonBean);
            }
        }
        mDaoMicroLessonBeans.addAll(microLessonBeanSet);
        mCourseCacheAdapter.addAllData(mDaoMicroLessonBeans);
    }


    @Override
    protected void initListener() {
        super.initListener();
        mCourseCacheAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<DaoMicroLessonBean> data = adapter.getData();
                toClass(CourseCacheChildActivity.class, false, Extras.MICROLESS_ID, data.get(position).getId(), Extras.MICROLESS_NAME, data.get(position).getMlessonName(),Extras.MICROLESS_PVC_ID,data.get(position).getPcvId());
             }
        });
        mToolbarCustom.setOnRightClickListener(new AwViewCustomToolbar.OnRightClickListener() {
            @Override
            public void onRightTextClick() {
                if ("编辑".equals(mToolbarCustom.getRightText())) {
                    mToolbarCustom.setRightText("完成");
                    mCourseCacheAdapter.setChose(true);
                    showView(mLlOfSetting, true);
                } else if ("完成".equals(mToolbarCustom.getRightText())) {
                    mToolbarCustom.setRightText("编辑");
                    mCourseCacheAdapter.setChose(false);
                    showView(mLlOfSetting, false);
                }
                mCourseCacheAdapter.notifyDataSetChanged();
            }
        });
        mCbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                for (DaoMicroLessonBean daoMicroLessonBean : mDaoMicroLessonBeans) {
                    daoMicroLessonBean.setIsCheck(b);
                }
                mCourseCacheAdapter.notifyDataSetChanged();
            }
        });
        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<DaoMicroLessonBean> list = new ArrayList<>();
                for (DaoMicroLessonBean daoMicroLessonBean : mDaoMicroLessonBeans) {
                    if (daoMicroLessonBean.getIsCheck()) {
                        list.add(daoMicroLessonBean);
                    }
                }
                if (list.isEmpty()) {
                    showMsg("请选择课程");
                    return;
                }
                for (DaoMicroLessonBean daoMicroLessonBean : list) {
                    List<DaoCatalogueBean> daoCatalogueBeans = DaoUtil.getInstance().queryCatalogueListByQueryBuilder(daoMicroLessonBean.getId());
                    for (DaoCatalogueBean daoCatalogueBean : daoCatalogueBeans) {
                        List<DaoVideoBean> daoVideoBeans1 = DaoUtil.getInstance().queryVideoListByQueryBuilder(daoCatalogueBean.getId());
                        for (DaoVideoBean daoVideoBean : daoVideoBeans1) {
                            DownloadLimitManager.getInstance().cancelDownload(daoVideoBean);
                            FileUtils.deleteSingleFile(mActivity, daoVideoBean.getFilePath());
                        }
                        DaoUtil.getInstance().deleteVideoList(daoVideoBeans1);
                    }
                }
                mDaoMicroLessonBeans.removeAll(list);
                mCourseCacheAdapter.setChose(false);
                mCourseCacheAdapter.notifyDataSetChanged();
                mToolbarCustom.setRightText("编辑");
                showView(mLlOfSetting, false);
            }
        });
        mTvPauseAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("全部暂停".equals(mTvPauseAll.getText().toString())) {
                    pauseAllDownload();
                    mTvPauseAll.setText("全部开始");
                    Drawable drawable = getResources().getDrawable(R.mipmap.load_start_blue);
                    mTvPauseAll.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                } else if ("全部开始".equals(mTvPauseAll.getText().toString())) {
                    continueAllDownLoad();
                    mTvPauseAll.setText("全部暂停");
                    Drawable drawable = getResources().getDrawable(R.mipmap.load_pause_blue);
                    mTvPauseAll.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                }
            }
        });
    }

    /**
     * 继续下载
     */
    private void continueAllDownLoad() {
        ArrayList<DaoVideoBean> needPauseVideos = new ArrayList<>();
        for (DaoMicroLessonBean daoMicroLessonBean : mDaoMicroLessonBeans) {
            daoMicroLessonBean.setPause(false);
            List<DaoCatalogueBean> daoCatalogueBeans = DaoUtil.getInstance().queryCatalogueListByQueryBuilder(daoMicroLessonBean.getId());
            for (DaoCatalogueBean daoCatalogueBean : daoCatalogueBeans) {
                List<DaoVideoBean> daoVideoBeans1 = DaoUtil.getInstance().queryVideoListByQueryBuilder(daoCatalogueBean.getId());
                for (DaoVideoBean daoVideoBean : daoVideoBeans1) {
                    if (!DaoVideoBean.DOWNLOAD_OVER.equals(daoVideoBean.getDownloadStatus())) {
                        needPauseVideos.add(daoVideoBean);
                    }
                }

            }

        }
        for (DaoVideoBean needPauseVideo : needPauseVideos) {
            if (!needPauseVideo.getDownloadStatus().equals(DaoVideoBean.DOWNLOAD_OVER)) {
                needPauseVideo.setDownloadStatus(DaoVideoBean.DOWNLOAD);
                EventBus.getDefault().post(needPauseVideo);
                DownloadThreadManager.getInstance().downVideo(needPauseVideo);
            }
        }
        mCourseCacheAdapter.notifyDataSetChanged();
    }


    /**
     * 暂停全部下载
     */
    private void pauseAllDownload() {
        //移除等待中的课程
        ArrayList<DaoVideoBean> needPauseVideos = new ArrayList<>();
        for (DaoMicroLessonBean daoMicroLessonBean : mDaoMicroLessonBeans) {
            daoMicroLessonBean.setPause(true);
            List<DaoCatalogueBean> daoCatalogueBeans = DaoUtil.getInstance().queryCatalogueListByQueryBuilder(daoMicroLessonBean.getId());
            for (DaoCatalogueBean daoCatalogueBean : daoCatalogueBeans) {
                List<DaoVideoBean> daoVideoBeans1 = DaoUtil.getInstance().queryVideoListByQueryBuilder(daoCatalogueBean.getId());
                for (DaoVideoBean daoVideoBean : daoVideoBeans1) {
                    if (!DaoVideoBean.DOWNLOAD_OVER.equals(daoVideoBean.getDownloadStatus())) {
                        daoVideoBean.setDownloadStatus(DaoVideoBean.DOWNLOAD_PAUSE);
                        needPauseVideos.add(daoVideoBean);
                        EventBus.getDefault().post(daoVideoBean);
                    }
                }
            }
        }
        DownloadThreadManager.getInstance().removeDownList(needPauseVideos);
        for (DaoVideoBean needPauseVideo : needPauseVideos) {
            if (!needPauseVideo.getDownloadStatus().equals(DaoVideoBean.DOWNLOAD_OVER)) {
                needPauseVideo.setDownloadStatus(DaoVideoBean.DOWNLOAD_PAUSE);
                EventBus.getDefault().post(needPauseVideo);
            }
        }
        mCourseCacheAdapter.notifyDataSetChanged();

    }

    /**
     * 暂停当前目录下的视频下载
     *
     * @param daoMicroLessonBean
     */
    private void pauseCatalogueDownload(DaoMicroLessonBean daoMicroLessonBean) {
        daoMicroLessonBean.setPause(true);
        ArrayList<DaoVideoBean> needPauseVideos = new ArrayList<>();
        for (DaoCatalogueBean daoCatalogueBean : daoMicroLessonBean.getList()) {
            List<DaoVideoBean> mVideoBeanList = daoCatalogueBean.getMVideoBeanList();
            for (DaoVideoBean daoVideoBean : mVideoBeanList) {
                if (!DaoVideoBean.DOWNLOAD_OVER.equals(daoVideoBean.getDownloadStatus())) {
                    daoVideoBean.setDownloadStatus(DaoVideoBean.DOWNLOAD_PAUSE);
                    needPauseVideos.add(daoVideoBean);
                    EventBus.getDefault().post(daoVideoBean);
                }
            }
        }
        DownloadThreadManager.getInstance().removeDownList(needPauseVideos);
        mCourseCacheAdapter.notifyDataSetChanged();
    }


    /**
     * //继续下载目录下的视频
     *
     * @param daoMicroLessonBean
     */
    private void continueCatalogueDownLoad(DaoMicroLessonBean daoMicroLessonBean) {
        daoMicroLessonBean.setPause(false);
        ArrayList<DaoVideoBean> needPauseVideos = new ArrayList<>();
        for (DaoCatalogueBean daoCatalogueBean : daoMicroLessonBean.getList()) {
            List<DaoVideoBean> mVideoBeanList = daoCatalogueBean.getMVideoBeanList();
            for (DaoVideoBean daoVideoBean : mVideoBeanList) {
                if (!DaoVideoBean.DOWNLOAD_OVER.equals(daoVideoBean.getDownloadStatus())) {
                    daoVideoBean.setDownloadStatus(DaoVideoBean.DOWNLOAD);
                    needPauseVideos.add(daoVideoBean);
                    EventBus.getDefault().post(daoVideoBean);
                }
            }
        }
        for (DaoVideoBean needPauseVideo : needPauseVideos) {
            if (!needPauseVideo.getDownloadStatus().equals(DaoVideoBean.DOWNLOAD_OVER)) {
                needPauseVideo.setDownloadStatus(DaoVideoBean.DOWNLOAD);
                EventBus.getDefault().post(needPauseVideo);
                DownloadThreadManager.getInstance().downVideo(needPauseVideo);
            }
        }
        mCourseCacheAdapter.notifyDataSetChanged();
    }

    /**
     * 获取文件大小
     */
    private void setCourseNumAndSize() {
        for (DaoMicroLessonBean daoMicroLessonBean : mDaoMicroLessonBeans) {
            int cacheNum = 0;
            int cacheingNum = 0;
            long cacheSize = 0;
            List<DaoCatalogueBean> daoCatalogueBeans = DaoUtil.getInstance().queryCatalogueListByQueryBuilder(daoMicroLessonBean.getId());
            for (DaoCatalogueBean daoCatalogueBean : daoCatalogueBeans) {
                List<DaoVideoBean> daoVideoBeans1 = DaoUtil.getInstance().queryVideoListByQueryBuilder(daoCatalogueBean.getId());
                cacheNum += daoVideoBeans1.size();
                for (DaoVideoBean daoVideoBean : daoVideoBeans1) {
                    if (DaoVideoBean.DOWNLOAD_OVER.equals(daoVideoBean.getDownloadStatus())) {
                        cacheingNum += 1;
                        cacheSize += Long.parseLong(daoVideoBean.getSize());
                    }
                }
            }
            daoMicroLessonBean.setCacheNum("已缓存" + cacheingNum + "节/共" + cacheNum + "节");
            daoMicroLessonBean.setCacheSize(FileUtils.getPrintSize(cacheSize));

        }

    }
}
