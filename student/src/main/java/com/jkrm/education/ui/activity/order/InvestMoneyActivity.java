package com.jkrm.education.ui.activity.order;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hzw.baselib.base.AwBaseActivity;
import com.jkrm.education.R;

public class InvestMoneyActivity extends AwBaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invest_money;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invest_money);
    }
}
