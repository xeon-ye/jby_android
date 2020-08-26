package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.ClassessBean;
import com.jkrm.education.bean.TaskBean;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.mvp.views.ChoiceClassesView;
import com.jkrm.education.mvp.views.TaskView;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/10 15:06
 */

public class TaskPresent extends AwCommonPresenter implements TaskView.Presenter {


    private TaskView.View mView;

    public TaskPresent(TaskView.View view) {
        this.mView = view;
    }


    @Override
    public void getExamList(String teacherId) {
        Observable<ResponseBean<TaskBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getExamList(teacherId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<TaskBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(TaskBean data) {
                mView.getExamListSuccess(data);
            }



            @Override
            public void onFailure(int code, String msg) {
                mView.getExamListFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }
}
