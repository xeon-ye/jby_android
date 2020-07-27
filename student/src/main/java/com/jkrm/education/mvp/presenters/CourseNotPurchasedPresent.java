package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.CourseAttrBean;
import com.jkrm.education.bean.result.CoursePlayResultBean;
import com.jkrm.education.mvp.views.CourseNotPurchasedView;

import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/5 16:40
 */

public class CourseNotPurchasedPresent extends AwCommonPresenter implements CourseNotPurchasedView.Presenter {
    private CourseNotPurchasedView.View mView;

    public CourseNotPurchasedPresent(CourseNotPurchasedView.View view) {
        mView = view;
    }

    @Override
    public void getCoursePlayList(RequestBody requestBody) {
        Observable<ResponseBean<List<CoursePlayResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getCoursePlayList(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<CoursePlayResultBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<CoursePlayResultBean> data) {
                mView.getCoursePlayListSuccess(data);
            }


            @Override
            public void onFailure(int code, String msg) {
                if (AwDataUtil.isEmpty(msg)) {
                    mView.getCoursePlayListFail(msg);
                    return;
                }
                if (msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                     getCoursePlayList(requestBody);
                } else {
                    mView.getCoursePlayListFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }
}
