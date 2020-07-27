package com.jkrm.education.ui.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hzw.baselib.base.AwBaseActivity;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.MicroLessonResultBean;
import com.jkrm.education.constants.Extras;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaySuccessActivity extends AwBaseActivity {

    @BindView(R.id.btn_view_order)
    Button mBtnViewOrder;
    @BindView(R.id.btn_start_now)
    Button mBtnStartNow;
    MicroLessonResultBean mMicroLessonResultBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_success;
    }

    @Override
    protected void initView() {
        super.initView();
        mMicroLessonResultBean = (MicroLessonResultBean) getIntent().getSerializableExtra(Extras.KEY_COURSE_BEAN);

        setToolbar("支付成功", new AwViewCustomToolbar.OnLeftClickListener() {
            @Override
            public void onLeftTextClick() {
                finish();
            }
        });
    }




    @OnClick({R.id.btn_view_order, R.id.btn_start_now})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_view_order:
                break;
            case R.id.btn_start_now:
                toClass(CoursePurchasedActivity.class,true,Extras.KEY_COURSE_BEAN,mMicroLessonResultBean);
                break;
        }
    }
}
