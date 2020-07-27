package com.hzw.baselib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hzw.baselib.presenters.AwCommonPresenter;

/**
 * Created by hzw on 2017/11/29.
 */

public abstract class AwMvpActivity<T extends AwCommonPresenter> extends AwBaseActivity {
    protected T mPresenter;

    protected abstract T createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        if (null != mPresenter) {
            mPresenter.attachView();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPresenter) {
            mPresenter.detachView();
        }
    }
}
