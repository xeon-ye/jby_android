package com.jkrm.education.ui.activity.me;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.interfaces.IPermissionListener;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.AwRxPermissionUtil;
import com.hzw.baselib.util.AwSystemIntentUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.MeTeachersAdapter;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.result.TeachersResultBean;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.MeTeacherPresent;
import com.jkrm.education.mvp.views.MeTeacherView;
import com.jkrm.education.util.UserUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by hzw on 2019/8/4.
 */

public class MeTeacherContractActivity extends AwMvpActivity<MeTeacherPresent> implements MeTeacherView.View {

    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;

    private MeTeachersAdapter mAdapter;

    @Override
    protected MeTeacherPresent createPresenter() {
        return new MeTeacherPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_me_teacher_contract;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        setToolbarWithBackImg("任课教师", null);
    }

    @Override
    protected void initData() {
        super.initData();
        mAdapter = new MeTeachersAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mAdapter, false);
        mPresenter.getTeacherList(UserUtil.getStudId());
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId() == R.id.tv_mobile) {
                    TeachersResultBean bean = (TeachersResultBean) adapter.getItem(position);
                    if(AwDataUtil.isEmpty(bean.getPhone())) {
                        return;
                    }
                    AwRxPermissionUtil.getInstance().checkPermission(mActivity,
                            AwRxPermissionUtil.permissionsPhone, new IPermissionListener() {
                                @Override
                                public void granted() {
                                    AwSystemIntentUtil.callPhone(mActivity, bean.getPhone());
                                }

                                @Override
                                public void shouldShowRequestPermissionRationale() {
                                    showMsg("如需直接拨打该号码，请授予APP拨打电话权限");
                                }

                                @Override
                                public void needToSetting() {
                                    showMsg("如需直接拨打该号码，请授予APP拨打电话权限");
                                }
                            });
                }
            }
        });
    }

    @Override
    public void getTeacherListSuccess(List<TeachersResultBean> list) {
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
    public void getTeacherListFail(String msg) {
        mAdapter.clearData();
        mRcvData.removeAllViews();
        mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
    }
}
