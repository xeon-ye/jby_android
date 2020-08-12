package com.jkrm.education.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hzw.baselib.base.AwBaseActivity;
import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.AnswerSheetBean;
import com.jkrm.education.bean.OrderBean;
import com.jkrm.education.bean.result.MicroLessonResultBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.ui.activity.AnswerSheetActivity;
import com.jkrm.education.ui.activity.AnswerSituationActivity;
import com.jkrm.education.ui.activity.CoursePurchasedActivity;
import com.jkrm.education.ui.activity.ScanQrcodeActivity;
import com.jkrm.education.ui.activity.login.ChoiceClassesActivity;
import com.jkrm.education.util.UserUtil;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
    @BindView(R.id.tv_timeing)
    TextView mTvTimeing;
    @BindView(R.id.tv_order_num)
    TextView mTvOrderNum;
    @BindView(R.id.tv_watch)
    TextView mTvWatch;
    private OrderBean.RowsBean mBean;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_paid_order;
    }

    @Override
    protected void initView() {
        super.initView();
        setToolbarWithBackImg("我的订单", null);
        mTvStep.setText("已完成");
    }

    @Override
    protected void initData() {
        super.initData();
        mBean = (OrderBean.RowsBean) getIntent().getExtras().getSerializable(Extras.KEY_ORDER);
        mTvCourse.setText(mBean.getDetaiList().get(0).getCourseName());
        mTvTitle.setText(mBean.getDetaiList().get(0).getGoodsName());
        mTvNum.setText("共" + mBean.getDetaiList().get(0).getComboNum() + "节课");
        mTvPrice.setText("￥" + mBean.getGoodsPrice());
        mTvPriceDis.setText("￥" + mBean.getGoodsPrice());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date parse = null;
        try {
            parse = simpleDateFormat.parse(mBean.getCreateTime());
            mTvTime.setText("下单时间:" + AwDateUtils.getyyyyMMddHHmmssWithNo(parse.getTime()));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        mTvOrderNum.setText("订单编号:" + mBean.getDetaiList().get(0).getOrderId());
        showView(mTvWatch,true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_watch)
    public void onViewClicked() {
        RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getCourseById(mBean.getDetaiList().get(0).getGoodsId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AwApiSubscriber(new AwApiCallback<MicroLessonResultBean>() {
                    @Override
                    public void onStart() {
                        showLoadingDialog();
                    }

                    @Override
                    public void onSuccess(MicroLessonResultBean data) {
                        toClass(CoursePurchasedActivity.class,false,Extras.KEY_COURSE_BEAN,data);
                    }
                    @Override
                    public void onFailure(int code, String msg) {
                        showMsg(msg);
                    }

                    @Override
                    public void onCompleted() {
                        dismissLoadingDialog();
                    }
                }));

    }
}
