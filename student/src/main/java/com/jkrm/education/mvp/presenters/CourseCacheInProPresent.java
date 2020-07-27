package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.mvp.views.CourseCacheInProView;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/6 15:50
 */

public class CourseCacheInProPresent extends AwCommonPresenter implements CourseCacheInProView.Presenter {

    private CourseCacheInProView.View mView;

    public CourseCacheInProPresent(CourseCacheInProView.View view) {
        mView = view;
    }
}
