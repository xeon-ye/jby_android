package com.jkrm.education.bean;

import java.io.Serializable;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/30 9:24
 */

public class SubQuestionsBean implements Serializable {
    private String answer;
    private String answerExplanation;
    private String content;
    private String difficulty;
    private String groupQuestion;
    private String id;
    private String isOption;
    private String lineOptions;
    private String maxScore;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String optionE;
    private String optionF;
    private String optionG;
    private Object options;
    private String parentContent;
    private String parentId;
    private String questionNum;
    private String questionVideo;
    private String questionVideoId;
    private String scoreBasis;
    private String source;
    private String sourceNote;
    private StudentAnswer studentAnswer;
    private String title;
    private String totalId;
    //private TypeBean type;
    private String typeId;
    private String typeName;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerExplanation() {
        return answerExplanation;
    }

    public void setAnswerExplanation(String answerExplanation) {
        this.answerExplanation = answerExplanation;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getGroupQuestion() {
        return groupQuestion;
    }

    public void setGroupQuestion(String groupQuestion) {
        this.groupQuestion = groupQuestion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsOption() {
        return isOption;
    }

    public void setIsOption(String isOption) {
        this.isOption = isOption;
    }

    public String getLineOptions() {
        return lineOptions;
    }

    public void setLineOptions(String lineOptions) {
        this.lineOptions = lineOptions;
    }

    public String getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(String maxScore) {
        this.maxScore = maxScore;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getOptionE() {
        return optionE;
    }

    public void setOptionE(String optionE) {
        this.optionE = optionE;
    }

    public String getOptionF() {
        return optionF;
    }

    public void setOptionF(String optionF) {
        this.optionF = optionF;
    }

    public String getOptionG() {
        return optionG;
    }

    public void setOptionG(String optionG) {
        this.optionG = optionG;
    }

    public String getParentContent() {
        return parentContent;
    }

    public void setParentContent(String parentContent) {
        this.parentContent = parentContent;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(String questionNum) {
        this.questionNum = questionNum;
    }

    public String getQuestionVideo() {
        return questionVideo;
    }

    public void setQuestionVideo(String questionVideo) {
        this.questionVideo = questionVideo;
    }

    public String getQuestionVideoId() {
        return questionVideoId;
    }

    public void setQuestionVideoId(String questionVideoId) {
        this.questionVideoId = questionVideoId;
    }

    public String getScoreBasis() {
        return scoreBasis;
    }

    public void setScoreBasis(String scoreBasis) {
        this.scoreBasis = scoreBasis;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceNote() {
        return sourceNote;
    }

    public void setSourceNote(String sourceNote) {
        this.sourceNote = sourceNote;
    }

    public StudentAnswer getStudentAnswer() {
        return studentAnswer;
    }

    public static class StudentAnswer implements Serializable{
        private String answer;
        private String answerSheetId;
        private String correctness;
        private String explain;
        private String gradedScan;
        private String graderId;
        private String id;
        private String mistake;
        private String questionId;
        private String questionNum;
        private String rawScan;
        private String rightAnswer;
        private String score;
        private String status;
        private String studentId;

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getAnswerSheetId() {
            return answerSheetId;
        }

        public void setAnswerSheetId(String answerSheetId) {
            this.answerSheetId = answerSheetId;
        }

        public String getCorrectness() {
            return correctness;
        }

        public void setCorrectness(String correctness) {
            this.correctness = correctness;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public String getGradedScan() {
            return gradedScan;
        }

        public void setGradedScan(String gradedScan) {
            this.gradedScan = gradedScan;
        }

        public String getGraderId() {
            return graderId;
        }

        public void setGraderId(String graderId) {
            this.graderId = graderId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMistake() {
            return mistake;
        }

        public void setMistake(String mistake) {
            this.mistake = mistake;
        }

        public String getQuestionId() {
            return questionId;
        }

        public void setQuestionId(String questionId) {
            this.questionId = questionId;
        }

        public String getQuestionNum() {
            return questionNum;
        }

        public void setQuestionNum(String questionNum) {
            this.questionNum = questionNum;
        }

        public String getRawScan() {
            return rawScan;
        }

        public void setRawScan(String rawScan) {
            this.rawScan = rawScan;
        }

        public String getRightAnswer() {
            return rightAnswer;
        }

        public void setRightAnswer(String rightAnswer) {
            this.rightAnswer = rightAnswer;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }
    }
    public void setStudentAnswer(StudentAnswer studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTotalId() {
        return totalId;
    }

    public void setTotalId(String totalId) {
        this.totalId = totalId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Object getOptions() {
        return options;
    }

    public void setOptions(Object options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "SubQuestionsBean{" +
                "answer='" + answer + '\'' +
                ", answerExplanation='" + answerExplanation + '\'' +
                ", content='" + content + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", groupQuestion='" + groupQuestion + '\'' +
                ", id='" + id + '\'' +
                ", isOption='" + isOption + '\'' +
                ", lineOptions='" + lineOptions + '\'' +
                ", maxScore='" + maxScore + '\'' +
                ", optionA='" + optionA + '\'' +
                ", optionB='" + optionB + '\'' +
                ", optionC='" + optionC + '\'' +
                ", optionD='" + optionD + '\'' +
                ", optionE='" + optionE + '\'' +
                ", optionF='" + optionF + '\'' +
                ", optionG='" + optionG + '\'' +
                ", parentContent='" + parentContent + '\'' +
                ", parentId='" + parentId + '\'' +
                ", questionNum='" + questionNum + '\'' +
                ", questionVideo='" + questionVideo + '\'' +
                ", questionVideoId='" + questionVideoId + '\'' +
                ", scoreBasis='" + scoreBasis + '\'' +
                ", source='" + source + '\'' +
                ", sourceNote='" + sourceNote + '\'' +
                ", studentAnswer=" + studentAnswer +
                ", title='" + title + '\'' +
                ", totalId='" + totalId + '\'' +
                ", typeId='" + typeId + '\'' +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
