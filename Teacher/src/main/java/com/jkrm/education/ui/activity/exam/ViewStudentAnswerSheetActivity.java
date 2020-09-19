package com.jkrm.education.ui.activity.exam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hzw.baselib.base.AwBaseActivity;
import com.hzw.baselib.base.AwMvpActivity;
import com.jkrm.education.R;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.ViewStudentAnswerSheetPresent;
import com.jkrm.education.mvp.views.ViewStudentAnswerSheetView;
import com.jkrm.education.util.RequestUtil;

import java.util.List;

public class ViewStudentAnswerSheetActivity extends AwMvpActivity<ViewStudentAnswerSheetPresent> implements ViewStudentAnswerSheetView.View {


    String EXAM_ID;
    String STUDENT_ID;
    String COURSE_ID;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_student_answer_sheet;
    }

    @Override
    protected ViewStudentAnswerSheetPresent createPresenter() {
        return new ViewStudentAnswerSheetPresent(this);
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusBlue();
    }

    @Override
    protected void initData() {
        super.initData();
        EXAM_ID = getIntent().getExtras().getString(Extras.EXAM_ID);
        STUDENT_ID = getIntent().getExtras().getString(Extras.STUDENT_ID);
        COURSE_ID = getIntent().getExtras().getString(Extras.KEY_COURSE_ID);
        showDialog(EXAM_ID+STUDENT_ID+COURSE_ID);
        mPresenter.getCourseList(RequestUtil.getCourseListBody(EXAM_ID,STUDENT_ID,COURSE_ID));
    }

    @Override
    public void getCourseListSuccess(List<String> data) {

    }

    @Override
    public void getCourseListFail(String msg) {

    }
}
