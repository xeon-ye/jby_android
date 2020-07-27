package com.jkrm.education.bean.rx;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/25 16:31
 */

public class RxPayType {
    public static int PAY_TYPE;
    public static final int PURSE_PAY = 0;//钱包支付
    public static final int WECHAT_PAY = 1;//微信支付
    public static final int ALI_PAY = 2;//支付宝支付
    public static final int WECHAT_RECHARGE=3;//微信充值



    public static  int PAY_CODE;
    public static final int PAY_SUCCESS=200;//支付成功
    public static  final int PAY_FAIL=100;//支付失败
    public static  final int PAY_CANCEL=300;//取消支付

    public RxPayType(int PAY_TYPE, int PAY_CODE) {
        this.PAY_TYPE = PAY_TYPE;
        this.PAY_CODE = PAY_CODE;
    }

    public static int getPayType() {
        return PAY_TYPE;
    }

    public static void setPayType(int payType) {
        PAY_TYPE = payType;
    }

    public static int getPayCode() {
        return PAY_CODE;
    }

    public static void setPayCode(int payCode) {
        PAY_CODE = payCode;
    }
}
