package com.jkrm.education.ui.activity.me;



import com.hzw.baselib.base.AwBaseActivity;
import com.jkrm.education.R;

/**
 * 隐私政策
 */
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
