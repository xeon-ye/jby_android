package com.jkrm.education.bean.result;

import java.util.HashMap;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/13 11:17
 */

public class CourseAttrBean {

    public HashMap<String, List<Value>> getValues() {
        return values;
    }

    public void setValues(HashMap<String, List<Value>> values) {
        this.values = values;
    }

    /**
     * types : [{"cascade":"","etcId":"","gOrderBy":"101","id":"","name":"","nameId":"247d5aeea8a4a16b1492dfa6b0dd6ee7","parentId":"","typeName":"学段","valueId":"","valueIdx":"0","valueName":""},{"cascade":"","etcId":"","gOrderBy":"102","id":"","name":"","nameId":"98a21d2826ffcb853c72129044aa3c3c","parentId":"","typeName":"年级","valueId":"","valueIdx":"0","valueName":""},{"cascade":"","etcId":"","gOrderBy":"103","id":"","name":"","nameId":"2c4ebb908f68cc2704e4167996fe4b0a","parentId":"","typeName":"学科","valueId":"","valueIdx":"0","valueName":""},{"cascade":"","etcId":"","gOrderBy":"104","id":"","name":"","nameId":"0b87afacc8b9fdf71dc4f33f869463a8","parentId":"","typeName":"版本","valueId":"","valueIdx":"0","valueName":""},{"cascade":"","etcId":"","gOrderBy":"105","id":"","name":"","nameId":"a701782656fd0fa983209235df34e8f7","parentId":"","typeName":"模块","valueId":"","valueIdx":"0","valueName":""}]
     * values : {"14F28087A3D14BADAED3192698269E0F":[{"cascade":"E0E3E3FEF5644F84A328F540F62C31C1","etcId":"","gOrderBy":"102","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"6d0005e737f6890a434ac68dd7ae9a51","valueIdx":"4","valueName":"七年级"}],"2A086ACA707D44EBA0C2FC8C942C176F":[{"cascade":"8564010F0239436DB2D39A26CD6ED7F9","etcId":"","gOrderBy":"104","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"169a04116c0f3296c127d366960db5b6","valueIdx":"9","valueName":"浙教版"}],"2E7C6F69413842AB87E8DC2D8D80CAA6":[{"cascade":"442781738F4D4C6895BB18D54D237936","etcId":"","gOrderBy":"102","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"e710f6ca36537899f3155a08d4fbe604","valueIdx":"1","valueName":"高一"}],"442781738F4D4C6895BB18D54D237936":[{"cascade":"2A086ACA707D44EBA0C2FC8C942C176F","etcId":"","gOrderBy":"103","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"fab6c26483dd26d2dbf4995ece9acdb3","valueIdx":"1","valueName":"语文"},{"cascade":"8F33313DA5FF44E296D60F70A2731FEF","etcId":"","gOrderBy":"103","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"2abad4627d44c059a599dcd55b40869b","valueIdx":"2","valueName":"数学"},{"cascade":"D123A0FB9A55423FB7A624A0A914AD78","etcId":"","gOrderBy":"103","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"eb96a394615f679a562ef6acdf104dbe","valueIdx":"3","valueName":"英语"}],"8F33313DA5FF44E296D60F70A2731FEF":[{"cascade":"FB0361583F68458BB0961B9B6E7E6B61","etcId":"","gOrderBy":"104","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"9e1e0b25fd34519f851f613eb1cbad83","valueIdx":"3","valueName":"人教A版"}],"B1A32D626CC047989380F5F4E74F10E8":[{"cascade":"6080ED2716344723874B38D62B5F0590","etcId":"","gOrderBy":"105","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"ed71eb90adcd78ff57949fbf3152e4d2","valueIdx":"3","valueName":"必修1"}],"D123A0FB9A55423FB7A624A0A914AD78":[{"cascade":"B1A32D626CC047989380F5F4E74F10E8","etcId":"","gOrderBy":"104","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"9b934e7cfbcf0de1f4eed293c7c547b7","valueIdx":"1","valueName":"人教版"}],"E0E3E3FEF5644F84A328F540F62C31C1":[{"cascade":"F4B862B4B5D749079C894B935C50C307","etcId":"","gOrderBy":"103","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"2abad4627d44c059a599dcd55b40869b","valueIdx":"2","valueName":"数学"}],"F4B862B4B5D749079C894B935C50C307":[{"cascade":"D754865491624D1687B3362CBC3C22CA","etcId":"","gOrderBy":"104","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"9b934e7cfbcf0de1f4eed293c7c547b7","valueIdx":"1","valueName":"人教版"}],"FB0361583F68458BB0961B9B6E7E6B61":[{"cascade":"B86699AD91A948BEBFEED6E1721537C6","etcId":"","gOrderBy":"105","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"ed71eb90adcd78ff57949fbf3152e4d2","valueIdx":"3","valueName":"必修1"}],"first":[{"cascade":"14F28087A3D14BADAED3192698269E0F","etcId":"","gOrderBy":"101","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"c26c325c00c4fad2fb95658039876401","valueIdx":"1","valueName":"初中"},{"cascade":"2E7C6F69413842AB87E8DC2D8D80CAA6","etcId":"","gOrderBy":"101","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"becf6768719891af957cac5ffd1e6f0a","valueIdx":"2","valueName":"高中"}]}
     */

    private HashMap<String, List<Value>> values;
    private List<TypesBean> types;

    public List<TypesBean> getTypes() {
        return types;
    }

    public void setTypes(List<TypesBean> types) {
        this.types = types;
    }


    public static class TypesBean {
        /**
         * cascade :
         * etcId :
         * gOrderBy : 101
         * id :
         * name :
         * nameId : 247d5aeea8a4a16b1492dfa6b0dd6ee7
         * parentId :
         * typeName : 学段
         * valueId :
         * valueIdx : 0
         * valueName :
         */

        private String cascade;
        private String etcId;
        private String gOrderBy;
        private String id;
        private String name;
        private String nameId;
        private String parentId;
        private String typeName;
        private String valueId;
        private String valueIdx;
        private String valueName;

        public String getCascade() {
            return cascade;
        }

        public void setCascade(String cascade) {
            this.cascade = cascade;
        }

        public String getEtcId() {
            return etcId;
        }

        public void setEtcId(String etcId) {
            this.etcId = etcId;
        }

        public String getGOrderBy() {
            return gOrderBy;
        }

        public void setGOrderBy(String gOrderBy) {
            this.gOrderBy = gOrderBy;
        }

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

        public String getNameId() {
            return nameId;
        }

        public void setNameId(String nameId) {
            this.nameId = nameId;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getValueId() {
            return valueId;
        }

        public void setValueId(String valueId) {
            this.valueId = valueId;
        }

        public String getValueIdx() {
            return valueIdx;
        }

        public void setValueIdx(String valueIdx) {
            this.valueIdx = valueIdx;
        }

        public String getValueName() {
            return valueName;
        }

        public void setValueName(String valueName) {
            this.valueName = valueName;
        }
    }

    public class Value {

        /**
         * cascade : 14F28087A3D14BADAED3192698269E0F
         * etcId :
         * gOrderBy : 101
         * id :
         * name :
         * nameId :
         * parentId :
         * typeName :
         * valueId : c26c325c00c4fad2fb95658039876401
         * valueIdx : 1
         * valueName : 初中
         */

        private String cascade;
        private String etcId;
        private String gOrderBy;
        private String id;
        private String name;
        private String nameId;
        private String parentId;
        private String typeName;
        private String valueId;
        private String valueIdx;
        private String valueName;
        private boolean checked;

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public String getCascade() {
            return cascade;
        }

        public void setCascade(String cascade) {
            this.cascade = cascade;
        }

        public String getEtcId() {
            return etcId;
        }

        public void setEtcId(String etcId) {
            this.etcId = etcId;
        }

        public String getGOrderBy() {
            return gOrderBy;
        }

        public void setGOrderBy(String gOrderBy) {
            this.gOrderBy = gOrderBy;
        }

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

        public String getNameId() {
            return nameId;
        }

        public void setNameId(String nameId) {
            this.nameId = nameId;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getValueId() {
            return valueId;
        }

        public void setValueId(String valueId) {
            this.valueId = valueId;
        }

        public String getValueIdx() {
            return valueIdx;
        }

        public void setValueIdx(String valueIdx) {
            this.valueIdx = valueIdx;
        }

        public String getValueName() {
            return valueName;
        }

        public void setValueName(String valueName) {
            this.valueName = valueName;
        }
    }
}
