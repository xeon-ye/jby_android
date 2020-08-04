package com.jkrm.education.ui.fragment;

import android.app.DownloadManager;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.FileUtils;
import com.jkrm.education.R;
import com.jkrm.education.adapter.CourseInProAdapter;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.db.DaoCatalogueBean;
import com.jkrm.education.db.DaoMicroLessonBean;
import com.jkrm.education.db.DaoVideoBean;
import com.jkrm.education.db.util.DaoUtil;
import com.jkrm.education.download.DownloadLimitManager;
import com.jkrm.education.download.DownloadThreadManager;
import com.jkrm.education.mvp.presenters.CourseCacheInProFragmentPresent;
import com.jkrm.education.mvp.views.CourseCacheInProFragmentView;
import com.jkrm.education.ui.activity.CourseCacheInProActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @Description: 缓冲中
 * @Author: xiangqian
 * @CreateDate: 2020/3/6 15:26
 */

public class CourseCacheInProFragment extends AwMvpFragment<CourseCacheInProFragmentPresent> implements CourseCacheInProFragmentView.View {
    @BindView(R.id.tv_mana)
    TextView mTvMana;
    @BindView(R.id.tv_pause_all)
    TextView mTvPauseAll;
    @BindView(R.id.cb_all)
    CheckBox mCbAll;
    @BindView(R.id.btn_delete)
    Button mBtnDelete;
    @BindView(R.id.ll_of_setting)
    LinearLayout mLlOfSetting;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    Unbinder unbinder;
    @BindView(R.id.view)
    View mView;
    private List<DaoMicroLessonBean> mDaoMicroLessonBeans = new ArrayList<>();
    private CourseInProAdapter mCourseInProAdapter;
    private Set<DaoMicroLessonBean> microLessonBeanSet = new HashSet<>();

    @Override
    protected CourseCacheInProFragmentPresent createPresenter() {
        return new CourseCacheInProFragmentPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_course_cache_inpro_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        EventBus.getDefault().register(this);
        mCourseInProAdapter = new CourseInProAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mCourseInProAdapter, false);
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
                if (checkIsDownLoading(daoVideoBeans)) {
                    microLessonBeanSet.add(mDaoMicroLessonBean);
                }
            }
        }
        mDaoMicroLessonBeans.addAll(microLessonBeanSet);
        mCourseInProAdapter.addAllData(mDaoMicroLessonBeans);
        setCourseNumAndSize();
    }


    private boolean checkIsDownLoading(List<DaoVideoBean> daoVideoBeans) {
        boolean is_all_com = false;
        if (null == daoVideoBeans || daoVideoBeans.size() == 0) {
            return false;
        }
        for (DaoVideoBean daoVideoBean : daoVideoBeans) {
            if (!DaoVideoBean.DOWNLOAD_OVER.equals(daoVideoBean.getDownloadStatus())) {
                is_all_com = true;
            }
        }
        return is_all_com;
    }

    @OnClick({R.id.tv_mana, R.id.tv_pause_all, R.id.btn_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_mana:
                if ("管理".equals(mTvMana.getText().toString())) {
                    mTvMana.setText("完成");
                    mLlOfSetting.setVisibility(View.VISIBLE);
                    mTvPauseAll.setVisibility(View.VISIBLE);
                    mView.setVisibility(View.VISIBLE);
                    mCourseInProAdapter.setChose(true);
                } else if ("完成".equals(mTvMana.getText().toString())) {
                    mTvMana.setText("管理");
                    mLlOfSetting.setVisibility(View.GONE);
                    mTvPauseAll.setVisibility(View.GONE);
                    mView.setVisibility(View.GONE);
                    mCourseInProAdapter.setChose(false);
                }
                break;
            case R.id.tv_pause_all:
                if ("全部暂停".equals(mTvPauseAll.getText().toString())) {
                    mTvPauseAll.setText("全部开始");
                    pauseAllDownload();
                } else if ("全部开始".equals(mTvPauseAll.getText().toString())) {
                    mTvPauseAll.setText("全部暂停");
                    continueAllDownLoad();
                }
                break;
            case R.id.btn_delete:
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
                            FileUtils.deleteSingleFile(getActivity(), daoVideoBean.getFileName());
                        }
                        DaoUtil.getInstance().deleteVideoList(daoVideoBeans1);
                    }
                }
                mDaoMicroLessonBeans.removeAll(list);
                mCourseInProAdapter.notifyDataSetChanged();
                break;
        }
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
        mCourseInProAdapter.notifyDataSetChanged();
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
        mCourseInProAdapter.notifyDataSetChanged();

    }

    /**
     * 暂停当前目录下的视频下载
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
        mCourseInProAdapter.notifyDataSetChanged();
    }


    /**
     * //继续下载目录下的视频
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
        mCourseInProAdapter.notifyDataSetChanged();
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

    @Override
    protected void initListener() {
        super.initListener();
        mCbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                for (DaoMicroLessonBean daoMicroLessonBean : mDaoMicroLessonBeans) {
                    daoMicroLessonBean.setIsCheck(b);
                }
                mCourseInProAdapter.notifyDataSetChanged();
/*                for (CourseSuccessBean courseSuccessBean : mCourseSuccessBeanArrayList) {
                    courseSuccessBean.setCheck(b);
                }
                mCourseSuccessAdapter.notifyDataSetChanged();*/
            }
        });
        mCourseInProAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_img:

                        toClass(CourseCacheInProActivity.class, false, Extras.MICROLESS_ID, mDaoMicroLessonBeans.get(position).getId());
                        break;
                    case R.id.iv_img_center:
                        DaoMicroLessonBean daoMicroLessonBean = (DaoMicroLessonBean) adapter.getData().get(position);
                        if (!daoMicroLessonBean.isPause()) {
                            pauseCatalogueDownload(daoMicroLessonBean);
                        } else {
                            continueCatalogueDownLoad(daoMicroLessonBean);
                        }
                        break;
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(DaoVideoBean info) {
      /*  getAvailableMemorySize();
        if (DaoVideoBean.DOWNLOAD_OVER.equals(info.getDownloadStatus())) {
            Toast.makeText(this, "完成", Toast.LENGTH_SHORT).show();
        } else if (DaoVideoBean.DOWNLOAD_ERROR.equals(info.getDownloadStatus())) {
            Toast.makeText(this, "下载出错", Toast.LENGTH_SHORT).show();
        }*/
    }

}
