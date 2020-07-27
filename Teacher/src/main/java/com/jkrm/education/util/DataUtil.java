package com.jkrm.education.util;

import com.jkrm.education.R;
import com.jkrm.education.bean.result.TeacherTodoBean;
import com.jkrm.education.bean.test.TestMarkKindsBean;
import com.jkrm.education.bean.todo.TodoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzw on 2019/5/9.
 */

public class DataUtil {

    private static int[] numIcons = {
            R.mipmap.home_icon_blue, R.mipmap.home_icon_green, R.mipmap.home_icon_orange, R.mipmap.home_icon_red,
    };

    public static List<TodoBean> convertTodoBeanList(List<TodoBean> list) {
        for(int i=0; i<list.size(); i++) {
            TodoBean bean = list.get(i);
            if(i+1 > numIcons.length) {
                bean.setIdBg(numIcons[i - numIcons.length * (i/numIcons.length)]);
            } else {
                bean.setIdBg(numIcons[i]);
            }
        }
        return list;
    }

    public static List<TeacherTodoBean> convertTeacherTodoBeanList(List<TeacherTodoBean> list) {
        for(int i=0; i<list.size(); i++) {
            TeacherTodoBean bean = list.get(i);
            if(i+1 > numIcons.length) {
                bean.setIdBg(numIcons[i - numIcons.length * (i/numIcons.length)]);
            } else {
                bean.setIdBg(numIcons[i]);
            }
        }
        return list;
    }
    /**
     * 将一组数据平均分成n组
     *
     * @param source 要分组的数据源
     * @param n      平均分成n组
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        List<List<T>> result = new ArrayList<List<T>>();
        int remainder = source.size() % n;  //(先计算出余数)
        int number = source.size() / n;  //然后是商
        int offset = 0;//偏移量
        for (int i = 0; i < n; i++) {
            List<T> value = null;
            if (remainder > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remainder--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
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
}
