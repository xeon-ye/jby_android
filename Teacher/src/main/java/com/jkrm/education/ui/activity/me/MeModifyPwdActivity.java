package com.jkrm.education.ui.activity.me;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.R;
import com.jkrm.education.mvp.presenters.ModifyPwdPresent;
import com.jkrm.education.mvp.views.MeModifyPwdView;
import com.jkrm.education.util.ReLoginUtil;
import com.jkrm.education.util.RequestUtil;

import butterknife.BindView;

/**
 * Created by hzw on 2019/8/14.
 */

public class MeModifyPwdActivity extends AwMvpActivity<ModifyPwdPresent> implements MeModifyPwdView.View {

    @BindView(R.id.et_pwdOld)
    EditText mEtPwdOld;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.et_pwdConfirm)
    EditText mEtPwdConfirm;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;

    @Override
    protected ModifyPwdPresent createPresenter() {
        return new ModifyPwdPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_pwd;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        setToolbarWithBackImg("修改密码", null);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwdOld = mEtPwdOld.getText().toString();
                String pwdNew = mEtPwd.getText().toString();
                String pwdConfirm = mEtPwdConfirm.getText().toString();
                if(AwDataUtil.isEmpty(pwdOld)) {
                    showDialog("请输入原密码");
                    return;
                }
                if(AwDataUtil.isEmpty(pwdNew)) {
                    showDialog("请输入新密码");
                    return;
                }
                if(AwDataUtil.isEmpty(pwdNew)) {
                    showDialog("请再次输入新密码");
                    return;
                }
                if(pwdNew.length() < 6) {
                    showDialog("新密码长度不可小于6位");
                    return;
                }
                if(pwdOld.equals(pwdNew)) {
                    showDialog("新密码与原密码相同，无需修改");
                    return;
                }
                if(!pwdNew.equals(pwdConfirm)) {
                    showDialog("您输入的两次密码不同，请重新输入");
                    return;
                }
                mPresenter.modifyPwd(RequestUtil.updateUserPwd(pwdOld, pwdNew, pwdConfirm));
            }
        });
    }

    @Override
    public void modifyPwdSuccess() {
        showDialog("密码修改成功，请重新登录", v -> ReLoginUtil.reLogin(mActivity));
    }

}
