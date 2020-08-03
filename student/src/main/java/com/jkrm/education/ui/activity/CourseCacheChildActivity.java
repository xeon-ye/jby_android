package com.jkrm.education.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzw.baselib.base.AwBaseActivity;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.CourseCacheChildAdapter;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.db.DaoCatalogueBean;
import com.jkrm.education.db.DaoVideoBean;
import com.jkrm.education.db.util.DaoUtil;

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
    private String MICROLESS_ID,MICROLESS_NAME;
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
                if("编辑".equals(mToolbarCustom.getRightText())){
                    mToolbarCustom.setRightText("完成");
                    mCourseCacheChildAdapter.setChose(true);
                }else if("完成".equals(mToolbarCustom.getRightText())){
                    mToolbarCustom.setRightText("编辑");
                    mCourseCacheChildAdapter.setChose(false);
                }
                mCourseCacheChildAdapter.notifyDataSetChanged();

            }
        });
    }
}
