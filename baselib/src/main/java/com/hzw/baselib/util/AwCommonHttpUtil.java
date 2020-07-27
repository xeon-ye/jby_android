package com.hzw.baselib.util;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by hzw on 2019/4/8.
 */

public class AwCommonHttpUtil {

    public static void getOkHttp(String url, Callback callback) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                AwLog.d("okhttp", message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient();
        client.newBuilder().connectTimeout(1, TimeUnit.MINUTES).readTimeout(1, TimeUnit.MINUTES).writeTimeout(1, TimeUnit.MINUTES).addInterceptor(logging);
        Request request = new Request.Builder()
                .method("GET", null)
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}
