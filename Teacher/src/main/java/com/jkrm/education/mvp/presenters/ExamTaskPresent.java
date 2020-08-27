package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.ClassessBean;
import com.jkrm.education.bean.ReViewTaskBean;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.mvp.views.ChoiceClassesView;
import com.jkrm.education.mvp.views.ExamTaskView;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/10 15:06
 */

public class ExamTaskPresent extends AwCommonPresenter implements ExamTaskView.Presenter {


    private ExamTaskView.View mView;

    public ExamTaskPresent(ExamTaskView.View view) {
        this.mView = view;
    }


    @Override
    public void getReviewTaskList(String teacherId, String examId, String paperId) {
        Observable<ResponseBean<List<ReViewTaskBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getReviewTaskList(teacherId,examId,paperId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<ReViewTaskBean>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(List<ReViewTaskBean> data) {
                mView.getReviewTaskListSuccess(data);

            }


            @Override
            public void onFailure(int code, String msg) {
                mView.getReviewTaskListFail(msg);
            }

            @Override
            public void onCompleted() {
            }
        }));
    }
}
