package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.mvp.views.StudycourseView;

public class StudyCoursePresent extends AwCommonPresenter implements StudycourseView.Presenter {
    private StudycourseView.View mView;

    public StudyCoursePresent(StudycourseView.View view){
        this.mView=view;
    }

}
