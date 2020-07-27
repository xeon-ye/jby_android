package com.jkrm.education.ui.activity.me;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.MeClassesAdapter;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.result.TeacherClassBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.MeClassContractPresent;
import com.jkrm.education.mvp.views.MeClassContractView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by hzw on 2019/8/4.
 */

public class MeClassContractActivity extends AwMvpActivity<MeClassContractPresent> implements MeClassContractView.View {

    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;

    private MeClassesAdapter mAdapter;

    @Override
    protected MeClassContractPresent createPresenter() {
        return new MeClassContractPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_me_class_contract;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        setToolbarWithBackImg("班级通讯录", null);
    }

    @Override
    protected void initData() {
        super.initData();
        mAdapter = new MeClassesAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mAdapter, false);
        mPresenter.getTeacherClassList(MyApp.getInstance().getAppUser().getTeacherId());
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId() == R.id.tv_contact) {
                    TeacherClassBean bean = (TeacherClassBean) adapter.getItem(position);
                    toClass(MeContactListActivity.class, false, Extras.KEY_BEAN_CLASSES, bean);
                }
            }
        });
    }

    @Override
    public void getTeacherClassListSuccess(List<TeacherClassBean> list) {
        if(AwDataUtil.isEmpty(list)) {
            mAdapter.clearData();
            mRcvData.removeAllViews();
            mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
            return;
        }

        mAdapter.addAllData(list);
        mAdapter.loadMoreComplete();
        mAdapter.setEnableLoadMore(false);
        mAdapter.disableLoadMoreIfNotFullPage(mRcvData);
    }

    @Override
    public void getTeacherClassListFail(String msg) {
        mAdapter.clearData();
        mRcvData.removeAllViews();
        mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
    }
}
