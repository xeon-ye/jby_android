package com.hzw.baselib.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

/**
 * Created by hzw on 2019/5/26.
 */

public class AwCommonUtil {

    /**
     * 置空 TextView 值
     * @param v
     */
    public static void resetView(TextView v) {
        if(null == v)
            return;
        v.setText("");
    }

    /**
     * View是否可见
     * @param v
     * @return
     */
    public static  boolean isViewVisiable(View v) {
        if(null == v)
            return false;
        if(View.VISIBLE == v.getVisibility())
            return true;
        else
            return false;
    }

    /**
     * 设置TextView 值
     * @param tv
     * @param content
     */
    public static  void setText(TextView tv, String content) {
        if(TextUtils.isEmpty(content))
            content = "";
        tv.setText(content);
    }

    /**
     * 设置TextView format 值
     * @param context
     * @param tv
     * @param resId
     * @param content
     */
    public static  void setText(Context context, TextView tv, int resId, String content) {
        if(TextUtils.isEmpty(content))
            content = "";
        tv.setText(String.format(context.getResources().getString(resId), content == null ? "" : content));
    }

    /**
     * 判断TextView值是否存在
     * @param tv
     * @return
     */
    public static  boolean isViewContentNull(TextView tv) {
        if(tv == null)
            return true;
        if(TextUtils.isEmpty(tv.getText()))
            return true;
        return false;
    }

    /**
     * 是否显示V
     * @param v
     * @param show
     */
    public static void showView(View v, boolean show) {
        if(null == v)
            return;
        v.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
