package com.jkrm.education.mvp.presenters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.ClassessBean;
import com.jkrm.education.bean.TaskBean;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.exam.ClassBean;
import com.jkrm.education.bean.exam.ExamSearchBean;
import com.jkrm.education.bean.exam.GradeBean;
import com.jkrm.education.bean.result.StatisticsHomeworkSubmitTableResultBeanNew;
import com.jkrm.education.mvp.views.AnalysisView;
import com.jkrm.education.mvp.views.TaskView;
import com.jkrm.education.ui.activity.exam.ExamSearchActivity;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/10 15:06
 */

public class AnalysisPresent extends AwCommonPresenter implements AnalysisView.Presenter {


    private AnalysisView.View mView;

    public AnalysisPresent(AnalysisView.View view) {
        this.mView = view;
    }




    @Override
    public void getAnalysisList(RequestBody requestBody) {
        Observable<ExamSearchBean> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getAnalysisList(requestBody);
        addIOSubscription(observable, new Subscriber() {
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
            public void onError(Throwable e) {
                mView.dismissLoadingDialog();
                mView.getAnalysisListFail(e.getMessage());
            }

            @Override
            public void onNext(Object o) {
                //格式不一样
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setPrettyPrinting();
                Gson gson = gsonBuilder.create();
                String s = gson.toJson(o);
                ExamSearchBean data = new Gson().fromJson(s, ExamSearchBean.class);
                mView.getAnalysisListSuccess(data);
            }
        });
    }

    @Override
    public void getGradeList(RequestBody requestBody) {
        Observable<ResponseBean<List<GradeBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getGradeList(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<GradeBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<GradeBean> data) {
                mView.getGradeListSuccess(data);

            }

            @Override
            public void onFailure(int code, String msg) {
                mView.getGradeListFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getClassList(String teacherId) {
        Observable<ResponseBean<List<ClassBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getClassList(teacherId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<ClassBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<ClassBean> data) {
                mView.getClassListSuccess(data);
            }


            @Override
            public void onFailure(int code, String msg) {
                mView.getClassListFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }
}
