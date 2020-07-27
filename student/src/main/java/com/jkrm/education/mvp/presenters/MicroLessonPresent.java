package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.CourseAttrBean;
import com.jkrm.education.bean.result.CourseTypeBean;
import com.jkrm.education.bean.result.MicroLessonResultBean;
import com.jkrm.education.bean.result.TeachersResultBean;
import com.jkrm.education.mvp.views.MicroLessonFragmentView;

import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/5 10:14
 */

public class MicroLessonPresent extends AwCommonPresenter implements MicroLessonFragmentView.Presenter {
    private MicroLessonFragmentView.View mView;

    public MicroLessonPresent(MicroLessonFragmentView.View mView) {
        this.mView = mView;
    }

    @Override
    public void getCourseType(RequestBody requestBody) {
        Observable<ResponseBean<List<CourseTypeBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getCourseType(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<CourseTypeBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<CourseTypeBean> model) {
                mView.getCourseTypeSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if (AwDataUtil.isEmpty(msg)) {
                    mView.getCourseTypeFail(msg);
                    return;
                }
                if (msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getCourseType(requestBody);
                } else {
                    mView.getCourseTypeFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getCourseAttr() {
        Observable<ResponseBean<CourseAttrBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getCourseAttr();
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<CourseAttrBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(CourseAttrBean data) {
                mView.getCourseAttrSuccess(data);
            }

            @Override
            public void onFailure(int code, String msg) {
                if (AwDataUtil.isEmpty(msg)) {
                    mView.getCourseAttrFail(msg);
                    return;
                }
                if (msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getCourseAttr();
                } else {
                    mView.getCourseAttrFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getCourseList(RequestBody requestBody) {
        Observable<ResponseBean<List<MicroLessonResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getCourseList(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<MicroLessonResultBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<MicroLessonResultBean> data) {
                mView.getCourseListSuccess(data);
            }


            @Override
            public void onFailure(int code, String msg) {
                if (AwDataUtil.isEmpty(msg)) {
                    mView.getCourseListFail(msg);
                    return;
                }
                if (msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getCourseAttr();
                } else {
                    mView.getCourseListFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }
}
