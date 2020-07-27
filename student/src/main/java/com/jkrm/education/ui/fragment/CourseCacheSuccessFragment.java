package com.jkrm.education.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.FileUtils;
import com.jkrm.education.R;
import com.jkrm.education.adapter.CourseInProAdapter;
import com.jkrm.education.adapter.CourseSuccessAdapter;
import com.jkrm.education.bean.result.CourseSuccessBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.db.DaoCatalogueBean;
import com.jkrm.education.db.DaoMicroLessonBean;
import com.jkrm.education.db.DaoVideoBean;
import com.jkrm.education.db.util.DaoUtil;
import com.jkrm.education.mvp.presenters.CourseCacheSuccessFragmentPresent;
import com.jkrm.education.mvp.views.CourseCacheSuccessFragmentView;
import com.jkrm.education.ui.activity.CourseCacheInProActivity;
import com.jkrm.education.ui.activity.CourseCacheSuccessActivity;
import com.sobot.chat.camera.util.FileUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/6 15:25
 */

public class CourseCacheSuccessFragment extends AwMvpFragment<CourseCacheSuccessFragmentPresent> implements CourseCacheSuccessFragmentView.View {
    @BindView(R.id.tv_mana)
    TextView mTvMana;
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
    private CourseSuccessAdapter mCourseSuccessAdapter;
    private List<DaoMicroLessonBean> mDaoMicroLessonBeans = new ArrayList<>();
    private Set<DaoMicroLessonBean> microLessonBeanSet = new HashSet<>();



    @Override
    protected CourseCacheSuccessFragmentPresent createPresenter() {
        return new CourseCacheSuccessFragmentPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_course_cache_success_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        EventBus.getDefault().register(this);
        //假数据
        mCourseSuccessAdapter=new CourseSuccessAdapter();
        mCourseSuccessAdapter.setChoseAll(mCbAll);
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mCourseSuccessAdapter, false);
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
                if (checkIsAllDownCom(daoVideoBeans)) {
                    microLessonBeanSet.add(mDaoMicroLessonBean);
                }
            }
        }
        mDaoMicroLessonBeans.addAll(microLessonBeanSet);
        //根据课程 目录 查找对应视频
        mCourseSuccessAdapter.addAllData(mDaoMicroLessonBeans);
        setCourseNumAndSize();
    }

    private boolean checkIsAllDownCom(List<DaoVideoBean> daoVideoBeans) {
        boolean is_all_com = true;
        if (null == daoVideoBeans || daoVideoBeans.size() == 0) {
            return false;
        }
        for (DaoVideoBean daoVideoBean : daoVideoBeans) {
            if (!DaoVideoBean.DOWNLOAD_OVER.equals(daoVideoBean.getDownloadStatus())) {
                is_all_com = false;
            }
        }
        return is_all_com;
    }

    private boolean checkIsAllChose(List<CourseSuccessBean> courseSuccessBeanArrayList) {
        boolean allChose = true;
        for (CourseSuccessBean courseSuccessBean : courseSuccessBeanArrayList) {
            if (!courseSuccessBean.isCheck()) {
                allChose = false;
            }
        }
        return allChose;
    }

    private void setCourseNumAndSize() {
        for (DaoMicroLessonBean daoMicroLessonBean : mDaoMicroLessonBeans) {
            int cacheNum=0;
            long cacheSize=0;
            List<DaoCatalogueBean> daoCatalogueBeans = DaoUtil.getInstance().queryCatalogueListByQueryBuilder(daoMicroLessonBean.getId());
            for (DaoCatalogueBean daoCatalogueBean : daoCatalogueBeans) {
                List<DaoVideoBean> daoVideoBeans1 = DaoUtil.getInstance().queryVideoListByQueryBuilder(daoCatalogueBean.getId());
                cacheNum += daoVideoBeans1.size();
                for (DaoVideoBean daoVideoBean : daoVideoBeans1){
                    cacheSize +=Long.parseLong(daoVideoBean.getSize());
                }
            }
            daoMicroLessonBean.setCacheNum("已缓存" + cacheNum + "节");
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
                mCourseSuccessAdapter.notifyDataSetChanged();
   /*             for (CourseSuccessBean courseSuccessBean : mCourseSuccessBeanArrayList) {
                    courseSuccessBean.setCheck(b);
                }
                mCourseSuccessAdapter.notifyDataSetChanged();*/
            }
        });
        mCourseSuccessAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_img:
                        toClass(CourseCacheSuccessActivity.class, false, Extras.MICROLESS_ID, mDaoMicroLessonBeans.get(position).getId());
                        break;
                }
            }
        });
    }

    @OnClick({R.id.tv_mana, R.id.btn_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_mana:
                if ("管理".equals(mTvMana.getText().toString())) {
                    mTvMana.setText("完成");
                    mLlOfSetting.setVisibility(View.VISIBLE);
                    mView.setVisibility(View.VISIBLE);
                    mCourseSuccessAdapter.setChose(true);
                } else if ("完成".equals(mTvMana.getText().toString())) {
                    mTvMana.setText("管理");
                    mLlOfSetting.setVisibility(View.GONE);
                    mView.setVisibility(View.GONE);
                    mCourseSuccessAdapter.setChose(false);
                }
                break;
            case R.id.btn_delete:
                ArrayList<DaoMicroLessonBean> list=new ArrayList<>();
                for (DaoMicroLessonBean daoMicroLessonBean : mDaoMicroLessonBeans) {
                    if(daoMicroLessonBean.getIsCheck()){
                        list.add(daoMicroLessonBean);
                    }
                }
                if(list.isEmpty()){
                    showMsg("请选择课程");
                    return;
                }
                for (DaoMicroLessonBean daoMicroLessonBean : list) {
                    List<DaoCatalogueBean> daoCatalogueBeans = DaoUtil.getInstance().queryCatalogueListByQueryBuilder(daoMicroLessonBean.getId());
                    for (DaoCatalogueBean daoCatalogueBean : daoCatalogueBeans) {
                        List<DaoVideoBean> daoVideoBeans1 = DaoUtil.getInstance().queryVideoListByQueryBuilder(daoCatalogueBean.getId());
                        DaoUtil.getInstance().deleteVideoList(daoVideoBeans1);
                        for (DaoVideoBean daoVideoBean : daoVideoBeans1) {
                            FileUtils.deleteSingleFile(getActivity(),daoVideoBean.getFilePath());
                        }
                    }
                }
                mDaoMicroLessonBeans.removeAll(list);
                mCourseSuccessAdapter.notifyDataSetChanged();

                break;
        }
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
       /* getAvailableMemorySize();
        if (DaoVideoBean.DOWNLOAD_OVER.equals(info.getDownloadStatus())) {
            Toast.makeText(this, "完成", Toast.LENGTH_SHORT).show();
        } else if (DaoVideoBean.DOWNLOAD_ERROR.equals(info.getDownloadStatus())) {
            Toast.makeText(this, "下载出错", Toast.LENGTH_SHORT).show();
        }*/
    }

}
