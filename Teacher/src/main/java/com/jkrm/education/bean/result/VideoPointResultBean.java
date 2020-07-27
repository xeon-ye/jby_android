package com.jkrm.education.bean.result;

import java.io.Serializable;

/**
 * Created by hzw on 2019/7/9.
 */

public class VideoPointResultBean implements Serializable{


    /**
     * accessUrl : http://jbyadmin.oss-cn-beijing.aliyuncs.com/jby_files/prod/videos/knowledge/video-djwk-yls-4.mp4
     * contentType : video/mpeg4
     * createBy :
     * createTime : 2019-05-30T20:34:07+0800
     * deleteStatus : 0
     * fileExt : mp4
     * fileSize : 40999322
     * filename : 负数和0的认识.mp4
     * id : video101
     * remark :
     * updateBy :
     * updateTime : 2019-06-26T17:59:08+0800
     */

    private String accessUrl;
    private String contentType;
    private String createBy;
    private String createTime;
    private String deleteStatus;
    private String fileExt;
    private String fileSize;
    private String filename;
    private String id;
    private String remark;
    private String updateBy;
    private String updateTime;

    //20190829新返回字段
    private String name;
    private String type;
    private String face;
    private String url;
    private String price;
    private String brief;
    private String times;
    private String size;
    private String teacherName;
    //新返回字段结束

    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
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

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
