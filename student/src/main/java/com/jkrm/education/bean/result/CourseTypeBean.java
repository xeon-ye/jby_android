package com.jkrm.education.bean.result;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/13 10:23
 */

public class CourseTypeBean {


    /**
     * createBy : e3ab0a3c47592fc83c3388f56d3635fc
     * createTime : 2020-02-22T10:46:30+0800
     * deleteStatus : 0
     * id : 37ca9927258b19cb5c5c420fd4947ced
     * name : 免费微课
     * remark :
     * updateBy : e3ab0a3c47592fc83c3388f56d3635fc
     * updateTime : 2020-02-22T10:46:30+0800
     */

    private String createBy;
    private String createTime;
    private String deleteStatus;
    private String id;
    private String name;
    private String remark;
    private String updateBy;
    private String updateTime;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
