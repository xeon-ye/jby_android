package com.jkrm.education.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/3 17:33
 */

public class ErrorQuestionTimeBean implements Serializable {


    /**
     * answer : <p style="font-family: 'Times New Roman';">（1）繁花嫩叶；呼朋引伴；卖弄；婉转；应和
     * （2）嘹亮
     * （3）黄晕；烘托
     * （4）静默  </p>
     * answerExplanation :
     * answerSheetId :
     * classRatio :
     * content : <p style="font-family: 'Times New Roman';">根据拼音写出相应的词语。    </p><p style="font-family: 'Times New Roman';">（1）鸟儿将巢安在f&aacute;n huā n&egrave;n y&egrave;________当中，高兴起来了，hū p&eacute;ng yǐn b&agrave;n________地m&agrave;i nong________清脆的喉咙，唱出wǎn zhuǎn________的曲子，跟轻风流水y&igrave;ng h&egrave;________着。    </p><p style="font-family: 'Times New Roman';">（2）牛背上牧童的短笛，这时候也成天li&aacute;o li&agrave;ng________地响着。    </p><p style="font-family: 'Times New Roman';">（3）傍晚时候，上灯了，一点点hu&aacute;ng y&ugrave;n________的光，hōng tuō________出一片安静而和平的夜。    </p><p style="font-family: 'Times New Roman';">（4）他们的房屋，稀稀疏疏的，在雨里j&igrave;ng m&ograve;________着。    </p>
     * corrAnswer :
     * correctAnswer :
     * courseId :
     * errorType : 1
     * errorTypeName : 错题
     * grade : null
     * gradedScan :
     * graderId :
     * homeworkId :
     * id : 59596
     * isNoMake :
     * isNoVideo :
     * isOption :
     * maxScore :
     * myAnswer :
     * optionA :
     * optionB :
     * optionC :
     * optionD :
     * optionE :
     * optionF :
     * optionG :
     * options : {"optionA":"","optionB":"","optionC":"","optionD":"","optionE":"","optionF":"","optionG":""}
     * parentId :
     * personRatio :
     * practice :
     * questionId : 59596
     * questionNum :
     * questionVideo :
     * questionVideoId :
     * rawScan :
     * scanFinishTime :
     * scanQuesType :
     * showTime : 1589945126000
     * similar :
     * smDto : null
     * source :
     * studentId : 6d2aab36a7e01ff7caa98fefb2ba97aa
     * templateId :
     * type : {"id":"46a323ca12951baa576155c5f4fa3a4b","isMulti":"","isOption":"1","name":"填空题","totalId":""}
     * typeId : 46a323ca12951baa576155c5f4fa3a4b
     * typeName :
     * typeNames :
     * subQuestions : [{"answer":"<p style=\"font-family: 'Times New Roman';\">杨花、子规、明月、风（两个即可）<\/p>","answerExplanation":"","answerSheetId":"","classRatio":"","content":"<p style=\"font-family: 'Times New Roman';\">《1》诗人通过哪些景物表达离别伤感的愁绪？<\/p>","corrAnswer":"","correctAnswer":"","courseId":"","errorType":"1","errorTypeName":"错题","grade":null,"gradedScan":"","graderId":"","homeworkId":"","id":"66427","isNoMake":"","isNoVideo":"","isOption":"","maxScore":"","myAnswer":"","optionA":"","optionB":"","optionC":"","optionD":"","optionE":"","optionF":"","optionG":"","options":{"optionA":"","optionB":"","optionC":"","optionD":"","optionE":"","optionF":"","optionG":""},"parentId":"66426","personRatio":"","practice":"","questionId":"66427","questionNum":"","questionVideo":"","questionVideoId":"","rawScan":"","scanFinishTime":"","scanQuesType":"","showTime":null,"similar":"","smDto":null,"source":"","studentId":"6d2aab36a7e01ff7caa98fefb2ba97aa","templateId":"","type":{"id":"4ec3cb81e346fe337dba88b58887d764","isMulti":"","isOption":"1","name":"诗歌鉴赏","totalId":""},"typeId":"4ec3cb81e346fe337dba88b58887d764","typeName":"","typeNames":""}]
     */

    private String answer;
    private String answerExplanation;
    private String answerSheetId;
    private String classRatio;
    private String content;
    private String corrAnswer;
    private String correctAnswer;
    private String courseId;
    private String errorType;
    private String errorTypeName;
    private Object grade;
    private String gradedScan;
    private String graderId;
    private String homeworkId;
    private String id;
    private String isNoMake;
    private String isNoVideo;
    private String isOption;
    private String maxScore;
    private String myAnswer;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String optionE;
    private String optionF;
    private String optionG;
    private Object options;
    private String parentId;
    private String personRatio;
    private String practice;
    private String questionId;
    private String questionNum;
    private String questionVideo;
    private String questionVideoId;
    private String rawScan;
    private String scanFinishTime;
    private String scanQuesType;
    private long showTime;
    private String similar;
    private Object smDto;
    private String source;
    private String studentId;
    private String templateId;
    private TypeBean type;
    private String typeId;
    private String typeName;
    private String typeNames;
    private List<SubQuestionsBean> subQuestions;
    private boolean isChose;
    private String studAnswer;

    public String getStudAnswer() {
        return studAnswer;
    }

    public void setStudAnswer(String studAnswer) {
        this.studAnswer = studAnswer;
    }

    public boolean isChose() {
        return isChose;
    }

    public void setChose(boolean chose) {
        isChose = chose;
    }

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

    public String getAnswerSheetId() {
        return answerSheetId;
    }

    public void setAnswerSheetId(String answerSheetId) {
        this.answerSheetId = answerSheetId;
    }

    public String getClassRatio() {
        return classRatio;
    }

    public void setClassRatio(String classRatio) {
        this.classRatio = classRatio;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCorrAnswer() {
        return corrAnswer;
    }

    public void setCorrAnswer(String corrAnswer) {
        this.corrAnswer = corrAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorTypeName() {
        return errorTypeName;
    }

    public void setErrorTypeName(String errorTypeName) {
        this.errorTypeName = errorTypeName;
    }

    public Object getGrade() {
        return grade;
    }

    public void setGrade(Object grade) {
        this.grade = grade;
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

    public String getIsNoMake() {
        return isNoMake;
    }

    public void setIsNoMake(String isNoMake) {
        this.isNoMake = isNoMake;
    }

    public String getIsNoVideo() {
        return isNoVideo;
    }

    public void setIsNoVideo(String isNoVideo) {
        this.isNoVideo = isNoVideo;
    }

    public String getIsOption() {
        return isOption;
    }

    public void setIsOption(String isOption) {
        this.isOption = isOption;
    }

    public String getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(String maxScore) {
        this.maxScore = maxScore;
    }

    public String getMyAnswer() {
        return myAnswer;
    }

    public void setMyAnswer(String myAnswer) {
        this.myAnswer = myAnswer;
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

    public Object getOptions() {
        return options;
    }

    public void setOptions(OptionsBean options) {
        this.options = options;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPersonRatio() {
        return personRatio;
    }

    public void setPersonRatio(String personRatio) {
        this.personRatio = personRatio;
    }

    public String getPractice() {
        return practice;
    }

    public void setPractice(String practice) {
        this.practice = practice;
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

    public String getRawScan() {
        return rawScan;
    }

    public void setRawScan(String rawScan) {
        this.rawScan = rawScan;
    }

    public String getScanFinishTime() {
        return scanFinishTime;
    }

    public void setScanFinishTime(String scanFinishTime) {
        this.scanFinishTime = scanFinishTime;
    }

    public String getScanQuesType() {
        return scanQuesType;
    }

    public void setScanQuesType(String scanQuesType) {
        this.scanQuesType = scanQuesType;
    }

    public long getShowTime() {
        return showTime;
    }

    public void setShowTime(long showTime) {
        this.showTime = showTime;
    }

    public String getSimilar() {
        return similar;
    }

    public void setSimilar(String similar) {
        this.similar = similar;
    }

    public Object getSmDto() {
        return smDto;
    }

    public void setSmDto(Object smDto) {
        this.smDto = smDto;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public TypeBean getType() {
        return type;
    }

    public void setType(TypeBean type) {
        this.type = type;
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

    public String getTypeNames() {
        return typeNames;
    }

    public void setTypeNames(String typeNames) {
        this.typeNames = typeNames;
    }

    public List<SubQuestionsBean> getSubQuestions() {
        return subQuestions;
    }

    public void setSubQuestions(List<SubQuestionsBean> subQuestions) {
        this.subQuestions = subQuestions;
    }

    public static class OptionsBean {
        /**
         * optionA :
         * optionB :
         * optionC :
         * optionD :
         * optionE :
         * optionF :
         * optionG :
         */

        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private String optionE;
        private String optionF;
        private String optionG;

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
    }

    public static class TypeBean {
        /**
         * id : 46a323ca12951baa576155c5f4fa3a4b
         * isMulti :
         * isOption : 1
         * name : 填空题
         * totalId :
         */

        private String id;
        private String isMulti;
        private String isOption;
        private String name;
        private String totalId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIsMulti() {
            return isMulti;
        }

        public void setIsMulti(String isMulti) {
            this.isMulti = isMulti;
        }

        public String getIsOption() {
            return isOption;
        }

        public void setIsOption(String isOption) {
            this.isOption = isOption;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTotalId() {
            return totalId;
        }

        public void setTotalId(String totalId) {
            this.totalId = totalId;
        }
    }

    public static class SubQuestionsBean {
        /**
         * answer : <p style="font-family: 'Times New Roman';">杨花、子规、明月、风（两个即可）</p>
         * answerExplanation :
         * answerSheetId :
         * classRatio :
         * content : <p style="font-family: 'Times New Roman';">《1》诗人通过哪些景物表达离别伤感的愁绪？</p>
         * corrAnswer :
         * correctAnswer :
         * courseId :
         * errorType : 1
         * errorTypeName : 错题
         * grade : null
         * gradedScan :
         * graderId :
         * homeworkId :
         * id : 66427
         * isNoMake :
         * isNoVideo :
         * isOption :
         * maxScore :
         * myAnswer :
         * optionA :
         * optionB :
         * optionC :
         * optionD :
         * optionE :
         * optionF :
         * optionG :
         * options : {"optionA":"","optionB":"","optionC":"","optionD":"","optionE":"","optionF":"","optionG":""}
         * parentId : 66426
         * personRatio :
         * practice :
         * questionId : 66427
         * questionNum :
         * questionVideo :
         * questionVideoId :
         * rawScan :
         * scanFinishTime :
         * scanQuesType :
         * showTime : null
         * similar :
         * smDto : null
         * source :
         * studentId : 6d2aab36a7e01ff7caa98fefb2ba97aa
         * templateId :
         * type : {"id":"4ec3cb81e346fe337dba88b58887d764","isMulti":"","isOption":"1","name":"诗歌鉴赏","totalId":""}
         * typeId : 4ec3cb81e346fe337dba88b58887d764
         * typeName :
         * typeNames :
         */

        private String answer;
        private String answerExplanation;
        private String answerSheetId;
        private String classRatio;
        private String content;
        private String corrAnswer;
        private String correctAnswer;
        private String courseId;
        private String errorType;
        private String errorTypeName;
        private Object grade;
        private String gradedScan;
        private String graderId;
        private String homeworkId;
        private String id;
        private String isNoMake;
        private String isNoVideo;
        private String isOption;
        private String maxScore;
        private String myAnswer;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private String optionE;
        private String optionF;
        private String optionG;
        private OptionsBeanX options;
        private String parentId;
        private String personRatio;
        private String practice;
        private String questionId;
        private String questionNum;
        private String questionVideo;
        private String questionVideoId;
        private String rawScan;
        private String scanFinishTime;
        private String scanQuesType;
        private Object showTime;
        private String similar;
        private Object smDto;
        private String source;
        private String studentId;
        private String templateId;
        private TypeBeanX type;
        private String typeId;
        private String typeName;
        private String typeNames;

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

        public String getAnswerSheetId() {
            return answerSheetId;
        }

        public void setAnswerSheetId(String answerSheetId) {
            this.answerSheetId = answerSheetId;
        }

        public String getClassRatio() {
            return classRatio;
        }

        public void setClassRatio(String classRatio) {
            this.classRatio = classRatio;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCorrAnswer() {
            return corrAnswer;
        }

        public void setCorrAnswer(String corrAnswer) {
            this.corrAnswer = corrAnswer;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }

        public void setCorrectAnswer(String correctAnswer) {
            this.correctAnswer = correctAnswer;
        }

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getErrorType() {
            return errorType;
        }

        public void setErrorType(String errorType) {
            this.errorType = errorType;
        }

        public String getErrorTypeName() {
            return errorTypeName;
        }

        public void setErrorTypeName(String errorTypeName) {
            this.errorTypeName = errorTypeName;
        }

        public Object getGrade() {
            return grade;
        }

        public void setGrade(Object grade) {
            this.grade = grade;
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

        public String getIsNoMake() {
            return isNoMake;
        }

        public void setIsNoMake(String isNoMake) {
            this.isNoMake = isNoMake;
        }

        public String getIsNoVideo() {
            return isNoVideo;
        }

        public void setIsNoVideo(String isNoVideo) {
            this.isNoVideo = isNoVideo;
        }

        public String getIsOption() {
            return isOption;
        }

        public void setIsOption(String isOption) {
            this.isOption = isOption;
        }

        public String getMaxScore() {
            return maxScore;
        }

        public void setMaxScore(String maxScore) {
            this.maxScore = maxScore;
        }

        public String getMyAnswer() {
            return myAnswer;
        }

        public void setMyAnswer(String myAnswer) {
            this.myAnswer = myAnswer;
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

        public OptionsBeanX getOptions() {
            return options;
        }

        public void setOptions(OptionsBeanX options) {
            this.options = options;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getPersonRatio() {
            return personRatio;
        }

        public void setPersonRatio(String personRatio) {
            this.personRatio = personRatio;
        }

        public String getPractice() {
            return practice;
        }

        public void setPractice(String practice) {
            this.practice = practice;
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

        public String getRawScan() {
            return rawScan;
        }

        public void setRawScan(String rawScan) {
            this.rawScan = rawScan;
        }

        public String getScanFinishTime() {
            return scanFinishTime;
        }

        public void setScanFinishTime(String scanFinishTime) {
            this.scanFinishTime = scanFinishTime;
        }

        public String getScanQuesType() {
            return scanQuesType;
        }

        public void setScanQuesType(String scanQuesType) {
            this.scanQuesType = scanQuesType;
        }

        public Object getShowTime() {
            return showTime;
        }

        public void setShowTime(Object showTime) {
            this.showTime = showTime;
        }

        public String getSimilar() {
            return similar;
        }

        public void setSimilar(String similar) {
            this.similar = similar;
        }

        public Object getSmDto() {
            return smDto;
        }

        public void setSmDto(Object smDto) {
            this.smDto = smDto;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getTemplateId() {
            return templateId;
        }

        public void setTemplateId(String templateId) {
            this.templateId = templateId;
        }

        public TypeBeanX getType() {
            return type;
        }

        public void setType(TypeBeanX type) {
            this.type = type;
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

        public String getTypeNames() {
            return typeNames;
        }

        public void setTypeNames(String typeNames) {
            this.typeNames = typeNames;
        }

        public static class OptionsBeanX {
            /**
             * optionA :
             * optionB :
             * optionC :
             * optionD :
             * optionE :
             * optionF :
             * optionG :
             */

            private String optionA;
            private String optionB;
            private String optionC;
            private String optionD;
            private String optionE;
            private String optionF;
            private String optionG;

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
        }

        public static class TypeBeanX {
            /**
             * id : 4ec3cb81e346fe337dba88b58887d764
             * isMulti :
             * isOption : 1
             * name : 诗歌鉴赏
             * totalId :
             */

            private String id;
            private String isMulti;
            private String isOption;
            private String name;
            private String totalId;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIsMulti() {
                return isMulti;
            }

            public void setIsMulti(String isMulti) {
                this.isMulti = isMulti;
            }

            public String getIsOption() {
                return isOption;
            }

            public void setIsOption(String isOption) {
                this.isOption = isOption;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTotalId() {
                return totalId;
            }

            public void setTotalId(String totalId) {
                this.totalId = totalId;
            }
        }
    }
}
