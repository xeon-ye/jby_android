package com.hzw.baselib.util;


import android.content.Context;
import android.util.Log;

import com.hzw.baselib.base.AwBaseApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hzw on 2018/08/13
 */

public class AwFileDownloadUtil {

    private static AwFileDownloadUtil downloadUtil;
    private final OkHttpClient okHttpClient;

    public static AwFileDownloadUtil get() {
        if (downloadUtil == null) {
            downloadUtil = new AwFileDownloadUtil();
        }
        return downloadUtil;
    }

    private AwFileDownloadUtil() {
        okHttpClient = new OkHttpClient();
    }


    public void downApk(final Context context, final String url, final OnDownloadListener listener) {
        if(AwBaseApplication.downCalls.containsKey(url)) {
            listener.onDownloadIngRejectRepeat();
            return;
        }
        final File file = getFile(context, url);
        get(url, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                fail(context, url, listener);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;//输入流
                FileOutputStream fos = null;//输出流
                try {
                    is = response.body().byteStream();//获取输入流
                    long total = response.body().contentLength();//获取文件大小
                    //view.setMax(total);//为progressDialog设置大小
                    if (is != null) {
                        Log.d("SettingPresenter", "onResponse: 不为空");
                        fos = new FileOutputStream(file);
                        byte[] buf = new byte[2048];
                        int ch = -1;
                        int process = 0;
                        while ((ch = is.read(buf)) != -1) {
                            fos.write(buf, 0, ch);
                            process += ch;
                            int progress = (int) (process * 1.0f / total * 100);
                            listener.onDownloading(progress);
                        }
                    }
                    fos.flush();
                    // 下载完成
                    if (fos != null) {
                        fos.close();
                    }
                    success(url, listener, file.getAbsolutePath());
                } catch (Exception e) {
                    fail(context, url, listener);
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
    }

    public void get(String address, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        client.newBuilder().connectTimeout(1, TimeUnit.MINUTES).readTimeout(1, TimeUnit.MINUTES).writeTimeout(1, TimeUnit.MINUTES);
        FormBody.Builder builder = new FormBody.Builder();
        FormBody body = builder.build();
        Request request = new Request.Builder()
                .url(address)
                .build();
        Call call = client.newCall(request);
        AwBaseApplication.downCalls.put(address, call);//把这个添加到call里,方便取消
        call.enqueue(callback);
    }

    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess(String path);

        /**
         * @param progress 下载进度
         */
        void onDownloading(int progress);

        /**
         * 下载失败
         */
        void onDownloadFailed(String path);

        /**
         * 正在下载, 拒绝重复
         */
        void onDownloadIngRejectRepeat();
    }

    public void cancel(Context context, String url) {
        AwFileUtil.deleteFile(getFile(context, url));
        if(AwBaseApplication.downCalls == null)
            return;
        Call call = AwBaseApplication.downCalls.get(url);
        if (call != null) {
            call.cancel();//取消
            AwBaseApplication.downCalls.remove(url);
            AwLog.d("下载取消了");
        }
    }

    public void success(String url, OnDownloadListener listener, String path) {
        listener.onDownloadSuccess(path);
//        DaoCacheFileUtil.getInstance().updateBean(url);
        AwBaseApplication.downCalls.remove(url);
    }

    public void fail(Context context, String url, OnDownloadListener listener) {
        listener.onDownloadFailed(url);
        cancel(context, url);
    }


    public File getFile(Context context, String url) {
        File file = new File(context.getExternalFilesDir(""), "jby.apk");
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return file;
    }
}
