package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.mvp.views.CourseCacheInProFragmentView;
import com.jkrm.education.mvp.views.CourseCacheSuccessFragmentView;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/6 15:28
 */

public class CourseCacheSuccessFragmentPresent extends AwCommonPresenter implements CourseCacheInProFragmentView.Presenter {

    private CourseCacheSuccessFragmentView.View mView;

    public CourseCacheSuccessFragmentPresent(CourseCacheSuccessFragmentView.View view) {
        mView = view;
    }
}
