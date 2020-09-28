package com.jkrm.education.bean.exam;

import java.io.Serializable;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/23 19:10
 * Description:
 */
public class SectionScoreBean implements Serializable {

    /**
     * code : 200
     * data : 100
     * msg :
     */

    private String code;
    private String data;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
