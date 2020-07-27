package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.CourseAttrBean;
import com.jkrm.education.bean.result.CourseTypeBean;
import com.jkrm.education.bean.result.MicroLessonResultBean;

import java.util.List;

import okhttp3.RequestBody;


/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/5 10:12
 */

public interface MicroLessonFragmentView extends AwBaseView {
    interface Presenter extends AwBasePresenter {
        void getCourseType(RequestBody requestBody);

        void getCourseAttr();

        void getCourseList(RequestBody requestBody);

    }

    interface View extends AwBaseView {
        void getCourseTypeSuccess(List<CourseTypeBean> list);
        void getCourseTypeFail(String msg);

        void getCourseAttrSuccess(CourseAttrBean attrBean);
        void getCourseAttrFail(String msg);

        void getCourseListSuccess(List<MicroLessonResultBean>list);
        void getCourseListFail(String msg);
    }
}
