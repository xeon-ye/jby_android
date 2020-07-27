package com.jkrm.education.bean;

import java.util.List;

/**
 * Description 考试-筛选年
 * Created by jby on 2018/12/22.
 */

public class EYear {

    private String code;
    private String msg;
    private List<EYearInfo> rows;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<EYearInfo> getRows() {
        return rows;
    }

    public void setRows(List<EYearInfo> rows) {
        this.rows = rows;
    }


    public static class EYearInfo{

        private String id;//学年id
        private String name;//学年名字
        private String curYear;//学年

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

        public String getCurYear() {
            return curYear;
        }

        public void setCurYear(String curYear) {
            this.curYear = curYear;
        }
    }
}
