package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.mvp.views.CourseCacheSuccessView;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/6 15:50
 */

public class CourseCacheSuccessPresent extends AwCommonPresenter implements CourseCacheSuccessView.Presenter {
    private CourseCacheSuccessView.View mView;

    public CourseCacheSuccessPresent(CourseCacheSuccessView.View view) {
        mView = view;
    }
}
