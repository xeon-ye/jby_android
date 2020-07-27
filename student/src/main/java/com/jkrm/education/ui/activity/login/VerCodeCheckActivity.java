package com.jkrm.education.ui.activity.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwSpUtil;
import com.hzw.baselib.widgets.VerifyCodeView;
import com.jkrm.education.R;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.RegisterBean;
import com.jkrm.education.bean.result.LoginResult;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.FindPwdPresent;
import com.jkrm.education.mvp.views.FindPwdView;
import com.jkrm.education.ui.activity.MainActivity;
import com.jkrm.education.util.RequestUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerCodeCheckActivity extends AwMvpActivity<FindPwdPresent> implements FindPwdView.View {

    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.tv_ver_hint)
    TextView mTvVerHint;
    @BindView(R.id.tv_ver_time_hint)
    TextView mTvVerTimeHint;
    @BindView(R.id.verify_code_view)
    VerifyCodeView mVerifyCodeView;
    private TimeCount mTimeCount;
    private String mStrPhone;
    private String mStrVerCode;
    private boolean mIsVercodeLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ver_code_check;
    }

    @Override
    protected void initView() {
        super.initView();
        mStrPhone = AwSpUtil.getString(MyConstant.SPConstant.XML_TEMPORARY, MyConstant.SPConstant.KEY_TEMPORARY_MOBILE, "");
        if (!AwDataUtil.isEmpty(mStrPhone)) {
            mTvVerHint.setText("短信发送至+86 " + mStrPhone);
        }
        mTimeCount = new TimeCount(MyApp.codeConuntDown, 1000);
        mTimeCount.start();
        if (MyApp.getInstance().isDefaultCountdown()) {
            mTvVerTimeHint.setText("重新发送验证码");
            mTvVerTimeHint.setClickable(true);
            mTvVerTimeHint.setTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            mTvVerTimeHint.setClickable(false);
            mTimeCount = new TimeCount(MyApp.codeConuntDown, 1000);
            mTimeCount.start();
            mTvVerTimeHint.setTextColor(getResources().getColor(R.color.gray));
        }
        mIsVercodeLogin = getIntent().getBooleanExtra(Extras.KEY_IS_VER_LOGIN, false);
        // toClass(ChoiceLearningSectionActivity.class,false);
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
        mTvVerTimeHint.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    @Override
    public void verifyPhoneCodeSuccess() {
        if (mIsVercodeLogin) {
            toClass(MainActivity.class, true);
        } else {
            //输入密码
            //toClass(InputPswActivity.class, false);
            String phone = AwSpUtil.getString(MyConstant.SPConstant.XML_TEMPORARY, MyConstant.SPConstant.KEY_TEMPORARY_MOBILE, "");
            mPresenter.userRegister(RequestUtil.getRegisterBody(phone,"","",""));
        }
    }

    @Override
    public void verifyCodeLoginSuccess(LoginResult data) {
        MyApp.getInstance().saveToken(data.getToken());
        MyApp.getInstance().saveRoleID(data.getRoleId());
        MyApp.getInstance().saveLoginUser(data.getUser());
        MyApp.getInstance().saveAcc(mStrPhone);
        toClass(MainActivity.class,true);
    }

    @Override
    public void verifyCodeLoginFail(String msg) {
        showMsg(msg);
    }

    @Override
    public void userRegisterSuccess(RegisterBean data) {
        toClass(InputPswActivity.class, false,Extras.KEY_REGISTER_ID,data.getId());
    }

    @Override
    public void userRegisterFail(String msg) {
        showMsg(msg);
    }


    @OnClick({R.id.iv_close, R.id.tv_ver_time_hint})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.tv_ver_time_hint:
                if ("重新发送验证码".equals(mTvVerTimeHint.getText().toString())) {
                    mPresenter.getVerifyCode(RequestUtil.getPhoneCodeRequest(mStrPhone));
                }
                break;
        }
    }


    @Override
    protected void initListener() {
        super.initListener();
        mVerifyCodeView.setInputCompleteListener(new VerifyCodeView.InputCompleteListener() {
            @Override
            public void inputComplete() {
                mStrVerCode = mVerifyCodeView.getEditContent();
                MyApp.mStrVerCode = mStrVerCode;
                // toClass(ChoiceLearningSectionActivity.class,false);
                if(mIsVercodeLogin){
                    mPresenter.verifyCodeLogin(RequestUtil.vercodeLogin(mStrPhone,mStrVerCode));
                }else{
                    //toClass(InputPswActivity.class, false);
                    // mPresenter.verifyPhoneCode(RequestUtil.verifyPhoneCodeRequest(mStrPhone, mStrVerCode));
                    mPresenter.userRegister(RequestUtil.getRegisterBody(mStrPhone,mStrVerCode,"",""));
                }

            }

            @Override
            public void invalidContent() {

            }
        });
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
                mTvVerTimeHint.setText("重新发送验证码");
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
