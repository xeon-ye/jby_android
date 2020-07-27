package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.SaveReinforceBean;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.PracticeDataResultBean;
import com.jkrm.education.mvp.views.OnlineSubmitQuestionFragmentView;
import com.jkrm.education.mvp.views.QuestionBasketFragmentView;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * @Description:  提交作答
 * @Author: xiangqian
 * @CreateDate: 2020/5/25 15:46
 */

public class OnlineSubmitQuestionPresent extends AwCommonPresenter implements OnlineSubmitQuestionFragmentView.Presenter {

    private OnlineSubmitQuestionFragmentView.View mView;

    public OnlineSubmitQuestionPresent(OnlineSubmitQuestionFragmentView.View view) {
        this.mView = view;
    }

    @Override
    public void saveReinforce(RequestBody requestBody) {
        Observable<ResponseBean<SaveReinforceBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .saveReinforce(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<SaveReinforceBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(SaveReinforceBean model) {
                mView.saveReinforceSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.saveReinforceFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    saveReinforce(requestBody);
                } else {
                    mView.saveReinforceFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }
}
