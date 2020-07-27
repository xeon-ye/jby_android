package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.ResolveTeacherProgressResultBean;
import com.jkrm.education.bean.scan.ScanBean;

import java.util.List;


/**
 * Created by hzw on 2018/11/13
 */
public interface ScanView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getScanInfo(int totalPage, int scanedPage);
        void getTeacherResolveProgress(String teacherId);
    }

    interface View extends AwBaseView {
        void getScanInfoSuccess(ScanBean bean);

        void getTeacherResolveProgressSuccess(List<ResolveTeacherProgressResultBean> list);
        void getTeacherResolveProgressFail(String msg);
    }

}