package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.exam.MultipleAchievementBean;
import com.jkrm.education.mvp.views.CommonlyMultipleView;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

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
    public void getMultipleAchievementList(RequestBody requestBody) {
        //接口访问逻辑
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
                        mView.getMultipleAchievementSuccess(data);
                    else
                        mView.getMultipleAchievementListFail(data.getMsg());
                } else
                    mView.getMultipleAchievementListFail("数据异常！！");
            }
        });
    }
}
