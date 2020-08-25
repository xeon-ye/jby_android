package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.AccountBalancesBean;
import com.jkrm.education.bean.result.CreateAliPayOrderResultBean;
import com.jkrm.education.bean.result.CreateOrderResultBean;
import com.jkrm.education.bean.result.CreateWechatPayOrderResultBean;
import com.jkrm.education.bean.result.PurseResultBean;

import okhttp3.RequestBody;


/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/6 10:04
 */

public interface ConfirmAnOrderView extends AwBaseView {
    interface Presenter extends AwBasePresenter {
        //创建订单
        void createOrder(RequestBody body);

        void createWechatOrder(RequestBody body);
        void createAlipayOrder(String orderNo, String amount);

        void getAccountBalances(String user_id);
        //钱包支付
        void pursePay(RequestBody body);
    }

    interface View extends AwBaseView {
        void createOrderSuccess(CreateOrderResultBean resultBean);
        void createOrderFail(String msg);

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
