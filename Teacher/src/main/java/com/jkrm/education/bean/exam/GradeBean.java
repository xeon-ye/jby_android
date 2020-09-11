package com.jkrm.education.bean.exam;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/9/11 16:46
 */

public class GradeBean implements Serializable {

    /**
     * code : 200
     * data : [{"areaId":"","areaName":"","cityId":"","cityName":"","gradeId":"e710f6ca36537899f3155a08d4fbe604","gradeName":"高一","provId":"","provName":"","schId":"","schIds":"","schName":""},{"areaId":"","areaName":"","cityId":"","cityName":"","gradeId":"56a3d789f298d99a49fbb6d51bba079e","gradeName":"高二","provId":"","provName":"","schId":"","schIds":"","schName":""},{"areaId":"","areaName":"","cityId":"","cityName":"","gradeId":"080c30db07dc3c6df71b25c843bf44b3","gradeName":"高三","provId":"","provName":"","schId":"","schIds":"","schName":""},{"areaId":"","areaName":"","cityId":"","cityName":"","gradeId":"6d0005e737f6890a434ac68dd7ae9a51","gradeName":"七年级","provId":"","provName":"","schId":"","schIds":"","schName":""},{"areaId":"","areaName":"","cityId":"","cityName":"","gradeId":"7deffd58c33ad44bff9ac877a0cfbe58","gradeName":"八年级","provId":"","provName":"","schId":"","schIds":"","schName":""},{"areaId":"","areaName":"","cityId":"","cityName":"","gradeId":"8e73056652ab8c1cbc10b39435e51c70","gradeName":"九年级","provId":"","provName":"","schId":"","schIds":"","schName":""}]
     * msg :
     */

    /**
     * areaId :
     * areaName :
     * cityId :
     * cityName :
     * gradeId : e710f6ca36537899f3155a08d4fbe604
     * gradeName : 高一
     * provId :
     * provName :
     * schId :
     * schIds :
     * schName :
     */

    private String areaId;
    private String areaName;
    private String cityId;
    private String cityName;
    private String gradeId;
    private String gradeName;
    private String provId;
    private String provName;
    private String schId;
    private String schIds;
    private String schName;

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

    public String getSchId() {
        return schId;
    }

    public void setSchId(String schId) {
        this.schId = schId;
    }

    public String getSchIds() {
        return schIds;
    }

    public void setSchIds(String schIds) {
        this.schIds = schIds;
    }

    public String getSchName() {
        return schName;
    }

    public void setSchName(String schName) {
        this.schName = schName;
    }
}
