package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.ClassessBean;
import com.jkrm.education.bean.ErrorBasketBean;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/10 15:05
 */

public interface ErrorQuestionView extends AwBaseView {
    interface Presenter extends AwBasePresenter {
        void getErrorBasket(String teacherId);

        void deleteErrorBasket(RequestBody requestBody);

        void clearErrorBasket(String teacherId);
    }

    interface View extends AwBaseView {
        void getErrorBasketSuccess(List<ErrorBasketBean> data);
        void getErrorBasketFail(String msg);

        void deleteErrorBasketSuccess(String data);
        void deleteErrorBasketFail(String msg);

        void clearErrorBasketSuccess(String data);
        void clearErrorBasketFail(String msg);
    }
}
