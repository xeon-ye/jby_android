package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.mvp.views.CommonlyMultipleView;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/1 18:35
 * Description:
 */
public class CommonlyMultiplePresent extends AwCommonPresenter implements CommonlyMultipleView.Presenter {



    private CommonlyMultipleView.View mView;

    public CommonlyMultiplePresent(CommonlyMultipleView.View view) {
        this.mView = view;
    }

    @Override
    public void getMultipleAchievementList(String teacherId, String examId, String paperId) {
        //接口访问逻辑

//        Observable<ResponseBean<List<ReViewTaskBean>>> observable = RetrofitClient.builderRetrofit()
//                .create(APIService.class)
//                .getReviewTaskList(teacherId,examId,paperId);
//        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<ReViewTaskBean>>() {
//            @Override
//            public void onStart() {
//            }
//
//            @Override
//            public void onSuccess(List<ReViewTaskBean> data) {
//                mView.getReviewTaskListSuccess(data);
//
//            }
//
//
//            @Override
//            public void onFailure(int code, String msg) {
//                mView.getReviewTaskListFail(msg);
//            }
//
//            @Override
//            public void onCompleted() {
//            }
//        }));
    }
}
