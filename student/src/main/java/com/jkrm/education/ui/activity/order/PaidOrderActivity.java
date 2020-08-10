package com.jkrm.education.ui.activity.order;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzw.baselib.base.AwBaseActivity;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.bean.OrderBean;
import com.jkrm.education.constants.Extras;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaidOrderActivity extends AwBaseActivity {

    @BindView(R.id.toolbar_custom)
    AwViewCustomToolbar mToolbarCustom;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.tv_step)
    TextView mTvStep;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_course)
    TextView mTvCourse;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_num)
    TextView mTvNum;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_price_dis)
    TextView mTvPriceDis;
    private OrderBean.RowsBean mBean;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_paid_order;
    }

    @Override
    protected void initView() {
        super.initView();
        setToolbarWithBackImg("我的订单", null);
    }

    @Override
    protected void initData() {
        super.initData();
        mBean = (OrderBean.RowsBean) getIntent().getExtras().getSerializable(Extras.KEY_ORDER);
        mTvCourse.setText(mBean.getDetaiList().get(0).getCourseName());
        mTvTitle.setText(mBean.getDetaiList().get(0).getGoodsName());
        mTvNum.setText("共"+mBean.getDetaiList().get(0).getGoodsName()+"节课");
        mTvPrice.setText("￥"+mBean.getGoodsPrice());
        mTvPriceDis.setText("￥"+mBean.getGoodsPrice());
    }
}
