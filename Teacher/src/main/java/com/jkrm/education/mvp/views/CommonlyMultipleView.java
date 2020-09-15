package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.ReViewTaskBean;

import java.util.List;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/1 18:36
 * Description:
 */
public interface CommonlyMultipleView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
//        综合成绩表
        void getMultipleAchievementList(String teacherId, String examId, String paperId);//参数暂时不知道
////        小题得分表
//        void getQuestionScoreList(String teacherId,String examId,String paperId);//参数暂时不知道
////        班级成绩对比
//        void getClassAchievementList(String teacherId,String examId,String paperId);//参数暂时不知道
////        成绩分段表
//        void getAchievementSectionList(String teacherId,String examId,String paperId);//参数暂时不知道

    }

    interface View extends AwBaseView {
        void getMultipleAchievementSuccess(List<ReViewTaskBean> data);
        void getMultipleAchievementListFail(String msg);
    }



}
