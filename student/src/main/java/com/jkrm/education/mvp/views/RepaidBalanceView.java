package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.AccountBalancesBean;
import com.jkrm.education.bean.result.CreateAliPayOrderResultBean;
import com.jkrm.education.bean.result.CreateOrderResultBean;
import com.jkrm.education.bean.result.CreateWechatPayOrderResultBean;
import com.jkrm.education.bean.result.InvestResultBean;

import java.util.List;

import okhttp3.RequestBody;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/26 18:22
 */

public interface RepaidBalanceView extends AwBaseView{
    interface Presenter extends AwBasePresenter {
        //获取账号余额
        void getAccountBalances(String user_id);
        //获取充值列表
        void getInvestList(String clientType);
        //创建订单
        void createOrder(RequestBody body);
        //创建微信
        void createWechatOrder(RequestBody body);
        //创建支付宝支付
        void createAlipayOrder(String orderNo, String amount);
    }

    interface View extends AwBaseView {
        void getAccountBalancesSuccess(AccountBalancesBean bean);
        void getAccountBalancesFail(String msg);

        void getInvestListSuccess(List<InvestResultBean> list);
        void getInvestListFail(String msg);

        void createOrderSuccess(CreateOrderResultBean resultBean);
        void createOrderFail(String msg);

        void createWechatOrderSuccess(CreateWechatPayOrderResultBean bean);
        void createWechatOrderFail(String msg);

        void createAlipayOrderSuccess(CreateAliPayOrderResultBean bean);
        void createAlipayOrderFail(String msg);
    }

}
