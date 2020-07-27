package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.request.RequestClassesBean;
import com.jkrm.education.bean.result.OssResultBean;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;


/**
 * Created by hzw on 2018/11/13
 */
public interface MeUnbindClassView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void unBindSchoolClass(String teacherId,String classId);
        void getClassesById(String teacherId);
    }

    interface View extends AwBaseView {
        void unBindSchoolClassSuccess(String data);
        void unBindSchoolClassFail(String msg);

        void getClassesByIdSuccess(List<RequestClassesBean> data);
        void getClassesByIdFail(String msg);
    }

}