package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.AnswerSheetDataDetailResultBean;
import com.jkrm.education.bean.result.LoginResult;
import com.jkrm.education.bean.result.MaxScoreResultBean;
import com.jkrm.education.bean.result.ReportDurationsResultBean;
import com.jkrm.education.bean.result.ReportQuestionScoreRateResultBean;
import com.jkrm.education.bean.result.ReportQuestionScoreResultBean;
import com.jkrm.education.bean.result.VideoPointResultBean;
import com.jkrm.education.bean.result.VideoResultBean;

import java.util.List;

import okhttp3.RequestBody;


/**
 * Created by hzw on 2018/11/13
 */
public interface HomeworkDetailView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void answerSheetsQuestion(String answer_sheet_id);
        void getClassScoreMaxScore(String answer_sheet_id);
        void getVideos(String params);
        void getVideoPointList(String homework_id);
        void getVideoPointListNew(String homework_id);
        void addQuestionBasket(RequestBody body);
        void delSomeOneQuestionBasket(String questionId, String studentId);
    }

    interface View extends AwBaseView {
        void answerSheetsQuestionSuccess(List<AnswerSheetDataDetailResultBean> result);
        void answerSheetsQuestionFail(String msg);

        void getClassScoreMaxScoreSuccess(MaxScoreResultBean result);
        void getClassScoreMaxScoreFail(String msg);

        void getVideosSuccess(VideoResultBean result);
        void getVideoFail(String msg);

        void getVideoPointListSuccess(List<VideoPointResultBean> list);
        void getVideoPointListFail(String msg);

        void getVideoPointListNewSuccess(List<VideoPointResultBean> list);
        void getVideoPointListNewFail(String msg);

        void addQuestionBasketSuccess(String msg);
        void delSomeOneQuestionBasketSuccess(String msg);
    }

}