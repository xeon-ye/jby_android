package com.jkrm.education.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/8/27 15:48
 */

public class ReViewTaskBean implements Serializable {
    private int readWay;
    private String readWayName;
    private int finishRead;
    private int totalRead;
    private List<Bean> quesList;

    public int getReadWay() {
        return readWay;
    }

    public void setReadWay(int readWay) {
        this.readWay = readWay;
    }

    public String getReadWayName() {
        return readWayName;
    }

    public void setReadWayName(String readWayName) {
        this.readWayName = readWayName;
    }

    public int getFinishRead() {
        return finishRead;
    }

    public void setFinishRead(int finishRead) {
        this.finishRead = finishRead;
    }

    public int getTotalRead() {
        return totalRead;
    }

    public void setTotalRead(int totalRead) {
        this.totalRead = totalRead;
    }

    public List<Bean> getQuesList() {
        return quesList;
    }

    public void setQuesList(List<Bean> quesList) {
        this.quesList = quesList;
    }

    public static class Bean implements Serializable{
        private String examId;
        private String haveRead;
        private String maxScore;
        private String paperId;
        private String percent;
        private String questionId;
        private String questionNum;
        private String questionType;
        private String readDistName;
        private String readNum;
        private String readWay;
        private String toBeRead;

        public String getExamId() {
            return examId;
        }

        public void setExamId(String examId) {
            this.examId = examId;
        }

        public String getHaveRead() {
            return haveRead;
        }

        public void setHaveRead(String haveRead) {
            this.haveRead = haveRead;
        }

        public String getMaxScore() {
            return maxScore;
        }

        public void setMaxScore(String maxScore) {
            this.maxScore = maxScore;
        }

        public String getPaperId() {
            return paperId;
        }

        public void setPaperId(String paperId) {
            this.paperId = paperId;
        }

        public String getPercent() {
            return percent;
        }

        public void setPercent(String percent) {
            this.percent = percent;
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

        public String getQuestionType() {
            return questionType;
        }

        public void setQuestionType(String questionType) {
            this.questionType = questionType;
        }

        public String getReadDistName() {
            return readDistName;
        }

        public void setReadDistName(String readDistName) {
            this.readDistName = readDistName;
        }

        public String getReadNum() {
            return readNum;
        }

        public void setReadNum(String readNum) {
            this.readNum = readNum;
        }

        public String getReadWay() {
            return readWay;
        }

        public void setReadWay(String readWay) {
            this.readWay = readWay;
        }

        public String getToBeRead() {
            return toBeRead;
        }

        public void setToBeRead(String toBeRead) {
            this.toBeRead = toBeRead;
        }
    }
}
