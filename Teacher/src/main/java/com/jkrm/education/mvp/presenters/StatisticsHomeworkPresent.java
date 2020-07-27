package com.jkrm.education.mvp.presenters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hzw.baselib.bean.AwResponseListBean;
import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiListCallback;
import com.hzw.baselib.interfaces.AwApiListSubscriber;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.AnswerSheetDataResultBean;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.bean.result.StatisticsErrorQuestionKnowledgeResultBean;
import com.jkrm.education.bean.result.StatisticsErrorQuestionRatioResultBean;
import com.jkrm.education.bean.result.StatisticsHomeworkSubmitRatioResultBean;
import com.jkrm.education.bean.result.StatisticsHomeworkSubmitTableResultBean;
import com.jkrm.education.bean.result.StatisticsHomeworkSubmitTableResultBeanNew;
import com.jkrm.education.bean.result.TeacherClassBean;
import com.jkrm.education.mvp.views.StatisticsHomeworkView;

import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

public class StatisticsHomeworkPresent extends AwCommonPresenter implements StatisticsHomeworkView.Presenter {

    private StatisticsHomeworkView.View mView;

    public StatisticsHomeworkPresent(StatisticsHomeworkView.View view) {
        this.mView = view;
    }

    @Override
    public void getAnswerSheets(String teacher_id, String start_date, String end_date, String class_ids, String course_id, int page_) {
        Observable<AwResponseListBean<AnswerSheetDataResultBean, RowsHomeworkBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .answer_sheets(teacher_id, start_date, end_date, class_ids, course_id, page_, APIService.COMMON_PAGE_SIZE);

        addIOSubscription(observable, new AwApiListSubscriber(new AwApiListCallback<AnswerSheetDataResultBean, RowsHomeworkBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(AnswerSheetDataResultBean data, List<RowsHomeworkBean> list, int total, int size, int pages, int current) {
                mView.getAnswerSheetsSuccess(data, list, total, size, pages, current);
                AwLog.d("getAnswerSheets success total: " + total + " ,size: " + size + " ,pages: " + pages + " ,current: " + current);
                if(data != null) {
                    AwLog.d("getAnswerSheets success data: " + data.toString());
                } else {
                    AwLog.d("getAnswerSheets success data is null");
                }
                if(list != null) {
                    AwLog.d("getAnswerSheets success list: " + list.size());
                } else {
                    AwLog.d("getAnswerSheets success list is null");
                }

            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getAnswerSheets(teacher_id, start_date, end_date, class_ids, course_id, page_);
                } else {
                    mView.getAnswerSheetsFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getStatisticsHomeworkSubmitTable(String teacherId, RequestBody body) {
        Observable<ResponseBean<List<StatisticsHomeworkSubmitTableResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getStatisticsHomeworkSubmitTable(teacherId, body);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<StatisticsHomeworkSubmitTableResultBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<StatisticsHomeworkSubmitTableResultBean> model) {
                mView.getStatisticsHomeworkSubmitTableSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.getStatisticsHomeworkSubmitTableFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getStatisticsHomeworkSubmitTable(teacherId, body);
                } else {
                    mView.getStatisticsHomeworkSubmitTableFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getStatisticsHomeworkSubmitRatio(String teacherId, RequestBody body) {
        Observable<ResponseBean<List<StatisticsHomeworkSubmitRatioResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getStatisticsHomeworkSubmitRatio(teacherId, body);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<StatisticsHomeworkSubmitRatioResultBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<StatisticsHomeworkSubmitRatioResultBean> model) {
                mView.getStatisticesHomeworkSubmitRatioSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getStatisticsHomeworkSubmitRatio(teacherId, body);
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
    public void getStatisticsHomeworkMisstakeRatio(String teacherId, RequestBody body) {
        Observable<ResponseBean<List<StatisticsErrorQuestionRatioResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getStatisticsHomeworkMisstakeRatio(teacherId, body);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<StatisticsErrorQuestionRatioResultBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<StatisticsErrorQuestionRatioResultBean> model) {
                mView.getStatisticsHomeworkMisstakeRatioSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getStatisticsHomeworkMisstakeRatio(teacherId, body);
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
    public void getStatisticsHomeworkErrorQuestionKnowledge(String teacherId, RequestBody body) {
        Observable<ResponseBean<List<StatisticsErrorQuestionKnowledgeResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getStatisticsHomeworkErrorQuestionKnowledge(teacherId, body);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<StatisticsErrorQuestionKnowledgeResultBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<StatisticsErrorQuestionKnowledgeResultBean> model) {
                mView.getStatisticsHomeworkErrorQuestionKnowledgeSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.getStatisticsHomeworkErrorQuestionKnowledgeFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getStatisticsHomeworkErrorQuestionKnowledge(teacherId, body);
                } else {
                    mView.getStatisticsHomeworkErrorQuestionKnowledgeFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getStatisticsHomeworkSubmitTableNew(String teacherId, String classId, int current, int size) {
        Observable<StatisticsHomeworkSubmitTableResultBeanNew> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getStatisticsHomeworkSubmitTableNew(teacherId, classId,current,size);
        addIOSubscription(observable, new Subscriber() {

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoadingDialog();
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                mView.dismissLoadingDialog();
                mView.getStatisticsHomeworkSubmitTableNewFail(e.getMessage());
            }

            @Override
            public void onNext(Object o) {
                //格式不一样
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setPrettyPrinting();
                Gson gson = gsonBuilder.create();
                String s = gson.toJson(o);
                StatisticsHomeworkSubmitTableResultBeanNew data = new Gson().fromJson(s, StatisticsHomeworkSubmitTableResultBeanNew.class);
                mView.getStatisticsHomeworkSubmitTableNewSuccess(data);
            }
        });
    }

    @Override
    public void getTeacherClassList(String teacherId) {
        Observable<ResponseBean<List<TeacherClassBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getTeacherClassList(teacherId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<TeacherClassBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<TeacherClassBean> model) {
                mView.getTeacherClassListSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.getTeacherClassListFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getTeacherClassList(teacherId);
                } else {
                    mView.getTeacherClassListFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

}
