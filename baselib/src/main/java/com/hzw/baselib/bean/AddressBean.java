package com.hzw.baselib.bean;

import java.io.Serializable;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/9 13:23
 */

public class AddressBean implements Serializable {


        /**
         * code : 110000
         * name : 北京市
         * shortName : 北京
         */

        private String code;
        private String name;
        private String shortName;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }
}
