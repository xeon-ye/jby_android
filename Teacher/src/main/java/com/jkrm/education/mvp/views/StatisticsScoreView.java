package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.AnswerSheetDataResultBean;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.bean.result.RowsStatisticsHomeworkResultBean;
import com.jkrm.education.bean.result.StatisticsScoreAverageResultBean;
import com.jkrm.education.bean.result.StatisticsScorePositionRankResultBean;
import com.jkrm.education.bean.result.StatisticsScorePositionScoreResultBean;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Path;

/**
 * Created by hzw on 2018/11/13
 */
public interface StatisticsScoreView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getStatisticsScoreHomeworkList(String teacherId, RequestBody body);
        void getStatisticsScoreAverage(String homeworkId);
        void getStatisticsScorePositionRank(String homeworkId, RequestBody body);
        void getStatisticsScorePositionScore(String homeworkId, RequestBody body);
    }

    interface View extends AwBaseView {
        void getStatisticsScoreHomeworkListSuccess(AnswerSheetDataResultBean data, List<RowsStatisticsHomeworkResultBean> list, int total, int size, int pages, int current);
        void getStatisticsScoreHomeworkListFail(String msg);

        void getStatisticsScoreAverageSuccess(List<StatisticsScoreAverageResultBean> list);
        void getStatisticsScoreAverageFail(String msg);

        void getStatisticsScorePositionRankSuccess(List<StatisticsScorePositionRankResultBean> list);
        void getStatisticsScorePositionRankFail(String msg);

        void getStatisticsScorePositionScoreSuccess(List<StatisticsScorePositionScoreResultBean> list);
        void getStatisticsScorePositionScoreFail(String msg);
    }
}