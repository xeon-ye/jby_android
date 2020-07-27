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
import com.jkrm.education.bean.result.AnswerSheetProgressResultBean;
import com.jkrm.education.bean.result.NormalBean;
import com.jkrm.education.bean.result.OriginalPagerResultBean;
import com.jkrm.education.mvp.views.SeeOriginalPagerView;

import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

public class SeeOriginalPagerPresent extends AwCommonPresenter implements SeeOriginalPagerView.Presenter {

    private SeeOriginalPagerView.View mView;

    public SeeOriginalPagerPresent(SeeOriginalPagerView.View view) {
        this.mView = view;
    }

    @Override
    public void answerSheetProgress(String homeworkId, String classId) {
        Observable<ResponseBean<List<AnswerSheetProgressResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .answerSheetProgress(homeworkId, classId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<AnswerSheetProgressResultBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<AnswerSheetProgressResultBean> model) {
                mView.answerSheetProgressSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if (AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if (msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    answerSheetProgress(homeworkId, classId);
                } else {
                    //                    mView.getAnswerSheetsFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getStudentOriginalQuestionAnswer(String homework_id, String student_id) {
        Observable<ResponseBean<OriginalPagerResultBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getStudentOriginalQuestionAnswer(homework_id, student_id);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<OriginalPagerResultBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(OriginalPagerResultBean model) {
                mView.getStudentOriginalQuestionAnswerSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if (AwDataUtil.isEmpty(msg)) {
                    mView.showMsg("获取原卷失败");
                    return;
                }
                if (msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getStudentOriginalQuestionAnswer(homework_id, student_id);
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
    public void unConnect(RequestBody requestBody) {
        Observable<NormalBean> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .unConnect(requestBody);
        addIOSubscription(observable, new Subscriber() {

                    @Override
                    public void onCompleted() {
                        mView.dismissLoadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        String see = e.getMessage();
                        mView.unConnectFail("请求错误");
                    }

                    @Override
                    public void onNext(Object o) {
                        //格式与项目封装不一样单独处理
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.setPrettyPrinting();
                        Gson gson = gsonBuilder.create();
                        String s = gson.toJson(o);
                        NormalBean data = new Gson().fromJson(s, NormalBean.class);
                        if ("200".equals(data.getCode())) {
                            mView.unConnectSuccess(data);
                        } else {
                            mView.unConnectFail(data.getMsg());
                        }
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoadingDialog();
                    }
                }
        );
    }
}
