package com.jkrm.education.bean.exam;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/10 16:17
 * Description:
 */
public class ScoreAchievementBean implements Serializable {


    /**
     * code : 200
     * current : 1
     * data : [{"answer":"<span style=\"font-family: 'Times New Roman';\">C<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"C",
     * "questionId":"422350","questionNum":"1","studAnswer":""},{"answer":"<span style=\"font-family: 'Times New Roman';\">C<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"C","questionId":"422351","questionNum":"2","studAnswer":""},{"answer":"<span style=\"font-family: 'Times New Roman';\">A<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"A","questionId":"422352","questionNum":"3","studAnswer":""},{"answer":"<span style=\"font-family: 'Times New Roman';\">B<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"B","questionId":"422353","questionNum":"4","studAnswer":""},{"answer":"<span style=\"font-family: 'Times New Roman';\">(1)独立寒秋　湘江北去　橘子洲头　(2)怅寥廓　问苍茫大地　谁主沉浮　(3)曾记否　到中流击水　浪遏飞舟<\/span>","isOption":"1","maxScore":"9.0","noSpanAnswer":"","questionId":"422354","questionNum":"5","studAnswer":""},{"answer":"<span style=\"font-family: 'Times New Roman';\">&ldquo;舒&rdquo;是舒展开阔之意，它既写出了南方天空的开阔，又写出了作者心中的舒畅开朗，景和情有机地融合在了一起。<\/span>","isOption":"1","maxScore":"6.0","noSpanAnswer":"","questionId":"422355","questionNum":"6","studAnswer":""},{"answer":"<span style=\"font-family: 'Times New Roman';\">(示例)远望群山上那高高低低的层层枫树林，它们经过秋风的吹拂和严霜的侵蚀，已经变成红色，似无尽的丹砂在缓缓流动，又似一团团炽烈的火焰在熊熊燃烧。近看那波涛滚滚的湘江，一片碧绿，清澈见底，秋水与长天浑然一色。无数船只在湘江上南来北往，竞相行驶。好一幅美丽壮观的&ldquo;湘江扬帆图&rdquo;！<\/span>","isOption":"1","maxScore":"40.0","noSpanAnswer":"","questionId":"422356","questionNum":"7","studAnswer":""}]
     * msg :
     * pages : 1
     * rows : [{"classAvgScore":"46.5","classBeatNum":"1","classId":"67574d7db09c46088ed05fa5da312eb8","classMaxScore":"50","classMinScore":"","className":"3班","classRank":"1","courseId":"fab6c26483dd26d2dbf4995ece9acdb3","courseName":"语文","examId":"6bfe14f69ba949bb944cdb2c3e4d63be","gradeAvgScore":"39.0","gradeBeatNum":"5","gradeMaxScore":"50","gradeRank":"1","id":"1ae20e3a9fc305d21235fee9dd6a4ceb","jointRank":"","myScore":"50","objectScore":"0","paperId":"26521da261cd4f3f969f23f591a6775f","questList":[{"answer":"<span style=\"font-family: 'Times New Roman';\">C<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"","questionId":"422350","questionNum":"1","score":"0.0","studAnswer":"A"},{"answer":"<span style=\"font-family: 'Times New Roman';\">C<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"","questionId":"422351","questionNum":"2","score":"0.0","studAnswer":"B"},{"answer":"<span style=\"font-family: 'Times New Roman';\">A<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"","questionId":"422352","questionNum":"3","score":"0.0","studAnswer":"C"},{"answer":"<span style=\"font-family: 'Times New Roman';\">B<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"","questionId":"422353","questionNum":"4","score":"0.0","studAnswer":"D"},
     * {"answer":"<span style=\"font-family: 'Times New Roman';\">(1)独立寒秋　湘江北去　橘子洲头　(2)怅寥廓　问苍茫大地　谁主沉浮　(3)曾记否　到中流击水　浪遏飞舟<\/span>","isOption":"1","maxScore":"9.0","noSpanAnswer":"","questionId":"422354","questionNum":"5","score":"4.0","studAnswer":"https://img.xinjiaoyu.com/jby_files/test/mark_server/EDF7EA5740544EDCB96FF57E3517AC3E.png"},{"answer":"<span style=\"font-family: 'Times New Roman';\">&ldquo;舒&rdquo;是舒展开阔之意，它既写出了南方天空的开阔，又写出了作者心中的舒畅开朗，景和情有机地融合在了一起。<\/span>","isOption":"1","maxScore":"6.0","noSpanAnswer":"","questionId":"422355","questionNum":"6","score":"6.0","studAnswer":"https://img.xinjiaoyu.com/jby_files/test/mark_server/534DA6AA990D4027935AE2770D26F633.png"},{"answer":"<span style=\"font-family: 'Times New Roman';\">(示例)远望群山上那高高低低的层层枫树林，它们经过秋风的吹拂和严霜的侵蚀，已经变成红色，似无尽的丹砂在缓缓流动，又似一团团炽烈的火焰在熊熊燃烧。近看那波涛滚滚的湘江，一片碧绿，清澈见底，秋水与长天浑然一色。无数船只在湘江上南来北往，竞相行驶。好一幅美丽壮观的&ldquo;湘江扬帆图&rdquo;！<\/span>","isOption":"1","maxScore":"40.0","noSpanAnswer":"","questionId":"422356","questionNum":"7","score":"40.0","studAnswer":"https://img.xinjiaoyu.com/jby_files/test/mark_server/80C7195413D04D25AB9A621FEA302FC2.png"}],"schId":"6a349f665db2496c9e48036d770c6e40","sheetId":"1600164465670DM9su","studCode":"99953","studExamCode":"99953","studId":"a83a7e4ab8db402b882c1dfe65134925","studName":"学生53","studNum":"","subjectScore":"50"},{"classAvgScore":"26.5","classBeatNum":"1","classId":"806fdcda96bf4ca69ea55882e907a327","classMaxScore":"49","classMinScore":"","className":"1班","classRank":"1","courseId":"fab6c26483dd26d2dbf4995ece9acdb3","courseName":"语文","examId":"6bfe14f69ba949bb944cdb2c3e4d63be","gradeAvgScore":"39.0","gradeBeatNum":"4","gradeMaxScore":"50","gradeRank":"2","id":"2ba11559ef5a519ba63fc24f149734e4","jointRank":"","myScore":"49","objectScore":"0","paperId":"26521da261cd4f3f969f23f591a6775f","questList":[{"answer":"<span style=\"font-family: 'Times New Roman';\">C<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"","questionId":"422350","questionNum":"1","score":"0.0","studAnswer":"A"},{"answer":"<span style=\"font-family: 'Times New Roman';\">C<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"","questionId":"422351","questionNum":"2","score":"0.0","studAnswer":"B"},{"answer":"<span style=\"font-family: 'Times New Roman';\">A<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"","questionId":"422352","questionNum":"3","score":"0.0","studAnswer":"C"},
     * {"answer":"<span style=\"font-family: 'Times New Roman';\">B<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"","questionId":"422353","questionNum":"4","score":"0.0","studAnswer":"D"},
     * {"answer":"<span style=\"font-family: 'Times New Roman';\">&ldquo;舒&rdquo;是舒展开阔之意，它既写出了南方天空的开阔，又写出了作者心中的舒畅开朗，景和情有机地融合在了一起。<\/span>","isOption":"1","maxScore":"6.0","noSpanAnswer":"","questionId":"422355","questionNum":"6","score":"5.0","studAnswer":"https://img.xinjiaoyu.com/jby_files/test/mark_server/DCFF16E0EFF24BA9820C9FDE9AFB826C.png"},{"answer":"<span style=\"font-family: 'Times New Roman';\">(示例)远望群山上那高高低低的层层枫树林，它们经过秋风的吹拂和严霜的侵蚀，已经变成红色，似无尽的丹砂在缓缓流动，又似一团团炽烈的火焰在熊熊燃烧。近看那波涛滚滚的湘江，一片碧绿，清澈见底，秋水与长天浑然一色。无数船只在湘江上南来北往，竞相行驶。好一幅美丽壮观的&ldquo;湘江扬帆图&rdquo;！<\/span>","isOption":"1","maxScore":"40.0","noSpanAnswer":"","questionId":"422356","questionNum":"7","score":"40.0","studAnswer":"https://img.xinjiaoyu.com/jby_files/test/mark_server/4F4A97AE9038465DA97E2FF62E17593A.png"}],"schId":"6a349f665db2496c9e48036d770c6e40","sheetId":"1600164434726H1A1F","studCode":"99951","studExamCode":"99951","studId":"fa54dea7aeee4913bb8ddaf1b85eb46c","studName":"学生51","studNum":"","subjectScore":"49"},{"classAvgScore":"44.0","classBeatNum":"1","classId":"bf0b8650db11434195780f6f09946d71","classMaxScore":"44","classMinScore":"","className":"2班","classRank":"1","courseId":"fab6c26483dd26d2dbf4995ece9acdb3","courseName":"语文","examId":"6bfe14f69ba949bb944cdb2c3e4d63be","gradeAvgScore":"39.0","gradeBeatNum":"3","gradeMaxScore":"50","gradeRank":"3","id":"45f8f3e73db9c9b453a0075e6b4290b5","jointRank":"","myScore":"44","objectScore":"0","paperId":"26521da261cd4f3f969f23f591a6775f","questList":[{"answer":"<span style=\"font-family: 'Times New Roman';\">C<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"","questionId":"422350","questionNum":"1","score":"0.0","studAnswer":"A"},{"answer":"<span style=\"font-family: 'Times New Roman';\">C<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"","questionId":"422351","questionNum":"2","score":"0.0","studAnswer":"B"},{"answer":"<span style=\"font-family: 'Times New Roman';\">A<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"","questionId":"422352","questionNum":"3","score":"0.0","studAnswer":"C"},{"answer":"<span style=\"font-family: 'Times New Roman';\">B<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"","questionId":"422353","questionNum":"4","score":"0.0","studAnswer":"D"},
     * {"answer":"<span style=\"font-family: 'Times New Roman';\">&ldquo;舒&rdquo;是舒展开阔之意，它既写出了南方天空的开阔，又写出了作者心中的舒畅开朗，景和情有机地融合在了一起。<\/span>","isOption":"1","maxScore":"6.0","noSpanAnswer":"","questionId":"422355","questionNum":"6","score":"4.0","studAnswer":"https://img.xinjiaoyu.com/jby_files/test/mark_server/E98205FD0E4644FDA76013EC45D9B9D1.png"},
     * {"answer":"<span style=\"font-family: 'Times New Roman';\">(示例)远望群山上那高高低低的层层枫树林，它们经过秋风的吹拂和严霜的侵蚀，已经变成红色，似无尽的丹砂在缓缓流动，又似一团团炽烈的火焰在熊熊燃烧。近看那波涛滚滚的湘江，一片碧绿，清澈见底，秋水与长天浑然一色。无数船只在湘江上南来北往，竞相行驶。好一幅美丽壮观的&ldquo;湘江扬帆图&rdquo;！<\/span>","isOption":"1","maxScore":"40.0","noSpanAnswer":"","questionId":"422356","questionNum":"7","score":"40.0","studAnswer":"https://img.xinjiaoyu.com/jby_files/test/mark_server/B1124B004A8D49C5AF90D8826E74BBCA.png"}],"schId":"6a349f665db2496c9e48036d770c6e40","sheetId":"16001644495714Gjy3","studCode":"99952","studExamCode":"99952","studId":"5914144896ff4ac5934c400f3969c146","studName":"学生52","studNum":"","subjectScore":"44"},{"classAvgScore":"44.0","classBeatNum":"1","classId":"bf0b8650db11434195780f6f09946d71","classMaxScore":"44","classMinScore":"","className":"2班","classRank":"1","courseId":"fab6c26483dd26d2dbf4995ece9acdb3","courseName":"语文","examId":"6bfe14f69ba949bb944cdb2c3e4d63be","gradeAvgScore":"39.0","gradeBeatNum":"3","gradeMaxScore":"50","gradeRank":"3","id":"4f76dd9eb0ee7172a0e33d19acf41367","jointRank":"","myScore":"44","objectScore":"0","paperId":"26521da261cd4f3f969f23f591a6775f","questList":[{"answer":"<span style=\"font-family: 'Times New Roman';\">C<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"","questionId":"422350","questionNum":"1","score":"0.0","studAnswer":"A"},{"answer":"<span style=\"font-family: 'Times New Roman';\">C<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"","questionId":"422351","questionNum":"2","score":"0.0","studAnswer":"B"},{"answer":"<span style=\"font-family: 'Times New Roman';\">A<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"","questionId":"422352","questionNum":"3","score":"0.0","studAnswer":"C"},
     * {"answer":"<span style=\"font-family: 'Times New Roman';\">B<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"","questionId":"422353","questionNum":"4","score":"0.0","studAnswer":"D"},{"answer":"<span style=\"font-family: 'Times New Roman';\">(示例)远望群山上那高高低低的层层枫树林，它们经过秋风的吹拂和严霜的侵蚀，已经变成红色，似无尽的丹砂在缓缓流动，又似一团团炽烈的火焰在熊熊燃烧。近看那波涛滚滚的湘江，一片碧绿，清澈见底，秋水与长天浑然一色。无数船只在湘江上南来北往，竞相行驶。好一幅美丽壮观的&ldquo;湘江扬帆图&rdquo;！<\/span>","isOption":"1","maxScore":"40.0","noSpanAnswer":"","questionId":"422356","questionNum":"7","score":"35.0","studAnswer":"https://img.xinjiaoyu.com/jby_files/test/mark_server/D9898BA4CEC8469BB975EE4E232FEB31.png"}],
     * "schId":"6a349f665db2496c9e48036d770c6e40","sheetId":"16001644050924VGeG","studCode":"99002","studExamCode":"99002","studId":"790c2d764b9c40a48ff73b8176110661","studName":"学生2","studNum":"","subjectScore":"44"},{"classAvgScore":"46.5","classBeatNum":"0","classId":"67574d7db09c46088ed05fa5da312eb8","classMaxScore":"50","classMinScore":"","className":"3班","classRank":"2","courseId":"fab6c26483dd26d2dbf4995ece9acdb3","courseName":"语文","examId":"6bfe14f69ba949bb944cdb2c3e4d63be","gradeAvgScore":"39.0","gradeBeatNum":"1","gradeMaxScore":"50","gradeRank":"5","id":"404bfb6f859fcd1091e505540c58dbeb","jointRank":"","myScore":"43","objectScore":"0","paperId":"26521da261cd4f3f969f23f591a6775f","questList":[{"answer":"<span style=\"font-family: 'Times New Roman';\">C<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"","questionId":"422350","questionNum":"1","score":"0.0","studAnswer":"A"},{"answer":"<span style=\"font-family: 'Times New Roman';\">C<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"","questionId":"422351","questionNum":"2","score":"0.0","studAnswer":"B"},{"answer":"<span style=\"font-family: 'Times New Roman';\">A<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"","questionId":"422352","questionNum":"3","score":"0.0","studAnswer":"C"},{"answer":"<span style=\"font-family: 'Times New Roman';\">B<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"","questionId":"422353","questionNum":"4","score":"0.0","studAnswer":"D"},{"answer":"<span style=\"font-family: 'Times New Roman';\">(示例)远望群山上那高高低低的层层枫树林，它们经过秋风的吹拂和严霜的侵蚀，已经变成红色，似无尽的丹砂在缓缓流动，又似一团团炽烈的火焰在熊熊燃烧。近看那波涛滚滚的湘江，一片碧绿，清澈见底，秋水与长天浑然一色。无数船只在湘江上南来北往，竞相行驶。好一幅美丽壮观的&ldquo;湘江扬帆图&rdquo;！<\/span>","isOption":"1","maxScore":"40.0","noSpanAnswer":"","questionId":"422356","questionNum":"7","score":"40.0","studAnswer":"https://img.xinjiaoyu.com/jby_files/test/mark_server/B28CDFA1E8AC41F2A2F175FC0EBB686E.png"}],"schId":"6a349f665db2496c9e48036d770c6e40","sheetId":"1600164419558w3m13","studCode":"99003","studExamCode":"99003","studId":"e6df6b59a14e468ca6049f57e08a6836","studName":"学生3","studNum":"","subjectScore":"43"},
     * {"classAvgScore":"26.5","classBeatNum":"0","classId":"806fdcda96bf4ca69ea55882e907a327","classMaxScore":"49","classMinScore":"","className":"1班","classRank":"2","courseId":"fab6c26483dd26d2dbf4995ece9acdb3","courseName":"语文","examId":"6bfe14f69ba949bb944cdb2c3e4d63be","gradeAvgScore":"39.0","gradeBeatNum":"0","gradeMaxScore":"50","gradeRank":"6","id":"3eff2a2676b8f06a0ab6f61d3ba0d41d","jointRank":"","myScore":"4","objectScore":"0","paperId":"26521da261cd4f3f969f23f591a6775f",
     * "questList":[{"answer":"<span style=\"font-family: 'Times New Roman';\">C<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"","questionId":"422350","questionNum":"1","score":"0.0","studAnswer":"A"},{"answer":"<span style=\"font-family: 'Times New Roman';\">C<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"","questionId":"422351","questionNum":"2","score":"0.0","studAnswer":"B"},{"answer":"<span style=\"font-family: 'Times New Roman';\">A<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"","questionId":"422352","questionNum":"3","score":"0.0","studAnswer":"C"},{"answer":"<span style=\"font-family: 'Times New Roman';\">B<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"","questionId":"422353","questionNum":"4","score":"0.0","studAnswer":"D"},{"answer":"<span style=\"font-family: 'Times New Roman';\">(示例)远望群山上那高高低低的层层枫树林，它们经过秋风的吹拂和严霜的侵蚀，已经变成红色，似无尽的丹砂在缓缓流动，又似一团团炽烈的火焰在熊熊燃烧。近看那波涛滚滚的湘江，一片碧绿，清澈见底，秋水与长天浑然一色。无数船只在湘江上南来北往，竞相行驶。好一幅美丽壮观的&ldquo;湘江扬帆图&rdquo;！<\/span>","isOption":"1","maxScore":"40.0","noSpanAnswer":"","questionId":"422356","questionNum":"7","score":"1.0","studAnswer":"https://img.xinjiaoyu.com/jby_files/test/mark_server/38D6915E09D046A4B2D6AF9D7C27BF0A.png"}],"schId":"6a349f665db2496c9e48036d770c6e40","sheetId":"1600164386364YK7y5","studCode":"99001","studExamCode":"99001","studId":"3fcdc66d0dc14f8eae682926da7299a2","studName":"学生1","studNum":"","subjectScore":"4"}]
     * size : 10
     * total : 6
     */

    private String code;
    private String current;
    private String msg;
    private String pages;
    private String size;
    private String total;
    private List<DataBean> data;
    private List<RowsBean> rows;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class DataBean {
        /**
         * answer : <span style="font-family: 'Times New Roman';">C</span>
         * isOption : 2
         * maxScore : 3.0
         * noSpanAnswer : C
         * questionId : 422350
         * questionNum : 1
         * studAnswer :
         */

        private String answer;
        private String isOption;
        private String maxScore;
        private String noSpanAnswer;
        private String questionId;
        private String questionNum;
        private String studAnswer;

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
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

        public String getNoSpanAnswer() {
            return noSpanAnswer;
        }

        public void setNoSpanAnswer(String noSpanAnswer) {
            this.noSpanAnswer = noSpanAnswer;
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

        public String getStudAnswer() {
            return studAnswer;
        }

        public void setStudAnswer(String studAnswer) {
            this.studAnswer = studAnswer;
        }
    }

    public static class RowsBean {
        /**
         * classAvgScore : 46.5
         * classBeatNum : 1
         * classId : 67574d7db09c46088ed05fa5da312eb8
         * classMaxScore : 50
         * classMinScore :
         * className : 3班
         * classRank : 1
         * courseId : fab6c26483dd26d2dbf4995ece9acdb3
         * courseName : 语文
         * examId : 6bfe14f69ba949bb944cdb2c3e4d63be
         * gradeAvgScore : 39.0
         * gradeBeatNum : 5
         * gradeMaxScore : 50
         * gradeRank : 1
         * id : 1ae20e3a9fc305d21235fee9dd6a4ceb
         * jointRank :
         * myScore : 50
         * objectScore : 0
         * paperId : 26521da261cd4f3f969f23f591a6775f
         * questList : [{"answer":"<span style=\"font-family: 'Times New Roman';\">C<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"","questionId":"422350","questionNum":"1","score":"0.0","studAnswer":"A"},{"answer":"<span style=\"font-family: 'Times New Roman';\">C<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"","questionId":"422351","questionNum":"2","score":"0.0","studAnswer":"B"},{"answer":"<span style=\"font-family: 'Times New Roman';\">A<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"","questionId":"422352","questionNum":"3","score":"0.0","studAnswer":"C"},{"answer":"<span style=\"font-family: 'Times New Roman';\">B<\/span>","isOption":"2","maxScore":"3.0","noSpanAnswer":"","questionId":"422353","questionNum":"4","score":"0.0","studAnswer":"D"},{"answer":"<span style=\"font-family: 'Times New Roman';\">(1)独立寒秋　湘江北去　橘子洲头　(2)怅寥廓　问苍茫大地　谁主沉浮　(3)曾记否　到中流击水　浪遏飞舟<\/span>","isOption":"1","maxScore":"9.0","noSpanAnswer":"","questionId":"422354","questionNum":"5","score":"4.0","studAnswer":"https://img.xinjiaoyu.com/jby_files/test/mark_server/EDF7EA5740544EDCB96FF57E3517AC3E.png"},{"answer":"<span style=\"font-family: 'Times New Roman';\">&ldquo;舒&rdquo;是舒展开阔之意，它既写出了南方天空的开阔，又写出了作者心中的舒畅开朗，景和情有机地融合在了一起。<\/span>","isOption":"1","maxScore":"6.0","noSpanAnswer":"","questionId":"422355","questionNum":"6","score":"6.0","studAnswer":"https://img.xinjiaoyu.com/jby_files/test/mark_server/534DA6AA990D4027935AE2770D26F633.png"},{"answer":"<span style=\"font-family: 'Times New Roman';\">(示例)远望群山上那高高低低的层层枫树林，它们经过秋风的吹拂和严霜的侵蚀，已经变成红色，似无尽的丹砂在缓缓流动，又似一团团炽烈的火焰在熊熊燃烧。近看那波涛滚滚的湘江，一片碧绿，清澈见底，秋水与长天浑然一色。无数船只在湘江上南来北往，竞相行驶。好一幅美丽壮观的&ldquo;湘江扬帆图&rdquo;！<\/span>","isOption":"1","maxScore":"40.0","noSpanAnswer":"","questionId":"422356","questionNum":"7","score":"40.0","studAnswer":"https://img.xinjiaoyu.com/jby_files/test/mark_server/80C7195413D04D25AB9A621FEA302FC2.png"}]
         * schId : 6a349f665db2496c9e48036d770c6e40
         * sheetId : 1600164465670DM9su
         * studCode : 99953
         * studExamCode : 99953
         * studId : a83a7e4ab8db402b882c1dfe65134925
         * studName : 学生53
         * studNum :
         * subjectScore : 50
         */

        private String classAvgScore;
        private String classBeatNum;
        private String classId;
        private String classMaxScore;
        private String classMinScore;
        private String className;
        private String classRank;
        private String courseId;
        private String courseName;
        private String examId;
        private String gradeAvgScore;
        private String gradeBeatNum;
        private String gradeMaxScore;
        private String gradeRank;
        private String id;
        private String jointRank;
        private String myScore;
        private String objectScore;
        private String paperId;
        private String schId;
        private String sheetId;
        private String studCode;
        private String studExamCode;
        private String studId;
        private String studName;
        private String studNum;
        private String subjectScore;
        private List<QuestListBean> questList;

        public String getClassAvgScore() {
            return classAvgScore;
        }

        public void setClassAvgScore(String classAvgScore) {
            this.classAvgScore = classAvgScore;
        }

        public String getClassBeatNum() {
            return classBeatNum;
        }

        public void setClassBeatNum(String classBeatNum) {
            this.classBeatNum = classBeatNum;
        }

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getClassMaxScore() {
            return classMaxScore;
        }

        public void setClassMaxScore(String classMaxScore) {
            this.classMaxScore = classMaxScore;
        }

        public String getClassMinScore() {
            return classMinScore;
        }

        public void setClassMinScore(String classMinScore) {
            this.classMinScore = classMinScore;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getClassRank() {
            return classRank;
        }

        public void setClassRank(String classRank) {
            this.classRank = classRank;
        }

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getExamId() {
            return examId;
        }

        public void setExamId(String examId) {
            this.examId = examId;
        }

        public String getGradeAvgScore() {
            return gradeAvgScore;
        }

        public void setGradeAvgScore(String gradeAvgScore) {
            this.gradeAvgScore = gradeAvgScore;
        }

        public String getGradeBeatNum() {
            return gradeBeatNum;
        }

        public void setGradeBeatNum(String gradeBeatNum) {
            this.gradeBeatNum = gradeBeatNum;
        }

        public String getGradeMaxScore() {
            return gradeMaxScore;
        }

        public void setGradeMaxScore(String gradeMaxScore) {
            this.gradeMaxScore = gradeMaxScore;
        }

        public String getGradeRank() {
            return gradeRank;
        }

        public void setGradeRank(String gradeRank) {
            this.gradeRank = gradeRank;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getJointRank() {
            return jointRank;
        }

        public void setJointRank(String jointRank) {
            this.jointRank = jointRank;
        }

        public String getMyScore() {
            return myScore;
        }

        public void setMyScore(String myScore) {
            this.myScore = myScore;
        }

        public String getObjectScore() {
            return objectScore;
        }

        public void setObjectScore(String objectScore) {
            this.objectScore = objectScore;
        }

        public String getPaperId() {
            return paperId;
        }

        public void setPaperId(String paperId) {
            this.paperId = paperId;
        }

        public String getSchId() {
            return schId;
        }

        public void setSchId(String schId) {
            this.schId = schId;
        }

        public String getSheetId() {
            return sheetId;
        }

        public void setSheetId(String sheetId) {
            this.sheetId = sheetId;
        }

        public String getStudCode() {
            return studCode;
        }

        public void setStudCode(String studCode) {
            this.studCode = studCode;
        }

        public String getStudExamCode() {
            return studExamCode;
        }

        public void setStudExamCode(String studExamCode) {
            this.studExamCode = studExamCode;
        }

        public String getStudId() {
            return studId;
        }

        public void setStudId(String studId) {
            this.studId = studId;
        }

        public String getStudName() {
            return studName;
        }

        public void setStudName(String studName) {
            this.studName = studName;
        }

        public String getStudNum() {
            return studNum;
        }

        public void setStudNum(String studNum) {
            this.studNum = studNum;
        }

        public String getSubjectScore() {
            return subjectScore;
        }

        public void setSubjectScore(String subjectScore) {
            this.subjectScore = subjectScore;
        }

        public List<QuestListBean> getQuestList() {
            return questList;
        }

        public void setQuestList(List<QuestListBean> questList) {
            this.questList = questList;
        }

        public static class QuestListBean {
            /**
             * answer : <span style="font-family: 'Times New Roman';">C</span>
             * isOption : 2
             * maxScore : 3.0
             * noSpanAnswer :
             * questionId : 422350
             * questionNum : 1
             * score : 0.0
             * studAnswer : A
             */

            private String answer;
            private String isOption;
            private String maxScore;
            private String noSpanAnswer;
            private String questionId;
            private String questionNum;
            private String score;
            private String studAnswer;

            public String getAnswer() {
                return answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
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

            public String getNoSpanAnswer() {
                return noSpanAnswer;
            }

            public void setNoSpanAnswer(String noSpanAnswer) {
                this.noSpanAnswer = noSpanAnswer;
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

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getStudAnswer() {
                return studAnswer;
            }

            public void setStudAnswer(String studAnswer) {
                this.studAnswer = studAnswer;
            }
        }
    }
}
