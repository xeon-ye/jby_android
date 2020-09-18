package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.PeriodCourseBean;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.exam.MultipleAchievementBean;
import com.jkrm.education.bean.exam.TableClassBean;
import com.jkrm.education.bean.result.error.ErrorCourseBean;
import com.jkrm.education.mvp.views.MultipleAchievementView;

import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

public class MultipleAchievementPresent extends AwCommonPresenter implements MultipleAchievementView.Presenter {

    private MultipleAchievementView.View mView;

    public MultipleAchievementPresent(MultipleAchievementView.View view) {
        this.mView = view;
    }


    @Override
    public void getTableList(RequestBody requestBody) {

        Observable<MultipleAchievementBean> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getMultipleTable(requestBody);
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
                    MultipleAchievementBean data = (MultipleAchievementBean) o;
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
    public void getSubjectList(String teacherId) {
        Observable<ResponseBean<List<ErrorCourseBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getTableSubjectList(teacherId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<ErrorCourseBean>>() {
            @Override
            public void onSuccess(List<ErrorCourseBean> data) {
                mView.getSubjectSuccess(data);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.getSubjectListFail(msg);
            }
        }));
    }

}
