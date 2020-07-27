package com.jkrm.education.bean.result;

import java.io.Serializable;

/**
 * Created by hzw on 2019/6/10.
 */

public class AnswerSheetProgressResultBean implements Serializable {


    /**
     * classId : 1
     * course_id :
     * createBy :
     * createTime : 2019-06-10T17:49:00+0800
     * deleteStatus : 0
     * examId :
     * finishTime : 2019-06-10T17:49:00+0800
     * homeworkId : 03012019020303030311
     * homeworkName :
     * id : sheet030120190203030303110001
     * identifyStatus : 1
     * progress : 17/17
     * rawScan : http://img.xinjiaoyu.com/app/output/BRN30055CAC9558_20190610_174340_009336/page.jpg,http://img.xinjiaoyu.com/app/output/BRN30055CAC9558_20190610_174340_009337/page.jpg
     * remark :
     * schoolId : 1
     * status : 1
     * studCode : 10001
     * studentId : schstudent081
     * studentName : 黄佳怡
     * templateId : 030120190203030303
     * totalScore : 66.0
     * updateBy :
     * updateTime : 2019-06-11T06:49:00+0800
     */

    private String classId;
    private String course_id;
    private String createBy;
    private String createTime;
    private String deleteStatus;
    private String examId;
    private String finishTime;
    private String homeworkId;
    private String homeworkName;
    private String id;
    private String identifyStatus;
    private String progress;
    private String rawScan;
    private String remark;
    private String schoolId;
    private String status;
    private String studCode;
    private String studentId;
    private String studentName;
    private String templateId;
    private String totalScore;
    private String updateBy;
    private String updateTime;
    private String  answerSheetId;
    private String sheetExtId;

    public String getSheetExtld() {
        return sheetExtId;
    }

    public void setSheetExtld(String sheetExtld) {
        this.sheetExtId = sheetExtld;
    }

    public String getAnswerSheetId() {
        return answerSheetId;
    }

    public void setAnswerSheetId(String answerSheetId) {
        this.answerSheetId = answerSheetId;
    }
//自定义开始.....................................................................................

    /**
     * 获取批阅状态
     * @return
     */
    public String getMarkProgress() {
        return "1".equals(status) ? "已批阅" : "未批阅";
    }

    public boolean isMarked() {
        return "1".equals(status);
    }

    //自定义结束.....................................................................................

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
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

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getHomeworkName() {
        return homeworkName;
    }

    public void setHomeworkName(String homeworkName) {
        this.homeworkName = homeworkName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentifyStatus() {
        return identifyStatus;
    }

    public void setIdentifyStatus(String identifyStatus) {
        this.identifyStatus = identifyStatus;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
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

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
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
}
