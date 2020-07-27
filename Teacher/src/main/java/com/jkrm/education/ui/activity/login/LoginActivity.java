package com.jkrm.education.ui.activity.login;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.bean.ResetPSWType;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwSoftKeyboardUtil;
import com.jkrm.education.R;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.result.VersionResultBean;
import com.jkrm.education.bean.result.login.LoginResult;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.LoginPresent;
import com.jkrm.education.mvp.views.LoginView;
import com.jkrm.education.ui.activity.MainActivity;
import com.jkrm.education.ui.activity.me.MeAgreementActivity;
import com.jkrm.education.ui.activity.me.MeModifyPwdActivity;
import com.jkrm.education.ui.activity.me.PrivacyActivity;
import com.jkrm.education.util.ReLoginUtil;
import com.jkrm.education.util.RequestUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hzw on 2019/4/25.
 */

public class LoginActivity extends AwMvpActivity<LoginPresent> implements LoginView.View {

    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.et_account)
    AutoCompleteTextView mEtAccount;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.tv_forgetPwd)
    TextView mTvForgetPwd;
    @BindView(R.id.checkbox)
    CheckBox mCheckbox;
    @BindView(R.id.tv_privacy)
    TextView mTvPrivacy;

    private boolean isResetPsw;
    // 按返回键的判断
    private long[] mHits = new long[2];
    private String[] array = new String[]{
            "teacher_user"
    };


    @Override
    protected LoginPresent createPresenter() {
        return new LoginPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        SpannableString spannableString = new SpannableString("我接受用户协议和隐私政策");
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                toClass(PrivacyActivity.class, false);
            }
        }, 3, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                toClass(MeAgreementActivity.class, false);
            }
        }, 8, 12, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvPrivacy.setMovementMethod(LinkMovementMethod.getInstance());
        mTvPrivacy.setText(spannableString);
    }

    @Override
    protected void initData() {
        super.initData();
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                this,
//                android.R.layout.simple_dropdown_item_1line,
//                array);
//        mEtAccount.setAdapter(adapter);
//        //设置输入多少字符后提示，默认值为2，在此设为１
//
//        mEtAccount.setThreshold(1);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mTvForgetPwd.setOnClickListener(v -> toClass(FindPwdActivity.class, false));
        //teacher . 123456
        mBtnLogin.setOnClickListener(v -> {
            String account = mEtAccount.getText().toString();
            String pwd = mEtPwd.getText().toString();
            AwSoftKeyboardUtil.hideSoftKeyboard(mActivity);
            if (AwDataUtil.isEmpty(account)) {
                showMsgInCenter("请输入用户名");
                return;
            }
            if (AwDataUtil.isEmpty(pwd)) {
                showMsgInCenter("请输入密码");
                return;
            }
            if(!mCheckbox.isChecked()){
                showMsgInCenter("请接受协议和政策");
                return;
            }
//            mPresenter.login(RequestUtil.getLoginRequest(account, pwd));
//            mPresenter.login(RequestUtil.getLoginFormBodyRequest(account, pwd));

            mPresenter.login(RequestUtil.getLoginRequest(account, pwd));
        });
    }

    @Override
    public void loginSuccess(LoginResult loginResultBean) {
        if (loginResultBean != null) {
         /*   if (!"role_teacher".equals(loginResultBean.getRoleId())
                    && !"role_principal".equals(loginResultBean.getRoleId())) {
                showDialog("您输入的不是教师账号，无法登陆");
                MyApp.getInstance().clearLoginUser();
                return;
            }*/
            // TODO: 2020/6/24  权限修改
            if(loginResultBean.getRoleId().contains("student")){
                showDialog("您输入的不是教师账号，无法登陆");
                MyApp.getInstance().clearLoginUser();
                return;
            }
            AwLog.d("login result: " + loginResultBean.toString());
            MyApp.getInstance().saveToken(loginResultBean.getToken());
            MyApp.getInstance().saveLoginUser(loginResultBean.getUser());
            MyApp.getInstance().saveRoleID(loginResultBean.getRoleId());
            MyApp.getInstance().saveAcc(mEtAccount.getText().toString());
            if (isResetPsw) {
                return;
            }
            toClass(MainActivity.class, true);
        } else {
            showDialog("获取登录信息失败，请重新登录尝试", v -> reLogin());
        }
    }

    @Override
    public void getVersionInfoSuccess(VersionResultBean bean) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(ResetPSWType type) {
        isResetPsw = true;
        showDialogCustomLeftAndRight("您的密码为初始密码，需要修改后登录", "下次再说", "修改密码", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissDialog();
                toClass(MainActivity.class, true);
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toClass(MeModifyPwdActivity.class, false, Extras.RESET_PWD, "0");
            }
        });
     /*   showDialog("您的密码为初始密码，需要修改后登录", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toClass(MeModifyPwdActivity.class,false, Extras.RESET_PWD,"0");

            }
        });*/
    }

    @Override
    protected void reLogin() {
        ReLoginUtil.reLogin(mActivity);
    }

    @Override
    public void onBackPressed() {
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= SystemClock.uptimeMillis() - 2000) {
            AwSoftKeyboardUtil.hideSoftKeyboard(mActivity);
            finish();
            ExitAppForced();
        } else {
            showMsg(String.format(getString(R.string.more_click), mHits.length - 1));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
