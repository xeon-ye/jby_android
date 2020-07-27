package com.jkrm.education.bean.result;

import java.io.Serializable;
import java.util.List;

/**
 * 学生作业列表rows bean
 * Created by hzw on 2019/5/30.
 */

public class RowsHomeworkBean implements Serializable {


    /**
     * id : 4
     * schoolId : 4
     * bookId : 3
     * name : 语文单元测验二
     * createTime : 2019-04-20T13:06:44+08:00
     * maxScore : 100
     * questionCount : 10
     * classes : [{"id":1,"name":"高一1班","population":40}]
     * attr : [{"attrNameId":3,"attrName":"图书系列","attrValueId":5,"attrValueName":"步步高"}]
     * statistics : [{"classId":1,"submitted":35,"missing":5,"progress":"20/35","finishTime":"","averageGrade":""}]
     */

    private String id;
    private String schoolId;
    private String bookId;
    private String name;
    private String createTime;
    private String maxScore;
    private String questionCount;
    private ClassesBean classes;
    private List<AttrBean> attr;
    private StatisticsBean statistics;
    private String studCode;
    private String effect;//1处理完毕, 0处理中

    //自定义开始...........................................................

    public boolean isMarkFinish() {
        if(statistics != null && statistics.getProgress() != null && statistics.getSubmitted() != null) {
            return statistics.progress.equals(statistics.submitted);
        }
        return false;
    }

    public boolean isHandle() {
        return "1".equals(effect);
    }

    //自定义结束.......................................................

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(String maxScore) {
        this.maxScore = maxScore;
    }

    public String getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(String questionCount) {
        this.questionCount = questionCount;
    }


    public List<AttrBean> getAttr() {
        return attr;
    }

    public void setAttr(List<AttrBean> attr) {
        this.attr = attr;
    }

    public ClassesBean getClasses() {
        return classes;
    }

    public void setClasses(ClassesBean classes) {
        this.classes = classes;
    }

    public StatisticsBean getStatistics() {
        return statistics;
    }

    public void setStatistics(StatisticsBean statistics) {
        this.statistics = statistics;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public static class ClassesBean implements Serializable {
        /**
         * id : 1
         * name : 高一1班
         * population : 40
         */

        private String id;
        private String name;
        private String population;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPopulation() {
            return population;
        }

        public void setPopulation(String population) {
            this.population = population;
        }
    }

    public static class AttrBean implements Serializable  {
        /**
         * attrNameId : 3
         * attrName : 图书系列
         * attrValueId : 5
         * attrValueName : 步步高
         */

        private String attrNameId;
        private String attrName;
        private String attrValueId;
        private String attrValueName;

        public String getAttrNameId() {
            return attrNameId;
        }

        public void setAttrNameId(String attrNameId) {
            this.attrNameId = attrNameId;
        }

        public String getAttrName() {
            return attrName;
        }

        public void setAttrName(String attrName) {
            this.attrName = attrName;
        }

        public String getAttrValueId() {
            return attrValueId;
        }

        public void setAttrValueId(String attrValueId) {
            this.attrValueId = attrValueId;
        }

        public String getAttrValueName() {
            return attrValueName;
        }

        public void setAttrValueName(String attrValueName) {
            this.attrValueName = attrValueName;
        }
    }

    public static class StatisticsBean implements Serializable  {
        /**
         * classId : 1
         * submitted : 35
         * missing : 5
         * progress : 20/35
         * finishTime :
         * averageGrade :
         */

        private String classId;
        private String submitted;
        private String missing;
        private String progress;
        private String finishTime;
        private String averageGrade;

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getSubmitted() {
            return submitted;
        }

        public void setSubmitted(String submitted) {
            this.submitted = submitted;
        }

        public String getMissing() {
            return missing;
        }

        public void setMissing(String missing) {
            this.missing = missing;
        }

        public String getProgress() {
            return progress;
        }

        public void setProgress(String progress) {
            this.progress = progress;
        }

        public String getFinishTime() {
            return finishTime;
        }

        public void setFinishTime(String finishTime) {
            this.finishTime = finishTime;
        }

        public String getAverageGrade() {
            return averageGrade;
        }

        public void setAverageGrade(String averageGrade) {
            this.averageGrade = averageGrade;
        }
    }
}
