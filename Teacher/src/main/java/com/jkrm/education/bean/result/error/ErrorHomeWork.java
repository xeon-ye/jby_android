package com.jkrm.education.bean.result.error;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/23 15:28
 */

public class ErrorHomeWork {


    /**
     * code : 200
     * data : [{"attrValueId":"","attrValueName":"","homeworkId":"","id":"1591580899774Ab2z6","name":"练与测83 曲线的参数方程(4)","templateId":"mxzy-202005080013020283"}]
     * msg :
     */
    /**
     * attrValueId :
     * attrValueName :
     * homeworkId :
     * id : 1591580899774Ab2z6
     * name : 练与测83 曲线的参数方程(4)
     * templateId : mxzy-202005080013020283
     */

    private String attrValueId;
    private String attrValueName;
    private String homeworkId;
    private String id;
    private String name;
    private String templateId;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
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

    public String getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
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

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
}
