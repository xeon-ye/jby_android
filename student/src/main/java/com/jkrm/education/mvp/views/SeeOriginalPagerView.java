package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.OriginalPagerResultBean;

/**
 * Created by hzw on 2018/11/13
 */
public interface SeeOriginalPagerView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getStudentOriginalQuestionAnswer(String homework_id, String student_id);
    }

    interface View extends AwBaseView {


        void getStudentOriginalQuestionAnswerSuccess(OriginalPagerResultBean bean);
    }

}