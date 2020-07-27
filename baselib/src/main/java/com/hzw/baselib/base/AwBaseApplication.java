package com.hzw.baselib.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.hzw.baselib.util.AwFileDownloadUtil;
import com.hzw.baselib.util.AwNetWatchdog;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.HashMap;

import okhttp3.Call;

/**
 * Created by hzw on 2017/11/29.
 */

public abstract class AwBaseApplication extends Application {

    private static AwBaseApplication instance;
    public static AwNetWatchdog netWatchdog;
    public static HashMap<String, Call> downCalls = new HashMap<>();

    public static AwBaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        checkNetwork();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void checkNetwork() {
        netWatchdog = new AwNetWatchdog(this);
        netWatchdog.setNetConnectedListener(new AwNetWatchdog.NetConnectedListener() {

            @Override
            public void onReNetConnected(boolean isReconnect) {

            }

            @Override
            public void onNetUnConnected() {
                if(AwBaseApplication.downCalls == null || AwBaseApplication.downCalls.size() == 0) {
                    return;
                }
                for (String key : AwBaseApplication.downCalls.keySet()) {
                    AwFileDownloadUtil.get().cancel(instance, key);
                }
                //                showDialogToFinish(getString(R.string.common_network_offline));
            }
        });
        netWatchdog.startWatch();
    }

    public void cancelNetWatchdog() {
        if(netWatchdog != null) {
            netWatchdog.stopWatch();
        }
    }
}
