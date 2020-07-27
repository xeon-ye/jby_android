package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.SimilarResultBean;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/5/6 19:27
 */

public interface OnlineGroupObjectiveQuestionsView extends AwBaseView {
    interface Presenter extends AwBasePresenter {
        void getSimilar(String params);
    }

    interface View extends AwBaseView {
        void getSimilarSuccess(List<SimilarResultBean> result);
        void getSimilarFail(String msg);
    }
}
