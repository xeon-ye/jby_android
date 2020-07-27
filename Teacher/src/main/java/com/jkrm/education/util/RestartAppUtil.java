package com.jkrm.education.util;

import android.content.Context;
import android.content.Intent;

import com.hzw.baselib.util.AwAPPUtils;

/**
 * @Description:  App重启
 * @Author: xiangqian
 * @CreateDate: 2020/1/14 10:46
 */

public class RestartAppUtil {
    /**
     * 重启整个APP
     * @param context
     */
    public static void restartAPP(Context context){
        Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(AwAPPUtils.getPackageName(context));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        //ActManager.getAppManager().finishAllActivity();
    }
}
