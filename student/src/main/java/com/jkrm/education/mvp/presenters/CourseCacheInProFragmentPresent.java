package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.mvp.views.CourseCacheInProFragmentView;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/6 15:28
 */

public class CourseCacheInProFragmentPresent extends AwCommonPresenter implements CourseCacheInProFragmentView.Presenter {

    private CourseCacheInProFragmentView.View mView;

    public CourseCacheInProFragmentPresent(CourseCacheInProFragmentView.View view) {
        mView = view;
    }
}
