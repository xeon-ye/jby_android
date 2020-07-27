package com.jkrm.education.util;

import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.util.MyDateUtil;
import com.hzw.baselib.util.RandomValueUtil;
import com.jkrm.education.bean.ErrorQuestionStatisticsBean;
import com.jkrm.education.bean.test.TestFamousTeacherLectureBean;
import com.jkrm.education.bean.test.TestMarkBean;
import com.jkrm.education.bean.test.TestMarkClassesBean;
import com.jkrm.education.bean.test.TestMarkCourseBean;
import com.jkrm.education.bean.test.TestMarkHomeworkDetailBean;
import com.jkrm.education.bean.test.TestMarkKindsBean;
import com.jkrm.education.bean.test.TestMarkPhaseBean;
import com.jkrm.education.bean.test.TestMeTeachersBean;
import com.jkrm.education.bean.test.TestMeClassesContactBean;
import com.jkrm.education.bean.test.TestQuestionBean;

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

    public static final String[] avatars = {
            "https://randomuser.me/api/portraits/women/57.jpg",
            "https://randomuser.me/api/portraits/women/34.jpg",
            "https://randomuser.me/api/portraits/women/50.jpg",
            "https://randomuser.me/api/portraits/women/93.jpg",
            "https://randomuser.me/api/portraits/women/85.jpg",
            "https://randomuser.me/api/portraits/women/82.jpg"
    };

    public static final String[] courses = {
            "语文", "数学", "英语", "物理", "化学", "生物", "地理"
    };

    public static final String[] phones = {
            "15600101627", "18617862923", "13292521520", "16601332886", "18511662323"
    };

    public static final String[] names = {
            "张老师", "梁老师", "邓老师", "田老师", "黄老师"
    };

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
            markingBean.setScore(RandomValueUtil.getNum(0, 100) + "");
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
     * 创建测试数据 --- 批阅筛选 分类 错题本
     * @return
     */
    public static List<TestMarkKindsBean> createMarkKindsList() {
        List<TestMarkKindsBean> list = new ArrayList<>();
        for(int i=0; i<2; i++) {
            TestMarkKindsBean bean = new TestMarkKindsBean();
            switch (i) {
                case 0:
                    bean.setChecked(true);
                    bean.setName("全部");
                    break;
                case 1:
                    bean.setChecked(false);
                    bean.setName("作业");
                    break;
            }
            list.add(bean);
        }
        TestMarkKindsBean bean = new TestMarkKindsBean();
        bean.setChecked(false);
        bean.setName("自定义");
        list.add(bean);
        return list;
    }

    /**
     * 创建数据 --- 错题本筛选 分类 错题本
     * @return
     */
    public static List<TestMarkKindsBean> createErrorQuestionKindsList() {
        List<TestMarkKindsBean> list = new ArrayList<>();
        for(int i=0; i<3; i++) {
            TestMarkKindsBean bean = new TestMarkKindsBean();
            switch (i) {
                case 0:
                    bean.setChecked(false);
                    bean.setName("日日清");
                    break;
                case 1:
                    bean.setChecked(false);
                    bean.setName("周周清");
                    break;
                case 2:
                    bean.setChecked(false);
                    bean.setName("月月清");
                    break;
            }
            list.add(bean);
        }
        TestMarkKindsBean bean = new TestMarkKindsBean();
        bean.setChecked(false);
        bean.setName("自定义");
        list.add(bean);
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
        for(int i=0; i<5; i++) {
            TestMarkCourseBean bean = new TestMarkCourseBean();
            switch (i) {
                case 0:
                    bean.setChecked(true);
                    bean.setName("全部");
                    break;
                default:
                    bean.setChecked(false);
                    bean.setName(courses[RandomValueUtil.getNum(0, courses.length - 1)]);
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
        list.add("按题号排序");
        list.add("得分率排序");
        return list;
    }

    public static List<String> createErrorQuestionType() {
        List<String> list = new ArrayList<>();
        list.add("时间");
        list.add("个人得分率");
        list.add("班级得分率");
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
            bean.setVideoUrl(TestDataUtil.videoUrls[RandomValueUtil.getNum(0, TestDataUtil.videoUrls.length - 1)]);
            bean.setImgUrl(imgUris[RandomValueUtil.getNum(0, imgUris.length - 1)]);
            list.add(bean);
        }
        return list;
    }

    /**i
     * 创建测试数据 --- 我的教师列表
     * @return
     */
    public static List<TestMeTeachersBean> createClassesList() {

        List<TestMeTeachersBean> list = new ArrayList<>();
        for(int i=0; i< 5; i++) {
            TestMeTeachersBean bean = new TestMeTeachersBean();
            bean.setId(i);
            bean.setName(names[i]);
            bean.setClasses("初一五班");
            bean.setCourse(courses[i]);
            bean.setMobile(phones[i]);
            bean.setAvatarUrl("");
            list.add(bean);
        }
        return list;
    }

    public static List<ErrorQuestionStatisticsBean> createErrorQuestionStatisticsBeanList() {
        List<ErrorQuestionStatisticsBean> list = new ArrayList<>();
        for(int i=1; i<21; i++) {
            ErrorQuestionStatisticsBean bean = new ErrorQuestionStatisticsBean();
            bean.setNum(i + "");
            bean.setType(i%2 == 0 ? "选择题" : "填空题");
            bean.setSelect(false);
            list.add(bean);
        }
        return list;
    }
}
