package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.ErrorBasketBean;
import com.jkrm.education.bean.MistakeBean;
import com.jkrm.education.bean.result.error.ErrorClassesBean;
import com.jkrm.education.bean.result.error.ErrorCourseBean;
import com.jkrm.education.bean.result.error.ErrorHomeWork;

import java.util.List;

import okhttp3.RequestBody;


/**
 * Created by hzw on 2018/11/13
 */
public interface ErrorQuestionFragmentView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getErrorCourseList(String teacherId);
        void getErrorHomewordList(String teacherId,String courseId);
        void getErrorClassesList(String schoolId,String templateId);
        void getErrorMistakeList(String classIds,String templateIds,String maxGradeRatio,String minGradeRatio);
        void addErrorBasket(RequestBody requestBody);
        void getErrorBasket(String teacherId);
        void deleteErrorBasket(RequestBody requestBody);

    }

    interface View extends AwBaseView {
        void getErrorCourseListSuccess(List<ErrorCourseBean> data);
        void getErrorCorrseListFail(String msg);

        void getErrorHomeworkListSuccess(List<ErrorHomeWork> data);
        void getErrorHomeworkListFail(String msg);

        void getErrorClassesListSuccess(List<ErrorClassesBean> data);
        void getErrorClassesListFail(String msg);

        void getErrorMistakeListSuccess(List<MistakeBean> data);
        void getErrorMistakeListFail(String mag);

        void addErrorBasketSuccess(String data);
        void addErrorBasketFail(String msg);

        void getErrorBasketSuccess(List<ErrorBasketBean> data);
        void getErrorBasketFail(String msg);

        void deleteErrorBasketSuccess(String data);
        void deleteErrorBasketFail(String msg);

    }

}