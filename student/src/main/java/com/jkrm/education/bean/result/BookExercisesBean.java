package com.jkrm.education.bean.result;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 图书习题
 * @CreateDate: 2020/2/21 11:01
 */

public class BookExercisesBean {

    private List<Types> types;
    private HashMap<String,List<Value>>  values;

    public HashMap<String, List<Value>> getValues() {
        return values;
    }

    public void setValues(HashMap<String, List<Value>> values) {
        this.values = values;
    }

    public void setTypes(List<Types> types) {
        this.types = types;
    }
    public List<Types> getTypes() {
        return types;
    }





    public class Value{
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

        public void setCascade(String cascade) {
            this.cascade = cascade;
        }
        public String getCascade() {
            return cascade;
        }

        public void setEtcId(String etcId) {
            this.etcId = etcId;
        }
        public String getEtcId() {
            return etcId;
        }

        public void setGOrderBy(String gOrderBy) {
            this.gOrderBy = gOrderBy;
        }
        public String getGOrderBy() {
            return gOrderBy;
        }

        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setNameId(String nameId) {
            this.nameId = nameId;
        }
        public String getNameId() {
            return nameId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }
        public String getParentId() {
            return parentId;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }
        public String getTypeName() {
            return typeName;
        }

        public void setValueId(String valueId) {
            this.valueId = valueId;
        }
        public String getValueId() {
            return valueId;
        }

        public void setValueIdx(String valueIdx) {
            this.valueIdx = valueIdx;
        }
        public String getValueIdx() {
            return valueIdx;
        }

        public void setValueName(String valueName) {
            this.valueName = valueName;
        }
        public String getValueName() {
            return valueName;
        }
    }


    public class Types {

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
        public void setCascade(String cascade) {
            this.cascade = cascade;
        }
        public String getCascade() {
            return cascade;
        }

        public void setEtcId(String etcId) {
            this.etcId = etcId;
        }
        public String getEtcId() {
            return etcId;
        }

        public void setGOrderBy(String gOrderBy) {
            this.gOrderBy = gOrderBy;
        }
        public String getGOrderBy() {
            return gOrderBy;
        }

        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setNameId(String nameId) {
            this.nameId = nameId;
        }
        public String getNameId() {
            return nameId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }
        public String getParentId() {
            return parentId;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }
        public String getTypeName() {
            return typeName;
        }

        public void setValueId(String valueId) {
            this.valueId = valueId;
        }
        public String getValueId() {
            return valueId;
        }

        public void setValueIdx(String valueIdx) {
            this.valueIdx = valueIdx;
        }
        public String getValueIdx() {
            return valueIdx;
        }

        public void setValueName(String valueName) {
            this.valueName = valueName;
        }
        public String getValueName() {
            return valueName;
        }

    }
}
