package com.hzw.baselib.interfaces;

import rx.Subscriber;

/**
 * Created by hzw on 2017/11/29.
 */

public class AwCommonSubscriber<T> extends Subscriber<T> {

    private AwCommonCallback<T> mCommonCallback;

    public AwCommonSubscriber(AwCommonCallback mCommonCallback) {
        this.mCommonCallback = mCommonCallback;
    }

    @Override
    public void onStart() {
        if (null == mCommonCallback) {
            return;
        }
        mCommonCallback.onStart();
    }

    @Override
    public void onCompleted() {
        //TODO ...
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (null == mCommonCallback) {
            return;
        }
        mCommonCallback.onFailure(e.getMessage());
    }

    @Override
    public void onNext(T t) {
        if (null == mCommonCallback) {
            return;
        }
        mCommonCallback.onSuccess(t);
    }
}
