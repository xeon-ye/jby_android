package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.exam.StuSectionTableBean;
import com.jkrm.education.bean.exam.StuTableTitleBean;
import com.jkrm.education.mvp.views.StuSectionTableView;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

public class StuSectionTablePresent extends AwCommonPresenter implements StuSectionTableView.Presenter {

    private StuSectionTableView.View mView;

    public StuSectionTablePresent(StuSectionTableView.View view) {
        this.mView = view;
    }

    @Override
    public void getTableList(RequestBody requestBody) {

        Observable<StuSectionTableBean> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getStuSectionTable(requestBody);
        addIOSubscription(observable, new Subscriber() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onNext(Object o) {
                if (o != null) {
                    StuSectionTableBean data = (StuSectionTableBean) o;
                    if (data.getCode().equals("200"))
                        mView.getTableListSuccess(data);
                    else
                        mView.getTableListFail(data.getMsg());
                } else
                    mView.getTableListFail("数据异常！！");
            }
        });
    }

    @Override
    public void getTableTitle(RequestBody requestBody) {
        Observable<StuTableTitleBean> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getStuSectionTitle(requestBody);
        addIOSubscription(observable, new Subscriber() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onNext(Object o) {
                if (o != null) {
                    StuTableTitleBean data = (StuTableTitleBean) o;
                    if (data.getCode().equals("200"))
                        mView.getTableTitleSuccess(data);
                    else
                        mView.getTableTitleFail(data.getMsg());
                } else
                    mView.getTableListFail("数据异常！！");
            }
        });
    }

}
