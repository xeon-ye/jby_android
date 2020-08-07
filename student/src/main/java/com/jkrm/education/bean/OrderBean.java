package com.jkrm.education.bean;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/8/7 17:58
 */

public class OrderBean {

    /**
     * code : 200
     * current : 1
     * data : null
     * msg :
     * pages : 1
     * rows : [{"client":"IOS","clientName":"未知","createTime":"2020-08-06T16:40:52+0800","current":"1","detaiList":[{"catalogId":"","catalogName":"","comboNum":"0","courseId":"","courseName":"","goodsId":"com.newb.student198","goodsName":"新教育币198","goodsNum":"1","goodsPrice":"198","goodsUrl":"","gradeId":"","gradeName":"","module":"chongzhi","moduleName":"未知","orderId":"1000000570","pcvId":"","validTime":1628239252000}],"encrypt":"","frontMsg":"","goodsNum":"1","goodsPrice":"198","gradeId":"","id":"1000000570","module":"","msg":"","orderName":"新教育币198","pcvId":"","roleId":"role_student_1c88b125c8b75eb4fbd46aa64b0b3aa5","schoolId":"1c88b125c8b75eb4fbd46aa64b0b3aa5","size":"10","step":"1","stepName":"","type":"","typeName":"","userId":"cdde869002d48cac4fd48bc9d9249743","userName":"","validTime":1596789652000},{"client":"IOS","clientName":"未知","createTime":"2020-08-06T16:38:46+0800","current":"1","detaiList":[{"catalogId":"","catalogName":"","comboNum":"0","courseId":"","courseName":"","goodsId":"com.newb.student198","goodsName":"新教育币198","goodsNum":"1","goodsPrice":"198","goodsUrl":"","gradeId":"","gradeName":"","module":"chongzhi","moduleName":"未知","orderId":"1000000569","pcvId":"","validTime":1628239126000}],"encrypt":"","frontMsg":"","goodsNum":"1","goodsPrice":"198","gradeId":"","id":"1000000569","module":"","msg":"","orderName":"新教育币198","pcvId":"","roleId":"role_student_1c88b125c8b75eb4fbd46aa64b0b3aa5","schoolId":"1c88b125c8b75eb4fbd46aa64b0b3aa5","size":"10","step":"1","stepName":"","type":"","typeName":"","userId":"cdde869002d48cac4fd48bc9d9249743","userName":"","validTime":1596789526000},{"client":"IOS","clientName":"未知","createTime":"2020-08-06T16:37:35+0800","current":"1","detaiList":[{"catalogId":"","catalogName":"","comboNum":"0","courseId":"","courseName":"","goodsId":"com.newb.student98","goodsName":"新教育币98","goodsNum":"1","goodsPrice":"98","goodsUrl":"","gradeId":"","gradeName":"","module":"chongzhi","moduleName":"未知","orderId":"1000000568","pcvId":"","validTime":1628239055000}],"encrypt":"","frontMsg":"","goodsNum":"1","goodsPrice":"98","gradeId":"","id":"1000000568","module":"","msg":"","orderName":"新教育币98","pcvId":"","roleId":"role_student_1c88b125c8b75eb4fbd46aa64b0b3aa5","schoolId":"1c88b125c8b75eb4fbd46aa64b0b3aa5","size":"10","step":"1","stepName":"","type":"","typeName":"","userId":"cdde869002d48cac4fd48bc9d9249743","userName":"","validTime":1596789455000},{"client":"IOS","clientName":"未知","createTime":"2020-08-06T15:39:27+0800","current":"1","detaiList":[{"catalogId":"","catalogName":"","comboNum":"2","courseId":"","courseName":"","goodsId":"10770","goodsName":"语文推荐课程","goodsNum":"1","goodsPrice":"0.0","goodsUrl":"https://img.xinjiaoyu.com/jby_files/test/videos/58AC87CC5EB24029866E315797ECD7D8.jpg","gradeId":"","gradeName":"","module":"videos","moduleName":"微课视频","orderId":"1000000565","pcvId":"","validTime":1628235567000}],"encrypt":"","frontMsg":"","goodsNum":"1","goodsPrice":"0.0","gradeId":"","id":"1000000565","module":"","msg":"","orderName":"微课视频","orderType":"1","payTime":"2020-08-06T15:39:27+0800","pcvId":"","roleId":"role_student_1c88b125c8b75eb4fbd46aa64b0b3aa5","schoolId":"1c88b125c8b75eb4fbd46aa64b0b3aa5","size":"10","step":"2","stepName":"","type":"card","typeName":"","userId":"cdde869002d48cac4fd48bc9d9249743","userName":"","validTime":1596785967000}]
     * size : 20
     * total : 4
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
         * client : IOS
         * clientName : 未知
         * createTime : 2020-08-06T16:40:52+0800
         * current : 1
         * detaiList : [{"catalogId":"","catalogName":"","comboNum":"0","courseId":"","courseName":"","goodsId":"com.newb.student198","goodsName":"新教育币198","goodsNum":"1","goodsPrice":"198","goodsUrl":"","gradeId":"","gradeName":"","module":"chongzhi","moduleName":"未知","orderId":"1000000570","pcvId":"","validTime":1628239252000}]
         * encrypt :
         * frontMsg :
         * goodsNum : 1
         * goodsPrice : 198
         * gradeId :
         * id : 1000000570
         * module :
         * msg :
         * orderName : 新教育币198
         * pcvId :
         * roleId : role_student_1c88b125c8b75eb4fbd46aa64b0b3aa5
         * schoolId : 1c88b125c8b75eb4fbd46aa64b0b3aa5
         * size : 10
         * step : 1
         * stepName :
         * type :
         * typeName :
         * userId : cdde869002d48cac4fd48bc9d9249743
         * userName :
         * validTime : 1596789652000
         * orderType : 1
         * payTime : 2020-08-06T15:39:27+0800
         */

        private String client;
        private String clientName;
        private String createTime;
        private String current;
        private String encrypt;
        private String frontMsg;
        private String goodsNum;
        private String goodsPrice;
        private String gradeId;
        private String id;
        private String module;
        private String msg;
        private String orderName;
        private String pcvId;
        private String roleId;
        private String schoolId;
        private String size;
        private String step;
        private String stepName;
        private String type;
        private String typeName;
        private String userId;
        private String userName;
        private long validTime;
        private String orderType;
        private String payTime;
        private List<DetaiListBean> detaiList;

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
        }

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCurrent() {
            return current;
        }

        public void setCurrent(String current) {
            this.current = current;
        }

        public String getEncrypt() {
            return encrypt;
        }

        public void setEncrypt(String encrypt) {
            this.encrypt = encrypt;
        }

        public String getFrontMsg() {
            return frontMsg;
        }

        public void setFrontMsg(String frontMsg) {
            this.frontMsg = frontMsg;
        }

        public String getGoodsNum() {
            return goodsNum;
        }

        public void setGoodsNum(String goodsNum) {
            this.goodsNum = goodsNum;
        }

        public String getGoodsPrice() {
            return goodsPrice;
        }

        public void setGoodsPrice(String goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public String getGradeId() {
            return gradeId;
        }

        public void setGradeId(String gradeId) {
            this.gradeId = gradeId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getOrderName() {
            return orderName;
        }

        public void setOrderName(String orderName) {
            this.orderName = orderName;
        }

        public String getPcvId() {
            return pcvId;
        }

        public void setPcvId(String pcvId) {
            this.pcvId = pcvId;
        }

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public String getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(String schoolId) {
            this.schoolId = schoolId;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getStep() {
            return step;
        }

        public void setStep(String step) {
            this.step = step;
        }

        public String getStepName() {
            return stepName;
        }

        public void setStepName(String stepName) {
            this.stepName = stepName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public long getValidTime() {
            return validTime;
        }

        public void setValidTime(long validTime) {
            this.validTime = validTime;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public List<DetaiListBean> getDetaiList() {
            return detaiList;
        }

        public void setDetaiList(List<DetaiListBean> detaiList) {
            this.detaiList = detaiList;
        }

        public static class DetaiListBean {
            /**
             * catalogId :
             * catalogName :
             * comboNum : 0
             * courseId :
             * courseName :
             * goodsId : com.newb.student198
             * goodsName : 新教育币198
             * goodsNum : 1
             * goodsPrice : 198
             * goodsUrl :
             * gradeId :
             * gradeName :
             * module : chongzhi
             * moduleName : 未知
             * orderId : 1000000570
             * pcvId :
             * validTime : 1628239252000
             */

            private String catalogId;
            private String catalogName;
            private String comboNum;
            private String courseId;
            private String courseName;
            private String goodsId;
            private String goodsName;
            private String goodsNum;
            private String goodsPrice;
            private String goodsUrl;
            private String gradeId;
            private String gradeName;
            private String module;
            private String moduleName;
            private String orderId;
            private String pcvId;
            private long validTime;

            public String getCatalogId() {
                return catalogId;
            }

            public void setCatalogId(String catalogId) {
                this.catalogId = catalogId;
            }

            public String getCatalogName() {
                return catalogName;
            }

            public void setCatalogName(String catalogName) {
                this.catalogName = catalogName;
            }

            public String getComboNum() {
                return comboNum;
            }

            public void setComboNum(String comboNum) {
                this.comboNum = comboNum;
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

            public String getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(String goodsId) {
                this.goodsId = goodsId;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsNum() {
                return goodsNum;
            }

            public void setGoodsNum(String goodsNum) {
                this.goodsNum = goodsNum;
            }

            public String getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsPrice(String goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public String getGoodsUrl() {
                return goodsUrl;
            }

            public void setGoodsUrl(String goodsUrl) {
                this.goodsUrl = goodsUrl;
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

            public String getModule() {
                return module;
            }

            public void setModule(String module) {
                this.module = module;
            }

            public String getModuleName() {
                return moduleName;
            }

            public void setModuleName(String moduleName) {
                this.moduleName = moduleName;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getPcvId() {
                return pcvId;
            }

            public void setPcvId(String pcvId) {
                this.pcvId = pcvId;
            }

            public long getValidTime() {
                return validTime;
            }

            public void setValidTime(long validTime) {
                this.validTime = validTime;
            }
        }
    }
}
