package com.jkrm.education.ui.activity.order;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.hzw.baselib.widgets.ChosePayDialogFragment;
import com.jkrm.education.R;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.OrderBean;
import com.jkrm.education.bean.result.AccountBalancesBean;
import com.jkrm.education.bean.result.CreateAliPayOrderResultBean;
import com.jkrm.education.bean.result.CreateOrderResultBean;
import com.jkrm.education.bean.result.CreateWechatPayOrderResultBean;
import com.jkrm.education.bean.result.InvestResultBean;
import com.jkrm.education.bean.result.PurseResultBean;
import com.jkrm.education.bean.rx.RxPayType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.RepaidBalancePresent;
import com.jkrm.education.mvp.views.RepaidBalanceView;
import com.jkrm.education.student.wxapi.alipay.PayResult;
import com.jkrm.education.ui.activity.ConfirmAnOrderActivity;
import com.jkrm.education.ui.activity.PayFailActivity;
import com.jkrm.education.ui.activity.PaySuccessActivity;
import com.jkrm.education.ui.activity.RepaidBalanceActivity;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 待支付界面
 */
public class ToBePaidOrderActivity extends AwMvpActivity<RepaidBalancePresent> implements RepaidBalanceView.View {

    @BindView(R.id.toolbar_custom)
    AwViewCustomToolbar mToolbarCustom;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.tv_step)
    TextView mTvStep;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_course)
    TextView mTvCourse;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_price_dis)
    TextView mTvPriceDis;
    @BindView(R.id.tv_num)
    TextView mTvNum;
    @BindView(R.id.tv_acc)
    TextView mTvAcc;
    @BindView(R.id.tv_invest)
    TextView mTvInvest;
    @BindView(R.id.tv_agreement)
    TextView mTvAgreement;
    @BindView(R.id.tv_pay_sure)
    TextView mTvPaySure;
    @BindView(R.id.tv_price_real)
    TextView mTvPriceReal;
    @BindView(R.id.tv_timeing)
    TextView mTvTimeing;
    @BindView(R.id.tv_order_num)
    TextView mTvOrderNum;
    private OrderBean.RowsBean mBean;
    private int PAY_TYPE;
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_to_be_paid_order;
    }

    @Override
    protected void initView() {
        super.initView();
        setToolbarWithBackImg("我的订单", null);
        //微信
        mWxapi = WXAPIFactory.createWXAPI(this, MyApp.WX_APP_ID);
        mWxapi.registerApp(MyApp.WX_APP_ID);
    }

    @Override
    protected void initData() {
        super.initData();
        mBean = (OrderBean.RowsBean) getIntent().getExtras().getSerializable(Extras.KEY_ORDER);
        mTvCourse.setText(mBean.getDetaiList().get(0).getCourseName());
        mTvTitle.setText(mBean.getDetaiList().get(0).getGoodsName());
        mTvNum.setText("共" + mBean.getDetaiList().get(0).getComboNum() + "节课");
        mTvPrice.setText("￥" + mBean.getGoodsPrice());
        mTvPriceDis.setText("￥" + mBean.getGoodsPrice());
        mTvPriceReal.setText("￥" + mBean.getGoodsPrice());
        mPresenter.getAccountBalances(UserUtil.getUserId());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date parse = null;
        try {
            parse = simpleDateFormat.parse(mBean.getCreateTime());
            mTvTime.setText("下单时间:" + AwDateUtils.getyyyyMMddHHmmssWithNo(parse.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mTvOrderNum.setText("订单编号:" + mBean.getDetaiList().get(0).getOrderId());

    }

    @Override
    protected RepaidBalancePresent createPresenter() {
        return new RepaidBalancePresent(this);
    }


    @Override
    public void getAccountBalancesSuccess(AccountBalancesBean bean) {
        mTvAcc.setText(bean.getData() + "");
    }

    @Override
    public void getAccountBalancesFail(String msg) {

    }

    @Override
    public void getInvestListSuccess(List<InvestResultBean> list) {

    }

    @Override
    public void getInvestListFail(String msg) {

    }

    @Override
    public void createOrderSuccess(CreateOrderResultBean resultBean) {

    }

    @Override
    public void createOrderFail(String msg) {

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
                PayTask alipay = new PayTask(ToBePaidOrderActivity.this);
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

    }

    @Override
    public void pursePaySuccess(PurseResultBean bean) {
        showMsg("钱包支付成功");
        finish();
    }

    @Override
    public void pursePayFail(String msg) {
        showMsg("钱包支付失败");
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_invest, R.id.tv_agreement, R.id.tv_pay_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //充值
            case R.id.tv_invest:
                toClass(RepaidBalanceActivity.class, false);
                break;
            case R.id.tv_agreement:
                break;
            case R.id.tv_pay_sure:
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
                chosePayDialogFragment.show(getSupportFragmentManager(),"");
                break;
        }
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
                        finish();
                        break;
                    case RxPayType.PAY_CANCEL:
                        showMsg("微信支付取消");
                        toClass(PayFailActivity.class,false);
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
                        finish();
                        break;
                    case RxPayType.PAY_CANCEL:
                        showMsg("支付宝支付取消");
                        toClass(PayFailActivity.class,false);
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
                        finish();
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
}
