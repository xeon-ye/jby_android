package com.hzw.baselib.interfaces;

/**
 * Created by hzw on 2017/11/9.
 */

public abstract class AwApiCallback<T> {

    public void onStart() {}
    public abstract void onSuccess(T data);
//    public abstract void onTokenError(int code, String msg);
    public abstract void onFailure(int code, String msg);
    public void onCompleted() {}

}
