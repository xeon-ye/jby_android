package com.jkrm.education.ui.activity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.hzw.baselib.widgets.ChosePayDialogFragment;
import com.jkrm.education.R;
import com.jkrm.education.adapter.RepaidBalanceAdapter;
import com.jkrm.education.adapter.VideoPointAdapter;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.GoodsDetai;
import com.jkrm.education.bean.result.AccountBalancesBean;
import com.jkrm.education.bean.result.CreateAliPayOrderResultBean;
import com.jkrm.education.bean.result.CreateOrderResultBean;
import com.jkrm.education.bean.result.CreateWechatPayOrderResultBean;
import com.jkrm.education.bean.result.InvestResultBean;
import com.jkrm.education.bean.result.PurseResultBean;
import com.jkrm.education.bean.rx.RxPayType;
import com.jkrm.education.bean.type.TypeCourseBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.RepaidBalancePresent;
import com.jkrm.education.mvp.views.RepaidBalanceView;
import com.jkrm.education.student.wxapi.alipay.PayResult;
import com.jkrm.education.ui.activity.me.BalanceDetailsActivity;
import com.jkrm.education.ui.fragment.MicrolessonFragment;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * c充值页面  --- 我的账户
 */

public class RepaidBalanceActivity extends AwMvpActivity<RepaidBalancePresent> implements RepaidBalanceView.View {

    @BindView(R.id.toolbar_custom)
    AwViewCustomToolbar mToolbarCustom;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.tv_num)
    TextView mTvNum;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.tv_repaid_balance_num)
    TextView mTvRepaidBalanceNum;
    @BindView(R.id.tv_now_buy)
    TextView mTvNowBuy;
    RepaidBalanceAdapter  mAdapter;
    List<InvestResultBean> mInvestResultList=new ArrayList<>();
    private InvestResultBean mInvestResultBean;

    private final int WECHAT_PAY = 1;//微信支付
    private final int ALI_PAY = 2;//支付宝支付
    private int PAY_TYPE;
    private IWXAPI mWxapi;
    private static final int SDK_PAY_FLAG = 1;



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
                        showMsg("充值成功");
                        finish();
                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        showMsg("取消充值");
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showMsg("充值失败");
                        finish();
                    }
                }
            }
        }
    };


    @Override
    protected RepaidBalancePresent createPresenter() {
        return new RepaidBalancePresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_repaid_balance;
    }

    @Override
    protected void initView() {
        super.initView();

        setToolbarWithBackImgAndRightView("我的账户", "账单", new AwViewCustomToolbar.OnRightClickListener() {
            @Override
            public void onRightTextClick() {
                toClass(BalanceDetailsActivity.class,false);
            }
        });
        mToolbarCustom.setRTextColor(R.color.colorAccent);
        //微信
        mWxapi = WXAPIFactory.createWXAPI(this, MyApp.WX_APP_ID);
        mWxapi.registerApp(MyApp.WX_APP_ID);

    }

    @Override
    protected void initData() {
        super.initData();
        mAdapter = new RepaidBalanceAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvData, mAdapter, 3);

        mPresenter.getAccountBalances(UserUtil.getUserId());
        mPresenter.getInvestList("ios");
    }

    @Override
    public void getAccountBalancesSuccess(AccountBalancesBean bean) {
        DecimalFormat decimalFormat=new DecimalFormat("#0.00");
        mTvNum.setText( decimalFormat.format(Double.parseDouble(bean.getData())));
    }

    @Override
    public void getAccountBalancesFail(String msg) {

    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                InvestResultBean investResultBean = (InvestResultBean) adapter.getItem(position);
                for(int i=0; i<adapter.getItemCount(); i++) {
                    InvestResultBean tempBean = (InvestResultBean) adapter.getItem(i);
                    tempBean.setSelect(false);
                }
                investResultBean.setSelect(true);
                mTvRepaidBalanceNum.setText(investResultBean.getPrice());
                mAdapter.notifyDataSetChanged();
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                InvestResultBean investResultBean = (InvestResultBean) adapter.getItem(position);
                for(int i=0; i<adapter.getItemCount(); i++) {
                    InvestResultBean tempBean = (InvestResultBean) adapter.getItem(i);
                    tempBean.setSelect(false);
                }
                investResultBean.setSelect(true);
                mTvRepaidBalanceNum.setText(investResultBean.getPrice());
                mAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void getInvestListSuccess(List<InvestResultBean> list) {
        if(AwDataUtil.isEmpty(list)) {
            showDialogToFinish("请稍后重试");
            mAdapter.clearData();
            mRcvData.removeAllViews();
            mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
            return;
        }
        mInvestResultList=list;
        mAdapter.addAllData(mInvestResultList);
        mAdapter.loadMoreComplete();
        mAdapter.setEnableLoadMore(false);
        selectNormal();
    }

    private void selectNormal() {
        List<InvestResultBean> data = mAdapter.getData();
        for (int i = 0; i < mAdapter.getData().size(); i++) {
            if(i==0){
                data.get(0).setSelect(true);
            }else{
                data.get(i).setSelect(false);
            }
        }
     /*   for (int i = 0; i < mInvestResultList.size(); i++) {
            if(i==0){
                mInvestResultList.get(0).setSelect(true);
            }else{
                mInvestResultList.get(i).setSelect(false);
            }
        }*/
        mAdapter.notifyDataSetChanged();
        mTvRepaidBalanceNum.setText( data.get(0).getPrice());
    }

    @Override
    public void getInvestListFail(String msg) {
        showMsg(msg);
    }

    @Override
    public void createOrderSuccess(CreateOrderResultBean resultBean) {
        //alertDialog(resultBean);
         ChosePayDialogFragment chosePayDialogFragment=new ChosePayDialogFragment();
        chosePayDialogFragment.show(getSupportFragmentManager(),"");
        chosePayDialogFragment.setOnChosePayListener(new ChosePayDialogFragment.onChosePayListener() {
            @Override
            public void choseWechatPay() {
                PAY_TYPE=WECHAT_PAY;
                chosePay(resultBean);
            }

            @Override
            public void choseAliPay() {
                PAY_TYPE=ALI_PAY;
                chosePay(resultBean);
            }

            @Override
            public void chosePursePay() {

            }
        });

    }

    @Override
    public void createOrderFail(String msg) {
        showMsg(msg);
    }

    @Override
    public void createWechatOrderSuccess(CreateWechatPayOrderResultBean bean) {
        //发起微信支付
        PayReq request = new PayReq();
        request.appId = bean.getAppid();
        request.partnerId = bean.getPartnerid();
        request.prepayId = bean.getPrepay_id();
        request.packageValue = bean.getPackageX();
        request.nonceStr = bean.getNonce_str();
        request.timeStamp = bean.getTimestamp();
        request.extData= Extras.RECHARGE_PAY;//充值
        request.sign = bean.getSign();
        //发起请求，调起微信前去支付
        mWxapi.sendReq(request);
    }

    @Override
    public void createWechatOrderFail(String msg) {
        showMsg(msg);
    }

    @Override
    public void createAlipayOrderSuccess(CreateAliPayOrderResultBean bean) {
        //支付宝订单信息
        final String orderInfo = bean.getData();
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(RepaidBalanceActivity.this);
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
    public void pursePaySuccess(String bean) {

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

    @OnClick(R.id.btn_pay)
    public void onViewClicked() {
        List<InvestResultBean> data = mAdapter.getData();
        ArrayList<GoodsDetai> goodsDetais = new ArrayList<>();
        for (InvestResultBean datum : data) {
            if(datum.isSelect()){
                mInvestResultBean = datum;
                goodsDetais.add(new GoodsDetai(datum.getId(), datum.getMsg(), datum.getPrice(), "1", "recharge", ""));
            }
        }

        mPresenter.createOrder(RequestUtil.getCreateOrderBody(mInvestResultBean.getMsg(),mInvestResultBean.getPrice(),"1","1",goodsDetais, MicrolessonFragment.mStrCourseId,MicrolessonFragment.mStrCourseName));
    }

    private void alertDialog(CreateOrderResultBean resultBean){
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();  //注意：必须在window.setContentView之前show
        Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog_layout_of_chose_pay);
        LinearLayout ll_of_ali = (LinearLayout) window.findViewById(R.id.ll_of_ali);
        ll_of_ali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PAY_TYPE=ALI_PAY;
                chosePay(resultBean);
                dialog.dismiss();
            }
        });
        LinearLayout ll_of_wechat = (LinearLayout) window.findViewById(R.id.ll_of_wechat);
        //点击确定按钮让对话框消失
        ll_of_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PAY_TYPE=WECHAT_PAY;
                chosePay(resultBean);
                dialog.dismiss();
            }
        });

    }
    private void chosePay(CreateOrderResultBean resultBean){
        switch (PAY_TYPE) {
            //微信支付
            case WECHAT_PAY:
                 mPresenter.createWechatOrder(RequestUtil.getCreateWechatOrderBody(resultBean.getOrderId(),mInvestResultBean.getPrice(),"1"));
                break;
            //支付宝支付
            case ALI_PAY:
                 mPresenter.createAlipayOrder(resultBean.getOrderId(),mInvestResultBean.getPrice());
                break;
        }
    }
    //支付结果
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByPayCode(RxPayType type) {
        switch (type.getPayType()) {
            //微信支付
            case RxPayType.WECHAT_RECHARGE:
                switch (type.getPayCode()) {
                    case RxPayType.PAY_SUCCESS:
                        showMsg("充值成功");
                        finish();
                        break;
                    case RxPayType.PAY_CANCEL:
                        showMsg("充值取消");
                        finish();
                        break;
                    case RxPayType.PAY_FAIL:
                        showMsg("充值失败");
                        finish();
                        break;
                }
                break;

        }
    }
}
