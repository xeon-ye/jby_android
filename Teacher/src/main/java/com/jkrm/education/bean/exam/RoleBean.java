package com.jkrm.education.bean.exam;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/9/30 14:33
 */

public class RoleBean implements Serializable {

    /**
     * code : 200
     * data : [{"createBy":"","createTime":"2020-06-16T16:47:40+0800","deleteStatus":"0","id":"role_teacher_63cb1f8eae0deed1d19e72c92b2291fb","parentId":"","remark":"new","roleName":"学科教师","roleStatus":"0","roleType":"2","title":"5","updateBy":"admin_001","updateTime":"2020-06-17T17:55:50+0800"},{"createBy":"","createTime":"2020-06-16T16:47:40+0800","deleteStatus":"0","id":"role_adviser_63cb1f8eae0deed1d19e72c92b2291fb","parentId":"","remark":"new","roleName":"班主任","roleStatus":"0","roleType":"2","title":"4","updateBy":"","updateTime":"2020-06-16T16:47:40+0800"}]
     * msg :
     */


    /**
     * createBy :
     * createTime : 2020-06-16T16:47:40+0800
     * deleteStatus : 0
     * id : role_teacher_63cb1f8eae0deed1d19e72c92b2291fb
     * parentId :
     * remark : new
     * roleName : 学科教师
     * roleStatus : 0
     * roleType : 2
     * title : 5
     * updateBy : admin_001
     * updateTime : 2020-06-17T17:55:50+0800
     */

    private String createBy;
    private String createTime;
    private String deleteStatus;
    private String id;
    private String parentId;
    private String remark;
    private String roleName;
    private String roleStatus;
    private String roleType;
    private String title;
    private String updateBy;
    private String updateTime;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(String roleStatus) {
        this.roleStatus = roleStatus;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
