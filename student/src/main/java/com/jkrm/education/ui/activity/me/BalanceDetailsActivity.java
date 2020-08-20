package com.jkrm.education.ui.activity.me;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.BalanceDetailsAdapter;
import com.jkrm.education.bean.OrderBean;
import com.jkrm.education.bean.result.AccountBalancesBean;
import com.jkrm.education.bean.result.CreateAliPayOrderResultBean;
import com.jkrm.education.bean.result.CreateWechatPayOrderResultBean;
import com.jkrm.education.bean.result.PurseResultBean;
import com.jkrm.education.mvp.presenters.MyOrderFramgmentPresent;
import com.jkrm.education.mvp.views.MyOrderFragmentView;
import com.jkrm.education.util.RequestUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 余额明细
 */
public class BalanceDetailsActivity extends AwMvpActivity<MyOrderFramgmentPresent> implements MyOrderFragmentView.View {

    @BindView(R.id.toolbar_custom)
    AwViewCustomToolbar mToolbarCustom;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    private BalanceDetailsAdapter mBalanceDetailsAdapter;
    private int size=50;
    private int current=0;

    @Override
    protected MyOrderFramgmentPresent createPresenter() {
        return new MyOrderFramgmentPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_balance_details;
    }

    @Override
    protected void initView() {
        super.initView();
        setToolbarWithBackImg("余额明细", new AwViewCustomToolbar.OnLeftClickListener() {
            @Override
            public void onLeftTextClick() {
                finish();
            }
        });
        mBalanceDetailsAdapter=new BalanceDetailsAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity,mRcvData,mBalanceDetailsAdapter,false);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getOrderList(RequestUtil.getOrderListBody(current+"", size+"", ""));
    }

    @Override
    public void getOrderListSuccess(OrderBean data) {
        if(data.getRows().size()==0){
            mBalanceDetailsAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, "暂无数据", -1));
            return;
        }
        mBalanceDetailsAdapter.addAllData(data.getRows());
    }

    @Override
    public void getOrderListFail(String msg) {
        showMsg(msg);
        mBalanceDetailsAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, "暂无数据", -1));

    }

    @Override
    public void createWechatOrderSuccess(CreateWechatPayOrderResultBean bean) {

    }

    @Override
    public void createWechatOrderFail(String msg) {

    }

    @Override
    public void createAlipayOrderSuccess(CreateAliPayOrderResultBean bean) {

    }

    @Override
    public void createAlipayOrderFail(String msg) {

    }

    @Override
    public void getAccountBalancesSuccess(AccountBalancesBean bean) {

    }

    @Override
    public void getAccountBalancesFail(String msg) {

    }

    @Override
    public void pursePaySuccess(PurseResultBean bean) {

    }

    @Override
    public void pursePayFail(String msg) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
