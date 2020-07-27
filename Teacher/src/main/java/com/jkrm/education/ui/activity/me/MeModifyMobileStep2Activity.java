package com.jkrm.education.ui.activity.me;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwFormatCheckUtils;
import com.hzw.baselib.util.AwSpUtil;
import com.jkrm.education.R;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.rx.RxUpdateUserBean;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.FindPwdPresent;
import com.jkrm.education.mvp.presenters.ModifyMobilePresent;
import com.jkrm.education.mvp.views.FindPwdView;
import com.jkrm.education.mvp.views.ModifyMobileView;
import com.jkrm.education.ui.activity.login.FindPwdActivity;
import com.jkrm.education.ui.activity.login.ResetPwdActivity;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hzw on 2019/5/14.
 */

public class MeModifyMobileStep2Activity extends AwMvpActivity<ModifyMobilePresent> implements ModifyMobileView.View {
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_verifyCode)
    EditText mEtVerifyCode;
    @BindView(R.id.tv_sendVerifyCode)
    TextView mTvSendVerifyCode;
    @BindView(R.id.btn_next)
    Button mBtnNext;

    private TimeCount mTimeCount;

    @Override
    protected ModifyMobilePresent createPresenter() {
        return new ModifyMobilePresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_me_modify_mobile_step2;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        setToolbarWithBackImg("修改手机号", null);
    }

    @Override
    protected void initData() {
        super.initData();
        setText(mEtPhone, AwSpUtil.getString(MyConstant.SPConstant.XML_TEMPORARY, MyConstant.SPConstant.KEY_TEMPORARY_MOBILE, ""));
        if(MyApp.getInstance().isDefaultCountdown()) {
            mTvSendVerifyCode.setText("发送验证码");
            mTvSendVerifyCode.setClickable(true);
        } else {
            mTvSendVerifyCode.setClickable(false);
            mTimeCount = new TimeCount(MyApp.codeConuntDown, 1000);
            mTimeCount.start();
        }
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
                if(phone.equals(UserUtil.getPhone())) {
                    showDialog("您输入的手机号与原有手机号相同，无需修改");
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
        mPresenter.modifyMobile(RequestUtil.updateMobileRequest(mEtPhone.getText().toString(), MyApp.getInstance().getAppUser().getId()));
    }

    @Override
    public void modifyMobileSuccess() {
        showMsg("手机号修改成功");
        setResult(RESULT_OK);
        EventBus.getDefault().postSticky(new RxUpdateUserBean(mEtPhone.getText().toString(), UserUtil.getAvatar(), UserUtil.getNickname()));
        finish();
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
