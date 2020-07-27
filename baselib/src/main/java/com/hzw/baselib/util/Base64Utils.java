package com.hzw.baselib.util;

import android.annotation.TargetApi;
import android.os.Build;

import android.util.Base64;


/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/12 11:11
 */

public class Base64Utils {


    @TargetApi(Build.VERSION_CODES.O)
    public  static  String encodeToString(byte[] bytes){
        return  Base64.encodeToString(bytes,Base64.NO_WRAP);
    }

    @TargetApi(Build.VERSION_CODES.O)
    public static  byte[]  decodeFromString(String string){
        return Base64.decode(string,Base64.NO_WRAP);
    }

}
