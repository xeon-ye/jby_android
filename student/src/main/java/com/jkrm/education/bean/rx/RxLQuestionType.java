package com.jkrm.education.bean.rx;

/**
 * @Description: 错题本 选中通知
 * @Author: xiangqian
 * @CreateDate: 2020/6/9 13:54
 */

public class RxLQuestionType {
    private String num;
    private int type;
    private int choseNum;

    public int getChoseNum() {
        return choseNum;
    }

    public void setChoseNum(int choseNum) {
        this.choseNum = choseNum;
    }

    public int getType() {
        return type;
    }

    public RxLQuestionType() {
    }

    public RxLQuestionType(String num, int type) {
        this.num = num;
        this.type = type;
    }

    public void setType(int type) {
        this.type = type;
    }



    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
