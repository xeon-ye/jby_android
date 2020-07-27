package com.jkrm.education.mvp.presenters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.ErrorQuestionClassifyBean;
import com.jkrm.education.bean.ErrorQuestionDetailBean;
import com.jkrm.education.bean.ErrorQuestionTimeBean;
import com.jkrm.education.bean.ErrorQuestionTimePagedBean;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.WatchLogBean;
import com.jkrm.education.mvp.views.ErrorQuestionFragmentView;

import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/3 17:16
 */

public class ErrorQuestionFragmentPresent extends AwCommonPresenter implements ErrorQuestionFragmentView.Presenter {
    private ErrorQuestionFragmentView.View mView;

    public ErrorQuestionFragmentPresent(ErrorQuestionFragmentView.View view) {
        mView = view;
    }
    @Override
    public void getByClassify(RequestBody requestBody) {
        Observable<ResponseBean<List<ErrorQuestionClassifyBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getByClassify(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<ErrorQuestionClassifyBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<ErrorQuestionClassifyBean> model) {
                mView.getByClassifySuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.getByClassifyFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getByClassify(requestBody);
                } else {
                    mView.getByClassifyFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getByTime(RequestBody requestBody) {
        Observable<ResponseBean<List<ErrorQuestionTimeBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getByTime(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<ErrorQuestionTimeBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<ErrorQuestionTimeBean> model) {
                mView.getByTimeSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.getByTimeSFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getByTime(requestBody);
                } else {
                    mView.getByTimeSFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getErrorDetail(RequestBody requestBody) {
        Observable<ResponseBean<List<ErrorQuestionDetailBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getErrorDetail(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<ErrorQuestionDetailBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<ErrorQuestionDetailBean> model) {
                mView.getErrorDetailSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.getErrorDetailFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getErrorDetail(requestBody);
                } else {
                    mView.getErrorDetailFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getByTimePaged(RequestBody requestBody) {
        Observable<ErrorQuestionTimePagedBean> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getByTimePaged(requestBody);
        addIOSubscription(observable, new Subscriber() {

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
            @Override
            public void onError(Throwable e) {
                String see=e.getMessage();
                mView.getByTimePagedFail("请求错误");
            }

            @Override
            public void onNext(Object o) {
                //格式与项目封装不一样单独处理
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setPrettyPrinting();
                Gson gson = gsonBuilder.create();
                String s = gson.toJson(o);
                ErrorQuestionTimePagedBean data = new Gson().fromJson(s, ErrorQuestionTimePagedBean.class);
                if("200".equals(data.getCode())){
                    mView.getByTimePagedSuccess(data);
                }else{
                    mView.getByTimePagedFail(data.getMsg());
                }
            }
        });
    }
}
