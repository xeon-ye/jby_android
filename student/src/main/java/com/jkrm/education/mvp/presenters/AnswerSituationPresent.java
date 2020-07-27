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
import com.jkrm.education.bean.result.WatchLogBean;
import com.jkrm.education.mvp.views.AnswerSituationView;

import rx.Observable;
import rx.Subscriber;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/1 16:52
 */

public class AnswerSituationPresent extends AwCommonPresenter implements AnswerSituationView.Presenter {
    private AnswerSituationView.View mView;

    public AnswerSituationPresent(AnswerSituationView.View view) {
        mView = view;
    }

    @Override
    public void explainQuestion(String value, String templateId, String studentId, String questionId) {
        Observable<WatchLogBean> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .explainQuestion(value,templateId,studentId,questionId);
        addIOSubscription(observable, new Subscriber() {

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
            @Override
            public void onError(Throwable e) {
                String see=e.getMessage();
                mView.explainQuestionFail("请求错误");
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
                    mView.explainQuestionSuccess(data);
                }else{
                    mView.explainQuestionFail(data.getMsg());
                }
            }
        });
    }

    @Override
    public void addMistake(String templateId, String studentId, String questionId) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .addMistake(templateId,studentId,questionId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String model) {
                mView.addMistakeSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.showMsg("添加错题本失败， 错误码： " + code);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    addMistake(templateId,studentId,questionId);
                } else {
                    mView.showMsg(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void deleteMistake(String questionId, String studentId) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .delErrorQuestion(questionId, studentId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String model) {
                mView.deleteMistakeFail(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.showMsg("移出错题本失败， 错误码： " + code);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    deleteMistake(questionId, studentId);
                } else {
                    mView.showMsg(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }
}
