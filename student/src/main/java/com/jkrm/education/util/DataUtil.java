package com.jkrm.education.util;

import com.jkrm.education.bean.test.TestMarkKindsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzw on 2019/7/8.
 */

public class DataUtil {

    /**
     * 筛选分类
     * @return
     */
    public static List<TestMarkKindsBean> createKindsList() {
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
        return list;
    }

    /**
     * 题篮导出选项
     * @return
     */
    public static List<String> getBasketExportChoiceList() {
        List<String> list = new ArrayList<>();
        list.add("仅题目");
        list.add("题目+答案解析");
        return list;
    }

    /**
     * 创建学段
     * @return
     */
    public static  List<TestMarkKindsBean> createLearnSection(){
        List<TestMarkKindsBean> list = new ArrayList<>();
        list.add(new TestMarkKindsBean(false,"高中"));
        list.add(new TestMarkKindsBean(false,"初中"));
        return  list;
    }

    /**
     * 入学年份
     * @return
     */
    public static List<TestMarkKindsBean> createLearnYear(){
        List<TestMarkKindsBean> list = new ArrayList<>();
        list.add(new TestMarkKindsBean(false,"2018年"));
        list.add(new TestMarkKindsBean(false,"2019年"));
        list.add(new TestMarkKindsBean(false,"2020年"));
        return  list;
    }

    /**
     * 初中学科
     * @return
     */
    public static List<TestMarkKindsBean> createJuniorHighSchool(){
        List<TestMarkKindsBean> list = new ArrayList<>();
        //语文 数学  英语  科学  道德与法治  历史与社会
        list.add(new TestMarkKindsBean(false,"语文"));
        list.add(new TestMarkKindsBean(false,"数学"));
        list.add(new TestMarkKindsBean(false,"英语"));
        list.add(new TestMarkKindsBean(false,"科学"));
        list.add(new TestMarkKindsBean(false,"道德与法治"));
        list.add(new TestMarkKindsBean(false,"历史与社会"));
        return list;
    }

    /**
     * 高中学科
     * @return
     */
    public static List<TestMarkKindsBean> createHighSchool(){
        List<TestMarkKindsBean> list = new ArrayList<>();
       //语文  数学  英语  物理  化学  生物  政治  历史  地理
        list.add(new TestMarkKindsBean(false,"语文"));
        list.add(new TestMarkKindsBean(false,"数学"));
        list.add(new TestMarkKindsBean(false,"英语"));
        list.add(new TestMarkKindsBean(false,"物理"));
        list.add(new TestMarkKindsBean(false,"化学"));
        list.add(new TestMarkKindsBean(false,"生物"));
        list.add(new TestMarkKindsBean(false,"政治"));
        list.add(new TestMarkKindsBean(false,"历史"));
        list.add(new TestMarkKindsBean(false,"地理"));
        return list;
    }
}
