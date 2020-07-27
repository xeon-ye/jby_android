package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.BookExercisesBean;
import com.jkrm.education.bean.result.BookExercisesListBean;
import com.jkrm.education.bean.result.ClassHourBean;
import com.jkrm.education.bean.result.LessonHourBean;
import com.jkrm.education.bean.result.PracticeDataResultBean;
import com.jkrm.education.bean.result.TopicScoreBean;
import com.jkrm.education.bean.result.VideoPointResultBean;
import com.jkrm.education.bean.result.VideoResultBean;

import java.util.List;

import okhttp3.RequestBody;


/**
 * 图书习题
 */
public interface BookExercisesFragmentView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getBookExercisesList();

        void getClassHourList(String class_hour);

        void getBookMainList(String class_hour_key,String studid);

        void getTopicScore(String class_hour_key);

        void getLessonHour(String class_hour_key);

        void getVideoPointListByTemplate(String template_id);

        void getVideos(String params);

        void addQuestionBasket(RequestBody body);
        void delSomeOneQuestionBasket(String questionId, String studentId);

        void getPracticeDataList(String student_id, String start_date, String end_date, String course_id, String questionTypeId);
    }

    interface View extends AwBaseView {
        void getBookExercisesListSuccess(BookExercisesBean bookExercisesBean);
        void getBookExercisesListFail(String msg);

        void getClassHourListSuccess(ClassHourBean classHourBean);
        void getClassHourListFail(String msg);

        void getBookMainListSuccess(List<BookExercisesListBean> list);
        void getBookMainListFail(String msg);

        void getTopicScoreSuccess(List<TopicScoreBean> list);

        void getLessonHour(LessonHourBean list);
        void getLessonHourFail(String msg);

        void getVideoPointListByTemplateSuccess(List<VideoPointResultBean> list);
        void getVideoPointListByTemolateFail(String msg);

        void getVideosSuccess(VideoResultBean result);
        void getVideoFail(String msg);

        void addQuestionBasketSuccess(String msg);
        void delSomeOneQuestionBasketSuccess(String msg);

        void getPracticeDataListSuccess(PracticeDataResultBean bean);
        void getPracticeDataListFail(String msg);
    }
}