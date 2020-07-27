package com.hzw.baselib.interfaces;

import java.util.List;

/**
 * Created by hzw on 2017/11/9.
 */

public abstract class AwApiListCallback<T, F> {

    public void onStart() {}
    public abstract void onSuccess(T data, List<F> list, int total, int size, int pages, int current);
//    public abstract void onTokenError(int code, String msg);
    public abstract void onFailure(int code, String msg);
    public void onCompleted() {}

}
