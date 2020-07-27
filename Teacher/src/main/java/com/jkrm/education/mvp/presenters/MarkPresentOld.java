package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.AnswerSheetProgressResultBean;
import com.jkrm.education.bean.result.HomeworkDetailResultBean;
import com.jkrm.education.bean.result.OssResultBean;
import com.jkrm.education.bean.result.StudentSingleQuestionAnswerResultBean;
import com.jkrm.education.mvp.views.MarkView;
import com.jkrm.education.mvp.views.MarkViewOld;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

public class MarkPresentOld extends AwCommonPresenter implements MarkViewOld.Presenter {

    private MarkViewOld.View mView;

    public MarkPresentOld(MarkViewOld.View view) {
        this.mView = view;
    }
    @Override
    public void getHomeworkDetail(String homeworkId, String classid) {
        Observable<ResponseBean<HomeworkDetailResultBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .homeworkDetail(homeworkId, classid);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<HomeworkDetailResultBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(HomeworkDetailResultBean model) {
                mView.getHomeworkDetailSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getHomeworkDetail(homeworkId, classid);
                } else {
                    mView.getHomeworkDetailFail(msg);
                }

            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void answerSheetProgress(String homeworkId, String classId) {
        Observable<ResponseBean<List<AnswerSheetProgressResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .answerSheetProgress(homeworkId, classId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<AnswerSheetProgressResultBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<AnswerSheetProgressResultBean> model) {
                mView.answerSheetProgressSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.answerSheetProgressFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    answerSheetProgress(homeworkId, classId);
                } else {
                    mView.answerSheetProgressFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getSingleStudentQuestionAnswer(String homeworkId, String questionId, String studentId) {
        Observable<ResponseBean<StudentSingleQuestionAnswerResultBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getSingleStudentQuestionAnswer(homeworkId, questionId, studentId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<StudentSingleQuestionAnswerResultBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(StudentSingleQuestionAnswerResultBean model) {
                mView.getSingleStudentQuestionAnswerSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.getSingleStudentQuestionAnswerFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getSingleStudentQuestionAnswer(homeworkId, questionId, studentId);
                } else {
                    mView.getSingleStudentQuestionAnswerFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void uploadOss(boolean isNext, String model, String filePath) {
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
                mView.uploadOssSuccess(isNext, model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.uploadOssFail(isNext, msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    uploadOss(isNext, model, filePath);
                } else {
                    mView.uploadOssFail(isNext, msg);
                }
            }

            @Override
            public void onCompleted() {
                AwLog.d("uploadOss onCompleted");
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void addSpecial(RequestBody body) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .addSpecial(body);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
                AwLog.d("addSpecial onStart");
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String model) {
                AwLog.d("addSpecial onSuccess");
                mView.addSpecialSuccess();
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.showMsg("添加典型失败");
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    addSpecial(body);
                } else {
                    mView.showMsg(msg);
                }
            }

            @Override
            public void onCompleted() {
                AwLog.d("addSpecial onCompleted");
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void deleteSpecial(String homeworkId, String questionId, String studCode) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .deleteSpecial(homeworkId, questionId, studCode);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String model) {
                mView.deleteSpecialSuccess();
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.showMsg(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    deleteSpecial(homeworkId, questionId, studCode);
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

    @Override
    public void markQuestion(boolean isNext, String questionAnswerId, RequestBody requestBody) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .markQuestion(questionAnswerId, requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String model) {
                mView.markQuestionSuccess(isNext);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.markQuestionFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    markQuestion(isNext, questionAnswerId, requestBody);
                } else {
                    mView.markQuestionFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

}
