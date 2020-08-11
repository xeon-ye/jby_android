package com.jkrm.education.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.widgets.ChosePayDialogFragment;
import com.jkrm.education.R;
import com.jkrm.education.adapter.OrderAdapter;
import com.jkrm.education.bean.OrderBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.MyOrderFramgmentPresent;
import com.jkrm.education.mvp.views.MyOrderFragmentView;
import com.jkrm.education.ui.activity.order.CanceledOrderActivity;
import com.jkrm.education.ui.activity.order.PaidOrderActivity;
import com.jkrm.education.ui.activity.order.ToBePaidOrderActivity;
import com.jkrm.education.util.RequestUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/8/7 14:58
 */

public class MyOrderFragment extends AwMvpFragment<MyOrderFramgmentPresent> implements MyOrderFragmentView.View {

    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    private String mStep;
    private OrderAdapter mOrderAdapter;
    private int size=50;
    private int current=0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_myorder_layout;
    }

    @Override
    protected MyOrderFramgmentPresent createPresenter() {
        return new MyOrderFramgmentPresent(this);
    }

    @Override
    protected void initView() {
        super.initView();
        mStep = getArguments().getString(Extras.KEY_STEP);
        mOrderAdapter = new OrderAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(getActivity(),mRcvData, mOrderAdapter,false);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getOrderList(RequestUtil.getOrderListBody(current+"", size+"", mStep));
    }

    @Override
    protected void initListener() {
        super.initListener();
        mOrderAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.tv_pay:

                        showDialogCustomLeftAndRight("确认支付", "取消", "确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dismissDialog();
                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dismissDialog();
                                ChosePayDialogFragment chosePayDialogFragment=new ChosePayDialogFragment();
                                chosePayDialogFragment.setHasPurse(true);
                                chosePayDialogFragment.setOnChosePayListener(new ChosePayDialogFragment.onChosePayListener() {
                                    @Override
                                    public void choseWechatPay() {

                                    }

                                    @Override
                                    public void choseAliPay() {

                                    }

                                    @Override
                                    public void chosePursePay() {

                                    }
                                });
                                chosePayDialogFragment.show(getFragmentManager(),"");
                            }
                        });
                        break;
                    default:
                        OrderBean.RowsBean data = (OrderBean.RowsBean) adapter.getData().get(position);
                        if("1".equals(data.getStep())){
                            toClass(ToBePaidOrderActivity.class,false,Extras.KEY_ORDER,data);
                        }else if("2".equals(data.getStep())){
                            toClass(PaidOrderActivity.class,false,Extras.KEY_ORDER,data);
                        }else if("3".equals(data.getStep())){
                            toClass(CanceledOrderActivity.class,false,Extras.KEY_ORDER,data);
                        }
                        break;
                }

            }
        });

    }

    @Override
    public void getOrderListSuccess(OrderBean data) {
        mOrderAdapter.addAllData(data.getRows());
    }

    @Override
    public void getOrderListFail(String msg) {
        showMsg(msg);
    }


}
