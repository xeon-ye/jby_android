package com.hzw.baselib.util;

import java.util.Iterator;
import java.util.List;

/**
 * Created by hzw on 2018/11/20.
 */

public class AwCollectionUtil {

    public static List<String> removeDataFromList(List<String> list1, List<String> list2) {
        if(AwDataUtil.isEmpty(list1))
            return null;
        for (Iterator it = list1.iterator(); it.hasNext(); ) {
            String temp1 = (String) it.next();
            for(String temp2 : list2) {
                if(temp1.equals(temp2)) {
                    it.remove();
                }
            }
        }
        return list1;
    }
}
