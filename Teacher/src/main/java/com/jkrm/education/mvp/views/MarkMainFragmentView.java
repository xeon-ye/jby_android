package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.test.TestMarkBean;

import java.util.List;


/**
 * Created by hzw on 2018/11/13
 */
public interface MarkMainFragmentView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getMarkingList();
    }

    interface View extends AwBaseView {
        void getMarkingListSuccess(List<TestMarkBean> list);
        void getMarkingListFailure();
    }

}