package com.jkrm.education.download;

import com.jkrm.education.db.DaoVideoBean;
import com.jkrm.education.db.util.DaoUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class DownloadThreadManager {
    private static final AtomicReference<DownloadThreadManager> INSTANCE = new AtomicReference<>();
    private HashMap<String, Thread> mDownHashMap;
    private ArrayList<DaoVideoBean> mWaitList;


    public static DownloadThreadManager getInstance() {
        for (; ; ) {
            DownloadThreadManager current = INSTANCE.get();
            if (current != null) {
                return current;
            }
            current = new DownloadThreadManager();
            if (INSTANCE.compareAndSet(null, current)) {
                return current;
            }
        }
    }


    private DownloadThreadManager() {
        mDownHashMap = new HashMap<>();
        mWaitList = new ArrayList<>();
    }

    public void downVideo(DaoVideoBean daoVideoBean) {
        if (mDownHashMap.size() >= 3) {
            if (!isWait(daoVideoBean)) {
                mWaitList.add(daoVideoBean);
            } else {

            }
        } else {
            CoutomDownloadThread coutomDownloadThread = new CoutomDownloadThread(daoVideoBean);
            coutomDownloadThread.start();
            mDownHashMap.put(daoVideoBean.getId(),coutomDownloadThread );
        }
    }

    public boolean isWait(DaoVideoBean dao) {
        for (DaoVideoBean dao1 : mWaitList) {
            if (dao1.getId().equals(dao.getId())) {
                return true;
            }
        }
        return false;
    }

    public void doNext() {
        if (mWaitList.size() > 0) {
            downVideo(mWaitList.get(0));
            mWaitList.remove(mWaitList.get(0));
        }


    }

    public void downFinish(DaoVideoBean daoVideoBean) {
        mDownHashMap.remove(daoVideoBean.getId());
        daoVideoBean.setDownloadStatus(DaoVideoBean.DOWNLOAD_OVER);
        DaoUtil.getInstance().updateVideo(daoVideoBean);
        EventBus.getDefault().post(daoVideoBean);
        doNext();
    }

    public void pauseOrStopDown(DaoVideoBean daoVideoBean) {
        Thread thread = DownloadThreadManager.getInstance().getDao(daoVideoBean.getId());
        if(null!=thread){
            thread.interrupt();
        }
        mDownHashMap.remove(daoVideoBean.getId());
        for (int i = 0; i < mWaitList.size(); i++) {
            if(mWaitList.get(i).getId().equals(daoVideoBean.getId())){
                mWaitList.remove(mWaitList.get(i));
            }
        }
        daoVideoBean.setDownloadStatus(DaoVideoBean.DOWNLOAD_PAUSE);
        EventBus.getDefault().post(daoVideoBean);
        doNext();
    }

    public Thread getDao(String id) {
        Set<Map.Entry<String, Thread>> entries = mDownHashMap.entrySet();
        for (Map.Entry<String, Thread> entry : entries) {
            if (entry.getKey().equals(id)) {
                return entry.getValue();
            }
        }

        return null;
    }
    //移除等待下载列表
    public void removeDownList(List<DaoVideoBean> urlList){
        if(mWaitList.isEmpty()||urlList.isEmpty()){
            return;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        for (DaoVideoBean daoVideoBean : urlList) {
            arrayList.add(daoVideoBean.getUrl());
            pauseOrStopDown(daoVideoBean);
        }
        mWaitList.removeAll(arrayList);

    }
}
