package com.jkrm.education.mvp.presenters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.ClassHourBean;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.bean.result.WatchLogBean;
import com.jkrm.education.mvp.views.AnswerAnalysisView;
import com.jkrm.education.mvp.views.BookExercisesFragmentView;

import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/5/28 18:45
 */

public class AnswerAnalysisPresent extends AwCommonPresenter implements AnswerAnalysisView.Presenter  {

    private AnswerAnalysisView.View mView;

    public AnswerAnalysisPresent(AnswerAnalysisView.View view) {
        this.mView = view;
    }



    @Override
    public void collectQuestion(RequestBody requestBody) {
        Observable<WatchLogBean> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .collectQuestion(requestBody);
        addIOSubscription(observable, new Subscriber() {

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
            @Override
            public void onError(Throwable e) {
                String see=e.getMessage();
                mView.collectQuestionFail("请求错误");
            }

            @Override
            public void onNext(Object o) {
                //格式与项目封装不一样单独处理
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setPrettyPrinting();
                Gson gson = gsonBuilder.create();
                String s = gson.toJson(o);
                WatchLogBean data = new Gson().fromJson(s, WatchLogBean.class);
                if("200".equals(data.getCode())){
                    mView.collectQuestionSuccess(data);
                }else{
                    mView.collectQuestionFail(data.getMsg());
                }
            }
        });
    }

    @Override
    public void removeCollectQuestion(RequestBody requestBody) {
        Observable<WatchLogBean> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .removeCollectQuestion(requestBody);
        addIOSubscription(observable, new Subscriber() {

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
            @Override
            public void onError(Throwable e) {
                String see=e.getMessage();
                mView.removeCollectQuestionFail("请求错误");
            }

            @Override
            public void onNext(Object o) {
                //格式与项目封装不一样单独处理
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setPrettyPrinting();
                Gson gson = gsonBuilder.create();
                String s = gson.toJson(o);
                WatchLogBean data = new Gson().fromJson(s, WatchLogBean.class);
                if("200".equals(data.getCode())){
                    mView.removeCollectQuestionSuccess(data);
                }else{
                    mView.removeCollectQuestionFail(data.getMsg());
                }
            }
        });
    }

    @Override
    public void readOverQuestion(RequestBody requestBody) {
        Observable<WatchLogBean> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .readOverQuestion(requestBody);
        addIOSubscription(observable, new Subscriber() {

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
            @Override
            public void onError(Throwable e) {
                String see=e.getMessage();
                mView.readOverQuestionFail("请求错误");
            }

            @Override
            public void onNext(Object o) {
                //格式与项目封装不一样单独处理
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setPrettyPrinting();
                Gson gson = gsonBuilder.create();
                String s = gson.toJson(o);
                WatchLogBean data = new Gson().fromJson(s, WatchLogBean.class);
                if("200".equals(data.getCode())){
                    mView.readOverQuestionSuccess(data);
                }else{
                    mView.readOverQuestionFail(data.getMsg());
                }
            }
        });
    }

    @Override
    public void getSimilar(String params) {
        Observable<ResponseBean<List<SimilarResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getSimilar(params);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<SimilarResultBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<SimilarResultBean> model) {
                mView.getSimilarSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getSimilar(params);
                } else {
                    mView.getSimilarFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }
}
