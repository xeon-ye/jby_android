package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.OssResultBean;
import com.jkrm.education.mvp.views.MeInfoView;
import com.jkrm.education.mvp.views.OnlineNonGroupSubjectiveQuestionsFragmentView;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/5/25 10:50
 */

public class OnlineNonGroupSubjectiveQuestionsFragmentPresent extends AwCommonPresenter implements OnlineNonGroupSubjectiveQuestionsFragmentView.Presenter {

    private OnlineNonGroupSubjectiveQuestionsFragmentView.View mView;

    public OnlineNonGroupSubjectiveQuestionsFragmentPresent(OnlineNonGroupSubjectiveQuestionsFragmentView.View view) {
        this.mView = view;
    }
    @Override
    public void uploadOss(String model, String filePath) {
        File file = new File((filePath));
        RequestBody modelBody =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), model);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image[]", file.getName(), requestBody);
        Observable<ResponseBean<OssResultBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .uploadOss(modelBody, filePart);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<OssResultBean>() {
            @Override
            public void onStart() {
                AwLog.d("uploadOss onStart");
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(OssResultBean model) {
                AwLog.d("uploadOss onSuccess");
                mView.uploadOssSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.uploadOssFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    uploadOss(model, filePath);
                } else {
                    mView.uploadOssFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                AwLog.d("uploadOss onCompleted");
                mView.dismissLoadingDialog();
            }
        }));
    }
}
