package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.exam.ColumnDataBean;
import com.jkrm.education.bean.exam.ExamCompreBean;
import com.jkrm.education.bean.exam.LineDataBean;
import com.jkrm.education.bean.exam.OverViewBean;
import com.jkrm.education.mvp.views.StudentAnalysisView;
import com.jkrm.education.mvp.views.ViewStudentAnswerSheetView;

import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/10 15:06
 */

public class ViewStudentAnswerSheetPresent extends AwCommonPresenter
        implements ViewStudentAnswerSheetView.Presenter {


    private ViewStudentAnswerSheetView.View mView;

    public ViewStudentAnswerSheetPresent(ViewStudentAnswerSheetView.View view) {
        this.mView = view;
    }




    @Override
    public void getCourseList(RequestBody requestBody) {
        Observable<ResponseBean<ExamCompreBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getCourseList(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<ExamCompreBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(ExamCompreBean data) {
                mView.getCourseListSuccess(data);
            }


            @Override
            public void onFailure(int code, String msg) {
                mView.getCourseListFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }








  /*  @Override
    public void getClassList(String teacherId) {
        Observable<ResponseBean<List<ClassBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getClassList(teacherId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<ClassBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<ClassBean> data) {
                mView.getClassListSuccess(data);
            }


            @Override
            public void onFailure(int code, String msg) {
                mView.getClassListFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }*/
}
