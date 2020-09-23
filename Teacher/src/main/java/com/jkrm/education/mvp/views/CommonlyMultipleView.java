package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.ReViewTaskBean;
import com.jkrm.education.bean.exam.ClassAchievementBean;
import com.jkrm.education.bean.exam.ExamCourseBean;
import com.jkrm.education.bean.exam.MultipleAchievementBean;
import com.jkrm.education.bean.exam.ScoreAchievementBean;
import com.jkrm.education.bean.exam.SectionAchievementBean;

import java.util.List;

import okhttp3.RequestBody;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/1 18:36
 * Description:
 */
public interface CommonlyMultipleView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        //        综合成绩表
        void getMultipleAchievementList(RequestBody requestBody);

        //        小题得分表
        void getQuestionScoreList(RequestBody requestBody);

        //        班级成绩对比
        void getClassAchievementList(RequestBody requestBody);

        //        成绩分段表
        void getAchievementSectionList(RequestBody requestBody);

        void getExamCourse(RequestBody requestBody);
    }

    interface View extends AwBaseView {
        void getMultipleAchievementSuccess(MultipleAchievementBean data);

        void getMultipleAchievementListFail(String msg);

        void getQuestionScoreListSuccess(ScoreAchievementBean data);

        void getQuestionScoreListFail(String msg);

        void getClassAchievementListSuccess(ClassAchievementBean data);

        void getClassAchievementListFail(String msg);

        void getAchievementSectionListSuccess(SectionAchievementBean data);

        void getAchievementSectionListFail(String msg);

        void getExamCourseSuccess(List<ExamCourseBean> data);

        void getExamCourseFail(String msg);
    }


}
