package com.jkrm.education.bean.result;

import com.hzw.baselib.util.MyDateUtil;
import com.jkrm.education.bean.CourseBean;

import java.util.List;

/**
 * Created by hzw on 2019/7/17.
 */

public class PracticeDataResultBean {


    private List<QuestTypeListBean> questTypeList;
    private List<CourseBean> coursesList;
    private List<PracticeBean> list;
    private List<ParcticeQuestBean> quest;

    public List<QuestTypeListBean> getQuestTypeList() {
        return questTypeList;
    }

    public void setQuestTypeList(List<QuestTypeListBean> questTypeList) {
        this.questTypeList = questTypeList;
    }

    public List<CourseBean> getCoursesList() {
        return coursesList;
    }

    public void setCoursesList(List<CourseBean> coursesList) {
        this.coursesList = coursesList;
    }

    public List<PracticeBean> getList() {
        return list;
    }

    public void setList(List<PracticeBean> list) {
        this.list = list;
    }

    public List<ParcticeQuestBean> getQuest() {
        return quest;
    }

    public void setQuest(List<ParcticeQuestBean> quest) {
        this.quest = quest;
    }

    public static class QuestTypeListBean {
        /**
         * id : 62f4199b229e7285430fdd2d66bd5b40
         * isOption :
         * name : 选择题
         */

        private String id;
        private String isOption;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIsOption() {
            return isOption;
        }

        public void setIsOption(String isOption) {
            this.isOption = isOption;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }



    public static class PracticeBean {
        /**
         * answer : C
         * classId : a1b601033d13ee4e22010e8507d9e76f
         * correctAnswer :
         * courseId : 09f3d51414a9b14916908c336aa6624e
         * courseName : 数学
         * createTime : 2019-07-12 16:26:47
         * homeworkId : 0301201902030303041a1b601033d13ee4e22010e8507d9e76f
         * id : 4
         * isNoSmallClass : 0
         * isNoVideo : 0
         * nums :
         * questionId : prodquestion006
         * questionNum : 6
         * questionTypeId : 62f4199b229e7285430fdd2d66bd5b40
         * questionTypeName : 选择题
         * questionVideo :
         * questionVideoId :
         * scanImage :
         * studCode : 90729
         * studentId : 6fe7c8a76d3fc00cfabe5e819c49b55a
         */

        private String answer;
        private String classId;
        private String correctAnswer;
        private String courseId;
        private String courseName;
        private String createTime;
        private String homeworkId;
        private String id;
        private String isNoSmallClass;
        private String isNoVideo;
        private String nums;
        private String questionId;
        private String questionNum;
        private String questionTypeId;
        private String questionTypeName;
        private String questionVideo;
        private String questionVideoId;
        private String scanImage;
        private String studCode;
        private String studentId;

        //------------------------------------------自定义start-------------------------------------------------
        /**
         * 是否是选择题
         * @return
         */
        public boolean isChoiceQuestion() {
            return MyDateUtil.isChoiceQuestion(questionTypeName,"");
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }

        public void setCorrectAnswer(String correctAnswer) {
            this.correctAnswer = correctAnswer;
        }

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getHomeworkId() {
            return homeworkId;
        }

        public void setHomeworkId(String homeworkId) {
            this.homeworkId = homeworkId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIsNoSmallClass() {
            return isNoSmallClass;
        }

        public void setIsNoSmallClass(String isNoSmallClass) {
            this.isNoSmallClass = isNoSmallClass;
        }

        public String getIsNoVideo() {
            return isNoVideo;
        }

        public void setIsNoVideo(String isNoVideo) {
            this.isNoVideo = isNoVideo;
        }

        public String getNums() {
            return nums;
        }

        public void setNums(String nums) {
            this.nums = nums;
        }

        public String getQuestionId() {
            return questionId;
        }

        public void setQuestionId(String questionId) {
            this.questionId = questionId;
        }

        public String getQuestionNum() {
            return questionNum;
        }

        public void setQuestionNum(String questionNum) {
            this.questionNum = questionNum;
        }

        public String getQuestionTypeId() {
            return questionTypeId;
        }

        public void setQuestionTypeId(String questionTypeId) {
            this.questionTypeId = questionTypeId;
        }

        public String getQuestionTypeName() {
            return questionTypeName;
        }

        public void setQuestionTypeName(String questionTypeName) {
            this.questionTypeName = questionTypeName;
        }

        public String getQuestionVideo() {
            return questionVideo;
        }

        public void setQuestionVideo(String questionVideo) {
            this.questionVideo = questionVideo;
        }

        public String getQuestionVideoId() {
            return questionVideoId;
        }

        public void setQuestionVideoId(String questionVideoId) {
            this.questionVideoId = questionVideoId;
        }

        public String getScanImage() {
            return scanImage;
        }

        public void setScanImage(String scanImage) {
            this.scanImage = scanImage;
        }

        public String getStudCode() {
            return studCode;
        }

        public void setStudCode(String studCode) {
            this.studCode = studCode;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }
    }
}
