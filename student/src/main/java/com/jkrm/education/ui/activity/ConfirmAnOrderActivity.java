package com.jkrm.education.ui.activity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.hzw.baselib.widgets.CustomNestRadioGroup;
import com.jkrm.education.R;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.GoodsDetai;
import com.jkrm.education.bean.result.AccountBalancesBean;
import com.jkrm.education.bean.result.CreateAliPayOrderResultBean;
import com.jkrm.education.bean.result.CreateOrderResultBean;
import com.jkrm.education.bean.result.CreateWechatPayOrderResultBean;
import com.jkrm.education.bean.result.MicroLessonResultBean;
import com.jkrm.education.bean.result.PurseResultBean;
import com.jkrm.education.bean.rx.RxPayType;
import com.jkrm.education.bean.rx.RxRePayType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.ConfirmAnOrderPresent;
import com.jkrm.education.mvp.views.ConfirmAnOrderView;
import com.jkrm.education.student.wxapi.alipay.PayResult;
import com.jkrm.education.ui.fragment.MicrolessonFragment;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 确认订单
 */
public class ConfirmAnOrderActivity extends AwMvpActivity<ConfirmAnOrderPresent> implements ConfirmAnOrderView.View {

    @BindView(R.id.cnr_chose_pay)
    CustomNestRadioGroup mCnrChosePay;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_now_buy)
    TextView mTvNowBuy;
    MicroLessonResultBean mMicroLessonResultBean;
    private final int PURSE_PAY = 0;//钱包支付
    private final int WECHAT_PAY = 1;//微信支付
    private final int ALI_PAY = 2;//支付宝支付
    @BindView(R.id.iv_img)
    ImageView mIvImg;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_goods_price)
    TextView mTvGoodsPrice;
    @BindView(R.id.toolbar_custom)
    AwViewCustomToolbar mToolbarCustom;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.rb_pay_purse)
    RadioButton mRbPayPurse;
    @BindView(R.id.ll_of_pay_purse)
    LinearLayout mLlOfPayPurse;
    @BindView(R.id.rb_alipay)
    RadioButton mRbAlipay;
    @BindView(R.id.ll_of_alipay)
    LinearLayout mLlOfAlipay;
    @BindView(R.id.rb_wechatpay)
    RadioButton mRbWechatpay;
    @BindView(R.id.ll_of_wechatpay)
    LinearLayout mLlOfWechatpay;
    @BindView(R.id.tv_acc_num)
    TextView mTvAccNum;
    @BindView(R.id.tv_buy)
    TextView mTvBuy;

    private int PAY_TYPE;
    private IWXAPI mWxapi;
    private static final int SDK_PAY_FLAG = 1;
    private String mMoney = "";
    CreateWechatPayOrderResultBean mCreateWechatPayOrderResultBean;//创建微信支付订单
    CreateAliPayOrderResultBean mCreateAliPayOrderResultBean;//创建支付宝订单
    public static long time = 60 * 60 * 24;
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
    protected ConfirmAnOrderPresent createPresenter() {
        return new ConfirmAnOrderPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_an_order;
    }

    @Override
    protected void initView() {
        super.initView();
        setToolbarWithBackImg("支付订单", new AwViewCustomToolbar.OnLeftClickListener() {
            @Override
            public void onLeftTextClick() {
                finish();
            }
        });
        mMicroLessonResultBean = (MicroLessonResultBean) getIntent().getSerializableExtra(Extras.KEY_COURSE_BEAN);
        Glide.with(this).load(mMicroLessonResultBean.getMlessonUrl()).into(mIvImg);
        mTvName.setText(mMicroLessonResultBean.getMlessonName());
        mTvGoodsPrice.setText("￥" + mMicroLessonResultBean.getMlessonPrice());
        mTvPrice.setText(mMicroLessonResultBean.getMlessonPrice());
        //微信
        mWxapi = WXAPIFactory.createWXAPI(this, MyApp.WX_APP_ID);
        mWxapi.registerApp(MyApp.WX_APP_ID);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                time--;
                long hh = time / 60 / 60 % 60;
                long mm = time / 60 % 60;
                long ss = time % 60;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(null!=mTvTime){
                            mTvTime.setText("支付剩余时间 " + hh + ":" + mm + ":" + ss);
                        }
                    }
                });
            }
        }, 0, 1000);

    }

    @Override
    protected void initListener() {
        super.initListener();

    }

    @Override
    protected void initData() {
        super.initData();
        String userId = UserUtil.getUserId();
        mPresenter.getAccountBalances(UserUtil.getUserId());
    }

    @OnClick(R.id.tv_buy)
    public void onViewClicked() {
        if (TextUtils.isEmpty(mMoney) || TextUtils.isEmpty(mMicroLessonResultBean.getMlessonPrice())) {
            showMsg("获取价钱失败，请稍后再试");
            return;
        }
        ArrayList<GoodsDetai> goodsDetais = new ArrayList<>();
        goodsDetais.add(new GoodsDetai(mMicroLessonResultBean.getId(), mMicroLessonResultBean.getMlessonName(), mMicroLessonResultBean.getMlessonPrice(), mMicroLessonResultBean.getMlessonCount(), "videos", mMicroLessonResultBean.getMlessonUrl()));
        switch (mCnrChosePay.getCheckedRadioButtonId()) {
            //钱包
            case R.id.rb_pay_purse:
                PAY_TYPE = PURSE_PAY;
                double v = Double.parseDouble(mMoney);//账户余额
                double v1 = Double.parseDouble(mMicroLessonResultBean.getMlessonPrice());//课程价钱
                //余额不足
                if (v < v1) {
                    showMsg("余额不足");
                    //余额不足
                    showDialogCustomLeftAndRight("余额不足，去充值", "取消", "充值", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dismissDialog();
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            toClass(RepaidBalanceActivity.class, false);
                            dismissDialog();
                        }
                    });
                    return;
                }
                break;
            //支付宝
            case R.id.rb_alipay:
                PAY_TYPE = ALI_PAY;
                break;
            //微信
            case R.id.rb_wechatpay:
                PAY_TYPE = WECHAT_PAY;
                break;
        }
        // mPresenter.createOrder(RequestUtil.getCreateOrderBody(mMicroLessonResultBean.getMlessonName(), mMicroLessonResultBean.getMlessonPrice(), "1", "1", goodsDetais));
        //测试金额 0.01
        //orderType  1. 充值  2.支付
         mPresenter.createOrder(RequestUtil.getCreateOrderBody(mMicroLessonResultBean.getMlessonName(),  mMicroLessonResultBean.getMlessonPrice(), "2", "1", goodsDetais,MicrolessonFragment.mStrCourseId,MicrolessonFragment.mStrCourseName));
       // mPresenter.createOrder(RequestUtil.getCreateOrderBody(mMicroLessonResultBean.getMlessonName(), "0.01", "2", "1", goodsDetais, MicrolessonFragment.mStrCourseId, MicrolessonFragment.mStrCourseName));
    }

    @Override
    public void createOrderSuccess(CreateOrderResultBean resultBean) {
        switch (PAY_TYPE) {
            //微信支付
            case WECHAT_PAY:
                 mPresenter.createWechatOrder(RequestUtil.getCreateWechatOrderBody(resultBean.getOrderId(),mMicroLessonResultBean.getMlessonPrice(),"2"));
                //测试金额 0.01
                //mPresenter.createWechatOrder(RequestUtil.getCreateWechatOrderBody(resultBean.getOrderId(), "0.01", "2"));
                break;
            //支付宝支付
            case ALI_PAY:
                mPresenter.createAlipayOrder(resultBean.getOrderId(),mMicroLessonResultBean.getMlessonPrice());
               // mPresenter.createAlipayOrder(resultBean.getOrderId(), "0.01");
                break;
            //钱包支付
            case PURSE_PAY:
                 mPresenter.pursePay(RequestUtil.gerCreatePursePayOrderBody(resultBean.getOrderId(),mMicroLessonResultBean.getMlessonPrice(),"2"));
              //  mPresenter.pursePay(RequestUtil.gerCreatePursePayOrderBody(resultBean.getOrderId(), mMicroLessonResultBean.getMlessonPrice(), "2"));
                break;
        }
    }

    @Override
    public void createOrderFail(String msg) {
        showMsg(msg);
    }

    @Override
    public void createWechatOrderSuccess(CreateWechatPayOrderResultBean bean) {
        mCreateWechatPayOrderResultBean = bean;
        //发起微信支付
        PayReq request = new PayReq();
        request.appId = bean.getAppid();
        request.partnerId = bean.getPartnerid();
        request.prepayId = bean.getPrepay_id();
        request.packageValue = bean.getPackageX();
        request.nonceStr = bean.getNonce_str();
        request.timeStamp = bean.getTimestamp();
        request.sign = bean.getSign();
        request.extData = Extras.ORDER_PAY;
        //发起请求，调起微信前去支付
        mWxapi.sendReq(request);
    }

    @Override
    public void createWechatOrderFail(String msg) {
        showMsg(msg);
    }

    @Override
    public void createAlipayOrderSuccess(CreateAliPayOrderResultBean bean) {
        mCreateAliPayOrderResultBean = bean;
        //支付宝订单信息
        final String orderInfo = bean.getData();
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(ConfirmAnOrderActivity.this);
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
    public void getAccountBalancesSuccess(AccountBalancesBean bean) {
        mMoney = bean.getData();
        mTvAccNum.setText(mMoney);
    }

    @Override
    public void getAccountBalancesFail(String msg) {
        showMsg(msg);
    }

    @Override
    public void pursePaySuccess(String bean) {
        showMsg("钱包支付成功");
        mMicroLessonResultBean.setWhetherBuy("1");
        toClass(PaySuccessActivity.class, true, Extras.KEY_COURSE_BEAN, mMicroLessonResultBean);
    }

    @Override
    public void pursePayFail(String msg) {
        showMsg("钱包支付失败");
        toClass(PayFailActivity.class, true, Extras.KEY_COURSE_BEAN, mMicroLessonResultBean);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    //重新支付
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void rePay(RxRePayType rePayType) {
        if (!rePayType.isRePay()) {
            finish();
            return;
        }
        switch (PAY_TYPE) {

            case WECHAT_PAY:
                //发起微信支付
                PayReq request = new PayReq();
                request.appId = mCreateWechatPayOrderResultBean.getAppid();
                request.partnerId = mCreateWechatPayOrderResultBean.getPartnerid();
                request.prepayId = mCreateWechatPayOrderResultBean.getPrepay_id();
                request.packageValue = mCreateWechatPayOrderResultBean.getPackageX();
                request.nonceStr = mCreateWechatPayOrderResultBean.getNonce_str();
                request.timeStamp = mCreateWechatPayOrderResultBean.getTimestamp();
                request.sign = mCreateWechatPayOrderResultBean.getSign();
                request.extData = Extras.ORDER_PAY;
                //发起请求，调起微信前去支付
                mWxapi.sendReq(request);
                break;
            case ALI_PAY:
                //支付宝订单信息
                final String orderInfo = mCreateAliPayOrderResultBean.getData();
                Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(ConfirmAnOrderActivity.this);
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
                break;
            case PURSE_PAY:
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
                        mMicroLessonResultBean.setWhetherBuy("1");
                        toClass(PaySuccessActivity.class, true, Extras.KEY_COURSE_BEAN, mMicroLessonResultBean);
                        break;
                    case RxPayType.PAY_CANCEL:
                        showMsg("微信支付取消");
                        toClass(PayFailActivity.class, false);
                        break;
                    case RxPayType.PAY_FAIL:
                        showMsg("微信支付失败");
                        toClass(PayFailActivity.class, false);
                        break;
                }
                break;
            //支付宝支付
            case RxPayType.ALI_PAY:
                switch (type.getPayCode()) {
                    case RxPayType.PAY_SUCCESS:
                        showMsg("支付宝支付成功");
                        mMicroLessonResultBean.setWhetherBuy("1");
                        toClass(PaySuccessActivity.class, true, Extras.KEY_COURSE_BEAN, mMicroLessonResultBean);
                        break;
                    case RxPayType.PAY_CANCEL:
                        showMsg("支付宝支付取消");
                        toClass(PayFailActivity.class, false);
                        break;
                    case RxPayType.PAY_FAIL:
                        showMsg("支付宝支付失败");
                        toClass(PayFailActivity.class, false);
                        break;
                }
                break;
            //钱包支付
            case RxPayType.PURSE_PAY:
                switch (type.getPayCode()) {
                    case RxPayType.PAY_SUCCESS:
                        showMsg("钱包支付成功");
                        mMicroLessonResultBean.setWhetherBuy("1");
                        toClass(PaySuccessActivity.class, true, Extras.KEY_COURSE_BEAN, mMicroLessonResultBean);
                        break;
                    case RxPayType.PAY_CANCEL:
                        showMsg("钱包支付取消");
                        toClass(PayFailActivity.class, false);
                        break;
                    case RxPayType.PAY_FAIL:
                        showMsg("钱包支付失败");
                        toClass(PayFailActivity.class, false);
                        break;
                }
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

}
