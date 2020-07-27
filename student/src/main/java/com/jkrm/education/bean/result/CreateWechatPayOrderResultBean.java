package com.jkrm.education.bean.result;

import com.google.gson.annotations.SerializedName;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/24 16:37
 */

public class CreateWechatPayOrderResultBean {


    /**
     * code : 200
     * data : {"nonce_str":"wveYNqAPmf7Ro8cJ","package":"Sign=WXPay","sign":"5A7BA3E7EA4EB0D9BB2C6F3F749C1BC1","return_msg":"OK","mch_id":"1503198241","prepay_id":"wx251550529231373cb315fc111454071800","appid":"wxfc095f07424b1c4d","trade_type":"APP","result_code":"SUCCESS","partnerid":"1503198241","return_code":"SUCCESS","timestamp":"1585122652","retcode":"0"}
     * msg :
     */


        /**
         * nonce_str : wveYNqAPmf7Ro8cJ
         * package : Sign=WXPay
         * sign : 5A7BA3E7EA4EB0D9BB2C6F3F749C1BC1
         * return_msg : OK
         * mch_id : 1503198241
         * prepay_id : wx251550529231373cb315fc111454071800
         * appid : wxfc095f07424b1c4d
         * trade_type : APP
         * result_code : SUCCESS
         * partnerid : 1503198241
         * return_code : SUCCESS
         * timestamp : 1585122652
         * retcode : 0
         */

        private String nonce_str;
        @SerializedName("package")
        private String packageX;
        private String sign;
        private String return_msg;
        private String mch_id;
        private String prepay_id;
        private String appid;
        private String trade_type;
        private String result_code;
        private String partnerid;
        private String return_code;
        private String timestamp;
        private String retcode;

        public String getNonce_str() {
            return nonce_str;
        }

        public void setNonce_str(String nonce_str) {
            this.nonce_str = nonce_str;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getReturn_msg() {
            return return_msg;
        }

        public void setReturn_msg(String return_msg) {
            this.return_msg = return_msg;
        }

        public String getMch_id() {
            return mch_id;
        }

        public void setMch_id(String mch_id) {
            this.mch_id = mch_id;
        }

        public String getPrepay_id() {
            return prepay_id;
        }

        public void setPrepay_id(String prepay_id) {
            this.prepay_id = prepay_id;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getTrade_type() {
            return trade_type;
        }

        public void setTrade_type(String trade_type) {
            this.trade_type = trade_type;
        }

        public String getResult_code() {
            return result_code;
        }

        public void setResult_code(String result_code) {
            this.result_code = result_code;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getReturn_code() {
            return return_code;
        }

        public void setReturn_code(String return_code) {
            this.return_code = return_code;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getRetcode() {
            return retcode;
        }

        public void setRetcode(String retcode) {
            this.retcode = retcode;
        }
}
