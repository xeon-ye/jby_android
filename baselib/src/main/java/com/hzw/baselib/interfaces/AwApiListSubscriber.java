package com.hzw.baselib.interfaces;

import com.hzw.baselib.bean.AwResponseBean;
import com.hzw.baselib.bean.AwResponseListBean;
import com.hzw.baselib.bean.RxLoginTimeOutType;

import org.greenrobot.eventbus.EventBus;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by hzw on 2017/11/29.
 */

public class AwApiListSubscriber<T, F> extends Subscriber<AwResponseListBean<T, F>> {
    public static final int UNKNOWN_CODE = -1;
    private AwApiListCallback<T, F> apiCallBack;

    public AwApiListSubscriber(AwApiListCallback<T, F> apiCallBack) {
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
    public void onNext(AwResponseListBean<T, F> tResponseBean) {
        if (null == apiCallBack) {
            return;
        }
//        tResponseBean.setCode(401);
        if (tResponseBean.isSuccess()) {
            apiCallBack.onSuccess(tResponseBean.getData(), tResponseBean.getRows(), tResponseBean.getTotal(), tResponseBean.getSize(), tResponseBean.getPages(), tResponseBean.getCurrent());
        } else if (tResponseBean.isTokenError()) {
            EventBus.getDefault().postSticky(new RxLoginTimeOutType());
        } else {
            apiCallBack.onFailure(tResponseBean.getCode(), tResponseBean.getMsg());
        }
    }
}
