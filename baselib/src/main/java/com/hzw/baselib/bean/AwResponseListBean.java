package com.hzw.baselib.bean;

import java.util.List;

/**
 * 针对金榜苑项目返回数据结构定制bean
 * Created by hzw on 2017/11/29.
 */

public class AwResponseListBean<T, F> {
    private static final int CODE_SUCCESS = 200;
    private static final int CODE_TOKEN_ERROR = 401;
    private static final int CODE_ERROR_NOLOGIN = 410;
    private static final int ERROR_ILLEGAL_IP = 411;

    private int code;
    private String msg;
    private T data;
    private List<F> rows;
    private int current;
    private int size;
    private int total;
    private int pages;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return code == CODE_SUCCESS;
    }

    public boolean isTokenError() {
        if(code==CODE_TOKEN_ERROR||code==CODE_ERROR_NOLOGIN||code==ERROR_ILLEGAL_IP){
            return true;
        }
        return false;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<F> getRows() {
        return rows;
    }

    public void setRows(List<F> rows) {
        this.rows = rows;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
