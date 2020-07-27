package com.jkrm.education.ui.activity.me;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwBaseActivity;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.ClassesAdapter;
import com.jkrm.education.bean.request.RequestClassesBean;
import com.jkrm.education.mvp.presenters.MeClassContractPresent;
import com.jkrm.education.mvp.presenters.MeUnbindClassPresent;
import com.jkrm.education.mvp.views.MeClassContractView;
import com.jkrm.education.mvp.views.MeUnbindClassView;
import com.jkrm.education.ui.activity.login.ChoiceClassesActivity;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeClassesActivity extends AwMvpActivity<MeUnbindClassPresent> implements MeUnbindClassView.View {

    @BindView(R.id.toolbar_custom)
    AwViewCustomToolbar mToolbarCustom;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.tv_join)
    TextView mTvJoin;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    ClassesAdapter mClassesAdapter;
    List<RequestClassesBean> mClassesBeans=new ArrayList<>();
    RequestClassesBean mRequestClassesBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_me_classes;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTransparent();
        setToolbarWithBackImg("我的班级", new AwViewCustomToolbar.OnLeftClickListener() {
            @Override
            public void onLeftTextClick() {
                finish();
            }
        });
        mClassesAdapter=new ClassesAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(this,mRcvData,mClassesAdapter,true);
    }


    @Override
    protected void initListener() {
        super.initListener();
        mClassesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<RequestClassesBean> data = adapter.getData();
                mRequestClassesBean=data.get(position);
                showDialogCustomLeftAndRight("是否确认退出" + mRequestClassesBean.getClassName(), "取消", "确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismissDialog();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.unBindSchoolClass(UserUtil.getAppUser().getTeacherId(),mRequestClassesBean.getClassId());
                        dismissDialog();
                    }
                });
            }
        });
    }

    @OnClick(R.id.tv_join)
    public void onViewClicked() {
        toClass(ChoiceClassesActivity.class,false);
    }

    @Override
    protected MeUnbindClassPresent createPresenter() {
        return new MeUnbindClassPresent(this);
    }

    @Override
    public void unBindSchoolClassSuccess(String data) {
        mClassesBeans.remove(mRequestClassesBean);
        mClassesAdapter.notifyDataSetChanged();
    }

    @Override
    public void unBindSchoolClassFail(String msg) {
        showMsg(msg);
    }

    @Override
    public void getClassesByIdSuccess(List<RequestClassesBean> data) {
        mClassesBeans=data;
        mClassesAdapter.addAllData(mClassesBeans);
    }

    @Override
    public void getClassesByIdFail(String msg) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getClassesById(UserUtil.getAppUser().getTeacherId());
    }
}
