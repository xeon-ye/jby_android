package com.jkrm.education.bean.result;

import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.MyDateUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hzw on 2019/6/6.
 */

public class HomeworkDetailResultBean implements Serializable{


    private List<GradQusetionBean> gradQusetion;
    private List<HomeworkDuratBean> homeworkDurat;
    private List<QuestionScoreBean> questionScore;

    public List<GradQusetionBean> getGradQusetion() {
        return gradQusetion;
    }

    public void setGradQusetion(List<GradQusetionBean> gradQusetion) {
        this.gradQusetion = gradQusetion;
    }

    public List<HomeworkDuratBean> getHomeworkDurat() {
        return homeworkDurat;
    }

    public void setHomeworkDurat(List<HomeworkDuratBean> homeworkDurat) {
        this.homeworkDurat = homeworkDurat;
    }

    public List<QuestionScoreBean> getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(List<QuestionScoreBean> questionScore) {
        this.questionScore = questionScore;
    }

    public static class GradQusetionBean implements Serializable{
        /**
         * answerSheetId : sheet030120190203030302130037
         * classId : 5
         * hdDto : [{"cnt":"1","duration":"A"},{"cnt":"2","duration":"B"},{"cnt":"3","duration":"C"},{"cnt":"30","duration":"D"}]
         * homeworkId : 03012019020303030215
         * id : 03012019020303030215
         * maxScore : 4.0
         * prodAnswer : D
         * questionId : prodquestion001
         * questionNum : 1
         * questionVideoId :
         * ratio : 0.8372093
         * typeName : 选择题
         */

        private String answerSheetId;
        private String classId;
        private String homeworkId;
        private String id;
        private String maxScore;
        private String prodAnswer;
        private String questionId;
        private String questionNum;
        private String questionVideoId;//名师讲题存在则不为空
        private String ratio;
        private String typeName;
        private List<HdDtoBean> hdDto;
        private String progress;
        private String status;
        private String typical; //典型题1存在0不存在
        private String similar; //类题加练1存在0不存在
        private String isOption; //2是选择题 否则非选择题
        private String exPlat;//需讲解人数
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getExPlat() {
            return exPlat;
        }

        public void setExPlat(String exPlat) {
            this.exPlat = exPlat;
        }
        //自定义开始.......................................................

        public boolean isChoiceQuestion() {
//            if(AwDataUtil.isEmpty(typeName) || AwDataUtil.isEmpty(questionId)) {
//                return true;
//            }
//            return MyDateUtil.isChoiceQuestion(typeName);
            if(AwDataUtil.isEmpty(isOption))
                return false;
            return "2".equals(isOption);
        }

        //自定义结束.......................................................

        public String getSimilar() {
            return similar;
        }

        public void setSimilar(String similar) {
            this.similar = similar;
        }

        public String getAnswerSheetId() {
            return answerSheetId;
        }

        public void setAnswerSheetId(String answerSheetId) {
            this.answerSheetId = answerSheetId;
        }

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
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

        public String getProdAnswer() {
            return prodAnswer;
        }

        public void setProdAnswer(String prodAnswer) {
            this.prodAnswer = prodAnswer;
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

        public String getQuestionVideoId() {
            return questionVideoId;
        }

        public void setQuestionVideoId(String questionVideoId) {
            this.questionVideoId = questionVideoId;
        }

        public String getRatio() {
            return ratio;
        }

        public void setRatio(String ratio) {
            this.ratio = ratio;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public List<HdDtoBean> getHdDto() {
            return hdDto;
        }

        public void setHdDto(List<HdDtoBean> hdDto) {
            this.hdDto = hdDto;
        }

        public String getProgress() {
            return progress;
        }

        public void setProgress(String progress) {
            this.progress = progress;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTypical() {
            return typical;
        }

        public void setTypical(String typical) {
            this.typical = typical;
        }

        public String getIsOption() {
            return isOption;
        }

        public void setIsOption(String isOption) {
            this.isOption = isOption;
        }

        public static class HdDtoBean implements Serializable{
            /**
             * cnt : 1
             * duration : A
             */

            private String cnt;
            private String duration;

            public String getCnt() {
                return cnt;
            }

            public void setCnt(String cnt) {
                this.cnt = cnt;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }
        }
    }

    public static class HomeworkDuratBean implements Serializable{
        /**
         * cnt : 0
         * duration : 0-10
         */

        private String cnt;
        private String duration;

        public String getCnt() {
            return cnt;
        }

        public void setCnt(String cnt) {
            this.cnt = cnt;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }
    }

    public static class QuestionScoreBean implements Serializable{
        /**
         * classAvg : null
         * classScoredRatio : {"questionNum":"1","ratio":"0.8372093"}
         * gradeAvg : null
         * gradeScoredRatio : null
         */

        private String classAvg;
        private ClassScoredRatioBean classScoredRatio;
        private String gradeAvg;
        private String gradeScoredRatio;

        public String getClassAvg() {
            return classAvg;
        }

        public void setClassAvg(String classAvg) {
            this.classAvg = classAvg;
        }

        public ClassScoredRatioBean getClassScoredRatio() {
            return classScoredRatio;
        }

        public void setClassScoredRatio(ClassScoredRatioBean classScoredRatio) {
            this.classScoredRatio = classScoredRatio;
        }

        public String getGradeAvg() {
            return gradeAvg;
        }

        public void setGradeAvg(String gradeAvg) {
            this.gradeAvg = gradeAvg;
        }

        public String getGradeScoredRatio() {
            return gradeScoredRatio;
        }

        public void setGradeScoredRatio(String gradeScoredRatio) {
            this.gradeScoredRatio = gradeScoredRatio;
        }

        public static class ClassScoredRatioBean implements Serializable{
            /**
             * questionNum : 1
             * ratio : 0.8372093
             */

            private String questionNum;
            private String ratio;

            public String getQuestionNum() {
                return questionNum;
            }

            public void setQuestionNum(String questionNum) {
                this.questionNum = questionNum;
            }

            public String getRatio() {
                return ratio;
            }

            public void setRatio(String ratio) {
                this.ratio = ratio;
            }
        }
    }
}
