package com.jkrm.education.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.bean.rx.RxRePayType;
import com.jkrm.education.mvp.presenters.PayFailPresenter;
import com.jkrm.education.mvp.views.PayFailView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayFailActivity extends AwMvpActivity<PayFailPresenter> implements PayFailView.View {

    @BindView(R.id.btn_con_pay)
    Button mBtnConPay;
    @BindView(R.id.btn_back_to_main)
    Button mBtnBackToMain;

    @Override
    protected PayFailPresenter createPresenter() {
        return new PayFailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_fail;
    }

    @Override
    protected void initView() {
        super.initView();
        setToolbar("支付失败", new AwViewCustomToolbar.OnLeftClickListener() {
            @Override
            public void onLeftTextClick() {
                finish();
            }
        });
    }



    @OnClick({R.id.btn_con_pay, R.id.btn_back_to_main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_con_pay:
                EventBus.getDefault().post(new RxRePayType(true));
                finish();
                break;
            case R.id.btn_back_to_main:
                EventBus.getDefault().post(new RxRePayType(false));
                finish();
                break;
        }
    }
}
