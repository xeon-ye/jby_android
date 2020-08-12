package com.jkrm.education.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alipay.sdk.app.PayTask;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.widgets.ChosePayDialogFragment;
import com.jkrm.education.R;
import com.jkrm.education.adapter.OrderAdapter;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.OrderBean;
import com.jkrm.education.bean.result.AccountBalancesBean;
import com.jkrm.education.bean.result.CreateAliPayOrderResultBean;
import com.jkrm.education.bean.result.CreateWechatPayOrderResultBean;
import com.jkrm.education.bean.result.PurseResultBean;
import com.jkrm.education.bean.rx.RxPayType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.MyOrderFramgmentPresent;
import com.jkrm.education.mvp.views.MyOrderFragmentView;
import com.jkrm.education.student.wxapi.alipay.PayResult;
import com.jkrm.education.ui.activity.PayFailActivity;
import com.jkrm.education.ui.activity.PaySuccessActivity;
import com.jkrm.education.ui.activity.order.CanceledOrderActivity;
import com.jkrm.education.ui.activity.order.PaidOrderActivity;
import com.jkrm.education.ui.activity.order.ToBePaidOrderActivity;
import com.jkrm.education.util.RequestUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

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
    private IWXAPI mWxapi;
    private String mMoney = "";
    private static final int SDK_PAY_FLAG = 1;
    CreateWechatPayOrderResultBean mCreateWechatPayOrderResultBean;//创建微信支付订单
    CreateAliPayOrderResultBean mCreateAliPayOrderResultBean;//创建支付宝订单

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        EventBus.getDefault().post(new RxPayType(RxPayType.ALI_PAY, RxPayType.PAY_SUCCESS));
                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        EventBus.getDefault().post(new RxPayType(RxPayType.ALI_PAY, RxPayType.PAY_CANCEL));
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        EventBus.getDefault().post(new RxPayType(RxPayType.ALI_PAY, RxPayType.PAY_FAIL));
                    }
                }
            }
        }
    };
    private OrderBean.RowsBean mBean;

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
        //微信
        mWxapi = WXAPIFactory.createWXAPI(getActivity(), MyApp.WX_APP_ID);
        mWxapi.registerApp(MyApp.WX_APP_ID);
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
                mBean = (OrderBean.RowsBean) adapter.getData().get(position);
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
                                        //  mPresenter.createWechatOrder(RequestUtil.getCreateWechatOrderBody(mBean.getId(),mBean.getGoodsPrice(),"2"));
                                        //测试金额 0.01
                                        mPresenter.createWechatOrder(RequestUtil.getCreateWechatOrderBody(mBean.getId(), "0.01", "2"));

                                    }

                                    @Override
                                    public void choseAliPay() {
                                        //mPresenter.createAlipayOrder(mBean.getId(),mBean.getGoodsPrice());
                                        mPresenter.createAlipayOrder(mBean.getId(), "0.01");
                                    }

                                    @Override
                                    public void chosePursePay() {
                                        //mPresenter.pursePay(RequestUtil.gerCreatePursePayOrderBody(mBean.getId(),mBean.getGoodsPrice(),"2"));
                                        mPresenter.pursePay(RequestUtil.gerCreatePursePayOrderBody(mBean.getId(),"0.01","2"));

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

    @Override
    public void createWechatOrderSuccess(CreateWechatPayOrderResultBean bean) {
        mCreateWechatPayOrderResultBean=bean;
        //发起微信支付
        PayReq request = new PayReq();
        request.appId = bean.getAppid();
        request.partnerId = bean.getPartnerid();
        request.prepayId = bean.getPrepay_id();
        request.packageValue = bean.getPackageX();
        request.nonceStr = bean.getNonce_str();
        request.timeStamp = bean.getTimestamp();
        request.sign = bean.getSign();
        request.extData=Extras.ORDER_PAY;
        //发起请求，调起微信前去支付
        mWxapi.sendReq(request);
    }

    @Override
    public void createWechatOrderFail(String msg) {
        showMsg(msg);
    }

    @Override
    public void createAlipayOrderSuccess(CreateAliPayOrderResultBean bean) {
        mCreateAliPayOrderResultBean=bean;
        //支付宝订单信息
        final String orderInfo = bean.getData();
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
                //发起支付 是否需要loading
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Override
    public void createAlipayOrderFail(String msg) {
        showMsg(msg);
    }

    @Override
    public void pursePaySuccess(PurseResultBean bean) {
        showMsg("钱包支付成功");
       // finish();
    }

    @Override
    public void pursePayFail(String msg) {
        showMsg("钱包支付失败");
        //finish();
    }

    @Override
    public void getAccountBalancesSuccess(AccountBalancesBean bean) {

    }

    @Override
    public void getAccountBalancesFail(String msg) {

    }

    //支付结果
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByPayCode(RxPayType type) {
        switch (type.getPayType()) {
            //微信支付
            case RxPayType.WECHAT_PAY:
                switch (type.getPayCode()) {
                    case RxPayType.PAY_SUCCESS:
                        showMsg("微信支付成功");
                        mBean.setStep("2");
                        mOrderAdapter.notifyDataSetChanged();
                        break;
                    case RxPayType.PAY_CANCEL:
                        showMsg("微信支付取消");
                        break;
                    case RxPayType.PAY_FAIL:
                        showMsg("微信支付失败");
                        toClass(PayFailActivity.class,false);
                        break;
                }
                break;
            //支付宝支付
            case RxPayType.ALI_PAY:
                switch (type.getPayCode()) {
                    case RxPayType.PAY_SUCCESS:
                        showMsg("支付宝支付成功");
                        mBean.setStep("2");
                        mOrderAdapter.notifyDataSetChanged();
                        break;
                    case RxPayType.PAY_CANCEL:
                        showMsg("支付宝支付取消");
                        break;
                    case RxPayType.PAY_FAIL:
                        showMsg("支付宝支付失败");
                        toClass(PayFailActivity.class,false);
                        break;
                }
                break;
            //钱包支付
            case RxPayType.PURSE_PAY:
                switch (type.getPayCode()) {
                    case RxPayType.PAY_SUCCESS:
                        showMsg("钱包支付成功");
                        mBean.setStep("2");
                        break;
                    case RxPayType.PAY_CANCEL:
                        showMsg("钱包支付取消");
                        toClass(PayFailActivity.class,false);
                        break;
                    case RxPayType.PAY_FAIL:
                        showMsg("钱包支付失败");
                        toClass(PayFailActivity.class,false);
                        break;
                }
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
