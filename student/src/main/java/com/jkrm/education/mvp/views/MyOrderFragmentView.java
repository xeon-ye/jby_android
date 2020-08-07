package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.OrderBean;
import com.jkrm.education.bean.result.CourseAttrBean;
import com.jkrm.education.bean.result.CourseTypeBean;
import com.jkrm.education.bean.result.MicroLessonResultBean;

import java.util.List;

import okhttp3.RequestBody;


/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/5 10:12
 */

public interface MyOrderFragmentView extends AwBaseView {
    interface Presenter extends AwBasePresenter {
        void getOrderList(RequestBody requestBody);
    }

    interface View extends AwBaseView {

        void getOrderListSuccess(OrderBean data);
        void getOrderListFail(String msg);
    }
}
