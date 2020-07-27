package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.SimilarResultBean;

import java.util.List;


/**
 * Created by hzw on 2018/11/13
 */
public interface QuestionSimilarView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getSimilar(String params);
    }

    interface View extends AwBaseView {
        void getSimilarSuccess(List<SimilarResultBean> result);
        void getSimilarFail(String msg);
    }

}