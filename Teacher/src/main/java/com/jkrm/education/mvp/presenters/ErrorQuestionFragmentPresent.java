package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.bean.ErrorBasketBean;
import com.jkrm.education.bean.MistakeBean;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.error.ErrorClassesBean;
import com.jkrm.education.bean.result.error.ErrorCourseBean;
import com.jkrm.education.bean.result.error.ErrorHomeWork;
import com.jkrm.education.mvp.views.ErrorQuestionFragmentView;

import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;

public class ErrorQuestionFragmentPresent extends AwCommonPresenter implements ErrorQuestionFragmentView.Presenter {

    private ErrorQuestionFragmentView.View mView;

    public ErrorQuestionFragmentPresent(ErrorQuestionFragmentView.View view) {
        this.mView = view;
    }


    @Override
    public void getErrorCourseList(String teacherId) {
        Observable<ResponseBean<List<ErrorCourseBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getErrorCourseList(teacherId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<ErrorCourseBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<ErrorCourseBean> data) {
                mView.getErrorCourseListSuccess(data);
            }


            @Override
            public void onFailure(int code, String msg) {
               mView.getErrorCorrseListFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getErrorHomewordList(String teacherId, String courseId) {
        Observable<ResponseBean<List<ErrorHomeWork>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getErrorHomeWordList(teacherId,courseId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<ErrorHomeWork>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<ErrorHomeWork> data) {
                mView.getErrorHomeworkListSuccess(data);
            }


            @Override
            public void onFailure(int code, String msg) {
                mView.getErrorHomeworkListFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getErrorClassesList(String schoolId, String templateId) {
        Observable<ResponseBean<List<ErrorClassesBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getErrorClassesList(schoolId,templateId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<ErrorClassesBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<ErrorClassesBean> data) {
                mView.getErrorClassesListSuccess(data);
            }


            @Override
            public void onFailure(int code, String msg) {
                mView.getErrorClassesListFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getErrorMistakeList(String classIds, String templateIds, String maxGradeRatio, String minGradeRatio) {
        Observable<ResponseBean<List<MistakeBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getErrorMistakeList(classIds,templateIds,maxGradeRatio,minGradeRatio);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<MistakeBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<MistakeBean> data) {
                mView.getErrorMistakeListSuccess(data);
            }


            @Override
            public void onFailure(int code, String msg) {
                mView.getErrorClassesListFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void addErrorBasket(RequestBody requestBody) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .addErrorBasket(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String data) {
                mView.addErrorBasketSuccess(data);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.addErrorBasketFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getErrorBasket(String teacherId) {
        Observable<ResponseBean<List<ErrorBasketBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getErrorBasket(teacherId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<ErrorBasketBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<ErrorBasketBean> data) {
                mView.getErrorBasketSuccess(data);

            }

            @Override
            public void onFailure(int code, String msg) {
                mView.getErrorBasketFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void deleteErrorBasket(RequestBody requestBody) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .deleteErrorBasket(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String data) {
                mView.deleteErrorBasketSuccess(data);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.deleteErrorBasketFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }
}
