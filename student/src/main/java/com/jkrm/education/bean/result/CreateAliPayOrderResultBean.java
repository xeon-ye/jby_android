package com.jkrm.education.bean.result;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/24 16:38
 */

public class CreateAliPayOrderResultBean {


    /**
     * code : 200
     * data : alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2018042102589549&biz_content=%7B%22out_trade_no%22%3A%22f48c92f08f8a481fb571b18531415244%22%2C%22product_code%22%3A%22FAST_INSTANT_TRADE_PAY%22%2C%22subject%22%3A%22yy%E7%9A%84%E5%A5%97%E9%A4%90%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=https%3A%2F%2Ftest.xinjiaoyu.com%2Fapi%2Fv2%2Fuser_server%2Falipay%2Fnotify&return_url=https%3A%2F%2Ftest.xinjiaoyu.com%2Fapi%2Fv2%2Fuser_server%2Falipay%2Freturn&sign=iiat%2FU5nDoSjub3OSocv7SF%2Bq9DwCNsmasGyrrHymhJ1x3q2jEkXgnJLg1rE%2Fzfmw8yxQbVMHyDkiqS2TN6th8xWpIyK1OJbnx%2BexpYTesLahmjx6J0VNDl6Kdbr0N2lTAN0i5q3aFukKIhNmRkFgpw9gd0LbO3RGwGJtZyTqLpyAgqeR6Aqp31mTTXUh3j6aqlLz6CdhocQrO2%2BOmTUxGt9MLmoyrQ4VFe5W2Chyy0wj0w8OrE0OqkWjIdw083dRwkUKy35an%2Ff3ktTR%2BorchUz5eEX5WX4Gsp7zdEOJ7lnDxnGhJ1zlhKH%2FMfRosdwUwbZIal76E7RsnxdC%2BPw8Q%3D%3D&sign_type=RSA2&timestamp=2020-03-26+17%3A14%3A25&version=1.0
     * msg :
     */

    private String code;
    private String data;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
