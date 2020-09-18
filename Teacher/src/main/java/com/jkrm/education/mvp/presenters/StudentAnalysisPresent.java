package com.jkrm.education.mvp.presenters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.exam.ClassBean;
import com.jkrm.education.bean.exam.ColumnDataBean;
import com.jkrm.education.bean.exam.ExamSearchBean;
import com.jkrm.education.bean.exam.GradeBean;
import com.jkrm.education.bean.exam.LineDataBean;
import com.jkrm.education.bean.exam.OverViewBean;
import com.jkrm.education.bean.exam.TableClassBean;
import com.jkrm.education.mvp.views.AnalysisView;
import com.jkrm.education.mvp.views.StudentAnalysisView;
import com.jkrm.education.ui.activity.exam.StudentAnalyseActivity;

import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/10 15:06
 */

public class StudentAnalysisPresent extends AwCommonPresenter
        implements StudentAnalysisView.Presenter {


    private StudentAnalysisView.View mView;

    public StudentAnalysisPresent(StudentAnalysisView.View view) {
        this.mView = view;
    }

    @Override
    public void getOverView(RequestBody requestBody) {
        Observable<ResponseBean<OverViewBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getOverView(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<OverViewBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(OverViewBean data) {
                mView.getOverViewSuccess(data);
            }


            @Override
            public void onFailure(int code, String msg) {
                mView.getOverViewFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getColumnData(RequestBody requestBody) {
        Observable<ResponseBean<List<ColumnDataBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getColumnData(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<ColumnDataBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<ColumnDataBean> data) {
                mView.getColumnDataSuccess(data);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.getLineDataFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getLineData(RequestBody requestBody) {
        Observable<ResponseBean<List<LineDataBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getLineData(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<LineDataBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<LineDataBean> data) {
                mView.getLineDataSuccess(data);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.getLineDataFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }








  /*  @Override
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
    }*/
}
