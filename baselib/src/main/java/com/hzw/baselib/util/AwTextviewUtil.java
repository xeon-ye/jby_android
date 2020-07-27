package com.hzw.baselib.util;

import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hzw on 2019/6/24.
 */

public class AwTextviewUtil {

    public static void setTextViewTxt(TextView tv, String text1, String text2) {
        AwLog.d("measureText tv: " + tv.getPaint().measureText(text1) + " ,getWidth: " + tv.getWidth() + " ,text1: " + text1);
        if (tv.getPaint().measureText(text1) > tv.getWidth()) {
            tv.setText(String.valueOf(text2));
        } else {
            tv.setText(text1);
        }
    }

    public static String replaceBlank(String src) {
        String dest = "";
        if (src != null) {
            Pattern pattern = Pattern.compile("\t|\r|\n|\\s*");
            Matcher matcher = pattern.matcher(src);
            dest = matcher.replaceAll("");
        }
        return dest;
    }
}