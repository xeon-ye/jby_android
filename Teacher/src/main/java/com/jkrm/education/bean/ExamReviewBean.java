package com.jkrm.education.bean;

import com.jkrm.education.bean.exam.ExamQuestionsBean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/8/28 10:19
 */

public class ExamReviewBean {
    private String id;
    private String answerId;//答题表id
    private String progress;//是否批阅0未批阅，1批阅
    private String rawScan;//题目解析图片
    private String sheetId;//sheet表Id
    private String maxScore;//题的最高分
    private String score;//题的得分
    private String questionId;//题目id
    private String questionNum;//题号
    private String sheetRawScan;//答题卡图片
    private String gradedScan;//批阅图片
    private String finishTime;//批阅完成时间
    private String missingStatus;//0缺考，1实考
    private int optStatus;
    private String scores;//步长
    private String questioType;//题的类型
    private ArrayList<reaListBean> reaList;

    public ArrayList<reaListBean> getReaList() {
        return reaList;
    }

    public void setReaList(ArrayList<reaListBean> reaList) {
        this.reaList = reaList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getRawScan() {
        return rawScan;
    }

    public void setRawScan(String rawScan) {
        this.rawScan = rawScan;
    }

    public String getSheetId() {
        return sheetId;
    }

    public void setSheetId(String sheetId) {
        this.sheetId = sheetId;
    }

    public String getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(String maxScore) {
        this.maxScore = maxScore;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
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

    public String getSheetRawScan() {
        return sheetRawScan;
    }

    public void setSheetRawScan(String sheetRawScan) {
        this.sheetRawScan = sheetRawScan;
    }

    public String getGradedScan() {
        return gradedScan;
    }

    public void setGradedScan(String gradedScan) {
        this.gradedScan = gradedScan;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getMissingStatus() {
        return missingStatus;
    }

    public void setMissingStatus(String missingStatus) {
        this.missingStatus = missingStatus;
    }

    public int getOptStatus() {
        return optStatus;
    }

    public void setOptStatus(int optStatus) {
        this.optStatus = optStatus;
    }

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }

    public String getQuestioType() {
        return questioType;
    }

    public void setQuestioType(String questioType) {
        this.questioType = questioType;
    }

    public static class  reaListBean implements Serializable {

        /**
         * answerId : AB1601000098768F2UT8@16
         * classId : bf163b3d3b5a4b139bf92893d8d84429
         * finishTime :
         * gradedScan :
         * homeworkId : exam1600941147891@63cb1f8eae0deed1d19e72c92b2291fb@bf163b3d3b5a4b139bf92893d8d84429
         * id : da59fdadf9fff0edd2b66205f1b6e871
         * maxScore : 5.0
         * missingStatus :
         * optStatus : 0
         * progress : 0
         * questioType :
         * questionId : 424634
         * questionNum : 16
         * rawScan : http://123.57.85.125/exam/20200925/E77673E9X112568_20200925_100955_0042/write_question_16.jpg
         * score :
         * scores : -1
         * sheetId : 1601000098768F2UT8
         * sheetRawScan : http://123.57.85.125/exam/20200925/E77673E9X112568_20200925_100955_0042/002_page.jpg,http://123.57.85.125/exam/20200925/E77673E9X112568_20200925_100955_0042/001_page.jpg
         * studCode : 75013
         * studentId : cf116e34831fbf378182d117f2c0fc62
         * typical :
         */

        private String answerId;
        private String classId;
        private String finishTime;
        private String gradedScan;
        private String homeworkId;
        private String id;
        private String maxScore;
        private String missingStatus;
        private String optStatus;
        private String progress;
        private String questioType;
        private String questionId;
        private String questionNum;
        private String rawScan;
        private String score;
        private String scores;
        private String sheetId;
        private String sheetRawScan;
        private String studCode;
        private String studentId;
        private String typical;
        private boolean isSelect;
        private String remarkScore="";

        public String getRemarkScore() {
            return remarkScore;
        }

        public void setRemarkScore(String remarkScore) {
            this.remarkScore = remarkScore;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getAnswerId() {
            return answerId;
        }

        public void setAnswerId(String answerId) {
            this.answerId = answerId;
        }

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getFinishTime() {
            return finishTime;
        }

        public void setFinishTime(String finishTime) {
            this.finishTime = finishTime;
        }

        public String getGradedScan() {
            return gradedScan;
        }

        public void setGradedScan(String gradedScan) {
            this.gradedScan = gradedScan;
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

        public String getMaxScore() {
            return maxScore;
        }

        public void setMaxScore(String maxScore) {
            this.maxScore = maxScore;
        }

        public String getMissingStatus() {
            return missingStatus;
        }

        public void setMissingStatus(String missingStatus) {
            this.missingStatus = missingStatus;
        }

        public String getOptStatus() {
            return optStatus;
        }

        public void setOptStatus(String optStatus) {
            this.optStatus = optStatus;
        }

        public String getProgress() {
            return progress;
        }

        public void setProgress(String progress) {
            this.progress = progress;
        }

        public String getQuestioType() {
            return questioType;
        }

        public void setQuestioType(String questioType) {
            this.questioType = questioType;
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

        public String getRawScan() {
            return rawScan;
        }

        public void setRawScan(String rawScan) {
            this.rawScan = rawScan;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getScores() {
            return scores;
        }

        public void setScores(String scores) {
            this.scores = scores;
        }

        public String getSheetId() {
            return sheetId;
        }

        public void setSheetId(String sheetId) {
            this.sheetId = sheetId;
        }

        public String getSheetRawScan() {
            return sheetRawScan;
        }

        public void setSheetRawScan(String sheetRawScan) {
            this.sheetRawScan = sheetRawScan;
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

        public String getTypical() {
            return typical;
        }

        public void setTypical(String typical) {
            this.typical = typical;
        }
    }
}
