package com.jkrm.education.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.common.eventbus.EventBus;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.FileUtils;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.CourseSuccessActAdapter;
import com.jkrm.education.adapter.CourseSuccessAdapter;
import com.jkrm.education.bean.result.CoursePlayResultBean;
import com.jkrm.education.bean.result.CourseSuccessBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.db.DaoCatalogueBean;
import com.jkrm.education.db.DaoVideoBean;
import com.jkrm.education.db.util.DaoUtil;
import com.jkrm.education.download.DownloadLimitManager;
import com.jkrm.education.mvp.presenters.CourseCacheSuccessPresent;
import com.jkrm.education.mvp.views.CourseCacheSuccessView;
import com.jkrm.education.widgets.CourseDialogFramgment;
import com.sobot.chat.camera.util.FileUtil;
import com.sobot.chat.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 缓存完成
 */
public class CourseCacheSuccessActivity extends AwMvpActivity<CourseCacheSuccessPresent> implements CourseCacheSuccessView.View, CourseDialogFramgment.ConfirmListener {

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
    @BindView(R.id.tv_more)
    TextView mTvMore;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;

    private CourseSuccessActAdapter mCourseSuccessActAdapter;
    private ArrayList<CourseSuccessBean> mCourseSuccessBeanArrayList;
    private String MICROLESS_ID;
    private List<DaoVideoBean> mDaoVideoBeans = new CopyOnWriteArrayList<>();

    @Override
    protected CourseCacheSuccessPresent createPresenter() {
        return new CourseCacheSuccessPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_course_cache_success;
    }

    @Override
    protected void initView() {
        super.initView();
        setToolbar("已缓存", new AwViewCustomToolbar.OnLeftClickListener() {
            @Override
            public void onLeftTextClick() {
                finish();
            }
        });
        mToolbarCustom.setRightText("编辑");


        mCourseSuccessActAdapter = new CourseSuccessActAdapter();
        mCourseSuccessBeanArrayList = new ArrayList<>();
        mCourseSuccessBeanArrayList.add(new CourseSuccessBean());
        mCourseSuccessBeanArrayList.add(new CourseSuccessBean());
        mCourseSuccessBeanArrayList.add(new CourseSuccessBean());
        mCourseSuccessBeanArrayList.add(new CourseSuccessBean());
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mCourseSuccessActAdapter, false);

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
        // mDaoVideoBeans = DaoUtil.getInstance().queryAllVideo();
        for (DaoVideoBean daoVideoBean : mDaoVideoBeans) {
            AwLog.e(TAG, daoVideoBean.getDownloadStatus() + "-->STATTE");
        }
        mCourseSuccessActAdapter.addAllData(mDaoVideoBeans);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mToolbarCustom.setOnRightClickListener(new AwViewCustomToolbar.OnRightClickListener() {
            @Override
            public void onRightTextClick() {
                if ("编辑".equals(mToolbarCustom.getRightText().toString())) {
                    mTvMore.setVisibility(View.GONE);
                    mLlOfSetting.setVisibility(View.VISIBLE);
                    mToolbarCustom.setRightText("完成");
                    mCourseSuccessActAdapter.setChose(true);
                } else if ("完成".equals(mToolbarCustom.getRightText().toString())) {
                    mTvMore.setVisibility(View.VISIBLE);
                    mLlOfSetting.setVisibility(View.GONE);
                    mToolbarCustom.setRightText("编辑");
                    mCourseSuccessActAdapter.setChose(false);
                }
            }
        });
        mCbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                for (DaoVideoBean daoVideoBean : mDaoVideoBeans) {
                    daoVideoBean.setIsCheck(b);
                }
                mCourseSuccessActAdapter.notifyDataSetChanged();
            }
        });
        mCourseSuccessActAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                DaoVideoBean daoVideoBean = mDaoVideoBeans.get(position);
                toClass(StudyCourseActivity.class, false, Extras.MICROLESS_ID, MICROLESS_ID,Extras.FILE_NAME,daoVideoBean.getFilePath());
            }
        });

    }

    @OnClick({R.id.btn_delete, R.id.tv_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_delete:
                ArrayList<DaoVideoBean> daoVideoBeans = new ArrayList<>();
                for (DaoVideoBean daoVideoBean : mDaoVideoBeans) {
                    if (daoVideoBean.getIsCheck()) {
                        daoVideoBeans.add(daoVideoBean);
                        DaoUtil.getInstance().deleteVideoBean(daoVideoBean);//删除数据库信息
                        FileUtils.deleteSingleFile(getApplicationContext(), daoVideoBean.getFilePath());
                    }
                }
                DaoUtil.getInstance().deleteVideoList(daoVideoBeans);
                mDaoVideoBeans.removeAll(daoVideoBeans);
                mCourseSuccessActAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_more:
                CourseDialogFramgment courseDialogFramgment = new CourseDialogFramgment();
                Bundle bundle = new Bundle();
                courseDialogFramgment.setArguments(bundle);
                courseDialogFramgment.show(getSupportFragmentManager(), "");

                break;
        }
    }


    @Override
    public void onClickComplete(List<CoursePlayResultBean.VideoListBean> mChildValues) {

    }
}
