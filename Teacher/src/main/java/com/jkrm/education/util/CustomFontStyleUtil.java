package com.jkrm.education.util;

import android.graphics.Typeface;

import com.jkrm.education.base.MyApp;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/4/15 11:19
 */

public class CustomFontStyleUtil {
    public static Typeface getNewRomenType(){
        return  Typeface.createFromAsset(MyApp.getInstance().getApplicationContext().getAssets(), "fonts/times.ttf");
    }
}
