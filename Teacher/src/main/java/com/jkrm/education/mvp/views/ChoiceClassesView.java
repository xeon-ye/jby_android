package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.ClassessBean;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/10 15:05
 */

public interface ChoiceClassesView extends AwBaseView {
    interface Presenter extends AwBasePresenter {
        void getClasses(String schoolId,String userId);
        void bindClasses(RequestBody requestBody);
    }

    interface View extends AwBaseView {
        void getClassesSuccess(Map<String, List<ClassessBean.Bean>> data);
        void getClasssesFail(String msg);

        void bindClassesSuccess(String data);
        void bindClassesFail(String msg);

    }
}
