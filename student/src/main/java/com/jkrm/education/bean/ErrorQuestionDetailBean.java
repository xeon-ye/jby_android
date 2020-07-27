package com.jkrm.education.bean;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/5 15:03
 */

public class ErrorQuestionDetailBean {


    /**
     * answer : <p style="font-family: 'Times New Roman';">90－4＝86,90＋9＝99,90＋0＝90,90－1＝89,90＋6＝96.</p><p style="font-family: 'Times New Roman';">答　这五名同学分别考了86分，99分，90分，89分，96分．</p>
     * answerExplanation :
     * answerSheetId :
     * classRatio :
     * content : <p style="font-family: 'Times New Roman';">某班5名学生在一次数学测验中的成绩以90分为标准，实际成绩为90分记为0，超过的分数记为正数，不足的分数记为负数，记录如下：－4，＋9,0，－1，＋6，你能说出这五名同学各考了多少分吗？请写出来.</p>
     * corrAnswer :
     * correctAnswer :
     * courseId :
     * errorType :
     * errorTypeName : 错题
     * grade : null
     * gradedScan :
     * graderId :
     * homeworkId :
     * id : 12684
     * isNoMake :
     * isNoVideo :
     * isOption :
     * maxScore :
     * myAnswer :
     * optionA :
     * optionB :
     * optionC :
     * optionD :
     * optionE :
     * optionF :
     * optionG :
     * options : {"optionA":"","optionB":"","optionC":"","optionD":"","optionE":"","optionF":"","optionG":""}
     * parentId :
     * personRatio :
     * practice :
     * questStatus : 3
     * questionId : 12684
     * questionNum :
     * questionVideo : http://dist.xinjiaoyu.com/videos/greatTeacher/5A89E1CCDABF4FC8839D34C2E8D5CAC9.mp4
     * questionVideoId : a26d20fe806d7bebe7441bab088cf1d4
     * rawScan : http://39.107.143.248:8088/app/output/E77673E9X112968_20200509_180000_0001/write/4/1.jpg
     * scanFinishTime :
     * scanQuesType :
     * showTime : null
     * similar :
     * smDto : null
     * source :
     * studAnswer :
     * studentId :
     * templateId :
     * type : {"id":"4b9b36205b8c25fcdf0ac6929ad19caa","isMulti":"","isOption":"1","name":"解答题","totalId":""}
     * typeId : 4b9b36205b8c25fcdf0ac6929ad19caa
     * typeName :
     * typeNames :
     */

    private String answer;
    private String answerExplanation;
    private String answerSheetId;
    private String classRatio;
    private String content;
    private String corrAnswer;
    private String correctAnswer;
    private String courseId;
    private String errorType;
    private String errorTypeName;
    private Object grade;
    private String gradedScan;
    private String graderId;
    private String homeworkId;
    private String id;
    private String isNoMake;
    private String isNoVideo;
    private String isOption;
    private String maxScore;
    private String myAnswer;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String optionE;
    private String optionF;
    private String optionG;
    private Object options;
    private String parentId;
    private String personRatio;
    private String practice;
    private String questStatus;
    private String questionId;
    private String questionNum;
    private String questionVideo;
    private String questionVideoId;
    private String rawScan;
    private String scanFinishTime;
    private String scanQuesType;
    private long showTime;
    private String similar;
    private Object smDto;
    private String source;
    private String studAnswer;
    private String studentId;
    private String templateId;
    private TypeBean type;
    private String typeId;
    private String typeName;
    private String typeNames;
    private boolean isChose;
    private List<ErrorQuestionTimeBean.SubQuestionsBean> subQuestions;

    public void setOptions(Object options) {
        this.options = options;
    }

    public List<ErrorQuestionTimeBean.SubQuestionsBean> getSubQuestions() {
        return subQuestions;
    }

    public void setSubQuestions(List<ErrorQuestionTimeBean.SubQuestionsBean> subQuestions) {
        this.subQuestions = subQuestions;
    }

    public boolean isChose() {
        return isChose;
    }

    public void setChose(boolean chose) {
        isChose = chose;
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

    public String getCorrAnswer() {
        return corrAnswer;
    }

    public void setCorrAnswer(String corrAnswer) {
        this.corrAnswer = corrAnswer;
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

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorTypeName() {
        return errorTypeName;
    }

    public void setErrorTypeName(String errorTypeName) {
        this.errorTypeName = errorTypeName;
    }

    public Object getGrade() {
        return grade;
    }

    public void setGrade(Object grade) {
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

    public String getMyAnswer() {
        return myAnswer;
    }

    public void setMyAnswer(String myAnswer) {
        this.myAnswer = myAnswer;
    }

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

    public Object getOptions() {
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

    public String getPractice() {
        return practice;
    }

    public void setPractice(String practice) {
        this.practice = practice;
    }

    public String getQuestStatus() {
        return questStatus;
    }

    public void setQuestStatus(String questStatus) {
        this.questStatus = questStatus;
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

    public String getScanQuesType() {
        return scanQuesType;
    }

    public void setScanQuesType(String scanQuesType) {
        this.scanQuesType = scanQuesType;
    }

    public long getShowTime() {
        return showTime;
    }

    public void setShowTime(long showTime) {
        this.showTime = showTime;
    }

    public String getSimilar() {
        return similar;
    }

    public void setSimilar(String similar) {
        this.similar = similar;
    }

    public Object getSmDto() {
        return smDto;
    }

    public void setSmDto(Object smDto) {
        this.smDto = smDto;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStudAnswer() {
        return studAnswer;
    }

    public void setStudAnswer(String studAnswer) {
        this.studAnswer = studAnswer;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public TypeBean getType() {
        return type;
    }

    public void setType(TypeBean type) {
        this.type = type;
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

    public static class TypeBean {
        /**
         * id : 4b9b36205b8c25fcdf0ac6929ad19caa
         * isMulti :
         * isOption : 1
         * name : 解答题
         * totalId :
         */

        private String id;
        private String isMulti;
        private String isOption;
        private String name;
        private String totalId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIsMulti() {
            return isMulti;
        }

        public void setIsMulti(String isMulti) {
            this.isMulti = isMulti;
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

        public String getTotalId() {
            return totalId;
        }

        public void setTotalId(String totalId) {
            this.totalId = totalId;
        }
    }
}
