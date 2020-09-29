package com.jkrm.education.ui.activity.exam;

import com.hzw.baselib.base.AwMvpActivity;
import com.jkrm.education.bean.exam.StuSectionTableBean;
import com.jkrm.education.mvp.presenters.StuSectionTablePresent;
import com.jkrm.education.mvp.views.StuSectionTableView;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/29 16:24
 * Description: 学生名单详情列表(成绩分段表人数跳转)
 */
public class StuSectionTableActivity extends AwMvpActivity<StuSectionTablePresent> implements StuSectionTableView.View{
    @Override
    protected StuSectionTablePresent createPresenter() {
        return new StuSectionTablePresent(this);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public void getTableListSuccess(StuSectionTableBean data) {

    }

    @Override
    public void getTableListFail(String msg) {

    }
}
