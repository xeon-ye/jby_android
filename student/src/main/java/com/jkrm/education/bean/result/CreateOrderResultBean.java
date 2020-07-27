package com.jkrm.education.bean.result;

/**
 * @Description: 创建订单信息
 * @Author: xiangqian
 * @CreateDate: 2020/3/24 16:34
 */

public class CreateOrderResultBean {

    /**
     * code : 200
     * data : {"orderId":"18d3bffe148e4d8eb2f72a528913d236","validTime":"2020-03-26 15:24:15"}
     * msg :
     */

        private String orderId;
        private String validTime;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getValidTime() {
            return validTime;
        }

        public void setValidTime(String validTime) {
            this.validTime = validTime;
        }
}
