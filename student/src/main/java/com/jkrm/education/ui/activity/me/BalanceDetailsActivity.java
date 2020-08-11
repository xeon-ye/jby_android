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
        mBalanceDetailsAdapter.addAllData(data.getRows());
    }

    @Override
    public void getOrderListFail(String msg) {
        showMsg(msg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
