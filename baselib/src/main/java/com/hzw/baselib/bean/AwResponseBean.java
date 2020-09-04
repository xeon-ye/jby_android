package com.hzw.baselib.bean;

/**
 * Created by hzw on 2017/11/29.
 */

public class AwResponseBean<T> {
    private static final int CODE_SUCCESS = 200;
    private static final int CODE_TOKEN_ERROR = 401;
    private static final int CODE_ERROR_NOLOGIN = 410;
    private static final int ERROR_ILLEGAL_IP = 411;
    private static final int RESET_PSW=413;
    private static final int CODE_REMOTE_LOGIN=416;

    private String msg;
    private T data;

    private String message;
    private int code;


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
    public boolean isNeedResetPSW(){
        if(code==RESET_PSW){
            return true;
        }
        return  false;
    }

    public boolean isRemoteLogin(){
        if(code==CODE_REMOTE_LOGIN){
            return true;
        }
        return false;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
