package com.jkrm.education.bean.result;

import java.io.Serializable;
import java.util.List;

/**
 * 学生作业列表rows bean
 * Created by hzw on 2019/5/30.
 */

public class RowsHomeworkBean implements Serializable {


    /**
     * id : 1
     * homeworkId : 1
     * examId : null
     * student : {"id":1,"name":"张三"}
     * progress : 20/20
     * finishTime : 2019-4-27
     * status : 0
     * score :
     * homeworkName : 训练2-硅酸盐和硅单质
     * questionAnswers : [{"questionId":"1","questionNum":"1","status":1},{"questionId":"2","questionNum":"2","status":1},{"questionId":"3","questionNum":"3","status":1}]
     * attr : [{"attrNameId":3,"attrName":"作业类型","attrValueId":3,"attrValueName":"图书作业"},{"attrNameId":3,"attrName":"品牌","attrValueId":3,"attrValueName":"步步高"}]
     * statistics : [{"classId":1,"grade":95,"averageGrade":84,"classHighestScore":100}]
     */

    private String id;
    private String homeworkId;
    private String examId;
    private StudentBean student;
    private String progress;
    private String createTime;
    private String finishTime;
    private String classId;
    private String courseId;
    private String className;
    private String courseName;
    private String status;
    private String score;
    private String homeworkName;
    private List<QuestionAnswersBean> questionAnswers;
    private List<AttrBean> attr;
    private List<StatisticsBean> statistics;
    private String effect;//1处理完毕, 0处理中
    private String templateId;
    private String averageGrade;
    private String origin;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(String averageGrade) {
        this.averageGrade = averageGrade;
    }

    public boolean isHandle() {
        return "1".equals(effect);
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public StudentBean getStudent() {
        return student;
    }

    public void setStudent(StudentBean student) {
        this.student = student;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getHomeworkName() {
        return homeworkName;
    }

    public void setHomeworkName(String homeworkName) {
        this.homeworkName = homeworkName;
    }

    public List<QuestionAnswersBean> getQuestionAnswers() {
        return questionAnswers;
    }

    public void setQuestionAnswers(List<QuestionAnswersBean> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }

    public List<AttrBean> getAttr() {
        return attr;
    }

    public void setAttr(List<AttrBean> attr) {
        this.attr = attr;
    }

    public List<StatisticsBean> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<StatisticsBean> statistics) {
        this.statistics = statistics;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public static class StudentBean implements Serializable{
        /**
         * id : 1
         * name : 张三
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class QuestionAnswersBean implements Serializable{
        /**
         * questionId : 1
         * questionNum : 1
         * status : 1
         */

        private String questionId;
        private String questionNum;
        private int status;

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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class AttrBean implements Serializable{
        /**
         * attrNameId : 3
         * attrName : 作业类型
         * attrValueId : 3
         * attrValueName : 图书作业
         */

        private String attrNameId;
        private String attrName;
        private String attrValueId;
        private String attrValueName;

        public String getAttrNameId() {
            return attrNameId;
        }

        public void setAttrNameId(String attrNameId) {
            this.attrNameId = attrNameId;
        }

        public String getAttrName() {
            return attrName;
        }

        public void setAttrName(String attrName) {
            this.attrName = attrName;
        }

        public String getAttrValueId() {
            return attrValueId;
        }

        public void setAttrValueId(String attrValueId) {
            this.attrValueId = attrValueId;
        }

        public String getAttrValueName() {
            return attrValueName;
        }

        public void setAttrValueName(String attrValueName) {
            this.attrValueName = attrValueName;
        }
    }

    public static class StatisticsBean implements Serializable{
        /**
         * classId : 1
         * grade : 95
         * averageGrade : 84
         * classHighestScore : 100
         */

        private String classId;
        private String grade;
        private String averageGrade;
        private String classHighestScore;

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getAverageGrade() {
            return averageGrade;
        }

        public void setAverageGrade(String averageGrade) {
            this.averageGrade = averageGrade;
        }

        public String getClassHighestScore() {
            return classHighestScore;
        }

        public void setClassHighestScore(String classHighestScore) {
            this.classHighestScore = classHighestScore;
        }
    }
}
