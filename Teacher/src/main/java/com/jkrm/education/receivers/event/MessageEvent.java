package com.jkrm.education.receivers.event;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/28 11:56
 * Description:
 */
public class MessageEvent {
    private int type;
    private String message;
    private  int tag;

    public MessageEvent(int type, String message,int tag) {
        this.type = type;
        this.message = message;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "type="+type+"--message= "+message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
