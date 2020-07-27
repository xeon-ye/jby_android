package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.ResolveTeacherProgressResultBean;
import com.jkrm.education.bean.scan.ScanBean;
import com.jkrm.education.mvp.views.ScanView;
import com.jkrm.education.util.TestDataUtil;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class ScanPresent extends AwCommonPresenter implements ScanView.Presenter {

    private ScanView.View mView;

    public ScanPresent(ScanView.View view) {
        this.mView = view;
    }

    @Override
    public void getScanInfo(int totalPage, int scanedPage) {
        if(totalPage == 50 && scanedPage == 50) {
            mView.getScanInfoSuccess(null);
            return;
        }
        if(totalPage < 50) {
            if(totalPage + 5 >= 50) {
                totalPage = 50;
            }  else {
                totalPage += 5;
            }
        } else {
            totalPage = 50;
        }
        if(scanedPage < 50) {
            if(scanedPage + 5 >= 50) {
                scanedPage = 50;
            }  else {
                scanedPage += 5;
            }
        } else {
            scanedPage = 50;
        }
        int finalTotalPage = totalPage;
        int finalScanedPage = scanedPage;
        Observable<ScanBean> observable = Observable.create(subscriber -> {
            subscriber.onNext(TestDataUtil.createScanList(finalTotalPage, finalScanedPage));
            subscriber.onCompleted();
        });
        addIOSubscription(observable, new Subscriber<ScanBean>() {

            @Override
            public void onStart() {
                super.onStart();
                //                mView.showLoadingDialog();
            }

            @Override
            public void onCompleted() {
                //                mView.dismissLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                mView.showMsg(e.getMessage());
            }

            @Override
            public void onNext(ScanBean bean) {
                mView.getScanInfoSuccess(bean);
            }
        });
    }

    @Override
    public void getTeacherResolveProgress(String teacherId) {
        Observable<ResponseBean<List<ResolveTeacherProgressResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getTeacherResolveProgress(teacherId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<ResolveTeacherProgressResultBean>>() {
            @Override
            public void onStart() {
//                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<ResolveTeacherProgressResultBean> model) {
                mView.getTeacherResolveProgressSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.getTeacherResolveProgressFail(msg);
            }

            @Override
            public void onCompleted() {
//                mView.dismissLoadingDialog();
            }
        }));
    }
}
