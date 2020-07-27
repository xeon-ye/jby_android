package com.jkrm.education.download;

import com.hzw.baselib.util.AwLog;
import com.jkrm.education.db.DaoVideoBean;
import com.jkrm.education.db.util.DaoUtil;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/18 11:20
 */

public class DownloadLimitObserver implements Observer<DaoVideoBean> {
    private static final String TAG = "DownloadLimitObserver";

    public Disposable d;//可以用于取消注册的监听者
    public DaoVideoBean downloadInfo;

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
    }

    @Override
    public void onNext(DaoVideoBean value) {
        this.downloadInfo = value;
        downloadInfo.setDownloadStatus(DaoVideoBean.DOWNLOAD);
        EventBus.getDefault().post(downloadInfo);

    }

    @Override
    public void onError(Throwable e) {
        AwLog.d(TAG,e.toString());
        if(null==downloadInfo){
            return;
        }
        if (DownloadLimitManager.getInstance().getDownloadUrl(downloadInfo.getUrl())){
            DownloadLimitManager.getInstance().pauseDownload(downloadInfo.getUrl());
            downloadInfo.setDownloadStatus(DaoVideoBean.DOWNLOAD_ERROR);
            EventBus.getDefault().post(downloadInfo);
        }else{
            downloadInfo.setDownloadStatus(DaoVideoBean.DOWNLOAD_PAUSE);
            EventBus.getDefault().post(downloadInfo);
        }

    }

    @Override
    public void onComplete() {
        AwLog.d(TAG,"onComplete");
        if (downloadInfo != null){
            downloadInfo.setDownloadStatus(DaoVideoBean.DOWNLOAD_OVER);
            EventBus.getDefault().post(downloadInfo);
            DaoUtil.getInstance().updateVideoState(downloadInfo);
        }
    }
}
