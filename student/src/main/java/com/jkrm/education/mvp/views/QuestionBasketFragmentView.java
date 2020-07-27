package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.PracticeDataResultBean;
import com.jkrm.education.bean.result.VideoResultBean;

import okhttp3.RequestBody;


/**
 * Created by hzw on 2018/11/13
 */
public interface QuestionBasketFragmentView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getPracticeDataList(String student_id, String start_date, String end_date, String course_id, String questionTypeId);
        void getVideos(String params);
        void delSomeOneQuestionBasket(String questionId, String studentId);
        void delAllQuestionBasket(String studentId);
        void exportQuestionBasket(RequestBody requestBody);
    }

    interface View extends AwBaseView {
        void getPracticeDataListSuccess(PracticeDataResultBean bean);
        void getPracticeDataListFail(String msg);

//        void getPracticeDataTableSuccess(List<PracticeTableResult> list);
//        void getPracticeDataTableFail(String msg);

        void getVideosSuccess(VideoResultBean result);
        void getVideoFail(String msg);

        void delSomeOneQuestionBasketSuccess(String msg);
        void delAllQuestionBasketSuccess(String msg);

    }
}