package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.bean.test.TestMarkBean;
import com.jkrm.education.mvp.views.MarkMainFragmentView;
import com.jkrm.education.util.TestDataUtil;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class MarkingMainPresent extends AwCommonPresenter implements MarkMainFragmentView.Presenter {

    private MarkMainFragmentView.View mView;

    public MarkingMainPresent(MarkMainFragmentView.View view) {
        this.mView = view;
    }

    @Override
    public void getMarkingList() {
        Observable<List<TestMarkBean>> observable = Observable.create(subscriber -> {
            subscriber.onNext(TestDataUtil.createMarkingList());
            subscriber.onCompleted();
        });
        addIOSubscription(observable, new Subscriber<List<TestMarkBean>>() {

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoadingDialog();
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                mView.showMsg(e.getMessage());
                mView.getMarkingListFailure();
            }

            @Override
            public void onNext(List<TestMarkBean> list) {
                mView.getMarkingListSuccess(list);
            }
        });
    }
}
