package com.jkrm.education.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PeriodCourseBean {



        /**
         * types : [{"cascade":"","etcId":"","gOrderBy":"101","id":"","name":"","nameId":"247d5aeea8a4a16b1492dfa6b0dd6ee7","parentId":"","typeName":"学段","valueId":"","valueIdx":"0","valueName":""},{"cascade":"","etcId":"","gOrderBy":"102","id":"","name":"","nameId":"2c4ebb908f68cc2704e4167996fe4b0a","parentId":"","typeName":"学科","valueId":"","valueIdx":"0","valueName":""}]
         * values : {"078EB38A34564D878EDFE1C0014BFAA4":[{"cascade":"CE64A90D65C940FA959E8FAC36911477","etcId":"","gOrderBy":"102","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"fab6c26483dd26d2dbf4995ece9acdb3","valueIdx":"1","valueName":"语文"},{"cascade":"5CE814F064EC4AE4A968A89130851215","etcId":"","gOrderBy":"102","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"2abad4627d44c059a599dcd55b40869b","valueIdx":"2","valueName":"数学"},{"cascade":"CC7AA17A581E4741920B992B80D4D98C","etcId":"","gOrderBy":"102","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"eb96a394615f679a562ef6acdf104dbe","valueIdx":"3","valueName":"英语"},{"cascade":"EFD9F4278F4B414C857B15BEEE5349D5","etcId":"","gOrderBy":"102","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"e533d90cddd06a2d280aa20027d1c15a","valueIdx":"10","valueName":"道德与法治"},{"cascade":"C75924BCEDF44E62862C6DDCC6423E86","etcId":"","gOrderBy":"102","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"60e06b3ccd9ff6c4bc754e265c8cd6cb","valueIdx":"11","valueName":"历史与社会"},{"cascade":"451D4FB18AEC4C69A499D5A84BB679D5","etcId":"","gOrderBy":"102","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"a15c1d2ced38de900e5158753b111aa8","valueIdx":"12","valueName":"科学"}],"0DA6A72246084AED86039DE37C39A35C":[{"cascade":"F38FF757D84F479AAE1862BAE8072B38","etcId":"","gOrderBy":"102","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"fab6c26483dd26d2dbf4995ece9acdb3","valueIdx":"1","valueName":"语文"},{"cascade":"6A0C9B1032B44497B081F963D5EA145D","etcId":"","gOrderBy":"102","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"2abad4627d44c059a599dcd55b40869b","valueIdx":"2","valueName":"数学"},{"cascade":"09245FCAFF3E476F929E05BB5842E7CC","etcId":"","gOrderBy":"102","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"eb96a394615f679a562ef6acdf104dbe","valueIdx":"3","valueName":"英语"},{"cascade":"FF7D64E773CA4C6FBCA82BF8313B9CFF","etcId":"","gOrderBy":"102","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"cbbf06bb966eca984018ba4cb434afa2","valueIdx":"4","valueName":"物理"},{"cascade":"FDF4C92C0F32437E9799458567700624","etcId":"","gOrderBy":"102","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"eea0aa1958f69dd3490eb37eca400444","valueIdx":"5","valueName":"化学"},{"cascade":"7FECDE5B15B44DD781699CE69FFDB650","etcId":"","gOrderBy":"102","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"320629e098cc5004c69637ff70badc33","valueIdx":"6","valueName":"生物"},{"cascade":"BC69619227654E0D9633725FCB47E5B2","etcId":"","gOrderBy":"102","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"becb174d42603288cc2646c4dacc627d","valueIdx":"7","valueName":"政治"},{"cascade":"4A2AE552E7EA4E5CB08FDDD895869659","etcId":"","gOrderBy":"102","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"ea39d72ac45aadad204a65f7e92bd9eb","valueIdx":"8","valueName":"历史"},{"cascade":"2C0010CAD58D4469AFAD4B6D4DA270E8","etcId":"","gOrderBy":"102","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"da6ff5ecb34b914796a5a10fd6c69a6b","valueIdx":"9","valueName":"地理"}],"first":[{"cascade":"078EB38A34564D878EDFE1C0014BFAA4","etcId":"","gOrderBy":"101","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"c26c325c00c4fad2fb95658039876401","valueIdx":"1","valueName":"初中"},{"cascade":"0DA6A72246084AED86039DE37C39A35C","etcId":"","gOrderBy":"101","id":"","name":"","nameId":"","parentId":"","typeName":"","valueId":"becf6768719891af957cac5ffd1e6f0a","valueIdx":"2","valueName":"高中"}]}
         */

        private ValuesBean values;
        private List<TypesBean> types;

        public ValuesBean getValues() {
            return values;
        }

        public void setValues(ValuesBean values) {
            this.values = values;
        }

        public List<TypesBean> getTypes() {
            return types;
        }

        public void setTypes(List<TypesBean> types) {
            this.types = types;
        }

        public static class ValuesBean {
            @SerializedName("078EB38A34564D878EDFE1C0014BFAA4")
            private List<_$078EB38A34564D878EDFE1C0014BFAA4Bean> _$078EB38A34564D878EDFE1C0014BFAA4;
            @SerializedName("0DA6A72246084AED86039DE37C39A35C")
            private List<_$0DA6A72246084AED86039DE37C39A35CBean> _$0DA6A72246084AED86039DE37C39A35C;
            private List<FirstBean> first;

            public List<_$078EB38A34564D878EDFE1C0014BFAA4Bean> get_$078EB38A34564D878EDFE1C0014BFAA4() {
                return _$078EB38A34564D878EDFE1C0014BFAA4;
            }

            public void set_$078EB38A34564D878EDFE1C0014BFAA4(List<_$078EB38A34564D878EDFE1C0014BFAA4Bean> _$078EB38A34564D878EDFE1C0014BFAA4) {
                this._$078EB38A34564D878EDFE1C0014BFAA4 = _$078EB38A34564D878EDFE1C0014BFAA4;
            }

            public List<_$0DA6A72246084AED86039DE37C39A35CBean> get_$0DA6A72246084AED86039DE37C39A35C() {
                return _$0DA6A72246084AED86039DE37C39A35C;
            }

            public void set_$0DA6A72246084AED86039DE37C39A35C(List<_$0DA6A72246084AED86039DE37C39A35CBean> _$0DA6A72246084AED86039DE37C39A35C) {
                this._$0DA6A72246084AED86039DE37C39A35C = _$0DA6A72246084AED86039DE37C39A35C;
            }

            public List<FirstBean> getFirst() {
                return first;
            }

            public void setFirst(List<FirstBean> first) {
                this.first = first;
            }

            public static class _$078EB38A34564D878EDFE1C0014BFAA4Bean {
                /**
                 * cascade : CE64A90D65C940FA959E8FAC36911477
                 * etcId :
                 * gOrderBy : 102
                 * id :
                 * name :
                 * nameId :
                 * parentId :
                 * typeName :
                 * valueId : fab6c26483dd26d2dbf4995ece9acdb3
                 * valueIdx : 1
                 * valueName : 语文
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

            public static class _$0DA6A72246084AED86039DE37C39A35CBean {
                /**
                 * cascade : F38FF757D84F479AAE1862BAE8072B38
                 * etcId :
                 * gOrderBy : 102
                 * id :
                 * name :
                 * nameId :
                 * parentId :
                 * typeName :
                 * valueId : fab6c26483dd26d2dbf4995ece9acdb3
                 * valueIdx : 1
                 * valueName : 语文
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

            public static class FirstBean {
                /**
                 * cascade : 078EB38A34564D878EDFE1C0014BFAA4
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
                private boolean isChecked;

                public boolean isChecked() {
                    return isChecked;
                }

                public void setChecked(boolean checked) {
                    isChecked = checked;
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
}
