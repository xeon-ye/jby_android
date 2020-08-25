package com.jkrm.education.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.hzw.baselib.widgets.CustomNestRadioGroup;
import com.jkrm.education.R;
import com.jkrm.education.bean.GoodsDetai;
import com.jkrm.education.bean.result.AccountBalancesBean;
import com.jkrm.education.bean.result.CreateAliPayOrderResultBean;
import com.jkrm.education.bean.result.CreateOrderResultBean;
import com.jkrm.education.bean.result.CreateWechatPayOrderResultBean;
import com.jkrm.education.bean.result.MicroLessonResultBean;
import com.jkrm.education.bean.result.PurseResultBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.ConfirmAnOrderPresent;
import com.jkrm.education.mvp.views.ConfirmAnOrderView;
import com.jkrm.education.ui.fragment.MicrolessonFragment;
import com.jkrm.education.util.RequestUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmAnOrderStepOneActivity extends AwMvpActivity<ConfirmAnOrderPresent> implements ConfirmAnOrderView.View {

    @BindView(R.id.toolbar_custom)
    AwViewCustomToolbar mToolbarCustom;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.iv_img)
    ImageView mIvImg;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_goods_price)
    TextView mTvGoodsPrice;
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
    @BindView(R.id.cnr_chose_pay)
    CustomNestRadioGroup mCnrChosePay;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_now_buy)
    TextView mTvNowBuy;
    MicroLessonResultBean mMicroLessonResultBean;


    @Override
    protected ConfirmAnOrderPresent createPresenter() {
        return new ConfirmAnOrderPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_an_order_step_one;
    }

    @Override
    protected void initView() {
        super.initView();
        setToolbarWithBackImg("确认订单", new AwViewCustomToolbar.OnLeftClickListener() {
            @Override
            public void onLeftTextClick() {
                finish();
            }
        });
        mMicroLessonResultBean = (MicroLessonResultBean) getIntent().getSerializableExtra(Extras.KEY_COURSE_BEAN);
        Glide.with(this).load(mMicroLessonResultBean.getMlessonUrl()).into(mIvImg);
        mTvTitle.setText(mMicroLessonResultBean.getMlessonName());
        mTvGoodsPrice.setText(mMicroLessonResultBean.getMlessonPrice());
        mTvPrice.setText("总计:" + mMicroLessonResultBean.getMlessonPrice());
    }


    @Override
    public void createOrderSuccess(CreateOrderResultBean resultBean) {
        toClass(ConfirmAnOrderActivity.class,true,Extras.KEY_COURSE_BEAN,mMicroLessonResultBean);
    }

    @Override
    public void createOrderFail(String msg) {
        showMsg(msg);
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

    @OnClick(R.id.tv_now_buy)
    public void onViewClicked() {
        ArrayList<GoodsDetai> goodsDetais = new ArrayList<>();
        goodsDetais.add(new GoodsDetai(mMicroLessonResultBean.getId(), mMicroLessonResultBean.getMlessonName(), mMicroLessonResultBean.getMlessonPrice(), mMicroLessonResultBean.getMlessonCount(), "videos", mMicroLessonResultBean.getMlessonUrl()));

        //  mPresenter.createOrder(RequestUtil.getCreateOrderBody(mMicroLessonResultBean.getMlessonName(),  mMicroLessonResultBean.getMlessonPrice(), "2", "1", goodsDetais,MicrolessonFragment.mStrCourseId,MicrolessonFragment.mStrCourseName));
        //测试金额  0.01
        mPresenter.createOrder(RequestUtil.getCreateOrderBody(mMicroLessonResultBean.getMlessonName(), "0.01", "2", "1", goodsDetais, MicrolessonFragment.mStrCourseId, MicrolessonFragment.mStrCourseName));

    }
}
