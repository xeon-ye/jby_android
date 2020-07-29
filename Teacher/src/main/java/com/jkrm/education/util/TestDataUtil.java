package com.jkrm.education.util;

import com.hzw.baselib.project.student.bean.MarkBean;
import com.hzw.baselib.util.AwConvertUtil;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.util.RandomValueUtil;
import com.jkrm.education.bean.DaoMarkCommonScoreUseBean;
import com.jkrm.education.bean.scan.ScanBean;
import com.jkrm.education.bean.test.TestFamousTeacherLectureBean;
import com.jkrm.education.bean.test.TestMarkClassesBean;
import com.jkrm.education.bean.test.TestMarkCommonUseScoreBean;
import com.jkrm.education.bean.test.TestMarkCourseBean;
import com.jkrm.education.bean.test.TestMarkDetailBean;
import com.jkrm.education.bean.test.TestMarkDetailWithStudentBean;
import com.jkrm.education.bean.test.TestMarkHomeworkDetailBean;
import com.jkrm.education.bean.test.TestMarkHomeworkQuestionSingleScoreBean;
import com.jkrm.education.bean.test.TestMarkKindsBean;
import com.jkrm.education.bean.test.TestMarkPhaseBean;
import com.jkrm.education.bean.test.TestMarkBean;
import com.jkrm.education.bean.test.TestMeClassesBean;
import com.jkrm.education.bean.test.TestMeClassesContactBean;
import com.jkrm.education.bean.test.TestQuestionBean;
import com.jkrm.education.bean.test.TestStudentAchievementAllQuestionBean;
import com.jkrm.education.bean.test.TestStudentChoiceQuestionSeeBean;
import com.jkrm.education.bean.todo.TodoBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hzw on 2019/5/9.
 */

public class TestDataUtil {

    public static final String[] videoUrls = {
            "http://vfx.mtime.cn/Video/2019/05/24/mp4/190524105156675387.mp4",
            "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
            "https://www.apple.com/105/media/us/iphone-x/2017/01df5b43-28e4-4848-bf20-490c34a926a7/films/feature/iphone-x-feature-tpl-cc-us-20170912_1280x720h.mp4",
            "http://vfx.mtime.cn/Video/2017/03/31/mp4/170331093811717750.mp4",
            "http://vfx.mtime.cn/Video/2019/05/22/mp4/190522181531032903.mp4",
            "http://vfx.mtime.cn/Video/2019/05/13/mp4/190513120435559365.mp4",
            "http://vfx.mtime.cn/Video/2019/05/09/mp4/190509143927851110.mp4"
    };

    public static final String[] imgUris = {
            "http://www.91taoke.com/images/photo/1440730460.png",
            "http://www.91taoke.com/images/photo/1440729843.png",
            "http://www.91taoke.com/images/photo/1404436094.png",
            "http://www.91taoke.com/images/photo/1403253776.png",
            "http://www.91taoke.com/images/photo/1436496281.png",
            "http://www.91taoke.com/images/photo/1409726834.png",
            "http://www.91taoke.com/images/photo/1403505587.png",
            "http://www.91taoke.com/images/photo/1404093198.png"
    };


    public static final String[] choiceAnswers = {"A", "B", "C", "D", "AB", "CD", "ACD", "BC", "ABCD"};

    /**
     * 创建测试数据 --- 待办事项列表
     * @return
     */
    public static List<TodoBean> createTotoList() {
        int randNum = RandomValueUtil.getNum(0, 6);

        List<TodoBean> list = new ArrayList<>();
        for(int i=1; i< randNum; i++) {
            TodoBean todoBean = new TodoBean();
            todoBean.setId(i);
            todoBean.setDate(AwDateUtils.formatDate16.format(new Date(System.currentTimeMillis() - i * 1000 * 60)));
            switch (i) {
                case 2:
                    todoBean.setStatus(1);
                    todoBean.setTitle("新的分班记录 操作待确认");
                    break;
                default:
                    todoBean.setStatus(0);
                    todoBean.setTitle("19日语文作业 作业待批阅 第" + i + "条");
                    break;
            }
            list.add(todoBean);
        }
        return list;
    }

    /**
     * 创建测试数据 --- 我的班级列表
     * @return
     */
    public static List<TestMeClassesBean> createClassesList() {
        int randNum = RandomValueUtil.getNum(2, 5);

        List<TestMeClassesBean> list = new ArrayList<>();
        for(int i=1; i< randNum; i++) {
            TestMeClassesBean meClassesBean = new TestMeClassesBean();
            meClassesBean.setId(i);
            meClassesBean.setClasses("高一 " + i + "班");
            meClassesBean.setCourse("语文");
            meClassesBean.setContactList(createClassesContactList());
            list.add(meClassesBean);
        }
        return list;
    }

    /**
     * 创建测试数据 --- 我的班级通讯录列表
     * @return
     */
    public static List<TestMeClassesContactBean> createClassesContactList() {
        int randNum = RandomValueUtil.getNum(20, 50);

        List<TestMeClassesContactBean> list = new ArrayList<>();
        for(int i=1; i< randNum; i++) {
            TestMeClassesContactBean meClassesContactBean = new TestMeClassesContactBean();
            meClassesContactBean.setStudentId(String.valueOf(i));
            meClassesContactBean.setName(RandomValueUtil.getChineseName());
            meClassesContactBean.setMobile(RandomValueUtil.getTelephone());
            list.add(meClassesContactBean);
        }
        return list;
    }

    /**
     * 创建测试数据 --- 批阅列表
     * @return
     */
    public static List<TestMarkBean> createMarkingList() {
        int randNum = RandomValueUtil.getNum(20, 50);

        List<TestMarkBean> list = new ArrayList<>();
        for(int i=1; i< randNum; i++) {
            TestMarkBean markingBean = new TestMarkBean();
            markingBean.setId(i);
            markingBean.setClasses("高一" + i + "班");
            markingBean.setCourse("语文");
            markingBean.setDate(AwDateUtils.formatDate16.format(new Date(System.currentTimeMillis() - i * 1000 * 60)));
            if(i%2 == 0) {
                markingBean.setPercent(0);
            } else {
                markingBean.setPercent(i);
            }
            list.add(markingBean);
        }
        return list;
    }

    /**
     * 创建测试数据 --- 扫描列表
     * @return
     */
    public static ScanBean createScanList(int totalPage, int scanedPage) {
        ScanBean scanBean = new ScanBean("高一三班", totalPage, scanedPage);
        return scanBean;
    }

    /**
     * 创建测试数据 --- 批阅筛选 分类
     * @return
     */
    public static List<TestMarkKindsBean> createMarkKindsList() {
        List<TestMarkKindsBean> list = new ArrayList<>();
        list.add(new TestMarkKindsBean(true, "全部"));
        list.add(new TestMarkKindsBean(false, "作业"));
//        list.add(new TestMarkKindsBean(false, "考试"));
        return list;
    }

    /**
     * 创建测试数据 --- 批阅筛选 学段年级
     * @return
     */
    public static List<TestMarkPhaseBean> createMarkPhaseList() {
        List<TestMarkPhaseBean> list = new ArrayList<>();
        for(int i=0; i<3; i++) {
            TestMarkPhaseBean bean = new TestMarkPhaseBean();
            switch (i) {
                case 0:
                    bean.setChecked(true);
                    bean.setName("高一");
                    break;
                case 1:
                    bean.setChecked(false);
                    bean.setName("高二");
                    break;
                case 2:
                    bean.setChecked(false);
                    bean.setName("高三");
                    break;
            }
            list.add(bean);
        }
        return list;
    }

    /**
     * 创建测试数据 --- 批阅筛选 班级
     * @return
     */
    public static List<TestMarkClassesBean> createMarkClassesList() {
        List<TestMarkClassesBean> list = new ArrayList<>();
        for(int i=0; i<8; i++) {
            TestMarkClassesBean bean = new TestMarkClassesBean();
            switch (i) {
                case 0:
                    bean.setChecked(true);
                    bean.setName("全部");
                    break;
                default:
                    bean.setChecked(false);
                    bean.setName(i + "班");
                    break;
            }
            list.add(bean);
        }
        return list;
    }

    /**
     * 创建测试数据 --- 批阅筛选 科目
     * @return
     */
    public static List<TestMarkCourseBean> createMarkCourseList() {
        List<TestMarkCourseBean> list = new ArrayList<>();
        for(int i=0; i<2; i++) {
            TestMarkCourseBean bean = new TestMarkCourseBean();
            switch (i) {
                case 0:
                    bean.setChecked(true);
                    bean.setName("全部");
                    break;
                default:
                    bean.setChecked(false);
                    bean.setName("语文");
                    break;
            }
            list.add(bean);
        }
        return list;
    }

    /**
     * 创建测试数据 --- 批阅作业详情 ---题目列表
     * @return
     */
    public static TestMarkHomeworkDetailBean createMarkHomeworkDetailBean() {
        int randNum = RandomValueUtil.getNum(15, 25);
        TestMarkHomeworkDetailBean detailBean = new TestMarkHomeworkDetailBean();
        detailBean.setClassesName("高一1班语文");
        detailBean.setTotalCount(55);
        detailBean.setSubmitCount(33);
        List<TestQuestionBean> list = new ArrayList<>();
        for(int i=1; i< randNum; i++) {
            TestQuestionBean questionBean = new TestQuestionBean();
            questionBean.setQuestionNum("第" + i + "题");
            questionBean.setRate(i);
            questionBean.setTotalScore(i);
            if(i < 5) {
                questionBean.setRightAnswer("C");
                questionBean.setType(1);
            } else if(i < 8){
                questionBean.setType(2);
            } else if(i < 12) {
                questionBean.setType(3);
            } else {
                questionBean.setType(4);
            }
            list.add(questionBean);
        }
        detailBean.setQuestionList(list);
        return detailBean;
    }

    public static List<String> createHomeworkDetailType() {
        List<String> list = new ArrayList<>();
        list.add("按题号正序");
        list.add("按得分率倒序");
        list.add("按需讲解人数倒序");
        return list;
    }


    public static List<MarkBean> createMarkByQuestion(){
        List<MarkBean> list = new ArrayList<>();
        list.add(new MarkBean(true,"按题批阅"));
        list.add(new MarkBean(false,"按人批阅"));
        return list;
    }

    public static List<MarkBean> createHandleSwitch(){
        List<MarkBean> list = new ArrayList<>();
        list.add(new MarkBean(true,"分步赋分"));
        list.add(new MarkBean(false,"一键赋分"));
        return list;
    }

    /**
     * 创建测试数据 --- 作业详情学生作答(答题详情)选择列表
     * @return
     */
    public static List<TestMarkHomeworkQuestionSingleScoreBean> createkHomeworkQuestionSingleScoreList(boolean choiceQuestion) {
        int randNum = RandomValueUtil.getNum(10, 20);
        String[] results = {"A", "B", "C", "D"};
        List<TestMarkHomeworkQuestionSingleScoreBean> list = new ArrayList<>();
        for(int i=1; i< randNum; i++) {
            TestMarkHomeworkQuestionSingleScoreBean bean = new TestMarkHomeworkQuestionSingleScoreBean();
            bean.setStudendId(i);
            bean.setName(RandomValueUtil.getChineseName());
            bean.setResult(choiceQuestion ? results[RandomValueUtil.getNum(0, 3)] : RandomValueUtil.getNum(0, 15) + "");
            list.add(bean);
        }
        return list;
    }

    /**
     * 创建测试数据 --- 批阅详情 ---题目列表
     * @return
     */
    public static List<TestMarkDetailBean> createMarkDetailBeanList() {
        int randNum = RandomValueUtil.getNum(15, 25);
        List<TestMarkDetailBean> list = new ArrayList<>();
        TestMarkDetailBean bean1 = new TestMarkDetailBean();
        bean1.setQuestionNum("第1-5题");
        bean1.setType(1);
        bean1.setMarkedCount(RandomValueUtil.getNum(1, 20));
        bean1.setPercent(RandomValueUtil.getNum(39, 100));
        list.add(bean1);
        for(int i=6; i< randNum; i++) {
            TestMarkDetailBean bean = new TestMarkDetailBean();
            bean.setQuestionNum("第" + i + "题");
            bean.setMarkedCount(RandomValueUtil.getNum(1, 20));
            bean.setPercent(i % 2 == 0 ? RandomValueUtil.getNum(1, 50) : 100);
            if(i < 10 && i >= 6){
                bean.setType(2);
            } else if(i < 15 && i >=10) {
                bean.setType(3);
            } else {
                bean.setType(4);
            }
            list.add(bean);
        }
        return list;
    }


    /**
     * 创建测试数据 --- 批阅详情按人选择 学生状态
     * @return
     */
    public static List<TestMarkDetailWithStudentBean> createkMarkDetailWithStudentBeanList() {
        int randNum = RandomValueUtil.getNum(10, 20);

        List<TestMarkDetailWithStudentBean> list = new ArrayList<>();
        for(int i=1; i< randNum; i++) {
            TestMarkDetailWithStudentBean bean = new TestMarkDetailWithStudentBean();
            bean.setStudendId(i);
            bean.setName(RandomValueUtil.getChineseName());
            bean.setStatus(RandomValueUtil.getNum(0, 15) % 2 == 0 ? "已批阅" : "未批阅");
            if(i == 3 || i == 6) {
                bean.setStatus("部分批阅");
            }
            list.add(bean);
        }
        return list;
    }

    /**
     * 创建测试数据 --- 批阅详情 查看成绩
     * @return
     */
    public static List<TestStudentAchievementAllQuestionBean> createkStudentAchievementAllQuestionBeanList() {
        int randNum = RandomValueUtil.getNum(30, 50);
        List<TestStudentAchievementAllQuestionBean> list = new ArrayList<>();
        for(int i=1; i< randNum; i++) {
            TestStudentAchievementAllQuestionBean bean = new TestStudentAchievementAllQuestionBean();
            bean.setStudentId(i + "");
            bean.setName(RandomValueUtil.getChineseName());
            bean.setClassesPosition(i + "");
            bean.setPhasePosition(i + "");
            bean.setScore(RandomValueUtil.getNum(10, 100) + "");
            bean.setOther1(choiceAnswers[RandomValueUtil.getNum(0, choiceAnswers.length - 1)]);
            bean.setOther2(choiceAnswers[RandomValueUtil.getNum(0, choiceAnswers.length - 1)]);
            bean.setOther3(choiceAnswers[RandomValueUtil.getNum(0, choiceAnswers.length - 1)]);
            bean.setOther4(RandomValueUtil.getNum(0, 15) + "");
            bean.setOther5(RandomValueUtil.getNum(0, 15) + "");
            list.add(bean);
        }
        TestStudentAchievementAllQuestionBean bean = new TestStudentAchievementAllQuestionBean();
        bean.setStudentId("");
        bean.setName("标准");
        bean.setClassesPosition("");
        bean.setPhasePosition("");
        bean.setScore("");
        bean.setOther1("A");
        bean.setOther2("C");
        bean.setOther3("D");
        bean.setOther4("5");
        bean.setOther5("10");
        list.add(bean);
        return list;
    }

    /**
     * 创建测试数据 --- 批阅详情 查阅选择题
     * @return
     */
    public static List<TestStudentChoiceQuestionSeeBean> createkStudentChoiceQuestionSeeBeanList() {
        int randNum = RandomValueUtil.getNum(30, 50);

        List<TestStudentChoiceQuestionSeeBean> list = new ArrayList<>();
        for(int i=1; i< randNum; i++) {
            TestStudentChoiceQuestionSeeBean bean = new TestStudentChoiceQuestionSeeBean();
            bean.setStudentId(i + "");
            bean.setName(RandomValueUtil.getChineseName());
            bean.setOther1(choiceAnswers[RandomValueUtil.getNum(0, choiceAnswers.length - 1)]);
            bean.setOther2(choiceAnswers[RandomValueUtil.getNum(0, choiceAnswers.length - 1)]);
            bean.setOther3(choiceAnswers[RandomValueUtil.getNum(0, choiceAnswers.length - 1)]);
            bean.setOther4(choiceAnswers[RandomValueUtil.getNum(0, choiceAnswers.length - 1)]);
            bean.setOther5(choiceAnswers[RandomValueUtil.getNum(0, choiceAnswers.length - 1)]);
            bean.setOther6(choiceAnswers[RandomValueUtil.getNum(0, choiceAnswers.length - 1)]);
            bean.setOther7(choiceAnswers[RandomValueUtil.getNum(0, choiceAnswers.length - 1)]);
            list.add(bean);
        }
        TestStudentChoiceQuestionSeeBean bean = new TestStudentChoiceQuestionSeeBean();
        bean.setStudentId("");
        bean.setName("标准");
        bean.setOther1("A");
        bean.setOther2("B");
        bean.setOther3("C");
        bean.setOther4("D");
        bean.setOther5("AC");
        bean.setOther6("BD");
        bean.setOther7("ABCD");
        list.add(bean);
        return list;
    }

    /**
     * 后台返回批阅分值时
     * @param scores
     * @return
     */
    public static  List<TestMarkCommonUseScoreBean> createMarkCommonUseScoreBeanByList(String scores, List<String> commonList){
        List<TestMarkCommonUseScoreBean> list = new ArrayList<>();
        String[] split=new String[]{};
        if(!AwDataUtil.isEmpty(scores)) {
             split = scores.split(",");
        }
        if(!AwDataUtil.isEmpty(commonList)) {
            for(String temp : commonList) {
                for (String s : split) {
                    if(s.equals(temp)){
                        if(Float.parseFloat(temp)>Float.parseFloat(split[split.length-1])){
                            break;
                        }
                        TestMarkCommonUseScoreBean bean = new TestMarkCommonUseScoreBean();
                        bean.setScore(temp);
                        bean.setHandleModify(true);
                        list.add(bean);
                    }
                }

            }
        }
        for(int i=0; i< split.length; i++) {
            TestMarkCommonUseScoreBean bean = new TestMarkCommonUseScoreBean();
            bean.setScore(split[i]);
            boolean isExist = false;
            if(!AwDataUtil.isEmpty(commonList)) {
                for(String temp : commonList) {
                    if(temp.equals(split[i])) {
                        isExist = true;
                        break;
                    }
                }
            }
            if(isExist) {
                continue;
            }
            bean.setHandleModify(false);
            list.add(bean);
        }

    /*    for (int i = 0; i < split.length; i++) {
            TestMarkCommonUseScoreBean bean = new TestMarkCommonUseScoreBean();
            bean.setScore(split[i]);
            bean.setHandleModify(false);
            if(!AwDataUtil.isEmpty(commonList)){
                for (String s : commonList) {
                    if(s.equals(split[i])){
                        bean.setHandleModify(true);
                    }
                }
            }
            list.add(bean);
        }*/
        return list;
    }

    /**
     * 创建测试数据 --- 批阅 常用分数
     * @return
     */
    public static List<TestMarkCommonUseScoreBean> createkMarkCommonUseScoreBeanList(float maxScore, List<String> commonList) {

        List<TestMarkCommonUseScoreBean> list = new ArrayList<>();
        if(!AwDataUtil.isEmpty(commonList)) {
            for(String temp : commonList) {
                if(Float.parseFloat(temp)>maxScore){
                    break;
                }
                TestMarkCommonUseScoreBean bean = new TestMarkCommonUseScoreBean();
                bean.setScore(temp);
                bean.setHandleModify(true);
                list.add(bean);
            }
        }
//        int randNum = RandomValueUtil.getNum(minScore, maxScore);

        String maxScoreStr = AwConvertUtil.rvZeroAndDot(String.valueOf(maxScore));
        if(maxScoreStr.contains(".")) {
            for(int i=0; i<= maxScore + 0.5; i++) {
                if(i > maxScore) {
                    break;
                }
                TestMarkCommonUseScoreBean bean = new TestMarkCommonUseScoreBean();
                bean.setScore(i + "");
                boolean isExist = false;
                if(!AwDataUtil.isEmpty(commonList)) {
                    for(String temp : commonList) {
                        if(temp.equals(String.valueOf(i))) {
                            isExist = true;
                            break;
                        }
                    }
                }
                if(isExist) {
                    continue;
                }
                bean.setHandleModify(false);

                list.add(bean);

                if(i+0.5 <= maxScore) {
                    TestMarkCommonUseScoreBean bean2 = new TestMarkCommonUseScoreBean();
                    bean2.setScore(String.valueOf(i + 0.5));
                    boolean isExist2 = false;
                    if(!AwDataUtil.isEmpty(commonList)) {
                        for(String temp : commonList) {
                            if(temp.equals(String.valueOf(i))) {
                                isExist2 = true;
                                break;
                            }
                        }
                    }
                    if(isExist2) {
                        continue;
                    }
                    bean2.setHandleModify(false);
                    list.add(bean2);
                } else {
                    break;
                }
            }
        } else {
            for(int i=0; i<= maxScore; i++) {
                TestMarkCommonUseScoreBean bean = new TestMarkCommonUseScoreBean();
                bean.setScore(i + "");
                boolean isExist = false;
                if(!AwDataUtil.isEmpty(commonList)) {
                    for(String temp : commonList) {
                        if(temp.equals(String.valueOf(i))) {
                            isExist = true;
                            break;
                        }
                    }
                }
                if(isExist) {
                    continue;
                }
                bean.setHandleModify(false);
                list.add(bean);
            }
        }

        return list;
    }

    /**
     * 创建测试数据 --- 名师讲卷相关视频列表
     * @return
     */
    public static List<TestFamousTeacherLectureBean> createkFamousTeacherLectureBeanList() {
        int randNum = RandomValueUtil.getNum(5, 10);

        List<TestFamousTeacherLectureBean> list = new ArrayList<>();
        for(int i=1; i< randNum; i++) {
            TestFamousTeacherLectureBean bean = new TestFamousTeacherLectureBean();
            bean.setTitle("视频" + i);
            bean.setDesc("视频" + i + "简介内容");
            bean.setVideoUrl(videoUrls[RandomValueUtil.getNum(0, videoUrls.length - 1)]);
            bean.setImgUrl(imgUris[RandomValueUtil.getNum(0, imgUris.length - 1)]);
            list.add(bean);
        }
        return list;
    }

}
