package com.jkrm.education.download;

import android.util.Log;

import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.FileUtils;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.db.DaoVideoBean;
import com.jkrm.education.db.util.DaoUtil;
import com.sobot.chat.camera.util.FileUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/18 11:16
 */

public class DownloadLimitManager {

    private static final AtomicReference<DownloadLimitManager> INSTANCE = new AtomicReference<>();

    private static final String TAG = "DownloadLimitManager";
    private OkHttpClient mClient;
    private HashMap<String, Call> downCalls; // 用来存放各个下载的请求
    private List<String> downWait;           // 用来存放等待下载的请求
    private int maxCount = 3;                // 同时下载的最大个数

    public static DownloadLimitManager getInstance() {
        for (; ; ) {
            DownloadLimitManager current = INSTANCE.get();
            if (current != null) {
                return current;
            }
            current = new DownloadLimitManager();
            if (INSTANCE.compareAndSet(null, current)) {
                return current;
            }
        }
    }

    private DownloadLimitManager() {
        downCalls = new HashMap<>();
        mClient = new OkHttpClient.Builder().build();
        downWait = new ArrayList<>();
    }

    /**
     * 如果文件已下载重新命名新文件名
     * @param downloadInfo
     * @return
     */
    private DaoVideoBean getRealFileName(DaoVideoBean downloadInfo) {
  /*      String fileName = downloadInfo.getFileName();
        long downloadLength = 0, contentLength = downloadInfo.getTotal();
        File path = new File(Constant.FILE_PATH);
        if (!path.exists()) {
            path.mkdir();
        }
        File file = new File(Constant.FILE_PATH, fileName);
        if (file.exists()) {
            //找到了文件,代表已经下载过,则获取其长度
            downloadLength = file.length();
        }
        //之前下载过,需要重新来一个文件
        int i = 1;
        while (downloadLength >= contentLength) {
            int dotIndex = fileName.lastIndexOf(".");
            String fileNameOther;
            if (dotIndex == -1) {
                fileNameOther = fileName + "(" + i + ")";
            } else {
                fileNameOther = fileName.substring(0, dotIndex)
                        + "(" + i + ")" + fileName.substring(dotIndex);
            }
            File newFile = new File(Constant.FILE_PATH, fileNameOther);
            file = newFile;
            downloadLength = newFile.length();
            i++;
        }
        //设置改变过的文件名/大小
        downloadInfo.setProgress(downloadLength);
        downloadInfo.setFileName(file.getName());*/
        return downloadInfo;
    }

    /**
     * 查看是否在下载任务中
     * @param url
     * @return
     */
    public boolean getDownloadUrl(String url){
        return downCalls.containsKey(url);
    }

    /**
     * 查看是否在等待任务中
     * @param url
     * @return
     */
    public boolean getWaitUrl(String url){
        for (String item : downWait){
            if (item.equals(url)){
                return true;
            }
        }
        return false;
    }

    /**
     * 开始下载
     *
     * @param url              下载请求的网址
     */
   public void download(String url) {


        Observable.just(url)
                .filter(new Predicate<String>() { // 过滤 call的map中已经有了,就证明正在下载,则这次不下载
                    @Override
                    public boolean test(String s) {

                        boolean flag = downCalls.containsKey(s);
                        if (flag){
                            // 如果已经在下载，查找下一个文件进行下载
                            downNext();
                            return false;
                        }else{
                            // 判断如果正在下载的个数达到最大限制，存到等下下载列表中
                            if (downCalls.size() >= maxCount){
                                if (!getWaitUrl(s)){
                                    downWait.add(s);
                                    DaoVideoBean info = new DaoVideoBean();
                                    info.setUrl(s);
                                    info.setDownloadStatus(DaoVideoBean.DOWNLOAD_WAIT);
                                    EventBus.getDefault().post(info);
                                }
                                return false;
                            }else{
                                return true;
                            }
                        }
                    }
                })
                .flatMap(new Function<String, ObservableSource<?>>() { // 生成 DownloadInfo
                    @Override
                    public ObservableSource<?> apply(String s) {
                        return Observable.just(createDownInfo(s));
                    }
                })
                .map(new Function<Object, DaoVideoBean>() { // 如果已经下载，重新命名
                    @Override
                    public DaoVideoBean apply(Object o) {
                        return getRealFileName((DaoVideoBean)o);
                    }
                })
                .flatMap(new Function<DaoVideoBean, ObservableSource<DaoVideoBean>>() { // 下载
                    @Override
                    public ObservableSource<DaoVideoBean> apply(DaoVideoBean downloadInfo) {
                        return Observable.create(new DownloadSubscribe(downloadInfo));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程中回调
                .subscribeOn(Schedulers.io()) //  在子线程中执行
                .subscribe(new DownloadLimitObserver()); //  添加观察者，监听下载进度
   }

    /**
     * 下载等待下载中的第一条
     */
    public void downNext(){
        if (downCalls.size() < maxCount && downWait.size() > 0){
            download(downWait.get(0));
            downWait.remove(0);
        }
    }

    /**
     * 下载取消或者暂停
     * @param url
     */
    public void pauseDownload(String url) {
/*        DaoVideoBean downloadInfo = new DaoVideoBean();
        downloadInfo.setUrl(url);
        downloadInfo.setDownloadStatus(DaoVideoBean.DOWNLOAD_PAUSE);
        EventBus.getDefault().post(downloadInfo);*/
        Call call = downCalls.get(url);
        if (call != null) {
            call.cancel();//取消
        }

        downCalls.remove(url);
        downNext();
    }
    //暂停所有下载
    public void pauseAllDownload(){
        downWait.clear();
    }
    //移除等待下载列表
    public void removeDownList(List<DaoVideoBean> urlList){
        if(downWait.isEmpty()||urlList.isEmpty()){
            return;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        for (DaoVideoBean daoVideoBean : urlList) {
            arrayList.add(daoVideoBean.getUrl());
        }
        downWait.removeAll(arrayList);
    }

    /**
     * 取消下载 删除本地文件
     * @param info
     */
    public void cancelDownload(DaoVideoBean info){
        pauseDownload(info.getUrl());
        info.setProgress(0);
        info.setDownloadStatus(DaoVideoBean.DOWNLOAD_CANCEL);
        EventBus.getDefault().post(info);
        //Constant.deleteFile(info.getFileName());
    }

    /**
     * 创建DownInfo
     *
     * @param url 请求网址
     * @return DownInfo
     */
    private DaoVideoBean createDownInfo(String url) {
        DaoVideoBean downloadInfo = new DaoVideoBean();
        downloadInfo.setUrl(url);
        long contentLength = getContentLength(url);//获得文件大小
        downloadInfo.setTotal(contentLength);
        String fileName = url.substring(url.lastIndexOf("/"));
        downloadInfo.setFileName(fileName);
        return downloadInfo;
    }



    private class DownloadSubscribe implements ObservableOnSubscribe<DaoVideoBean> {
        private DaoVideoBean downloadInfo;

        public DownloadSubscribe(DaoVideoBean downloadInfo) {
            this.downloadInfo = downloadInfo;
        }

        @Override
        public void subscribe(ObservableEmitter<DaoVideoBean> e) throws Exception {
            String url = downloadInfo.getUrl();
            File file = new File(Extras.FILE_PATH, downloadInfo.getFileName());

            DaoVideoBean daoVideoBean = DaoUtil.getInstance().queryVideoByUrl(downloadInfo);
            Log.e(TAG,daoVideoBean.getProgress()+"-dao->"+daoVideoBean.getTotal());
            //long downloadLength = daoVideoBean.getProgress()>=downloadInfo.getTotal()?0:daoVideoBean.getProgress();//已经下载好的长度
            long downloadLength = FileUtils.getFileLength(file);//已经下载好的长度
            long contentLength = getContentLength(downloadInfo.getUrl());//文件的总长度
            Log.e(TAG,downloadInfo.getProgress()+"-new->"+downloadInfo.getTotal());
            //初始进度信息
            e.onNext(downloadInfo);
            Request request = new Request.Builder()
                    //确定下载的范围,添加此头,则服务器就可以跳过已经下载好的部分
                    .addHeader("RANGE", "bytes=" + downloadLength + "-" + contentLength)
                    .url(url)
                    .build();
            Call call = mClient.newCall(request);
            downCalls.put(url, call);//把这个添加到call里,方便取消
            Response response = call.execute();
            InputStream is = null;
            FileOutputStream fileOutputStream = null;
            try {
                is = response.body().byteStream();
                is.skip(downloadLength);
                fileOutputStream = new FileOutputStream(file, true);
                byte[] buffer = new byte[2048];//缓冲数组2kB
                int len;
                while ((len = is.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, len);
                    downloadLength += len;
                    downloadInfo.setProgress(downloadLength);
                    e.onNext(downloadInfo);
                }
                fileOutputStream.flush();
                downCalls.remove(url);
                downNext();
            } finally {
                //关闭IO流
                DownloadIO.closeAll(is, fileOutputStream);

            }
            e.onComplete();//完成
        }
    }



    /**
     * 获取下载长度
     *
     * @param downloadUrl
     * @return
     */
    private long getContentLength(String downloadUrl) {
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        try {
            Response response = mClient.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                long contentLength = response.body().contentLength();
                response.close();
                return contentLength == 0 ? DaoVideoBean.TOTAL_ERROR : contentLength;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DaoVideoBean.TOTAL_ERROR;
    }
}
