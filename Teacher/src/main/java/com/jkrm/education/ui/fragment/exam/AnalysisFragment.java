package com.jkrm.education.ui.fragment.exam;

import com.hzw.baselib.base.AwBaseLazyFragment;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.R;
import com.jkrm.education.mvp.presenters.AnalysisPresent;
import com.jkrm.education.mvp.presenters.TaskPresent;
import com.jkrm.education.mvp.views.AnalysisView;
import com.jkrm.education.mvp.views.TaskView;

import java.util.List;

/**
 * @Description: 成绩分析
 * @Author: xiangqian
 * @CreateDate: 2020/8/26 10:28
 */

public class AnalysisFragment  extends AwMvpLazyFragment<AnalysisPresent> implements AnalysisView.View {
    @Override
    protected int getLayoutId() {
        return R.layout.analysis_layout;
    }


    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void getAnalysisListSuccess(List<String> data) {

    }

    @Override
    public void getAnalysisListFail(String msg) {

    }

    @Override
    protected AnalysisPresent createPresenter() {
        return new AnalysisPresent(this);
    }
}
