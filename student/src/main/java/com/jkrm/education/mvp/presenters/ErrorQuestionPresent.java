package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiListCallback;
import com.hzw.baselib.interfaces.AwApiListSubscriber;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.project.student.bean.SubjectDataResultBean;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.hzw.baselib.project.student.bean.AnswerSheetAllDataResultBean;
import com.jkrm.education.bean.common.ResponseListBean;
import com.jkrm.education.bean.result.AnswerSheetDataResultBean;
import com.jkrm.education.bean.result.ErrorQuestionResultBean;
import com.jkrm.education.bean.result.PracticeDataResultBean;
import com.jkrm.education.bean.result.VideoResultBean;
import com.jkrm.education.mvp.views.ErrorQuestionView;

import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;

public class ErrorQuestionPresent extends AwCommonPresenter implements ErrorQuestionView.Presenter {

    private ErrorQuestionView.View mView;

    public ErrorQuestionPresent(ErrorQuestionView.View view) {
        this.mView = view;
    }


    @Override
    public void getHomeworkListAll(String student_id,String course_id) {
        Observable<ResponseBean<List<AnswerSheetAllDataResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .answer_sheets_all(student_id,course_id);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<AnswerSheetAllDataResultBean>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(List<AnswerSheetAllDataResultBean> model) {
                mView.getHomeworkListAllSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getHomeworkListAll(student_id,course_id);
                } else {
                    mView.getHomeworkListAllFail(msg);
                }
            }

            @Override
            public void onCompleted() {
            }
        }));
    }

    @Override
    public void getMistakeListNew(String student_id, String current, String start_date, String end_date, String homework_id, String course_id, String order_type) {
        Observable<ResponseListBean<AnswerSheetDataResultBean, ErrorQuestionResultBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getMistakeListNew(student_id, current, String.valueOf(APIService.COMMON_PAGE_SIZE), start_date, end_date, homework_id, course_id, "", order_type);
        addIOSubscription(observable, new AwApiListSubscriber(new AwApiListCallback<AnswerSheetDataResultBean, ErrorQuestionResultBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(AnswerSheetDataResultBean data, List<ErrorQuestionResultBean> list, int total, int size, int pages, int current) {
                mView.getMistakeListSuccess(data, list, total, size, pages, current);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getMistakeListNew(student_id, current, start_date, end_date, homework_id, course_id, order_type);
                } else {
                    mView.getMistakeListFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
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
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
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
                if(AwDataUtil.isEmpty(msg)) {
                    mView.showMsg("添加题篮失败");
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
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
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
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
    public void getSubjectList(String studentId) {
        Observable<ResponseBean<List<SubjectDataResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .subject_all(studentId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<SubjectDataResultBean>>() {
            @Override
            public void onSuccess(List<SubjectDataResultBean> data) {
                mView.getSubjectListSuccess(data);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.getMistakeListFail(msg);
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
