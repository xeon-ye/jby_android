package com.jkrm.education.bean.exam;

import com.jkrm.education.bean.CourseBean;
import com.jkrm.education.bean.result.StatisticsHomeworkSubmitTableResultBeanNew;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/9/11 10:48
 */

public class ExamSearchBean {

    /**
     * code : 200
     * current : 1
     * data : null
     * msg :
     * pages : 0
     * rows : []
     * size : 100
     * total : 0
     */

    private String code;
    private String current;
    private Object data;
    private String msg;
    private String pages;
    private String size;
    private String total;
    private List<RowsBean> rows;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }
    public static class RowsBean {

        /**
         * classId : 4dca54f3b8ad4522a95d72f18caf1c6e
         * className : 七年级5班
         * courseId : 2abad4627d44c059a599dcd55b40869b
         * courseName : 1.1  正数和负数
         * id : 030120190201010301@1c88b125c8b75eb4fbd46aa64b0b3aa5@4dca54f3b8ad4522a95d72f18caf1c6e
         * missing : 60
         * studentName : 徐赛兵,蒋菊丽,曾云文,郑英志,应炎芳,王如明,项真玉,施卫,方红星,洪庆群,金彩,俞江英,曹利强,陈乃书,金晓晖,李峰,张高亮,陆其昌,包国芳,黄慧珍,何增喜,李江军,胡庆阳,蔡晓金,周朔英,楼冠军,秦峥,丁红英,徐小爱,邢一江,华红,金素珠,俞宏纬,陈敏,叶学农,吴嫺,赵可儿,邵建军,叶丽聪,张俊泉,张迪校,黄伯霓,陈燕凤,徐勋丰,徐小凡,朱瑞卫,李虹霞,俞美娟,朱国浩,汪忠,蔡海波,赵菊敏,韩英,姜正龙,张君利,张文表,胡建华,黄小丽,叶平,王琨宇
         */

        private String classId;
        private String className;
        private String courseId;
        private String courseName;
        private String id;
        private String missing;
        private String studentName;
        private List<CourseBean> courseList;
        private String createBy;
        private String createName;
        private String createTime;
        private String ctime;
        private String customCode;
        private String deleteStatus;
        private String examCategory;
        private String examCodeType;
        private String examEndTime;
        private String examName;
        private String examPattern;
        private String examStartTime;
        private String examStatus;
        private String examStepNum;
        private String examType;
        private String examTypeName;
        private String gradeId;
        private String gradeName;
        private String phaseId;
        private String readPattern;
        private String readPatternName;
        private String remark;
        private String schId;
        private String schName;
        //private List<TableJson> tableJson;
        private String tabletAreaIdSelect;
        private String tempCourseId;
        private String updateBy;
        private String updateTime;

        public List<CourseBean> getCourseList() {
            return courseList;
        }

        public void setCourseList(List<CourseBean> courseList) {
            this.courseList = courseList;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateName() {
            return createName;
        }

        public void setCreateName(String createName) {
            this.createName = createName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getCustomCode() {
            return customCode;
        }

        public void setCustomCode(String customCode) {
            this.customCode = customCode;
        }

        public String getDeleteStatus() {
            return deleteStatus;
        }

        public void setDeleteStatus(String deleteStatus) {
            this.deleteStatus = deleteStatus;
        }

        public String getExamCategory() {
            return examCategory;
        }

        public void setExamCategory(String examCategory) {
            this.examCategory = examCategory;
        }

        public String getExamCodeType() {
            return examCodeType;
        }

        public void setExamCodeType(String examCodeType) {
            this.examCodeType = examCodeType;
        }

        public String getExamEndTime() {
            return examEndTime;
        }

        public void setExamEndTime(String examEndTime) {
            this.examEndTime = examEndTime;
        }

        public String getExamName() {
            return examName;
        }

        public void setExamName(String examName) {
            this.examName = examName;
        }

        public String getExamPattern() {
            return examPattern;
        }

        public void setExamPattern(String examPattern) {
            this.examPattern = examPattern;
        }

        public String getExamStartTime() {
            return examStartTime;
        }

        public void setExamStartTime(String examStartTime) {
            this.examStartTime = examStartTime;
        }

        public String getExamStatus() {
            return examStatus;
        }

        public void setExamStatus(String examStatus) {
            this.examStatus = examStatus;
        }

        public String getExamStepNum() {
            return examStepNum;
        }

        public void setExamStepNum(String examStepNum) {
            this.examStepNum = examStepNum;
        }

        public String getExamType() {
            return examType;
        }

        public void setExamType(String examType) {
            this.examType = examType;
        }

        public String getExamTypeName() {
            return examTypeName;
        }

        public void setExamTypeName(String examTypeName) {
            this.examTypeName = examTypeName;
        }

        public String getGradeId() {
            return gradeId;
        }

        public void setGradeId(String gradeId) {
            this.gradeId = gradeId;
        }

        public String getGradeName() {
            return gradeName;
        }

        public void setGradeName(String gradeName) {
            this.gradeName = gradeName;
        }

        public String getPhaseId() {
            return phaseId;
        }

        public void setPhaseId(String phaseId) {
            this.phaseId = phaseId;
        }

        public String getReadPattern() {
            return readPattern;
        }

        public void setReadPattern(String readPattern) {
            this.readPattern = readPattern;
        }

        public String getReadPatternName() {
            return readPatternName;
        }

        public void setReadPatternName(String readPatternName) {
            this.readPatternName = readPatternName;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getSchId() {
            return schId;
        }

        public void setSchId(String schId) {
            this.schId = schId;
        }

        public String getSchName() {
            return schName;
        }

        public void setSchName(String schName) {
            this.schName = schName;
        }

      /*  public List<TableJson> getTableJson() {
            return tableJson;
        }

        public void setTableJson(List<TableJson> tableJson) {
            this.tableJson = tableJson;
        }*/

        public String getTabletAreaIdSelect() {
            return tabletAreaIdSelect;
        }

        public void setTabletAreaIdSelect(String tabletAreaIdSelect) {
            this.tabletAreaIdSelect = tabletAreaIdSelect;
        }

        public String getTempCourseId() {
            return tempCourseId;
        }

        public void setTempCourseId(String tempCourseId) {
            this.tempCourseId = tempCourseId;
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

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMissing() {
            return missing;
        }

        public void setMissing(String missing) {
            this.missing = missing;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }
    }
    public static class CourseBean{
        private String courseId;
        private String courseName;
        private String id;
        private String totalScore;

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTotalScore() {
            return totalScore;
        }

        public void setTotalScore(String totalScore) {
            this.totalScore = totalScore;
        }
    }
    public static class TableJson{
        private String courseId;
        private String courseName;
        private String emCourseName;
        private String emCourseType;
        private String orderBy;
        private String templateId;

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getEmCourseName() {
            return emCourseName;
        }

        public void setEmCourseName(String emCourseName) {
            this.emCourseName = emCourseName;
        }

        public String getEmCourseType() {
            return emCourseType;
        }

        public void setEmCourseType(String emCourseType) {
            this.emCourseType = emCourseType;
        }

        public String getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(String orderBy) {
            this.orderBy = orderBy;
        }

        public String getTemplateId() {
            return templateId;
        }

        public void setTemplateId(String templateId) {
            this.templateId = templateId;
        }
    }

    @Override
    public String toString() {
        return "ExamSearchBean{" +
                "code='" + code + '\'' +
                ", current='" + current + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                ", pages='" + pages + '\'' +
                ", size='" + size + '\'' +
                ", total='" + total + '\'' +
                ", rows=" + rows +
                '}';
    }
}
