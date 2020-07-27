package com.jkrm.education.ui.activity.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwFormatCheckUtils;
import com.hzw.baselib.util.AwSpUtil;
import com.jkrm.education.R;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.RegisterBean;
import com.jkrm.education.bean.result.LoginResult;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.FindPwdPresent;
import com.jkrm.education.mvp.views.FindPwdView;
import com.jkrm.education.ui.activity.MainActivity;
import com.jkrm.education.ui.activity.me.MeAgreementActivity;
import com.jkrm.education.ui.activity.me.PrivacyActivity;
import com.jkrm.education.util.RequestUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 验证码登录
 */
public class VerCodeLoginActivity extends AwMvpActivity<FindPwdPresent> implements FindPwdView.View {

    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.tv_psw)
    TextView mTvPsw;
    @BindView(R.id.cb_see_psw)
    TextView mCbSeePsw;
    @BindView(R.id.et_phone_num)
    EditText mEtPhoneNum;
    @BindView(R.id.btn_ver_code)
    Button mBtnVerCode;
    @BindView(R.id.tv_register)
    TextView mTvRegister;
    @BindView(R.id.tv_ver_time_hint)
    TextView mTvVerTimeHint;
    @BindView(R.id.tv_input_code)
    EditText mTvInputCode;
    private boolean mIsVerLogin;
    private TimeCount mTimeCount;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_ver_code_login;
    }


    @Override
    protected void initView() {
        super.initView();
/*
        SpannableString spannableString = new SpannableString(getResources().getString(R.string.phone_agreement_hint));
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                toClass(MeAgreementActivity.class, false);
            }
        }, 12, 16, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                toClass(PrivacyActivity.class, false);
            }
        }, 19, 23, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvRegister.setMovementMethod(LinkMovementMethod.getInstance());
        mTvRegister.setText(spannableString);
        mIsVerLogin = getIntent().getBooleanExtra(Extras.KEY_IS_VER_LOGIN, false);
*/


    }

    @Override
    protected void initListener() {
        super.initListener();
        mEtPhoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mBtnVerCode.setEnabled(editable.toString().trim().length() > 0);
            }
        });
    }

    @OnClick({R.id.iv_close, R.id.tv_psw, R.id.btn_ver_code, R.id.tv_register,R.id.tv_ver_time_hint})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.tv_psw:
                finish();
                break;
            case R.id.btn_ver_code:
               /* if (!AwFormatCheckUtils.checkMobileNumberValid(mEtPhoneNum.getText().toString().trim())) {
                    showMsg("请输入正确的手机号格式");
                    return;
                }*/
               // mPresenter.getVerifyCode(RequestUtil.getPhoneCodeRequest(mEtPhoneNum.getText().toString().trim()));
                mPresenter.verifyCodeLogin(RequestUtil.vercodeLogin(mEtPhoneNum.getText().toString().trim(),mTvInputCode.getText().toString().trim()));
                break;
            case R.id.tv_register:
                toClass(RegisterActivity.class,false);
                break;
            case R.id.tv_ver_time_hint:
                if (!AwFormatCheckUtils.checkMobileNumberValid(mEtPhoneNum.getText().toString().trim())) {
                    showMsg("请输入正确的手机号格式");
                    return;
                }
                mPresenter.getVerifyCode(RequestUtil.getPhoneCodeRequest(mEtPhoneNum.getText().toString().trim()));
                break;
        }
    }

    @Override
    protected FindPwdPresent createPresenter() {
        return new FindPwdPresent(this);
    }

    @Override
    public void getVerifyCodeSuccess(String code) {
        showMsg("验证码已发送到您的手机，请查收");
        mTimeCount = new TimeCount(MyApp.codeConuntDown, 1000);
        mTimeCount.start();
        if (MyApp.getInstance().isDefaultCountdown()) {
            mTvVerTimeHint.setText("获取验证码");
            mTvVerTimeHint.setClickable(true);
            mTvVerTimeHint.setTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            mTvVerTimeHint.setClickable(false);
            mTimeCount = new TimeCount(MyApp.codeConuntDown, 1000);
            mTimeCount.start();
            mTvVerTimeHint.setTextColor(getResources().getColor(R.color.gray));
        }
        //toClass(VerCodeCheckActivity.class, false, Extras.KEY_IS_VER_LOGIN, mIsVerLogin);
        AwSpUtil.saveString(MyConstant.SPConstant.XML_TEMPORARY, MyConstant.SPConstant.KEY_TEMPORARY_MOBILE, mEtPhoneNum.getText().toString().trim());
    }

    @Override
    public void verifyPhoneCodeSuccess() {

    }

    @Override
    public void verifyCodeLoginSuccess(LoginResult data) {
        MyApp.getInstance().saveToken(data.getToken());
        MyApp.getInstance().saveRoleID(data.getRoleId());
        MyApp.getInstance().saveLoginUser(data.getUser());
        MyApp.getInstance().saveAcc(mEtPhoneNum.getText().toString().trim());
        toClass(MainActivity.class,true);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
            if (mTvVerTimeHint != null) {
                mTvVerTimeHint.setText("获取验证码");
                mTvVerTimeHint.setClickable(true);
                mTvVerTimeHint.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            MyApp.codeConuntDown = millisUntilFinished;
            if (mTvVerTimeHint != null) {
                mTvVerTimeHint.setClickable(false);
                final long waitTime = millisUntilFinished / 1000;
                mTvVerTimeHint.setText(TextUtils.concat(waitTime + "s 后重新发送"));
                mTvVerTimeHint.setTextColor(getResources().getColor(R.color.gray));
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
