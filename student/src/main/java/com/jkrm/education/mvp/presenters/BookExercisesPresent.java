package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.BookExercisesBean;
import com.jkrm.education.bean.result.BookExercisesListBean;
import com.jkrm.education.bean.result.ClassHourBean;
import com.jkrm.education.bean.result.LessonHourBean;
import com.jkrm.education.bean.result.ParcticeQuestBean;
import com.jkrm.education.bean.result.PracticeDataResultBean;
import com.jkrm.education.bean.result.PracticeTableResult;
import com.jkrm.education.bean.result.TopicScoreBean;
import com.jkrm.education.bean.result.VideoPointResultBean;
import com.jkrm.education.bean.result.VideoResultBean;
import com.jkrm.education.mvp.views.BookExercisesFragmentView;
import com.jkrm.education.mvp.views.QuestionBasketFragmentView;

import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * @Description: 图书习题
 * @Author: xiangqian
 * @CreateDate: 2020/2/19 16:28
 */

public class BookExercisesPresent extends AwCommonPresenter implements BookExercisesFragmentView.Presenter {
    private BookExercisesFragmentView.View mView;

    public BookExercisesPresent(BookExercisesFragmentView.View view) {
        this.mView = view;
    }

    @Override
    public void getBookExercisesList() {
        Observable<ResponseBean<BookExercisesBean>> bookExercisesList = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getBookExercisesList();
        addIOSubscription(bookExercisesList, new AwApiSubscriber(
                new AwApiCallback<BookExercisesBean>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoadingDialog();
                    }

                    @Override
                    public void onCompleted() {
                        mView.dismissLoadingDialog();
                    }

                    @Override
                    public void onSuccess(BookExercisesBean data) {
                        mView.getBookExercisesListSuccess(data);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        mView.getBookExercisesListFail(msg);
                    }

                }));

    }

    @Override
    public void getClassHourList(String class_hour) {
        Observable<ResponseBean<ClassHourBean>> classHour = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getClassHour(class_hour);
        addIOSubscription(classHour, new AwApiSubscriber(new AwApiCallback<ClassHourBean>() {

            @Override
            public void onSuccess(ClassHourBean data) {
                mView.getClassHourListSuccess(data);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.getClassHourListFail(msg);
            }
        }));
    }

    @Override
    public void getBookMainList(String class_hour_key, String studid) {
        Observable<ResponseBean<List<BookExercisesListBean>>> bookMainList = RetrofitClient.builderRetrofit().create(APIService.class)
                .getBookMainList(class_hour_key, studid);
        addIOSubscription(bookMainList, new AwApiSubscriber(new AwApiCallback<List<BookExercisesListBean>>() {

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<BookExercisesListBean> data) {
                mView.getBookMainListSuccess(data);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.getBookMainListFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getTopicScore(String class_hour_key) {
        Observable<ResponseBean<List<TopicScoreBean>>> topicScore = RetrofitClient.builderRetrofit().create(APIService.class)
                .getTopicScore(class_hour_key);
        addIOSubscription(topicScore, new AwApiSubscriber(new AwApiCallback<List<TopicScoreBean>>() {

            @Override
            public void onSuccess(List<TopicScoreBean> data) {
                mView.getTopicScoreSuccess(data);
            }

            @Override
            public void onFailure(int code, String msg) {

            }
        }));
    }

    @Override
    public void getLessonHour(String class_hour_key) {
        Observable<ResponseBean<LessonHourBean>> lessonHour = RetrofitClient.builderRetrofit().create(APIService.class)
                .getLessonHour(class_hour_key);
        addIOSubscription(lessonHour, new AwApiSubscriber(new AwApiCallback<LessonHourBean>() {

            @Override
            public void onSuccess(LessonHourBean data) {
                mView.getLessonHour(data);
            }

            @Override
            public void onFailure(int code, String msg) {

            }
        }));
    }

    @Override
    public void getVideoPointListByTemplate(String template_id) {
        Observable<ResponseBean<List<VideoPointResultBean>>> videoPointListByTemplate = RetrofitClient.builderRetrofit().create(APIService.class)
                .getVideoPointListByTemplate(template_id);
        addIOSubscription(videoPointListByTemplate, new AwApiSubscriber(new AwApiCallback<List<VideoPointResultBean>>() {
            @Override
            public void onSuccess(List<VideoPointResultBean> data) {
                mView.getVideoPointListByTemplateSuccess(data);
            }

            @Override
            public void onFailure(int code, String msg) {

            }
        }));
    }

    @Override
    public void getVideos(String params) {
        Observable<ResponseBean<VideoResultBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getVideos(params);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<VideoResultBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(VideoResultBean model) {
                mView.getVideosSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if (AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if (msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getVideos(params);
                } else {
                    mView.getVideoFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void addQuestionBasket(RequestBody body) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .addQuestionBasket(body);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String model) {
                mView.addQuestionBasketSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if (AwDataUtil.isEmpty(msg)) {
                    mView.showMsg("添加题篮失败");
                    return;
                }
                if (msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    addQuestionBasket(body);
                } else {
                    mView.showMsg("添加题篮失败");
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void delSomeOneQuestionBasket(String questionId, String studentId) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .delSomeOneQuestionBasket(questionId, studentId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String model) {
                mView.delSomeOneQuestionBasketSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if (AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if (msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    delSomeOneQuestionBasket(questionId, studentId);
                } else {
                    mView.getVideoFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }


    @Override
    public void getPracticeDataList(String student_id, String start_date, String end_date, String course_id, String questionTypeId) {
        Observable<ResponseBean<PracticeDataResultBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getPracticeDataList(student_id, start_date, end_date, course_id, questionTypeId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<PracticeDataResultBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(PracticeDataResultBean model) {
                mView.getPracticeDataListSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.getPracticeDataListFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getPracticeDataList(student_id, start_date, end_date, course_id, questionTypeId);
                } else {
                    mView.getPracticeDataListFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }
}
