package com.jkrm.education.bean.rx;

/**
 * @Description: 通知已作答
 * @Author: xiangqian
 * @CreateDate: 2020/5/25 15:54
 */

public class RxQuestionBean {
    private String id; //题目id
    private String pid;//父id
    private boolean isAnswer;//是否作答
    private String answer;//答案
    private boolean isPhotos;//多张图片需要拆分成多个小题
    private boolean isDelete;

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public RxQuestionBean(String id,  String answer, String pid,boolean isPhotos,boolean isDelete) {
        this.id = id;
        this.answer = answer;
        this.pid=pid;
        this.isPhotos=isPhotos;
        this.isDelete = isDelete;
    }

    public RxQuestionBean(String id, boolean isAnswer, String answer, String pid) {
        this.id = id;
        this.isAnswer = isAnswer;
        this.answer = answer;
        this.pid = pid;
    }



    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }



    public boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(boolean answer) {
        isAnswer = answer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public boolean isPhotos() {
        return isPhotos;
    }

    public void setPhotos(boolean photos) {
        isPhotos = photos;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
