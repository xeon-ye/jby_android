package com.jkrm.education.ui.activity.login;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwFormatCheckUtils;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwSpUtil;
import com.jkrm.education.R;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.RegisterBean;
import com.jkrm.education.bean.result.LoginResult;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.FindPwdPresent;
import com.jkrm.education.mvp.views.FindPwdView;
import com.jkrm.education.util.RequestUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hzw on 2019/5/14.
 */

public class FindPwdActivity extends AwMvpActivity<FindPwdPresent> implements FindPwdView.View {


    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.tv_sendVerifyCode)
    TextView mTvSendVerifyCode;
    @BindView(R.id.et_verifyCode)
    EditText mEtVerifyCode;

    private TimeCount mTimeCount;

    @Override
    protected FindPwdPresent createPresenter() {
        return new FindPwdPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_find_pwd;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        setToolbarWithBackImg("找回密码", null);
        if(MyApp.getInstance().isDefaultCountdown()) {
            mTvSendVerifyCode.setText("发送验证码");
            mTvSendVerifyCode.setClickable(true);
        } else {
            mTvSendVerifyCode.setClickable(false);
            mTimeCount = new TimeCount(MyApp.codeConuntDown, 1000);
            mTimeCount.start();
        }
    }

    @Override
    protected void initData() {
        super.initData();
        AwLog.d("临时存储手机号: " + AwSpUtil.getString(MyConstant.SPConstant.XML_TEMPORARY, MyConstant.SPConstant.KEY_TEMPORARY_MOBILE, ""));
        setText(mEtPhone, AwSpUtil.getString(MyConstant.SPConstant.XML_TEMPORARY, MyConstant.SPConstant.KEY_TEMPORARY_MOBILE, ""));
    }

    @OnClick({R.id.tv_sendVerifyCode, R.id.btn_next})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_sendVerifyCode:
                String phone = mEtPhone.getText().toString();
                if(AwDataUtil.isEmpty(phone)) {
                    showMsg("手机号不可为空");
                    return;
                }
                if(!AwFormatCheckUtils.checkMobileNumberValid(phone)) {
                    showMsg("请输入正确的手机号格式");
                    return;
                }
                mPresenter.getVerifyCode(RequestUtil.getPhoneCodeRequest(phone));
                break;
            case R.id.btn_next:
                String verifyCode = mEtVerifyCode.getText().toString();
                if(AwDataUtil.isEmpty(verifyCode)) {
                    showMsg("请输入验证码");
                    return;
                }
                String phoneVerify = mEtPhone.getText().toString();
                if(AwDataUtil.isEmpty(phoneVerify)) {
                    showMsg("手机号不可为空");
                }
                if(!AwFormatCheckUtils.checkMobileNumberValid(phoneVerify)) {
                    showMsg("请输入正确的手机号格式");
                    return;
                }
                mPresenter.verifyPhoneCode(RequestUtil.verifyPhoneCodeRequest(phoneVerify, verifyCode));
                break;
        }
    }

    @Override
    public void getVerifyCodeSuccess(String code) {
        showMsg("验证码已发送到您的手机，请查收");
        mTimeCount = new TimeCount(MyApp.codeConuntDown, 1000);
        mTimeCount.start();
        AwSpUtil.saveString(MyConstant.SPConstant.XML_TEMPORARY, MyConstant.SPConstant.KEY_TEMPORARY_MOBILE, mEtPhone.getText().toString());
    }

    @Override
    public void verifyPhoneCodeSuccess() {
        AwSpUtil.saveString(MyConstant.SPConstant.XML_TEMPORARY, MyConstant.SPConstant.KEY_TEMPORARY_MOBILE, "");
        toClass(ResetPwdActivity.class, true, Extras.COMMON_PARAMS, mEtPhone.getText().toString());
    }

    @Override
    public void verifyCodeLoginSuccess(LoginResult data) {

    }

    @Override
    public void verifyCodeLoginFail(String msg) {

    }

    @Override
    public void userRegisterSuccess(RegisterBean data) {

    }

    @Override
    public void userRegisterFail(String msg) {

    }

    /**
     * 倒计时的内部类
     */
    private class TimeCount extends CountDownTimer {
        // 参数依次为总时长,和计时的时间间隔
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            MyApp.getInstance().setDefaultCountdown();
            if (mTvSendVerifyCode != null) {
                mTvSendVerifyCode.setText("发送验证码");
                mTvSendVerifyCode.setClickable(true);
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            MyApp.codeConuntDown = millisUntilFinished;
            if (mTvSendVerifyCode != null) {
                mTvSendVerifyCode.setClickable(false);
                final long waitTime = millisUntilFinished / 1000;
                mTvSendVerifyCode.setText(TextUtils.concat(waitTime + " s"));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimeCount != null && MyApp.getInstance().isDefaultCountdown()) {
            mTimeCount.cancel();
        }
    }
}
