package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.mvp.views.OnlineAnswerChoiceView;

import java.util.List;

import rx.Observable;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/4/22 16:49
 */

public class OnlineAnswerChoicePresent extends AwCommonPresenter implements OnlineAnswerChoiceView.Presenter {

    private OnlineAnswerChoiceView.View mView;

    public OnlineAnswerChoicePresent(OnlineAnswerChoiceView.View view) {
        this.mView = view;
    }

    @Override
    public void getSimilar(String params) {
        Observable<ResponseBean<List<SimilarResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getSimilar(params);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<SimilarResultBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<SimilarResultBean> model) {
                mView.getSimilarSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getSimilar(params);
                } else {
                    mView.getSimilarFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }
}
