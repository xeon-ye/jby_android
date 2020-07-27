package com.jkrm.education.ui.activity.me;

import android.Manifest;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.interfaces.IPermissionListener;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.AwRxPermissionUtil;
import com.hzw.baselib.util.AwSystemIntentUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.MeContactListAdapter;
import com.jkrm.education.bean.result.ClassStudentBean;
import com.jkrm.education.bean.result.TeacherClassBean;
import com.jkrm.education.bean.test.TestMeClassesBean;
import com.jkrm.education.bean.test.TestMeClassesContactBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.MeContractListPresent;
import com.jkrm.education.mvp.views.MeContractListView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by hzw on 2019/5/14.
 */

public class MeContactListActivity extends AwMvpActivity<MeContractListPresent> implements MeContractListView.View {

    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;

    private MeContactListAdapter mAdapter;
    private TeacherClassBean mBean;

    @Override
    protected MeContractListPresent createPresenter() {
        return new MeContractListPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_me_contact_list;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
//        setToolbarWithBackImg("通讯录", null);
    }

    @Override
    protected void initData() {
        super.initData();
        mAdapter = new MeContactListAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mAdapter, true);

        mBean = (TeacherClassBean) getIntent().getSerializableExtra(Extras.KEY_BEAN_CLASSES);
        if(mBean == null) {
            showDialogToFinish("获取班级信息失败");
            return;
        }
        setToolbarWithBackImg(mBean.getClassName(), null);
        mPresenter.getClassesStudentList(mBean.getClassId());
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            ClassStudentBean bean = (ClassStudentBean) adapter.getItem(position);
            if("暂无手机号".equals(bean.getPhone())) {
                showMsg("暂无手机号，无法拨打");
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


        });
    }

    @Override
    public void getClassesStudentListSuccess(List<ClassStudentBean> list) {
        if(AwDataUtil.isEmpty(list)) {
            mAdapter.clearData();
            mRcvData.removeAllViews();
            mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
        } else {
            mAdapter.addAllData(list);
            mAdapter.loadMoreComplete();
            mAdapter.setEnableLoadMore(false);
            mAdapter.disableLoadMoreIfNotFullPage(mRcvData);
        }
    }

    @Override
    public void getClassesStudentListFail(String msg) {

    }
}
