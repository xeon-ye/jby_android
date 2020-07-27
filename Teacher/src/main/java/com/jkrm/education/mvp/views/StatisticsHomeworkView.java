package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.AnswerSheetDataResultBean;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.bean.result.StatisticsErrorQuestionKnowledgeResultBean;
import com.jkrm.education.bean.result.StatisticsErrorQuestionRatioResultBean;
import com.jkrm.education.bean.result.StatisticsHomeworkSubmitRatioResultBean;
import com.jkrm.education.bean.result.StatisticsHomeworkSubmitTableResultBean;
import com.jkrm.education.bean.result.StatisticsHomeworkSubmitTableResultBeanNew;
import com.jkrm.education.bean.result.TeacherClassBean;

import java.util.List;

import okhttp3.RequestBody;

/**
 * Created by hzw on 2018/11/13
 */
public interface StatisticsHomeworkView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getAnswerSheets(String teacher_id, String start_date, String end_date, String class_ids, String course_id, int page_);
        void getStatisticsHomeworkSubmitTable(String teacherId, RequestBody body);
        void getStatisticsHomeworkSubmitRatio(String teacherId, RequestBody body);
        void getStatisticsHomeworkMisstakeRatio(String teacherId, RequestBody body);
        void getStatisticsHomeworkErrorQuestionKnowledge(String teacherId, RequestBody body);

        void getStatisticsHomeworkSubmitTableNew(String teacherId,String classId,int  current,int size);

        void getTeacherClassList(String teacherId);
    }

    interface View extends AwBaseView {
        void getAnswerSheetsSuccess(AnswerSheetDataResultBean data, List<RowsHomeworkBean> list, int total, int size, int pages, int current);
        void getAnswerSheetsFail(String msg);

        void getStatisticsHomeworkSubmitTableSuccess(List<StatisticsHomeworkSubmitTableResultBean> list);
        void getStatisticsHomeworkSubmitTableFail(String msg);

        void getStatisticesHomeworkSubmitRatioSuccess(List<StatisticsHomeworkSubmitRatioResultBean> list);
        void getStatisticsHomeworkMisstakeRatioSuccess(List<StatisticsErrorQuestionRatioResultBean> list);
        void getStatisticsHomeworkErrorQuestionKnowledgeSuccess(List<StatisticsErrorQuestionKnowledgeResultBean> list);
        void getStatisticsHomeworkErrorQuestionKnowledgeFail(String msg);

        void getStatisticsHomeworkSubmitTableNewSuccess(StatisticsHomeworkSubmitTableResultBeanNew statisticsHomeworkSubmitTableResultBeanNew);
        void getStatisticsHomeworkSubmitTableNewFail(String msg);

        void getTeacherClassListSuccess(List<TeacherClassBean> list);
        void getTeacherClassListFail(String msg);
    }
}