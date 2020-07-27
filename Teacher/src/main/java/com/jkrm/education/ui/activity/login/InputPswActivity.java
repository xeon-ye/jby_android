package com.jkrm.education.ui.activity.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwSpUtil;
import com.jkrm.education.R;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.RegisterBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.InputPswPresent;
import com.jkrm.education.mvp.views.InputPswView;
import com.jkrm.education.util.RequestUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class InputPswActivity extends AwMvpActivity<InputPswPresent> implements InputPswView.View  {
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_input_psw;
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
                mBtnVerCode.setEnabled(editable.toString().length()>0);
            }
        });
    }

    @OnClick({R.id.iv_close, R.id.btn_ver_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.btn_ver_code:
                MyApp.mStrPsw=mEtPhoneNum.getText().toString();
                String phone = AwSpUtil.getString(MyConstant.SPConstant.XML_TEMPORARY, MyConstant.SPConstant.KEY_TEMPORARY_MOBILE, "");
                mPresenter.userRegister(RequestUtil.getRegisterBody(phone,MyApp.mStrVerCode,mEtPhoneNum.getText().toString(),""));

                break;
        }
    }

    @Override
    protected InputPswPresent createPresenter() {
        return new InputPswPresent(this);
    }


    @Override
    public void userRegisterSuccess(RegisterBean data) {
        toClass(ChoiceCourseActivity.class,false, Extras.KEY_REGISTER_ID,data.getId());
    }

    @Override
    public void userRegisterFail(String msg) {
        showMsg(msg);
    }
}