package com.jkrm.education.bean.result;

import com.hzw.baselib.util.MyDateUtil;

/**
 * Created by hzw on 2019/5/30.
 */

public class AnswerSheetDataDetailResultBean {
    /**
     * answer : D
     * answerSheetId : sheet030120190203030305110097
     * classAvg :
     * className :
     * correctness : 0
     * createBy :
     * createTime : 2019-07-10T11:42:47+0800
     * deleteStatus : 0
     * gradeRatio : 0.0
     * gradedScan :
     * graderId : f93bc11a2988271f41bdbbac1d82b1a1
     * id : 040354dc9ad6c14523c9a6a40244ca98
     * isNoMake : 1
     * isNoVideo : 0
     * maxScore : 4.0
     * missing :
     * population :
     * prodAnswer : C
     * questionId : prodquestion107
     * questionNum : 4
     * questionVideo :
     * questionVideoId :
     * rawScan :
     * remark :
     * score : 0.0
     * similar : 0
     * smDto : {"questionId":"prodquestion107","studCode":"10117","studentId":"209a5fa288896e533cee2c90f4f504c1"}
     * status : 1
     * studCode :
     * studentId : 0ab0fd60dce5f37c36fa2d909fa406dd
     * studentName :
     * submitted :
     * typeName : 单选题
     * typical :
     * updateBy :
     * updateTime : 2019-07-10T11:42:47+0800
     */

    private String answer;
    private String answerSheetId;
    private String classAvg;
    private String className;
    private String correctness;
    private String createBy;
    private String createTime;
    private String deleteStatus;
    private String gradeRatio;
    private String gradedScan;
    private String graderId;
    private String id;
    private String isNoMake;
    private String isNoVideo;
    private String maxScore;
    private String missing;
    private String population;
    private String prodAnswer;
    private String questionId;
    private String questionNum;
    private String questionVideo;
    private String questionVideoId;
    private String rawScan;
    private String remark;
    private String score;
    private String similar;
    private SmDtoBean smDto;
    private String status;
    private String studCode;
    private String studentId;
    private String studentName;
    private String submitted;
    private String typeName;
    private String typical;
    private String updateBy;
    private String updateTime;
    private String typeId;
    private String practice;
    private String isOption;
    private String groupQuestion;// 是否是组题 1 不是  2 是
    private String title;
    private String isFree;

    public String getIsFree() {
        return isFree;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroupQuestion() {
        return groupQuestion;
    }

    public void setGroupQuestion(String groupQuestion) {
        this.groupQuestion = groupQuestion;
    }

    public String getIsOption() {
        return isOption;
    }

    public void setIsOption(String isOption) {
        this.isOption = isOption;
    }
//------------------------------------------自定义start-------------------------------------------------
    /**
     * 是否是选择题
     * @return
     */
    public boolean isChoiceQuestion() {
        return MyDateUtil.isChoiceQuestion(typeName,isOption);
    }

    //------------------------------------------自定义end-------------------------------------------------
    public String getPractice() {
        return practice;
    }

    public void setPractice(String practice) {
        this.practice = practice;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerSheetId() {
        return answerSheetId;
    }

    public void setAnswerSheetId(String answerSheetId) {
        this.answerSheetId = answerSheetId;
    }

    public String getClassAvg() {
        return classAvg;
    }

    public void setClassAvg(String classAvg) {
        this.classAvg = classAvg;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCorrectness() {
        return correctness;
    }

    public void setCorrectness(String correctness) {
        this.correctness = correctness;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(String deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getGradeRatio() {
        return gradeRatio;
    }

    public void setGradeRatio(String gradeRatio) {
        this.gradeRatio = gradeRatio;
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

    public String getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(String maxScore) {
        this.maxScore = maxScore;
    }

    public String getMissing() {
        return missing;
    }

    public void setMissing(String missing) {
        this.missing = missing;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSimilar() {
        return similar;
    }

    public void setSimilar(String similar) {
        this.similar = similar;
    }

    public SmDtoBean getSmDto() {
        return smDto;
    }

    public void setSmDto(SmDtoBean smDto) {
        this.smDto = smDto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSubmitted() {
        return submitted;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypical() {
        return typical;
    }

    public void setTypical(String typical) {
        this.typical = typical;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
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
