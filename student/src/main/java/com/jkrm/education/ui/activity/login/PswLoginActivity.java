package com.jkrm.education.ui.activity.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzw.baselib.base.AwBaseActivity;
import com.hzw.baselib.base.AwBaseApplication;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.bean.ResetPSWType;
import com.hzw.baselib.bean.UpdateBean;
import com.hzw.baselib.interfaces.TextChangeListener;
import com.hzw.baselib.util.AwAPPUtils;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwSpUtil;
import com.hzw.baselib.util.AwUpdateUtil;
import com.hzw.baselib.util.AwVersionUtil;
import com.hzw.baselib.widgets.BidirectionalSeekBar;
import com.jkrm.education.R;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.result.LoginResult;
import com.jkrm.education.bean.result.VersionResultBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.LoginPresent;
import com.jkrm.education.mvp.views.LoginView;
import com.jkrm.education.ui.activity.MainActivity;
import com.jkrm.education.ui.activity.me.MeModifyPwdActivity;
import com.jkrm.education.util.RequestUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 密码登录
 */
public class PswLoginActivity extends AwMvpActivity<LoginPresent> implements LoginView.View {

    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.tv_ver_code)
    TextView mTvVerCode;
    @BindView(R.id.et_input_acc)
    EditText mEtInputAcc;
    @BindView(R.id.et_input_psw)
    EditText mEtInputPsw;
    @BindView(R.id.cb_see_psw)
    CheckBox mCbSeePsw;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.tv_register)
    TextView mTvRegister;
    private boolean isResetPsw;
    private LoginResult mLoginResult;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_psw_login;
    }


    @Override
    protected LoginPresent createPresenter() {
        return new LoginPresent(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        super.initView();
        mPresenter.getVersionInfo();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mEtInputAcc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0 && mEtInputPsw.getText().toString().length() > 0) {
                    mBtnLogin.setEnabled(true);
                } else {
                    mBtnLogin.setEnabled(false);
                }
            }
        });
        mEtInputPsw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0 && mEtInputAcc.getText().toString().length() > 0) {
                    mBtnLogin.setEnabled(true);
                } else {
                    mBtnLogin.setEnabled(false);
                }
            }
        });
        mCbSeePsw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mEtInputPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mEtInputPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    @OnClick({R.id.iv_close, R.id.tv_ver_code, R.id.btn_login, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                //finish();
                break;
            case R.id.tv_ver_code:
                toClass(VerCodeLoginActivity.class, false,Extras.KEY_IS_VER_LOGIN,true);
                break;
            case R.id.btn_login:
                mPresenter.login(RequestUtil.getLoginRequest(mEtInputAcc.getText().toString().trim(), mEtInputPsw.getText().toString().trim()));
                break;
            case R.id.tv_register:
                toClass(RegisterActivity.class, false);
                break;
        }
    }

    @Override
    public void loginSuccess(LoginResult loginResultBean) {
        if (loginResultBean != null) {
            mLoginResult=loginResultBean;
         /*   if (!"role_student".equals(loginResultBean.getRoleId())) {
                showDialog("您输入的不是学生账号，无法登陆");
                MyApp.getInstance().clearLoginUser();
                return;
            }*/
            if(loginResultBean.getRoleId().contains("teacher")){
                showDialog("您输入的不是学生账号，无法登录");
                MyApp.getInstance().clearLoginUser();
                return;
            }

            AwLog.d("login result: " + loginResultBean.toString());
            MyApp.getInstance().saveToken(loginResultBean.getToken());
            MyApp.getInstance().saveRoleID(loginResultBean.getRoleId());
            MyApp.getInstance().saveLoginUser(loginResultBean.getUser());
            MyApp.getInstance().saveAcc(mEtInputAcc.getText().toString());
            AwSpUtil.saveString(MyConstant.SPConstant.XML_TEMPORARY, MyConstant.SPConstant.KEY_TEMPORARY_MOBILE, mEtInputAcc.getText().toString());

            if (isResetPsw) {
                return;
            }
            if(null==loginResultBean.getUser().getSchool()||AwDataUtil.isEmpty(loginResultBean.getUser().getSchool().getId())){
                toClass(ChoiceLearningSectionActivity.class,true,Extras.KEY_REGISTER_ID,loginResultBean.getUser().getId());
            }else{
                toClass(MainActivity.class, true);
            }
        } else {
            showDialog("获取登录信息失败，请重新登录尝试", v -> reLogin());

        }
    }

    @Override
    public void getVersionInfoSuccess(VersionResultBean bean) {
        if(bean != null && AwVersionUtil.compareVersions(AwAPPUtils.getAppVersionInfo(mActivity, AwAPPUtils.TYPE_VERSION.TYPE_VERSION_NAME), bean.getVersion()) > 0) {
            if(AwBaseApplication.netWatchdog.hasNet(mActivity)) {
                UpdateBean updateBean = new UpdateBean();
                updateBean.setVersion(bean.getVersion());
                updateBean.setUpdateContent(bean.getUpdateContent());
                updateBean.setUrl(bean.getUrl());
                AwUpdateUtil.getInstance(mActivity).handleUpdate(updateBean, () -> {});
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(ResetPSWType type) {
        isResetPsw = true;
        showDialogCustomLeftAndRight("您的密码为初始密码，需要修改后登录", "下次再说", "修改密码", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null==mLoginResult.getUser().getSchool()||AwDataUtil.isEmpty(mLoginResult.getUser().getSchool().getId())){
                    toClass(ChoiceLearningSectionActivity.class,true,Extras.KEY_REGISTER_ID,mLoginResult.getUser().getId());
                }else{
                    toClass(MainActivity.class, true);
                }
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toClass(MeModifyPwdActivity.class, false, Extras.RESET_PWD, "0");
            }
        });
       /* showDialog("您的密码为初始密码，需要修改后登录", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               toClass(MeModifyPwdActivity.class,false, Extras.RESET_PWD,"0");

            }
        });*/
    }
}
