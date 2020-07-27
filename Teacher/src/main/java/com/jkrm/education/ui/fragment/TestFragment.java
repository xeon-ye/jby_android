package com.jkrm.education.ui.fragment;

import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.jkrm.education.R;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.result.AnswerSheetProgressResultBean;
import com.jkrm.education.bean.result.OssResultBean;
import com.jkrm.education.mvp.presenters.TestPresent;
import com.jkrm.education.mvp.views.TestView;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.widget.CustomProgressView;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 调试入口页面
 * Created by hzw on 2019/5/5.
 */

public class TestFragment extends AwMvpLazyFragment<TestPresent> implements TestView.View {

    @BindView(R.id.customProgressView)
    CustomProgressView mCustomProgressView;
    private String url = "";

    @Override
    protected TestPresent createPresenter() {
        return new TestPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    protected void initView() {
        super.initView();
        setToolbarHideLeftAndRight("API调试", null);
    }

    @Override
    protected void initData() {
        super.initData();
        mCustomProgressView.setData(30, 20, 5);
    }

    @Override
    protected void initListener() {
        super.initListener();

    }

    @OnClick ({R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_1:
                mPresenter.uploadOss("test", Environment.getExternalStorageDirectory() + File.separator + "img0.jpg");
                break;
            case R.id.btn_2:
                mPresenter.addSpecial(RequestUtil.getAddSpecialRequest("1", "2", "3", "4", "",
                        "https://img.xinjiaoyu.com/jby_files/\"mark_server\"/8399A021EC2142F48479EDFFEE25E738.jpg", ""));
                break;
            case R.id.btn_3:
                mPresenter.answerSheetProgress("03012019020303030311", "1");
                break;
            case R.id.btn_4:
                mPresenter.answerSheetProgressWithStudent("03012019020303030311", "schstudent321");
                break;
            case R.id.btn_5:
                mPresenter.getQustionAnswerWithSingleStudent("03012019020303030311", "schstudent103");
                break;
            case R.id.btn_6:
                mPresenter.getStatusErrorQuestionInSomeDay(MyApp.getInstance().getAppUser().getTeacherId());
                break;
            case R.id.btn_7:
                mPresenter.getStatusMarkBeforeDawn(MyApp.getInstance().getAppUser().getTeacherId());
                break;
        }
    }

}
