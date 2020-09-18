package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.ClassessBean;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.exam.ExamQuestionsBean;
import com.jkrm.education.bean.exam.ExamReadHeaderBean;
import com.jkrm.education.bean.result.OssResultBean;
import com.jkrm.education.mvp.views.ChoiceClassesView;
import com.jkrm.education.mvp.views.CorrectionView;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/10 15:06
 */

public class CorrectingPresent extends AwCommonPresenter implements CorrectionView.Presenter {


    private CorrectionView.View mView;

    public CorrectingPresent(CorrectionView.View view) {
        this.mView = view;
    }

    @Override
    public void getExamReadHeader(String teacherId, String examId, String paperId, String readWay) {
        Observable<ResponseBean<List<ExamReadHeaderBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getExamReadHeader(teacherId,examId,paperId,readWay);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<ExamReadHeaderBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<ExamReadHeaderBean> data) {
                mView.getExamReadHeaderSuccess(data);
            }


            @Override
            public void onFailure(int code, String msg) {
                mView.getExamReadHeaderFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getExamQuestions(String teacherId, String examId, String paperId, String readWay, String questionId) {
        Observable<ResponseBean<List<ExamQuestionsBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getExamQuestions(teacherId,examId,paperId,readWay,questionId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<ExamQuestionsBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<ExamQuestionsBean> data) {
                mView.getExamQuestionsSuccess(data);
            }



            @Override
            public void onFailure(int code, String msg) {
                mView.getExamQuestionsFail(msg);
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

    @Override
    public void examMark(boolean isNext, RequestBody requestBody) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .examMark(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String model) {
                mView.examMarkSuccess(isNext);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.examMarkFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    examMark(isNext, requestBody);
                } else {
                    mView.examMarkFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getExamReviewHeader(String teacherId, String examId, String paperId, String readWay) {
        Observable<ResponseBean<List<ExamReadHeaderBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getExamReviewHeader(teacherId,examId,paperId,readWay);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<ExamReadHeaderBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<ExamReadHeaderBean> data) {
                mView.getExamReadHeaderSuccess(data);
            }


            @Override
            public void onFailure(int code, String msg) {
                mView.getExamReadHeaderFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getExamReviewQuestions(String teacherId, String examId, String paperId, String readWay, String questionId) {
        Observable<ResponseBean<List<ExamQuestionsBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getExamReviewQuestions(teacherId,examId,paperId,readWay,questionId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<ExamQuestionsBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<ExamQuestionsBean> data) {
                mView.getExamReviewQuestionsSuccess(data);
            }



            @Override
            public void onFailure(int code, String msg) {
                mView.getExamReviewQuestionsFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }


}
