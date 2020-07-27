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
import com.jkrm.education.bean.result.AccountBalancesBean;
import com.jkrm.education.bean.result.CreateOrderResultBean;
import com.jkrm.education.bean.result.ErrorQuestionResultBean;
import com.jkrm.education.bean.result.WatchLogBean;
import com.jkrm.education.bean.result.WatchResultBean;
import com.jkrm.education.mvp.views.CourseBroadcastView;

import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/6 14:27
 */

public class CourseBroadcastPresent extends AwCommonPresenter implements CourseBroadcastView.Presenter {
    private CourseBroadcastView.View mView;

    public CourseBroadcastPresent(CourseBroadcastView.View view) {
        mView = view;
    }

    @Override
    public void saveWatchLog(RequestBody requestBody) {
        Observable<WatchLogBean> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .saveWatchLog(requestBody);
        addIOSubscription(observable, new Subscriber() {

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
            @Override
            public void onError(Throwable e) {
                String see=e.getMessage();
                mView.onSaveWatchLogFail("请求错误");
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
                    mView.onSaveWatchLogSuccess(data);
                }else{
                    mView.onSaveWatchLogFail(data.getMsg());
                }
            }
        });
    }

    @Override
    public void getWatchList(RequestBody requestBody) {
        Observable<ResponseBean<WatchResultBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getWatchList(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<WatchResultBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(WatchResultBean data) {
                mView.getWatchListSuccess(data);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
//                    mView.showMsg("获取错题统计列表数据失败， 错误码： " + code);
                    mView.getWatchListFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getWatchList(requestBody);
                } else {
                    mView.getWatchListFail(msg);
//                    mView.showMsg(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }
}
