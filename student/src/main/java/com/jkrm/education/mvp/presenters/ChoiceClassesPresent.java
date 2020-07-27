package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.ClassessBean;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.PracticeTableResult;
import com.jkrm.education.mvp.views.ChoiceClassesView;
import com.jkrm.education.mvp.views.ChoiceSchoolView;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/10 15:06
 */

public class ChoiceClassesPresent extends AwCommonPresenter implements ChoiceClassesView.Presenter {


    private ChoiceClassesView.View mView;

    public ChoiceClassesPresent(ChoiceClassesView.View view) {
        this.mView = view;
    }

    @Override
    public void getClasses(String schoolId,String userId) {
        Observable<ResponseBean<Map<String, List<ClassessBean.Bean>>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getClasses(schoolId,userId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<Map<String, List<ClassessBean.Bean>>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(Map<String, List<ClassessBean.Bean>> data) {
                mView.getClassesSuccess(data);
            }



            @Override
            public void onFailure(int code, String msg) {
                mView.getClasssesFail(msg);
            }

            @Override
            public void onCompleted() {
            }
        }));
    }

    @Override
    public void bindClasses(RequestBody requestBody) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .bindSchoolClass(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(String data) {
                mView.bindClassesSuccess(data);
            }


            @Override
            public void onFailure(int code, String msg) {
                mView.bindClassesFail(msg);
            }

            @Override
            public void onCompleted() {
            }
        }));
    }
}
