package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.TemplateInfoResultBean;
import com.jkrm.education.mvp.views.VideoPointView;

import rx.Observable;

public class VideoPointPresent extends AwCommonPresenter implements VideoPointView.Presenter {

    private VideoPointView.View mView;

    public VideoPointPresent(VideoPointView.View view) {
        this.mView = view;
    }

    @Override
    public void templateInfo(String templateId) {
        Observable<ResponseBean<TemplateInfoResultBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .templateInfo(templateId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<TemplateInfoResultBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(TemplateInfoResultBean model) {
                mView.templateInfoSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    templateInfo(templateId);
                } else {
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }
}
