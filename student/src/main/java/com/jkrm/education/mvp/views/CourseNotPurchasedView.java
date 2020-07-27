package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.CoursePlayResultBean;
import com.jkrm.education.bean.result.CourseTypeBean;
import com.jkrm.education.bean.result.ErrorQuestionResultBean;

import java.util.List;

import okhttp3.RequestBody;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/5 16:39
 */

public interface CourseNotPurchasedView extends AwBaseView {
    interface Presenter extends AwBasePresenter {
        void getCoursePlayList(RequestBody requestBody);
    }

    interface View extends AwBaseView {
        void getCoursePlayListSuccess(List<CoursePlayResultBean> list);
        void getCoursePlayListFail(String msg);

    }
}
