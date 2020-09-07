package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.TaskBean;

import java.util.List;

import okhttp3.RequestBody;


/**
 * Created by hzw on 2018/11/13
 */
public interface AnalysisView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getAnalysisList(RequestBody requestBody);
    }

    interface View extends AwBaseView {
        void getAnalysisListSuccess(List<String> data);
        void getAnalysisListFail(String msg);
    }

}