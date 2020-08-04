package com.jkrm.education.download;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.hzw.baselib.interfaces.IPermissionListener;
import com.hzw.baselib.util.AwRxPermissionUtil;
import com.hzw.baselib.util.AwSystemIntentUtil;
import com.jkrm.education.bean.result.CoursePlayResultBean;
import com.jkrm.education.bean.rx.RxCostomDownType;
import com.jkrm.education.bean.rx.RxDownCourseType;
import com.jkrm.education.bean.rx.RxRefreshQuestionBasketType;
import com.jkrm.education.db.DaoVideoBean;
import com.jkrm.education.db.util.DaoUtil;
import com.sobot.chat.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/18 10:33
 */

public class DownLoadService extends Service {
    private static final String TAG = "DownLoadService";


    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            //数字是随便写的“40”，
            nm.createNotificationChannel(new NotificationChannel("40", "App Service", NotificationManager.IMPORTANCE_DEFAULT));
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "40");
            //其中的2，是也随便写的，正式项目也是随便写
            startForeground(2 ,builder.build());
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startDownLoad();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startDownLoad() {
        List<DaoVideoBean> daoVideoBeans = DaoUtil.getInstance().queryNeedDownVideoList();
        for (DaoVideoBean daoVideoBean : daoVideoBeans) {
           // DownloadThreadManager.getInstance().downVideo(daoVideoBean);
        }
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvnet(RxCostomDownType type) {
        List<DaoVideoBean> daoVideoBeanArrayList = type.getDaoVideoBeanArrayList();
        for (int i = 0; i < daoVideoBeanArrayList.size(); i++) {
            DaoVideoBean daoVideoBean = daoVideoBeanArrayList.get(i);
            DownloadThreadManager.getInstance().downVideo(daoVideoBean);
        }


    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
