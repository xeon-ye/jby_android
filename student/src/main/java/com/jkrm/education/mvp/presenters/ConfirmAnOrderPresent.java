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
import com.jkrm.education.bean.result.CreateAliPayOrderResultBean;
import com.jkrm.education.bean.result.CreateOrderResultBean;
import com.jkrm.education.bean.result.CreateWechatPayOrderResultBean;
import com.jkrm.education.bean.result.PracticeTableResult;
import com.jkrm.education.bean.result.PurseResultBean;
import com.jkrm.education.mvp.views.ConfirmAnOrderView;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/6 10:05
 */

public class ConfirmAnOrderPresent extends AwCommonPresenter implements ConfirmAnOrderView.Presenter {

    private ConfirmAnOrderView.View mView;

    public ConfirmAnOrderPresent(ConfirmAnOrderView.View view) {
        mView = view;
    }

    @Override
    public void createOrder(RequestBody body) {
        Observable<ResponseBean<CreateOrderResultBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .createOrder(body);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<CreateOrderResultBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(CreateOrderResultBean data) {
                mView.createOrderSuccess(data);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.createOrderFail(msg);
            }


            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void createWechatOrder(RequestBody body) {
        Observable<ResponseBean<CreateWechatPayOrderResultBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .createWechatPayOrder(body);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<CreateWechatPayOrderResultBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(CreateWechatPayOrderResultBean data) {
                mView.createWechatOrderSuccess(data);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.createWechatOrderFail(msg);
            }


            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void createAlipayOrder(String orderNo, String amount) {
        Observable<CreateAliPayOrderResultBean> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .createAlipayOrder(orderNo,amount);
        addIOSubscription(observable, new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                String see=e.getMessage();
                mView.getAccountBalancesFail("请求错误");
            }

            @Override
            public void onNext(Object o) {
                //格式与项目封装不一样单独处理
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setPrettyPrinting();
                Gson gson = gsonBuilder.create();
                String s = gson.toJson(o);
                CreateAliPayOrderResultBean data = new Gson().fromJson(s, CreateAliPayOrderResultBean.class);
                if("200".equals(data.getCode())){
                    mView.createAlipayOrderSuccess(data);
                }else{
                    mView.createAlipayOrderFail(data.getMsg());
                }
            }
        });
    }

    @Override
    public void getAccountBalances(String user_id) {
        //格式不一样单独处理
        Observable<AccountBalancesBean> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getAccountBalances(user_id);
        addIOSubscription(observable, new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                String see=e.getMessage();
                mView.getAccountBalancesFail("请求错误");
            }

            @Override
            public void onNext(Object o) {
                //格式与项目封装不一样单独处理
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setPrettyPrinting();
                Gson gson = gsonBuilder.create();
                String s = gson.toJson(o);
                AccountBalancesBean data = new Gson().fromJson(s, AccountBalancesBean.class);
                if("200".equals(data.getCode())){
                    mView.getAccountBalancesSuccess(data);
                }else{
                    mView.getAccountBalancesFail(data.getMsg());
                }
            }
        });
    }

    @Override
    public void pursePay(RequestBody body) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .pursePay(body);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String data) {
                mView.pursePaySuccess(data);
            }


            @Override
            public void onFailure(int code, String msg) {
                mView.pursePayFail(msg);
            }


            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }


}
