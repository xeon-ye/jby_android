package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.AnswerSheetDataResultBean;
import com.jkrm.education.bean.result.RowsStatisticsHomeworkResultBean;
import com.jkrm.education.bean.result.StatisticsScoreAverageResultBean;
import com.jkrm.education.bean.result.StatisticsScorePositionRankResultBean;
import com.jkrm.education.bean.result.StatisticsScorePositionScoreResultBean;

import java.util.List;

import okhttp3.RequestBody;

/**
 * Created by hzw on 2018/11/13
 */
public interface HomeworkChoiceView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getStatisticsScoreHomeworkList(String teacherId, RequestBody body);
    }

    interface View extends AwBaseView {
        void getStatisticsScoreHomeworkListSuccess(AnswerSheetDataResultBean data, List<RowsStatisticsHomeworkResultBean> list, int total, int size, int pages, int current);
        void getStatisticsScoreHomeworkListFail(String msg);
    }
}