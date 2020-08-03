package com.jkrm.education.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwBaseActivity;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.CourseCacheAdapter;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.db.DaoCatalogueBean;
import com.jkrm.education.db.DaoMicroLessonBean;
import com.jkrm.education.db.DaoVideoBean;
import com.jkrm.education.db.util.DaoUtil;

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
    private List<DaoMicroLessonBean> mDaoMicroLessonBeans = new ArrayList<>();
    private Set<DaoMicroLessonBean> microLessonBeanSet = new HashSet<>();
    private CourseCacheAdapter mCourseCacheAdapter;

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
        mCourseCacheAdapter=new CourseCacheAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData,mCourseCacheAdapter,false);
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
                toClass(CourseCacheChildActivity.class,false, Extras.MICROLESS_ID,data.get(position).getId(),Extras.MICROLESS_NAME,data.get(position).getMlessonName());
            }
        });
        mToolbarCustom.setOnRightClickListener(new AwViewCustomToolbar.OnRightClickListener() {
            @Override
            public void onRightTextClick() {
                if("编辑".equals(mToolbarCustom.getRightText())){
                    mToolbarCustom.setRightText("完成");
                    mCourseCacheAdapter.setChose(true);
                }else if("完成".equals(mToolbarCustom.getRightText())){
                    mToolbarCustom.setRightText("编辑");
                    mCourseCacheAdapter.setChose(false);
                }
                mCourseCacheAdapter.notifyDataSetChanged();
            }
        });
    }
}
