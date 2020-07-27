package com.hzw.baselib.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 合并数组
 * Created by hzw on 2018/11/7.
 */

public class AwArraysUtil {


    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static <T> T[] concatAll(T[] first, T[]... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    public static String convertToString(String[] strs) {
        StringBuffer string = new StringBuffer();//数组转字符串
        for (int i = 0; i < strs.length; i++) {
            string.append(strs[i]);
            string.append(",");
        }
        string.substring(0, string.length() - 1);
        return string.toString();
    }

    public static String listToString (List<String> list){
        if(null==list){
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        boolean isFirst=true;
        for (String s : list) {
            if(isFirst){
                isFirst=false;
            }else{
                stringBuilder.append(',');
            }
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }

    public static List<String> stringToList(String string){
       String[] str= string.split(",");
       return Arrays.asList(str);
    }

    public static String splitList(List<String> list){
        if(null==list||list.size()==0){
            return "";
        }
     /*   ArrayList<String> emptyList = new ArrayList<>();
        for (String s : list) {
            if(AwDataUtil.isEmpty(s)){
                emptyList.add(s);
            }
        }
        list.removeAll(emptyList);*/
        StringBuffer resultBuffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            String result = list.get(i);
            if (i == 0) {
                resultBuffer.append(result);
            } else {
                resultBuffer.append("," + result);
            }
        }
        return resultBuffer.toString();
    }


}
