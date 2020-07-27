package com.jkrm.education.bean.result;

import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.MyDateUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hzw on 2019/6/1.
 */

public class ErrorQuestionResultBean implements Serializable {
    /**
     * answer : (1)－6　(2)11　(3)7
     * answerExplanation :
     * answerSheetId : sheet030120190203030305110074
     * classRatio : 0.676056338028169
     * content :
     * correctAnswer :
     * correctness : 0
     * grade : {"averageGrade":"4.8403363","maxScore":"6.0","score":"2.0"}
     * gradedScan :
     * graderId : f93bc11a2988271f41bdbbac1d82b1a1
     * id : prodquestion115
     * knowlPoints : []
     * options : {"optionA":"","optionB":"","optionC":"","optionD":"","optionE":"","optionF":"","optionG":""}
     * parentId :
     * personRatio : 0.6666666666666666
     * questionId : prodquestion115
     * questionNum : 12
     * rawScan : http://img.xinjiaoyu.com/app/output/img20190525-151444_0027/write/3/1.jpg
     * scanFinishTime : 2019-07-08 15:01:28
     * score : 2.0
     * source : 金榜苑
     * studentId : 6e84e792c8d63cb7741fe9759344ab48
     * testPoints : []
     * typeId : 2f4c29c9b2ee5737af832ba908360752
     * typeName : 填空题
     * typeNames : 解答题
     */

    private String answer;
    private String answerExplanation;
    private String answerId;
    private String answerSheetId;
    private String classAvg;
    private String classRatio;
    private String content;
    private String correctAnswer;
    private String courseId;
    private String gradedScan;
    private String graderId;
    private String homeworkId;
    private String id;
    private String isMulti;
    private String isNoAdd;
    private String isNoMake;
    private String isNoVideo;
    private String isOption;
    private String maxScore;
    private String parentId;
    private String personRatio;
    private String questionId;
    private String questionNum;
    private String questionVideo;
    private String questionVideoId;
    private String rawScan;
    private String scanFinishTime;
    private String similar;
    private String source;
    private String studentId;
    private String templateId;
    private String typeId;
    private String typeName;

    private String typeNames;
    private String practice;
    private String score;
    private String correctness;

    private List<?> knowlPoints;
    private List<?> testPoints;
    private GradeBean grade;
    private OptionsBean options;
    private SmDtoBean smDto;


    //------------------------------------------自定义start-------------------------------------------------
    /**
     * 是否是选择题
     * @return
     */
    public boolean isChoiceQuestion() {
        return MyDateUtil.isChoiceQuestion(typeName,isOption);
    }

    private boolean isSelect;

    public boolean isSelect() {
        if(AwDataUtil.isEmpty(isNoAdd)) {
            return false;
        }
        if("0".equals(isNoAdd)) {
            return false;
        }
        return true;
    }

    public void setSelect(boolean select) {
        isNoAdd = select ? "1" : "0";
    }
    //------------------------------------------自定义end------------------------------------------------

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getClassAvg() {
        return classAvg;
    }

    public void setClassAvg(String classAvg) {
        this.classAvg = classAvg;
    }

    public String getIsMulti() {
        return isMulti;
    }

    public void setIsMulti(String isMulti) {
        this.isMulti = isMulti;
    }

    public String getIsNoAdd() {
        return isNoAdd;
    }

    public void setIsNoAdd(String isNoAdd) {
        this.isNoAdd = isNoAdd;
    }

    public String getIsOption() {
        return isOption;
    }

    public void setIsOption(String isOption) {
        this.isOption = isOption;
    }

    public String getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(String maxScore) {
        this.maxScore = maxScore;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getPractice() {
        return practice;
    }

    public void setPractice(String practice) {
        this.practice = practice;
    }

    public SmDtoBean getSmDto() {
        return smDto;
    }

    public void setSmDto(SmDtoBean smDto) {
        this.smDto = smDto;
    }

    public String getSimilar() {
        return similar;
    }

    public void setSimilar(String similar) {
        this.similar = similar;
    }

    public String getIsNoMake() {
        return isNoMake;
    }

    public void setIsNoMake(String isNoMake) {
        this.isNoMake = isNoMake;
    }

    public String getIsNoVideo() {
        return isNoVideo;
    }

    public void setIsNoVideo(String isNoVideo) {
        this.isNoVideo = isNoVideo;
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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerExplanation() {
        return answerExplanation;
    }

    public void setAnswerExplanation(String answerExplanation) {
        this.answerExplanation = answerExplanation;
    }

    public String getAnswerSheetId() {
        return answerSheetId;
    }

    public void setAnswerSheetId(String answerSheetId) {
        this.answerSheetId = answerSheetId;
    }

    public String getClassRatio() {
        return classRatio;
    }

    public void setClassRatio(String classRatio) {
        this.classRatio = classRatio;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getCorrectness() {
        return correctness;
    }

    public void setCorrectness(String correctness) {
        this.correctness = correctness;
    }

    public GradeBean getGrade() {
        return grade;
    }

    public void setGrade(GradeBean grade) {
        this.grade = grade;
    }

    public String getGradedScan() {
        return gradedScan;
    }

    public void setGradedScan(String gradedScan) {
        this.gradedScan = gradedScan;
    }

    public String getGraderId() {
        return graderId;
    }

    public void setGraderId(String graderId) {
        this.graderId = graderId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OptionsBean getOptions() {
        return options;
    }

    public void setOptions(OptionsBean options) {
        this.options = options;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPersonRatio() {
        return personRatio;
    }

    public void setPersonRatio(String personRatio) {
        this.personRatio = personRatio;
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

    public String getScanFinishTime() {
        return scanFinishTime;
    }

    public void setScanFinishTime(String scanFinishTime) {
        this.scanFinishTime = scanFinishTime;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeNames() {
        return typeNames;
    }

    public void setTypeNames(String typeNames) {
        this.typeNames = typeNames;
    }

    public List<?> getKnowlPoints() {
        return knowlPoints;
    }

    public void setKnowlPoints(List<?> knowlPoints) {
        this.knowlPoints = knowlPoints;
    }

    public List<?> getTestPoints() {
        return testPoints;
    }

    public void setTestPoints(List<?> testPoints) {
        this.testPoints = testPoints;
    }

    public static class GradeBean {
        /**
         * averageGrade : 4.8403363
         * maxScore : 6.0
         * score : 2.0
         */

        private String averageGrade;
        private String maxScore;
        private String score;

        public String getAverageGrade() {
            return averageGrade;
        }

        public void setAverageGrade(String averageGrade) {
            this.averageGrade = averageGrade;
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
    }

    public static class OptionsBean {
        /**
         * optionA :
         * optionB :
         * optionC :
         * optionD :
         * optionE :
         * optionF :
         * optionG :
         */

        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private String optionE;
        private String optionF;
        private String optionG;

        public String getOptionA() {
            return optionA;
        }

        public void setOptionA(String optionA) {
            this.optionA = optionA;
        }

        public String getOptionB() {
            return optionB;
        }

        public void setOptionB(String optionB) {
            this.optionB = optionB;
        }

        public String getOptionC() {
            return optionC;
        }

        public void setOptionC(String optionC) {
            this.optionC = optionC;
        }

        public String getOptionD() {
            return optionD;
        }

        public void setOptionD(String optionD) {
            this.optionD = optionD;
        }

        public String getOptionE() {
            return optionE;
        }

        public void setOptionE(String optionE) {
            this.optionE = optionE;
        }

        public String getOptionF() {
            return optionF;
        }

        public void setOptionF(String optionF) {
            this.optionF = optionF;
        }

        public String getOptionG() {
            return optionG;
        }

        public void setOptionG(String optionG) {
            this.optionG = optionG;
        }
    }

    public static class SmDtoBean {
        /**
         * questionId : prodquestion107
         * studCode : 10117
         * studentId : 209a5fa288896e533cee2c90f4f504c1
         */

        private String questionId;
        private String studCode;
        private String studentId;
        private String rawScan;

        public String getQuestionId() {
            return questionId;
        }

        public void setQuestionId(String questionId) {
            this.questionId = questionId;
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

        public String getRawScan() {
            return rawScan;
        }

        public void setRawScan(String rawScan) {
            this.rawScan = rawScan;
        }
    }
}
