package com.hzw.baselib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hzw.baselib.presenters.AwCommonPresenter;

/**
 * Created by hzw on 2017/11/29.
 */

public abstract class AwMvpFragment<T extends AwCommonPresenter> extends AwBaseFragment {
    protected T mPresenter;

    protected abstract T createPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (null != mPresenter) {
            mPresenter.attachView();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != mPresenter) {
            mPresenter.detachView();
        }
    }
}
