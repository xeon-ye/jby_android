package com.jkrm.education.ui.activity.exam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hzw.baselib.base.AwBaseActivity;
import com.jkrm.education.R;

public class ViewStudentAnswerSheetActivity extends AwBaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_student_answer_sheet;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_answer_sheet);
    }
}
