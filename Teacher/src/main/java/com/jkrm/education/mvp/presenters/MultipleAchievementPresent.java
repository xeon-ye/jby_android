package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.exam.MultipleAchievementBean;
import com.jkrm.education.bean.exam.TableClassBean;
import com.jkrm.education.mvp.views.MultipleAchievementView;

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
//        Observable<MultipleAchievementBean> observable = RetrofitClient.builderRetrofit()
//                .create(APIService.class)
//                .getMultipleTable(requestBody);
//        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<MultipleAchievementBean>() {
//            @Override
//            public void onSuccess(MultipleAchievementBean data) {
//                mView.getTableListSuccess(data);
//            }
//
//            @Override
//            public void onFailure(int code, String msg) {
//                mView.getTableListFail(msg);
//            }
//
////            @Override
////            public void onStart() {
////                mView.showLoadingDialog();
////            }
////
////            @Override
////            public void onCompleted() {
////                mView.dismissLoadingDialog();
////            }
//        }));

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
    public void getSubjectList() {
//        Observable<ResponseBean<List<ReViewTaskBean>>> observable = RetrofitClient.builderRetrofit()
//                .create(APIService.class)
//                .getTableClassList(teacherId);
//        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<PeriodCourseBean>() {
//            @Override
//            public void onSuccess(PeriodCourseBean data) {
//                mView.getClassListSuccess(data);
//            }
//
//            @Override
//            public void onFailure(int code, String msg) {
//                mView.getClassListFail(msg);
//            }
//        }));
    }

    @Override
    public void getClassList(String teacherId) {
//        Observable<ResponseBean<TableClassBean>> observable = RetrofitClient.builderRetrofit()
//                .create(APIService.class)
//                .getTableClassList(teacherId);
//        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<TableClassBean>() {
//            @Override
//            public void onSuccess(TableClassBean data) {
//                mView.getClassListSuccess(data);
//            }
//
//            @Override
//            public void onFailure(int code, String msg) {
//                mView.getClassListFail(msg);
//            }
//        }));

    }
}
