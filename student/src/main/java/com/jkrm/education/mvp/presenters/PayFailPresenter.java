package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.mvp.views.PayFailView;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/6 11:34
 */

public class PayFailPresenter extends AwCommonPresenter implements PayFailView.Presenter {
    private PayFailView.View mView;

    public PayFailPresenter(PayFailView.View view) {
        mView = view;
    }
}
