package com.jkrm.education.ui.activity.order;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.hzw.baselib.widgets.ChosePayDialogFragment;
import com.jkrm.education.R;
import com.jkrm.education.bean.OrderBean;
import com.jkrm.education.bean.result.AccountBalancesBean;
import com.jkrm.education.bean.result.CreateAliPayOrderResultBean;
import com.jkrm.education.bean.result.CreateOrderResultBean;
import com.jkrm.education.bean.result.CreateWechatPayOrderResultBean;
import com.jkrm.education.bean.result.InvestResultBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.RepaidBalancePresent;
import com.jkrm.education.mvp.views.RepaidBalanceView;
import com.jkrm.education.ui.activity.RepaidBalanceActivity;
import com.jkrm.education.util.UserUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_to_be_paid_order;
    }

    @Override
    protected void initView() {
        super.initView();
        setToolbarWithBackImg("我的订单", null);
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

                    }

                    @Override
                    public void choseAliPay() {

                    }

                    @Override
                    public void chosePursePay() {

                    }
                });
                chosePayDialogFragment.show(getSupportFragmentManager(),"");
                break;
        }
    }
}
