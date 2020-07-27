package com.jkrm.education.ui.activity.me;

import android.widget.Button;
import android.widget.EditText;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.R;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.mvp.presenters.UserLoginVerifyPresent;
import com.jkrm.education.mvp.views.UserLoginVerifyView;
import com.jkrm.education.util.RequestUtil;

import butterknife.BindView;

/**
 * Created by hzw on 2019/5/14.
 */

public class MeModifyMobileStep1Activity extends AwMvpActivity<UserLoginVerifyPresent> implements UserLoginVerifyView.View {

    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;

    @Override
    protected UserLoginVerifyPresent createPresenter() {
        return new UserLoginVerifyPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_me_modify_mobile_step1;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        setToolbarWithBackImg("修改手机号", null);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBtnConfirm.setOnClickListener(v -> {
            String pwd = mEtPwd.getText().toString();
            if(AwDataUtil.isEmpty(pwd)) {
                showDialog("密码不可为空");
                return;
            }
            mPresenter.verifyUserLogin(RequestUtil.verifyLoginRequest(MyApp.getInstance().getAppUser().getUsername(), pwd));
        });
    }

    @Override
    public void verifyUserLoginSuccess() {
        toClass(MeModifyMobileStep2Activity.class, true);
    }
}
