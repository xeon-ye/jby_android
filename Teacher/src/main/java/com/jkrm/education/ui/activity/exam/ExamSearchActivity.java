package com.jkrm.education.ui.activity.exam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hzw.baselib.base.AwBaseActivity;
import com.jkrm.education.R;

/**
 * 搜索
 */
public class ExamSearchActivity extends AwBaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exam_search;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_search);
    }
}
