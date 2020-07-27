package com.jkrm.education.bean.result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/27 16:27
 */

public class ErrorChoiceStatisticsBean {


    /**
     * code : 200
     * data : {"optionsStatistic1":{"unchoose":[]},"answer":"D","optionsStatistic":{"A":[],"B":[],"C":[{"answer":"C","maxScore":"5.0","score":"0.0","studentId":"ef46f9600b449b35146b97548203929c","studentName":"陈夏怡"},{"answer":"C","maxScore":"5.0","score":"0.0","studentId":"5a4c4f4c0518a167a1f4eabffda77a72","studentName":"高奇"},{"answer":"C","maxScore":"5.0","score":"0.0","studentId":"64b91bdb744e8cddc14af28ca6e6f19f","studentName":"王明惠"},{"answer":"C","maxScore":"5.0","score":"0.0","studentId":"ac0bbae51a78fb46cd00c183a4f7dedd","studentName":"赵娅君"}],"D":[],"unchoose":[]},"gradeAvg":"0.0"}
     * msg :
     */


        /**
         * optionsStatistic1 : {"unchoose":[]}
         * answer : D
         * optionsStatistic : {"A":[],"B":[],"C":[{"answer":"C","maxScore":"5.0","score":"0.0","studentId":"ef46f9600b449b35146b97548203929c","studentName":"陈夏怡"},{"answer":"C","maxScore":"5.0","score":"0.0","studentId":"5a4c4f4c0518a167a1f4eabffda77a72","studentName":"高奇"},{"answer":"C","maxScore":"5.0","score":"0.0","studentId":"64b91bdb744e8cddc14af28ca6e6f19f","studentName":"王明惠"},{"answer":"C","maxScore":"5.0","score":"0.0","studentId":"ac0bbae51a78fb46cd00c183a4f7dedd","studentName":"赵娅君"}],"D":[],"unchoose":[]}
         * gradeAvg : 0.0
         */

        private OptionsStatistic1Bean optionsStatistic1;
        private String answer;
        private HashMap<String,List<OptionsStatisticBean.CBean>> optionsStatistic;
        private String gradeAvg;

        public OptionsStatistic1Bean getOptionsStatistic1() {
            return optionsStatistic1;
        }

        public void setOptionsStatistic1(OptionsStatistic1Bean optionsStatistic1) {
            this.optionsStatistic1 = optionsStatistic1;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public HashMap<String,List<OptionsStatisticBean.CBean>> getOptionsStatistic() {
            return optionsStatistic;
        }

        public void setOptionsStatistic(HashMap<String,List<OptionsStatisticBean.CBean>> optionsStatistic) {
            this.optionsStatistic = optionsStatistic;
        }

        public String getGradeAvg() {
            return gradeAvg;
        }

        public void setGradeAvg(String gradeAvg) {
            this.gradeAvg = gradeAvg;
        }

        public static class OptionsStatistic1Bean {
            private List<?> unchoose;

            public List<?> getUnchoose() {
                return unchoose;
            }

            public void setUnchoose(List<?> unchoose) {
                this.unchoose = unchoose;
            }
        }

        public static class OptionsStatisticBean {



            public static class CBean {
                /**
                 * answer : C
                 * maxScore : 5.0
                 * score : 0.0
                 * studentId : ef46f9600b449b35146b97548203929c
                 * studentName : 陈夏怡
                 */

                private String answer;
                private String maxScore;
                private String score;
                private String studentId;
                private String studentName;

                public String getAnswer() {
                    return answer;
                }

                public void setAnswer(String answer) {
                    this.answer = answer;
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

                public String getStudentId() {
                    return studentId;
                }

                public void setStudentId(String studentId) {
                    this.studentId = studentId;
                }

                public String getStudentName() {
                    return studentName;
                }

                public void setStudentName(String studentName) {
                    this.studentName = studentName;
                }
            }
        }
}
