package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.ReViewTaskBean;
import com.jkrm.education.bean.exam.ExamCourseBean;
import com.jkrm.education.bean.exam.MultipleAchievementBean;

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
        void getMultipleAchievementList(RequestBody requestBody);//参数暂时不知道
////        小题得分表
//        void getQuestionScoreList(String teacherId,String examId,String paperId);//参数暂时不知道
////        班级成绩对比
//        void getClassAchievementList(String teacherId,String examId,String paperId);//参数暂时不知道
////        成绩分段表
//        void getAchievementSectionList(String teacherId,String examId,String paperId);//参数暂时不知道

        void  getExamCourse(RequestBody requestBody);
    }

    interface View extends AwBaseView {
        void getMultipleAchievementSuccess(MultipleAchievementBean data);
        void getMultipleAchievementListFail(String msg);

        void getExamCourseSuccess(List<ExamCourseBean> data);
        void getExamCourseFail(String msg);
    }



}
