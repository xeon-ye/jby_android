package com.jkrm.education.bean.result;

import com.hzw.baselib.util.MyDateUtil;

import java.util.List;

/**
 * Created by hzw on 2019/5/30.
 */

public class QuestionResultBean {


    /**
     * id : 3
     * parentId :
     * content : 生石灰中往往含有杂志CACO2和SIO2，检验它是否存在着两种杂质的最好试剂是（ ）
     * answer : B
     * answerExplanation : <p>历史上任何一次生产力的飞跃，都极大地促进了人口的增长。农业革命使人口增长速度加快，工业革命使人口迅速增长，而“二战”后的新技术革命使人口增长最快。</p>
     * options : {"A":"水","B":"盐酸","C":"硫酸","D":"烧碱溶液"}
     * knowlPoints : [{"id":"3","name":"氧化还原反应"},{"id":"3","name":"另外一种反应"}]
     * testPoints : [{"id":"3","name":"氧化还原反应"}]
     * miniClassVideoId : 4
     * attr : [{"attrNameId":"3","attrName":"题目来源","attrValueId":"5","attrValueName":"步步高"},{"attrNameId":"2","attrName":"题目类型","attrValueId":"2","attrValueName":"选择题"}]
     */

    private String id;
    private String parentId;
    private String content;
    private String answer;
    private String answerExplanation;
//    private String score_basis; //判分逻辑, 接口暂未添加此字段
    private Object options;
    private String miniClassVideoId;
    private List<KnowlPointsBean> knowlPoints;
    private List<TestPointsBean> testPoints;
    private List<AttrBean> attr;
    private TypeBean type;

//    public String getScore_basis() {
//        return score_basis;
//    }
//
//    public void setScore_basis(String score_basis) {
//        this.score_basis = score_basis;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Object getOptions() {
        return options;
    }

    public void setOptions(Object options) {
        this.options = options;
    }

    public String getMiniClassVideoId() {
        return miniClassVideoId;
    }

    public void setMiniClassVideoId(String miniClassVideoId) {
        this.miniClassVideoId = miniClassVideoId;
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

    public List<AttrBean> getAttr() {
        return attr;
    }

    public void setAttr(List<AttrBean> attr) {
        this.attr = attr;
    }

    public TypeBean getType() {
        return type;
    }

    public void setType(TypeBean type) {
        this.type = type;
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

    public static class KnowlPointsBean {
        /**
         * id : 3
         * name : 氧化还原反应
         */

        private String id;
        private String name;

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
    }

    public static class TestPointsBean {
        /**
         * id : 3
         * name : 氧化还原反应
         */

        private String id;
        private String name;

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
    }

    public static class AttrBean {
        /**
         * attrNameId : 3
         * attrName : 题目来源
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
}
