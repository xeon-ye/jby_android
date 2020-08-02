package com.jkrm.education.download;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwSpUtil;
import com.hzw.baselib.util.FileUtils;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.db.DaoVideoBean;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class CoutomDownloadThread extends Thread {
    private static final String TAG = "CoutomDownloadThread";
    private DaoVideoBean daoVideoBean;
    private Long fileLength;

    public CoutomDownloadThread(DaoVideoBean daoVideoBean) {
        this.daoVideoBean = daoVideoBean;
    }

    @Override
    public void run() {
        try {
            File file = new File(Extras.FILE_PATH, daoVideoBean.getFileName());
            //下载进度文件保存的路径和文件名
            URL url = new URL(daoVideoBean.getUrl());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(500000);
            conn.setReadTimeout(500000);
            conn.setRequestProperty("Content-Type", "application/octet-stream");
            conn.setRequestProperty("Authorization", AwSpUtil.getString(MyConstant.SPConstant.XML_USER_INFO, MyConstant.SPConstant.KEY_TOKEN, ""));
            conn.setRequestProperty("clientFlag", "android");
            conn.setRequestProperty("CLIENTSESSIONID", AwSpUtil.getString(MyConstant.SPConstant.XML_USER_INFO, MyConstant.SPConstant.KEY_CLI, ""));


            //设置connection打开链接资源
            long downloadLength = FileUtils.getFileLength(file);//已经下载好的长度
            if (downloadLength == Long.parseLong(daoVideoBean.getSize())) {
                DownloadThreadManager.getInstance().downFinish(daoVideoBean);
                return;
            }

            //设置请求数据的范围
            conn.setRequestProperty("Range", "bytes=" + downloadLength + "-" + Long.parseLong(daoVideoBean.getSize()));
            conn.connect();
            Log.e(TAG, "run: " + conn.getResponseCode() + "");
            //建立连接，状态码206表示请求部分数据成功，此时开始下载任务
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK||conn.getResponseCode()==HttpURLConnection.HTTP_PARTIAL) {
                //三个线程各自创建自己的随机存储文件
                RandomAccessFile raf = new RandomAccessFile(file, "rwd");
                //设置数据从哪个位置开始写入数据到临时文件
                raf.seek(downloadLength);
                InputStream is = null;
                FileOutputStream fileOutputStream = null;
                try {
                    is = conn.getInputStream();
                    is.skip(downloadLength);
                    fileOutputStream = new FileOutputStream(file, true);
                    byte[] buffer = new byte[512];//缓冲数组2kB
                    int len;
                    while ((len = is.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, len);
                        downloadLength += len;
                        fileLength = downloadLength;
                        if (daoVideoBean.getDownloadStatus().equals(DaoVideoBean.DOWNLOAD)|| AwDataUtil.isEmpty(daoVideoBean.getDownloadStatus())) {
                            daoVideoBean.setProgress(downloadLength);
                            daoVideoBean.setDownloadStatus(DaoVideoBean.DOWNLOAD);
                            EventBus.getDefault().post(this.daoVideoBean);
                        }
                    }
                    fileOutputStream.flush();
                } finally {
                    //关闭IO流
                    DownloadIO.closeAll(is, fileOutputStream);
                    if (fileLength == Long.parseLong(daoVideoBean.getSize())) {
                        DownloadThreadManager.getInstance().downFinish(daoVideoBean);
                    } else {
                        //DownloadThreadManager.getInstance().doNext();
                    }

                }
                raf.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
