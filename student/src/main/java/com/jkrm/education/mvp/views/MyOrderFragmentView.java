package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.OrderBean;
import com.jkrm.education.bean.result.AccountBalancesBean;
import com.jkrm.education.bean.result.CourseAttrBean;
import com.jkrm.education.bean.result.CourseTypeBean;
import com.jkrm.education.bean.result.CreateAliPayOrderResultBean;
import com.jkrm.education.bean.result.CreateWechatPayOrderResultBean;
import com.jkrm.education.bean.result.MicroLessonResultBean;
import com.jkrm.education.bean.result.PurseResultBean;

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

        void createWechatOrder(RequestBody body);
        void createAlipayOrder(String orderNo, String amount);

        void getAccountBalances(String user_id);
        //钱包支付
        void pursePay(RequestBody body);
    }

    interface View extends AwBaseView {

        void getOrderListSuccess(OrderBean data);
        void getOrderListFail(String msg);

        void createWechatOrderSuccess(CreateWechatPayOrderResultBean bean);
        void createWechatOrderFail(String msg);

        void createAlipayOrderSuccess(CreateAliPayOrderResultBean bean);
        void createAlipayOrderFail(String msg);

        void getAccountBalancesSuccess(AccountBalancesBean bean);
        void getAccountBalancesFail(String msg);

        void pursePaySuccess(String bean);
        void pursePayFail(String msg);
    }
}
