package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.OriginalPagerResultBean;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.mvp.views.SeeOriginalPagerView;

import rx.Observable;


public class SeeOriginalPagerPresent extends AwCommonPresenter implements SeeOriginalPagerView.Presenter {

    private SeeOriginalPagerView.View mView;

    public SeeOriginalPagerPresent(SeeOriginalPagerView.View view) {
        this.mView = view;
    }



    @Override
    public void getStudentOriginalQuestionAnswer(String homework_id, String student_id) {
        Observable<ResponseBean<OriginalPagerResultBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getStudentOriginalQuestionAnswer(homework_id, student_id);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<OriginalPagerResultBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(OriginalPagerResultBean model) {
                mView.getStudentOriginalQuestionAnswerSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.showMsg("获取原卷失败");
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getStudentOriginalQuestionAnswer(homework_id,student_id);
                } else {
                    mView.showMsg(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }
}
