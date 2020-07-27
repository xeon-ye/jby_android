package com.jkrm.education.ui.activity.login;

import android.widget.Button;
import android.widget.EditText;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.R;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.ResetPwdPresent;
import com.jkrm.education.mvp.views.ResetPwdView;
import com.jkrm.education.util.RequestUtil;
import com.sobot.chat.utils.HtmlTools;

import java.util.regex.Pattern;

import butterknife.BindView;

/**
 * Created by hzw on 2019/5/14.
 */

public class ResetPwdActivity extends AwMvpActivity<ResetPwdPresent> implements ResetPwdView.View {

    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.et_pwdConfirm)
    EditText mEtPwdConfirm;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;

    private String mobile = "";

    @Override
    protected ResetPwdPresent createPresenter() {
        return new ResetPwdPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_pwd;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        setToolbarWithBackImg("重置密码", null);
    }

    @Override
    protected void initData() {
        super.initData();
        mobile = getIntent().getStringExtra(Extras.COMMON_PARAMS);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBtnConfirm.setOnClickListener(v -> {
            String pwd = mEtPwd.getText().toString();
            String pwdConfirm = mEtPwdConfirm.getText().toString();
            if(AwDataUtil.isEmpty(pwd)) {
                showDialog("请输入新密码");
                return;
            }
            if(AwDataUtil.isEmpty(pwdConfirm)) {
                showDialog("请再次输入新密码");
                return;
            }
            if(!Pattern.matches(HtmlTools.USER_PASSWORD,pwd)){
                showDialog("密码需为8-12位 包含 字母 数字 特殊符号两种以上");
                return;
            }
            if(pwd.length() < 6 || pwdConfirm.length() < 6) {
                showDialog("新密码不可少于6位");
                return;
            }
            if(!pwd.equals(pwdConfirm)) {
                showDialog("两次密码输入不同");
                return;
            }
            mPresenter.updateUserPwd(RequestUtil.updatePwdRequest(mobile, pwd));

        });
    }

    @Override
    public void updateUserPwdSuccess() {
        showMsg("密码修改成功");
        toClass(PswLoginActivity.class, true);
    }

}
