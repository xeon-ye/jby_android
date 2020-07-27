package com.jkrm.education.bean.result;

import com.hzw.baselib.util.MyDateUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hzw on 2019/5/31.
 */

public class SimilarResultBean implements Serializable{


    /**
     * answer : (1)支出　(2)下降　(3)亏损　(4)运出　(5)浪费
     * answerExplanation :
     * content : <img src="https://jbyadmin.oss-cn-beijing.aliyuncs.com/jby_files/questions/302-9%E9%A2%98.png" />
     * createBy :
     * createTime : 2019-05-31T23:37:03+0800
     * deleteStatus : 0
     * id : prodquestion009
     * knowlPoints : [{"catalogName":"相反意义的量","createBy":"","createTime":"2019-05-31T09:46:32+0800","deleteStatus":"0","id":"5","orderBy":"2","parentId":"","pcvId":"1","remark":"","status":"2","updateBy":"","updateTime":"2019-05-31T10:53:14+0800"}]
     * optionA :
     * optionB :
     * optionC :
     * optionD :
     * optionE :
     * optionF :
     * optionG :
     * parentId :
     * questionVideoId :
     * remark :
     * source : 金榜苑
     * testPoints : [{"catalogName":"用正数负数表示具有相反意义的量","createBy":"","createTime":"2019-05-31T09:46:32+0800","deleteStatus":"0","id":"6","orderBy":"1","parentId":"5","pcvId":"1","remark":"","status":"2","updateBy":"","updateTime":"2019-05-31T10:50:33+0800"}]
     * typeName : 填空题
     * updateBy :
     */

    private String answer;
    private String answerExplanation;
    private String content;
    private String createBy;
    private String createTime;
    private String deleteStatus;
    private String id;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String optionE;
    private String optionF;
    private String optionG;
    private String parentId;
    private String questionVideoId;
    private String remark;
    private String source;
    private String typeName;
    private String updateBy;
    private List<KnowlPointsBean> knowlPoints;
    private List<TestPointsBean> testPoints;
    private TypeBean type;
    private Object options;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getQuestionVideoId() {
        return questionVideoId;
    }

    public void setQuestionVideoId(String questionVideoId) {
        this.questionVideoId = questionVideoId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public List<KnowlPointsBean> getKnowlPoints() {
        return knowlPoints;
    }

    public void setKnowlPoints(List<KnowlPointsBean> knowlPoints) {
        this.knowlPoints = knowlPoints;
    }

    public List<TestPointsBean> getTestPoints() {
        return testPoints;
    }

    public void setTestPoints(List<TestPointsBean> testPoints) {
        this.testPoints = testPoints;
    }

    public TypeBean getType() {
        return type;
    }

    public void setType(TypeBean type) {
        this.type = type;
    }

    public Object getOptions() {
        return options;
    }

    public void setOptions(Object options) {
        this.options = options;
    }

    public static class KnowlPointsBean implements Serializable{
        /**
         * catalogName : 相反意义的量
         * createBy :
         * createTime : 2019-05-31T09:46:32+0800
         * deleteStatus : 0
         * id : 5
         * orderBy : 2
         * parentId :
         * pcvId : 1
         * remark :
         * status : 2
         * updateBy :
         * updateTime : 2019-05-31T10:53:14+0800
         */

        private String catalogName;
        private String createBy;
        private String createTime;
        private String deleteStatus;
        private String id;
        private String orderBy;
        private String parentId;
        private String pcvId;
        private String remark;
        private String status;
        private String updateBy;
        private String updateTime;

        public String getCatalogName() {
            return catalogName;
        }

        public void setCatalogName(String catalogName) {
            this.catalogName = catalogName;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(String orderBy) {
            this.orderBy = orderBy;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getPcvId() {
            return pcvId;
        }

        public void setPcvId(String pcvId) {
            this.pcvId = pcvId;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

    public static class TestPointsBean  implements Serializable {
        /**
         * catalogName : 用正数负数表示具有相反意义的量
         * createBy :
         * createTime : 2019-05-31T09:46:32+0800
         * deleteStatus : 0
         * id : 6
         * orderBy : 1
         * parentId : 5
         * pcvId : 1
         * remark :
         * status : 2
         * updateBy :
         * updateTime : 2019-05-31T10:50:33+0800
         */

        private String catalogName;
        private String createBy;
        private String createTime;
        private String deleteStatus;
        private String id;
        private String orderBy;
        private String parentId;
        private String pcvId;
        private String remark;
        private String status;
        private String updateBy;
        private String updateTime;

        public String getCatalogName() {
            return catalogName;
        }

        public void setCatalogName(String catalogName) {
            this.catalogName = catalogName;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(String orderBy) {
            this.orderBy = orderBy;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getPcvId() {
            return pcvId;
        }

        public void setPcvId(String pcvId) {
            this.pcvId = pcvId;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

    public static class TypeBean {

        private String id;
        private String isOption;
        private String name;

        //------------------------------------------自定义start-------------------------------------------------
        /**
         * 是否是选择题
         * @return
         */
        public boolean isChoiceQuestion() {
            return MyDateUtil.isChoiceQuestion(name,isOption);
        }

        //------------------------------------------自定义end------------------------------------------------

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
    }
}
