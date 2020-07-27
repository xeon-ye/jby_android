package com.hzw.baselib.api.glide;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.hzw.baselib.api.SSLSocketClient;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * https://blog.csdn.net/u014752325/article/details/73217577
 *              https://www.jianshu.com/p/f1f17f816d1d   升级4.x  2020.04.29
 * Created by hzw on 2019/6/14.
 */

public class OkHttpGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
    }

/*    @Override
    public void registerComponents(Context context, Glide glide) {
        OkHttpClient mHttpClient = new OkHttpClient().newBuilder()
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .build();
        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(mHttpClient));
    }*/

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        registry.replace(GlideUrl.class,InputStream.class,new OkHttpUrlLoader.Factory());
    }
}
