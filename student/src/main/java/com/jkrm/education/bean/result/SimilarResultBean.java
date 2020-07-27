package com.jkrm.education.bean.result;

import com.hzw.baselib.util.AwDataUtil;
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
    private String isOption;
    private String groupQuestion;// 是否是组题 1 不是  2 是
    private List<SubQuestionsBean> subQuestions;
    private boolean isAnswer=false;

    public boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(boolean answer) {
        isAnswer = answer;
    }

    public List<SubQuestionsBean> getSubQuestions() {
        return subQuestions;
    }

    public void setSubQuestions(List<SubQuestionsBean> subQuestions) {
        this.subQuestions = subQuestions;
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

    /**
     * 是否是组题
     * @return
     */
    public boolean isGroupQuestion(){
        /*if("2".equals(groupQuestion)||"2".equals(type.getIsOption())|| !AwDataUtil.isEmpty(subQuestions)){
            return true;
        }*/
        return (!AwDataUtil.isEmpty(subQuestions));
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
    public static class SubQuestionsBean  implements Serializable{
        /**
         * answer : <span style="font-family: 'Times New Roman';">A</span>
         * answerExplanation : <p style="font-family: 'Times New Roman';">由此处语境以及空后句中的&ldquo;a responsibility, a source of pressure&rdquo;可知A项中的an honour与此呼应，其中的not only与空后句中的more of相对应，故选A项。
         * catalogString :
         * cname : 管理员
         * content :
         * createBy : admin_001
         * createTime : 2020-05-08 11:38:09
         * difficulty : 基础题
         * favourite :
         * groupQuestion : 1
         * id : 203134
         * knowlPoints : []
         * maxScore :
         * options : {"optionA":"<span style=\"font-family: 'Times New Roman';\"> 选项A \t<\/span>","optionB":"<span style=\"font-family: 'Times New Roman';\"> 选项B \t<\/span>","optionC":"<span style=\"font-family: 'Times New Roman';\"> 选项C \t<\/span>","optionD":"<span style=\"font-family: 'Times New Roman';\"> 选项D \t<\/span>","optionE":"<span style=\"font-family: 'Times New Roman';\"> 选项E \t<\/span>","optionF":"<span style=\"font-family: 'Times New Roman';\"> 选项F \t<\/span>","optionG":"<span style=\"font-family: 'Times New Roman';\"> 选项G<\/span>"}
         * orderBy : 1
         * parentId : 203133
         * practice :
         * questionNum :
         * questionVideo :
         * similar :
         * source :
         * sourceNote :
         * testPoints : []
         * totalId :
         * type : {"id":"c5c8de18b842cafc6a1c34f563eeb572","isOption":"2","name":"七选五","totalId":""}
         * uname : 管理员
         * updateBy : admin_001
         * updateTime : 2020-05-08 11:38:09
         * usedTimes :
         */

        private String answer;
        private String answerExplanation;
        private String catalogString;
        private String cname;
        private String content;
        private String createBy;
        private String createTime;
        private String difficulty;
        private String favourite;
        private String groupQuestion;
        private String id;
        private String maxScore;
        private Object options;
        private String orderBy;
        private String parentId;
        private String practice;
        private String questionNum;
        private String questionVideo;
        private String similar;
        private String source;
        private String sourceNote;
        private String totalId;
        private SubQuestionsBean.TypeBeanX type;
        private String uname;
        private String updateBy;
        private String updateTime;
        private String usedTimes;
        private List<?> knowlPoints;
        private List<?> testPoints;
        private boolean isAnswer;

        public boolean isAnswer() {
            return isAnswer;
        }

        public void setAnswer(boolean answer) {
            isAnswer = answer;
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

        public String getCatalogString() {
            return catalogString;
        }

        public void setCatalogString(String catalogString) {
            this.catalogString = catalogString;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
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

        public String getDifficulty() {
            return difficulty;
        }

        public void setDifficulty(String difficulty) {
            this.difficulty = difficulty;
        }

        public String getFavourite() {
            return favourite;
        }

        public void setFavourite(String favourite) {
            this.favourite = favourite;
        }

        public String getGroupQuestion() {
            return groupQuestion;
        }

        public void setGroupQuestion(String groupQuestion) {
            this.groupQuestion = groupQuestion;
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

        public Object getOptions() {
            return options;
        }

        public void setOptions(Object options) {
            this.options = options;
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

        public String getPractice() {
            return practice;
        }

        public void setPractice(String practice) {
            this.practice = practice;
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

        public String getSimilar() {
            return similar;
        }

        public void setSimilar(String similar) {
            this.similar = similar;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getSourceNote() {
            return sourceNote;
        }

        public void setSourceNote(String sourceNote) {
            this.sourceNote = sourceNote;
        }

        public String getTotalId() {
            return totalId;
        }

        public void setTotalId(String totalId) {
            this.totalId = totalId;
        }

        public SubQuestionsBean.TypeBeanX getType() {
            return type;
        }

        public void setType(SubQuestionsBean.TypeBeanX type) {
            this.type = type;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
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

        public String getUsedTimes() {
            return usedTimes;
        }

        public void setUsedTimes(String usedTimes) {
            this.usedTimes = usedTimes;
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

        public static class OptionsBeanX {
            /**
             * optionA : <span style="font-family: 'Times New Roman';"> 选项A 	</span>
             * optionB : <span style="font-family: 'Times New Roman';"> 选项B 	</span>
             * optionC : <span style="font-family: 'Times New Roman';"> 选项C 	</span>
             * optionD : <span style="font-family: 'Times New Roman';"> 选项D 	</span>
             * optionE : <span style="font-family: 'Times New Roman';"> 选项E 	</span>
             * optionF : <span style="font-family: 'Times New Roman';"> 选项F 	</span>
             * optionG : <span style="font-family: 'Times New Roman';"> 选项G</span>
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

        public static class TypeBeanX implements Serializable{
            /**
             * id : c5c8de18b842cafc6a1c34f563eeb572
             * isOption : 2
             * name : 七选五
             * totalId :
             */

            private String id;
            private String isOption;
            private String name;
            private String totalId;

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

            public String getTotalId() {
                return totalId;
            }

            public void setTotalId(String totalId) {
                this.totalId = totalId;
            }
        }
    }

    public static class TypeBean implements Serializable{

        private String id;
        private String isOption;
        private String name;
        private String totalId;


        public String getTotalid() {
            return totalId;
        }

        public void setTotalid(String totalid) {
            this.totalId = totalid;
        }

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

    @Override
    public String toString() {
        return "SimilarResultBean{" +
                "answer='" + answer + '\'' +
                ", answerExplanation='" + answerExplanation + '\'' +
                ", content='" + content + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime='" + createTime + '\'' +
                ", deleteStatus='" + deleteStatus + '\'' +
                ", id='" + id + '\'' +
                ", optionA='" + optionA + '\'' +
                ", optionB='" + optionB + '\'' +
                ", optionC='" + optionC + '\'' +
                ", optionD='" + optionD + '\'' +
                ", optionE='" + optionE + '\'' +
                ", optionF='" + optionF + '\'' +
                ", optionG='" + optionG + '\'' +
                ", parentId='" + parentId + '\'' +
                ", questionVideoId='" + questionVideoId + '\'' +
                ", remark='" + remark + '\'' +
                ", source='" + source + '\'' +
                ", typeName='" + typeName + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", knowlPoints=" + knowlPoints +
                ", testPoints=" + testPoints +
                ", type=" + type +
                ", options=" + options +
                ", isOption='" + isOption + '\'' +
                ", groupQuestion='" + groupQuestion + '\'' +
                ", subQuestions=" + subQuestions +
                ", isAnswer=" + isAnswer +
                '}';
    }
}
