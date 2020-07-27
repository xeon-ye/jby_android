package com.hzw.baselib.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/9 16:30
 */

public class SchoolBean implements Serializable {

    /**
     * code : 200
     * current : 1
     * data : null
     * msg :
     * pages : 1
     * rows : [{"address":"北京市昌平区","areaId":"110101","areaName":"东城区","attn":"","cellphone":"","cityId":"110100","cityName":"北京市","createTime":1593326487000,"enrollmentYear":"","id":"f34a71cb4e894e05a25d3e5e39ba46b8","landline":"","provId":"110000","provName":"北京市","schName":"测试学校","status":"1"},{"address":"36号","areaId":"110101","areaName":"东城区","attn":"","cellphone":"","cityId":"110100","cityName":"北京市","createTime":1592795070000,"enrollmentYear":"","id":"8d8c1279a7d345b18ceee24330f61eaa","landline":"","provId":"110000","provName":"北京市","schName":"学校-学期","status":"1"},{"address":"1","areaId":"110101","areaName":"东城区","attn":"","cellphone":"","cityId":"110100","cityName":"北京市","createTime":1592791515000,"enrollmentYear":"","id":"bb43b45e7bfd4daeacd6a8e234a0db31","landline":"","provId":"110000","provName":"北京市","schName":"顺义高中","status":"1"},{"address":"1","areaId":"110101","areaName":"东城区","attn":"","cellphone":"","cityId":"110100","cityName":"北京市","createTime":1592381647000,"enrollmentYear":"","id":"1e71fcd6c6f04e83a8bebec7a8c6d593","landline":"","provId":"110000","provName":"北京市","schName":"育民小学","status":"1"}]
     * size : 10
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

    public static class RowsBean implements Serializable{
        /**
         * address : 北京市昌平区
         * areaId : 110101
         * areaName : 东城区
         * attn :
         * cellphone :
         * cityId : 110100
         * cityName : 北京市
         * createTime : 1593326487000
         * enrollmentYear :
         * id : f34a71cb4e894e05a25d3e5e39ba46b8
         * landline :
         * provId : 110000
         * provName : 北京市
         * schName : 测试学校
         * status : 1
         */

        private String address;
        private String areaId;
        private String areaName;
        private String attn;
        private String cellphone;
        private String cityId;
        private String cityName;
        private long createTime;
        private String enrollmentYear;
        private String id;
        private String landline;
        private String provId;
        private String provName;
        private String schName;
        private String status;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAreaId() {
            return areaId;
        }

        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getAttn() {
            return attn;
        }

        public void setAttn(String attn) {
            this.attn = attn;
        }

        public String getCellphone() {
            return cellphone;
        }

        public void setCellphone(String cellphone) {
            this.cellphone = cellphone;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getEnrollmentYear() {
            return enrollmentYear;
        }

        public void setEnrollmentYear(String enrollmentYear) {
            this.enrollmentYear = enrollmentYear;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLandline() {
            return landline;
        }

        public void setLandline(String landline) {
            this.landline = landline;
        }

        public String getProvId() {
            return provId;
        }

        public void setProvId(String provId) {
            this.provId = provId;
        }

        public String getProvName() {
            return provName;
        }

        public void setProvName(String provName) {
            this.provName = provName;
        }

        public String getSchName() {
            return schName;
        }

        public void setSchName(String schName) {
            this.schName = schName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    @Override
    public String toString() {
        return "SchoolBean{" +
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
