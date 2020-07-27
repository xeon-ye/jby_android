package com.jkrm.education.mvp.presenters;

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
import com.jkrm.education.bean.result.HomeworkDetailResultBean;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.bean.result.RowsStatisticsHomeworkResultBean;
import com.jkrm.education.bean.result.StatisticsScoreAverageResultBean;
import com.jkrm.education.bean.result.StatisticsScorePositionRankResultBean;
import com.jkrm.education.bean.result.StatisticsScorePositionScoreResultBean;
import com.jkrm.education.mvp.views.StatisticsScoreView;

import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;

public class StatisticsScorePresent extends AwCommonPresenter implements StatisticsScoreView.Presenter {

    private StatisticsScoreView.View mView;

    public StatisticsScorePresent(StatisticsScoreView.View view) {
        this.mView = view;
    }

    @Override
    public void getStatisticsScoreHomeworkList(String teacherId, RequestBody body) {
        Observable<AwResponseListBean<AnswerSheetDataResultBean, RowsStatisticsHomeworkResultBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getStatisticsScoreHomeworkList(teacherId, body);

        addIOSubscription(observable, new AwApiListSubscriber(new AwApiListCallback<AnswerSheetDataResultBean, RowsStatisticsHomeworkResultBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(AnswerSheetDataResultBean data, List<RowsStatisticsHomeworkResultBean> list, int total, int size, int pages, int current) {
                mView.getStatisticsScoreHomeworkListSuccess(data, list, total, size, pages, current);
                AwLog.d("getStatisticsScoreHomeworkList success total: " + total + " ,size: " + size + " ,pages: " + pages + " ,current: " + current);
                if(data != null) {
                    AwLog.d("getStatisticsScoreHomeworkList success data: " + data.toString());
                } else {
                    AwLog.d("getStatisticsScoreHomeworkList success data is null");
                }
                if(list != null) {
                    AwLog.d("getStatisticsScoreHomeworkList success list: " + list.size());
                } else {
                    AwLog.d("getStatisticsScoreHomeworkList success list is null");
                }

            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.getStatisticsScoreHomeworkListFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getStatisticsScoreHomeworkList(teacherId, body);
                } else {
                    mView.getStatisticsScoreHomeworkListFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getStatisticsScoreAverage(String homeworkId) {
        Observable<ResponseBean<List<StatisticsScoreAverageResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getStatisticsScoreAverage(homeworkId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<StatisticsScoreAverageResultBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<StatisticsScoreAverageResultBean> model) {
                mView.getStatisticsScoreAverageSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.getStatisticsScoreAverageFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getStatisticsScoreAverage(homeworkId);
                } else {
                    mView.getStatisticsScoreAverageFail(msg);
                }

            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getStatisticsScorePositionRank(String homeworkId, RequestBody body) {
        Observable<ResponseBean<List<StatisticsScorePositionRankResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getStatisticsScorePositionRank(homeworkId, body);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<StatisticsScorePositionRankResultBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<StatisticsScorePositionRankResultBean> model) {
                mView.getStatisticsScorePositionRankSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.getStatisticsScorePositionRankFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getStatisticsScorePositionRank(homeworkId, body);
                } else {
                    mView.getStatisticsScorePositionRankFail(msg);
                }

            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getStatisticsScorePositionScore(String homeworkId, RequestBody body) {
        Observable<ResponseBean<List<StatisticsScorePositionScoreResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getStatisticsScorePositionScore(homeworkId, body);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<StatisticsScorePositionScoreResultBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<StatisticsScorePositionScoreResultBean> model) {
                mView.getStatisticsScorePositionScoreSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.getStatisticsScorePositionScoreFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getStatisticsScorePositionScore(homeworkId, body);
                } else {
                    mView.getStatisticsScorePositionScoreFail(msg);
                }

            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

}
