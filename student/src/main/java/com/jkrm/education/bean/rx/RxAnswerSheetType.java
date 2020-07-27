package com.jkrm.education.bean.rx;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/30 16:19
 */

public class RxAnswerSheetType {

    public RxAnswerSheetType(String id, String stuAnswer) {
        Id = id;
        this.stuAnswer = stuAnswer;
    }

    public RxAnswerSheetType(String id, String stuAnswer, boolean isChoice) {
        Id = id;
        this.stuAnswer = stuAnswer;
        this.isChoice = isChoice;
    }

    public boolean isChoice() {
        return isChoice;
    }

    public void setChoice(boolean choice) {
        isChoice = choice;
    }

    private String Id;
    private String stuAnswer;
    private boolean isChoice;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getStuAnswer() {
        return stuAnswer;
    }

    public void setStuAnswer(String stuAnswer) {
        this.stuAnswer = stuAnswer;
    }
}
