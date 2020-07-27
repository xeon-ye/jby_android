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
import com.jkrm.education.bean.result.StatusErrorQuestionResultBean;
import com.jkrm.education.bean.result.StatusHomeworkScanResultBean;
import com.jkrm.education.mvp.views.TestView;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

public class TestPresent extends AwCommonPresenter implements TestView.Presenter {

    private TestView.View mView;

    public TestPresent(TestView.View view) {
        this.mView = view;
    }

    @Override
    public void uploadOss(String model, String filePath) {
        File file = new File((filePath));
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image[]", file.getName(), requestBody);
        Observable<ResponseBean<OssResultBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .uploadOss(RequestBody.create(
                        MediaType.parse("multipart/form-data"), model), filePart);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<OssResultBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(OssResultBean model) {
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.showMsg(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void addSpecial(RequestBody body) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .addSpecial(body);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<OssResultBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(OssResultBean model) {

            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    addSpecial(body);
                } else {
//                    mView.getAnswerSheetsFail(msg);
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
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    answerSheetProgress(homeworkId, classId);
                } else {
                    //                    mView.getAnswerSheetsFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void answerSheetProgressWithStudent(String homeworkId, String studentId) {
        Observable<ResponseBean<List<AnswerSheetProgressResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .answerSheetProgressWithStudent(homeworkId, studentId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<AnswerSheetProgressResultBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<AnswerSheetProgressResultBean> model) {
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    answerSheetProgressWithStudent(homeworkId, studentId);
                } else {
                    //                    mView.getAnswerSheetsFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getQustionAnswerWithSingleStudent(String homework_id, String student_id) {
        Observable<ResponseBean<List<HomeworkDetailResultBean.GradQusetionBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getQustionAnswerWithSingleStudent(homework_id, student_id);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<HomeworkDetailResultBean.GradQusetionBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<HomeworkDetailResultBean.GradQusetionBean> model) {
                if(model != null) {
                    AwLog.d("getAllQuestionListByPerson list size: " + model.size());
                } else {
                    AwLog.d("getAllQuestionListByPerson list is null");
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getQustionAnswerWithSingleStudent(homework_id,student_id);
                } else {
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getStatusErrorQuestionInSomeDay(String teacherId) {
        Observable<ResponseBean<List<StatusErrorQuestionResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getStatusErrorQuestionInSomeDay(teacherId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<StatusErrorQuestionResultBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<StatusErrorQuestionResultBean> model) {

            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getStatusErrorQuestionInSomeDay(teacherId);
                } else {
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getStatusMarkBeforeDawn(String teacherId) {
        Observable<ResponseBean<StatusHomeworkScanResultBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getStatusMarkBeforeDawn(teacherId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<StatusHomeworkScanResultBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(StatusHomeworkScanResultBean model) {

            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getStatusMarkBeforeDawn(teacherId);
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
