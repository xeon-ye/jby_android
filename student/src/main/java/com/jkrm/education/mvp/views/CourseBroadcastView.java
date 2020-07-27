package com.jkrm.education.mvp.views;

import android.service.autofill.SaveRequest;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.WatchLogBean;
import com.jkrm.education.bean.result.WatchResultBean;

import okhttp3.RequestBody;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/6 14:27
 */

public interface CourseBroadcastView extends AwBaseView {
    interface Presenter extends AwBasePresenter {
        void saveWatchLog(RequestBody requestBody);
        void getWatchList(RequestBody requestBody);
    }

    interface View extends AwBaseView {
        void onSaveWatchLogSuccess(WatchLogBean request);
        void onSaveWatchLogFail(String msg);

        void getWatchListSuccess(WatchResultBean resultBean);
        void getWatchListFail(String msg);
    }
}
