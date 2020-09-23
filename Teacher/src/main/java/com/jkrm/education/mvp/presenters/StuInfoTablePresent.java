package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.exam.ScoreAchievementBean;
import com.jkrm.education.bean.exam.StuInfoTableBean;
import com.jkrm.education.mvp.views.ScoreAchievementView;
import com.jkrm.education.mvp.views.StuInfoTableView;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

public class StuInfoTablePresent extends AwCommonPresenter implements StuInfoTableView.Presenter {

    private StuInfoTableView.View mView;

    public StuInfoTablePresent(StuInfoTableView.View view) {
        this.mView = view;
    }


    @Override
    public void getTableList(RequestBody requestBody) {
        Observable<StuInfoTableBean> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getStuInfoTable(requestBody);
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
                    StuInfoTableBean data = (StuInfoTableBean) o;
                    if (data.getCode().equals("200"))
                        mView.getTableListSuccess(data);
                    else
                        mView.getTableListFail(data.getMsg());
                } else
                    mView.getTableListFail("数据异常！！");
            }
        });
    }


}
