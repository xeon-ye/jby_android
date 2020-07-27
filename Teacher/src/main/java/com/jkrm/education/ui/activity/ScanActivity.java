package com.jkrm.education.ui.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDisplayUtil;
import com.hzw.baselib.util.MyDateUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.ResolveTeacherProgressResultBean;
import com.jkrm.education.bean.scan.ScanBean;
import com.jkrm.education.mvp.presenters.ScanPresent;
import com.jkrm.education.mvp.views.ScanView;
import com.jkrm.education.util.UserUtil;

import java.util.List;

import butterknife.BindView;

/**
 * 扫描页面
 * Created by hzw on 2019/5/13.
 */

public class ScanActivity extends AwMvpActivity<ScanPresent> implements ScanView.View {

    @BindView(R.id.fl_scanIng)
    FrameLayout mFlScanIng;
    @BindView(R.id.iv_scanComplete)
    ImageView mIvScanComplete;
    @BindView(R.id.iv_scanLine)
    ImageView mIvScanLine;
    @BindView(R.id.tv_scanInfo)
    TextView mTvScanInfo;
    @BindView(R.id.tv_scanInfoSecond)
    TextView mTvScanInfoSecond;
    @BindView(R.id.btn_toNext)
    Button mBtnToNext;
    @BindView(R.id.ic_noData)
    RelativeLayout mIcNoData;


    private static final int CODE_MSG = 0;
    private int totalPage = 6;
    private int scanedPage = 3;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CODE_MSG:
                    mHandler.postDelayed(mRunnable, 3000);
                    break;
                default:
                    break;
            }
        }
    };
    private Runnable mRunnable = () -> {
        mPresenter.getTeacherResolveProgress(UserUtil.getTeacherId());
//        mPresenter.getScanInfo(totalPage, scanedPage);
    };

    @Override
    protected ScanPresent createPresenter() {
        return new ScanPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        setToolbarWithBackImg("扫描信息", null);
    }

    @SuppressLint("StringFormatMatches")
    @Override
    protected void initData() {
        super.initData();
        showView(mIcNoData, false);
//        mPresenter.getScanInfo(totalPage, scanedPage);
        mPresenter.getTeacherResolveProgress(UserUtil.getTeacherId());
//        testAnim();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBtnToNext.setOnClickListener(v -> finish());
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void getScanInfoSuccess(ScanBean scanBean) {
        if(null == scanBean) {
            stopScanAnim();
            setText(mTvScanInfo, String.format(getString(R.string.scan_info_complete), "高一三班"));
            showView(mTvScanInfoSecond, false);
//            setText(mBtnToNext, "确 认");
            return;
        }
        totalPage = scanBean.getTotalScanPage();
        scanedPage = scanBean.getScanedPage();
        showView(mTvScanInfoSecond, true);
        setText(mTvScanInfo, String.format(getString(R.string.scan_info_ing), scanBean.getClasses()));
        setText(mTvScanInfoSecond, Html.fromHtml(String.format(getString(R.string.scan_info_ing_second), totalPage, scanedPage)));
        setText(mBtnToNext, "返 回");
        if(totalPage < 50 || scanedPage < 50) {
            mHandler.sendEmptyMessage(CODE_MSG);
            startScanAnim();
        } else {
            stopScanAnim();
            setText(mTvScanInfo, String.format(getString(R.string.scan_info_complete), "高一三班"));
            showView(mTvScanInfoSecond, false);
//            setText(mBtnToNext, "确 认");
        }
    }

    @Override
    public void getTeacherResolveProgressSuccess(List<ResolveTeacherProgressResultBean> list) {
        if(AwDataUtil.isEmpty(list)) {
            stopScanAnim();
            setText(mBtnToNext, "确 认");
            setText(mTvScanInfo, String.format(getString(R.string.scan_info_complete_new)));
            showView(mTvScanInfoSecond, false);
            return;
        }
        ResolveTeacherProgressResultBean bean = list.get(0);
        scanedPage = MyDateUtil.stringToInt(bean.getCounter());
        setText(mTvScanInfo, String.format(getString(R.string.scan_info_ing), bean.getClassName() + " " + bean.getCourseName()));
        setText(mTvScanInfoSecond, Html.fromHtml(String.format(getString(R.string.scan_info_ing_second), totalPage, scanedPage)));
        setText(mBtnToNext, "返 回");
        startScanAnim();
        mHandler.sendEmptyMessage(CODE_MSG);
    }

    @Override
    public void getTeacherResolveProgressFail(String msg) {
        stopScanAnim();
        setText(mBtnToNext, "确 认");
        setText(mTvScanInfo, String.format(getString(R.string.scan_info_complete_new)));
        showView(mTvScanInfoSecond, false);
    }

    @Override
    protected void onDestroy() {
        mHandler.removeMessages(CODE_MSG);
        mHandler.removeCallbacks(mRunnable);
        super.onDestroy();
    }

    private void startScanAnim() {
        showView(mFlScanIng, true);
        showView(mIvScanComplete, false);
        TranslateAnimation mAnimation = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_PARENT, -0.1f, TranslateAnimation.RELATIVE_TO_PARENT,1.7f,TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0f);
//        TranslateAnimation mAnimation = new TranslateAnimation(0, AwDisplayUtil.dipToPix(mActivity, 500), 0, 0);
        mAnimation.setDuration(3000);
        mAnimation.setRepeatCount(-1);
        mAnimation.setRepeatMode(Animation.REVERSE);
//        mAnimation.setFillAfter(true);
        mAnimation.setInterpolator(new LinearInterpolator());
        mIvScanLine.startAnimation(mAnimation);
    }

     private void testAnim() {
         TranslateAnimation mAnimation = new TranslateAnimation(0, 300, 0, 300);
         mAnimation.setDuration(6000);
         mAnimation.setRepeatCount(-1);
         mAnimation.setRepeatMode(Animation.REVERSE);
         mAnimation.setInterpolator(new LinearInterpolator());
         mBtnToNext.startAnimation(mAnimation);
     }

    private void stopScanAnim() {
        showView(mFlScanIng, false);
        showView(mIvScanComplete, true);
        mIvScanLine.clearAnimation();
    }
}
