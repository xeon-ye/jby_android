package com.hzw.baselib.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.support.annotation.DrawableRes;

/**
 * Created by hzw on 2019/3/2.
 */

public class AwResourceUtil {

    /**
     * 获取资源路径
     * @param activity
     * @param id
     * @return
     */
    public static String getResourcesUri(Activity activity, @DrawableRes int id) {
        Resources resources = activity.getResources();
        String uriPath = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                resources.getResourcePackageName(id) + "/" +
                resources.getResourceTypeName(id) + "/" +
                resources.getResourceEntryName(id);
        return uriPath;
    }

}
