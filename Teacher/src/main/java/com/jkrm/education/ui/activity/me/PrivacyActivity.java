package com.jkrm.education.ui.activity.me;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hzw.baselib.base.AwBaseActivity;
import com.jkrm.education.R;

public class PrivacyActivity extends AwBaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_privacy;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        setToolbarWithBackImg("隐私政策",null);
    }
}