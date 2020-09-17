package com.jkrm.education.bean.exam;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/9/16 17:00
 */

public class LineDataBean {

    /**
     * code : 200
     * data : [{"classAvgScore":"142.0","examName":"校考5科","id":"","myScore":"146"},{"classAvgScore":"12.0","examName":"9位考号","id":"","myScore":"9"},{"classAvgScore":"21.0","examName":"新物理考试","id":"","myScore":"28"}]
     * msg :
     */


        /**
         * classAvgScore : 142.0
         * examName : 校考5科
         * id :
         * myScore : 146
         */

        private String classAvgScore;
        private String examName;
        private String id;
        private String myScore;

        public String getClassAvgScore() {
            return classAvgScore;
        }

        public void setClassAvgScore(String classAvgScore) {
            this.classAvgScore = classAvgScore;
        }

        public String getExamName() {
            return examName;
        }

        public void setExamName(String examName) {
            this.examName = examName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMyScore() {
            return myScore;
        }

        public void setMyScore(String myScore) {
            this.myScore = myScore;
        }
}
