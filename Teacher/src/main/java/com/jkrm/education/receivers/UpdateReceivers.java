package com.jkrm.education.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.hzw.baselib.util.AwAPPUtils;
import com.jkrm.education.util.RestartAppUtil;

/**
 * @Description: App安装完成接收广播
 * @Author: xiangqian
 * @CreateDate: 2020/1/14 10:43
 */

public class UpdateReceivers extends BroadcastReceiver {

    private static final String TAG = "UpdateReceivers";
    @Override
    public void onReceive(Context context, Intent intent) {
        String packageName = intent.getDataString();
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)||intent.getAction().equals(Intent.ACTION_MY_PACKAGE_REPLACED)) {//接收升级广播
            RestartAppUtil.restartAPP(context);//升级完自身app,重启自身
            Log.e(TAG,"ACTION_PACKAGE_REPLACED");
            if (packageName.equals("package:" + AwAPPUtils.getPackageName(context))) {
                RestartAppUtil.restartAPP(context);//升级完自身app,重启自身
            }
        } else if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {//接收安装广播
            if (packageName.equals("package:" + AwAPPUtils.getPackageName(context))) {
                /*SystemUtil.reBootDevice();*/
            }
        } else if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) { //接收卸载广播
        }
    }


}
