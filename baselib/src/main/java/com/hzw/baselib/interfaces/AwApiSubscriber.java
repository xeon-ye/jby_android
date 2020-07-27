package com.hzw.baselib.interfaces;

import com.google.gson.JsonParseException;
import com.hzw.baselib.bean.AwResponseBean;
import com.hzw.baselib.bean.ResetPSWType;
import com.hzw.baselib.bean.RxLoginTimeOutType;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by hzw on 2017/11/29.
 */

public class AwApiSubscriber<T> extends Subscriber<AwResponseBean<T>> {
    public static final int UNKNOWN_CODE = -1;
    private AwApiCallback<T> apiCallBack;

    public AwApiSubscriber(AwApiCallback<T> apiCallBack) {
        this.apiCallBack = apiCallBack;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (null == apiCallBack) {
            return;
        }
        apiCallBack.onStart();
    }

    @Override
    public void onCompleted() {
        if (null == apiCallBack) {
            return;
        }
        apiCallBack.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (null == apiCallBack) {
            return;
        }
        if (e instanceof HttpException || e instanceof UnknownHostException) {
            apiCallBack.onFailure(UNKNOWN_CODE, "系统异常，请稍后重试");
        } else if(e instanceof SocketTimeoutException) {
            apiCallBack.onFailure(UNKNOWN_CODE, "连接服务器超时，请检查网络或联系客服人员");
        } else if(e instanceof ConnectException) {
            apiCallBack.onFailure(UNKNOWN_CODE, "连接服务器失败，请检查网络或联系客服人员");
        }else if(e instanceof JsonParseException||e instanceof JSONException||e instanceof ParseException){
            apiCallBack.onFailure(UNKNOWN_CODE,"解析错误");
        } else {
            if (e.getMessage() != null && e.getMessage().contains("failed") || e.getMessage().contains("Failed")) {
                apiCallBack.onFailure(UNKNOWN_CODE, "网络异常，请稍后重试");
            } else {
                apiCallBack.onFailure(UNKNOWN_CODE, e.getMessage());
            }
        }
        apiCallBack.onCompleted();
    }

    @Override
    public void onNext(AwResponseBean<T> tResponseBean) {
        if (null == apiCallBack) {
            return;
        }
//        tResponseBean.setCode(401);
        if (tResponseBean.isSuccess()) {
            apiCallBack.onSuccess(tResponseBean.getData());
        }else if(tResponseBean.isNeedResetPSW()){
            //密码为初始密码需要强制重置密码
            EventBus.getDefault().postSticky(new ResetPSWType());
            apiCallBack.onSuccess(tResponseBean.getData());
        }
        else if (tResponseBean.isTokenError()) {
            EventBus.getDefault().postSticky(new RxLoginTimeOutType());
        } else {
            apiCallBack.onFailure(tResponseBean.getCode(), tResponseBean.getMsg());
        }
    }
}
