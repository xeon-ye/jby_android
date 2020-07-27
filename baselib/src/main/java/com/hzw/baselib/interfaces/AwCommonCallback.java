package com.hzw.baselib.interfaces;

/**
 * Created by hzw on 2017/11/9.
 */

public abstract class AwCommonCallback<T> {

    public abstract void onStart();
    public abstract void onSuccess(T data);
    public abstract void onFailure(String msg);

}
