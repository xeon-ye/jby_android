package com.hzw.baselib.constants;

import android.os.Environment;

import java.io.File;

/**
 * Created by hzw on 2017/11/29.
 */

public class AwBaseConstant {

    public class Popupwindow {
        public static final float POPUPWINDOW_DARK = 0.5f;
        public static final float POPUPWINDOW_NORMAL = 1.0f;
    }

    /**
     * 透明或无布局文件
     */
    public static final int LAYOUT_TRANSPARENT = -1;
    /**
     * APP 资源路径
     */
    public static final String PAHT_APP_DERECTORY = Environment.getExternalStorageDirectory() + File.separator + "aweiapp";

    public static final int CORNER_COMMON = 5;
    public static final int CORNER_IMG_COMMON = 15;

    public static final String COMMON_SUFFIX_RATIO = "%";
    public static final String COMMON_SUFFIX_PERSON = "人";
    public static final String COMMON_SUFFIX_SCORE = "分";

    public static final String COMMON_INVALID_VALUE = "-1";

    public static final String VERSION_SYSTEMTYPE_ANDROID = "1";
    public static final String VERSION_ORGTYPE_TEACHER = "1";
    public static final String VERSION_ORGTYPE_STUDENT = "2";
    public static final String VERSION_ENVIRONTYPE_TEST = "1";
    public static final String VERSION_ENVIRONTYPE_PROD = "2";
}
