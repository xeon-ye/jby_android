package com.jkrm.education.bean.result;

/**
 * @Description: 图书习题 列表 题目和得分
 * @Author: xiangqian
 * @CreateDate: 2020/2/22 12:32
 */

public class TopicScoreBean {

        /**
         * catalogId :
         * maxScore : 4.0
         * questionId : 154887
         * questionNum : 1
         */

        private String catalogId;
        private String maxScore;
        private String questionId;
        private String questionNum;

        public String getCatalogId() {
            return catalogId;
        }

        public void setCatalogId(String catalogId) {
            this.catalogId = catalogId;
        }

        public String getMaxScore() {
            return maxScore;
        }

        public void setMaxScore(String maxScore) {
            this.maxScore = maxScore;
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
}
