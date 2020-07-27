package com.jkrm.education.mvp.presenters;

import android.util.Log;

import com.hzw.baselib.bean.AwResponseListBean;
import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiListCallback;
import com.hzw.baselib.interfaces.AwApiListSubscriber;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.StatusErrorQuestionResultBean;
import com.jkrm.education.bean.result.StatusHomeworkScanResultBean;
import com.jkrm.education.bean.result.TeacherTodoBean;
import com.jkrm.education.mvp.views.MainFragmentView;

import java.util.List;

import rx.Observable;

public class MainFragmentPresent extends AwCommonPresenter implements MainFragmentView.Presenter {

    private MainFragmentView.View mView;

    public MainFragmentPresent(MainFragmentView.View view) {
        this.mView = view;
    }

    @Override
    public void getTeacherTodoList(String teacherId, int index) {
        Observable<AwResponseListBean<String, TeacherTodoBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getTeacherTodoList(teacherId, index, APIService.COMMON_PAGE_SIZE);

        addIOSubscription(observable, new AwApiListSubscriber(new AwApiListCallback<String, TeacherTodoBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String data, List<TeacherTodoBean> list, int total, int size, int pages, int current) {
                mView.getTeacherTodoListSuccess(data, list, total, size, pages, current);
                AwLog.d("getTeacherTodoList success total: " + total + " ,size: " + size + " ,pages: " + pages + " ,current: " + current);
                if(data != null) {
                    AwLog.d("getTeacherTodoList success data: " + data.toString());
                } else {
                    AwLog.d("getTeacherTodoList success data is null");
                }
                if(list != null) {
                    AwLog.d("getTeacherTodoList success list: " + list.size());
                } else {
                    AwLog.d("getTeacherTodoList success list is null");
                }

            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getTeacherTodoList(teacherId, index);
                } else {
                    mView.getTeacherTodoListFaile(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getStatusMarkBeforeDawn(String teacherId) {
        Observable<ResponseBean<StatusHomeworkScanResultBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getStatusMarkBeforeDawn(teacherId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<StatusHomeworkScanResultBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(StatusHomeworkScanResultBean model) {
                mView.getStatusMarkBeforeDawnSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getStatusMarkBeforeDawn(teacherId);
                } else {
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getStatusErrorQuestionInSomeDay(String teacherId) {
        Observable<ResponseBean<List<StatusErrorQuestionResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getStatusErrorQuestionInSomeDay(teacherId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<StatusErrorQuestionResultBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<StatusErrorQuestionResultBean> model) {
                mView.getStatusErrorQuestionInSomeDaySuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.getStatusErrorQuestionInSomeDayFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getStatusErrorQuestionInSomeDay(teacherId);
                } else {
                    mView.getStatusErrorQuestionInSomeDayFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

}
