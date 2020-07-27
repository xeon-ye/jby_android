package com.hzw.baselib.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by hzw on 2018/7/20.
 */

public class AwDataUtil {

    /**
     * 判断String是否为空 长度是否为0
     * @param str
     * @return 当字符串长度为空或长度为0时，返回true
     */
    public static boolean isEmpty(String str){
        if("".equals(str) || str==null || str.trim().equals("null")){
            return true;
        }
        return false;
    }

    /**
     * 判断List是否为空 或者长度为0
     * @param list
     * @return 当List为空或长度为0时，返回true
     */
    public static boolean isEmpty(List<?> list) {
        return (null == list || list.size() == 0);
    }

    /**
     * 判断数组是否为空 或者长度为0
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object [] obj){
        if(obj==null||obj.length==0){
            return true;
        }
        return false;
    }

    /**
     * 判断Set是否为空 或者长度0
     * @param set
     * @return 当Set为空或长度为0时，返回true
     */
    public static boolean isEmpty(Set<?> set) {
        return (null == set || set.size() == 0);
    }

    /**
     * 判断Map是是否为空 或者长度为0
     * @param map
     * @return 当Map为空或长度为0时，返回true
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return (null == map || map.size() == 0);
    }

    public static boolean isEmpty(Serializable serializable) {
        if(null == serializable) {
            return true;
        }
        return false;
    }
}
