package com.jkrm.education.constants;

import com.hzw.baselib.constants.AwBaseConstant;

/**
 * Created by hzw on 2018/11/14.
 */

public class MyConstant {

    public class SPConstant {
        /**
         * 保存临时信息XML文件名
         */
        public static final String XML_TEMPORARY = "xml_temporary";
        /**
         * 保存用户信息的XML文件名
         */
        public static final String XML_USER_INFO = "xml_userinfo";
        /**
         * 保存用户信息的XML文件名
         */
        public static final String XML_APP_INFO = "xml_appinfo";
        /**
         * 设置页面xml
         */
        public static final String SWITCH_XML_NAME = "switch_xml_name";
        /**
         * 保存用户登录信息的key
         */
        public static final String KEY_USER = "key_userinfo";
        public static final String KEY_IS_FIRST = "isFirst";
        public static final String KEY_LOGIN_NAME = "key_loginName";
        public static final String KEY_TOKEN = "key_token";
        public static final String KEY_CLI = "key_cli";
        public static final String KEY_ACC="user_acc";//账号
        public static final String ROLE_ID="role_id";



        /**
         * 临时手机号
         */
        public static final String KEY_TEMPORARY_MOBILE = "key_temporary_mobile";

    }

    public static class ServerConstant {
        //        public final static String API_BASE_URL = "http://103.91.208.29:9000/app/";
//        public final static String API_BASE_URL = "https://mockapi.eolinker.com/";
 //        public final static String API_BASE_URL = "https://test.api.xinjiaoyu.com/"; //测试接口url(扫描清数据使用) http://39.106.225.181:9000/
//        public final static String API_BASE_URL = "http://39.97.238.219:9000/";//日常接口url(供开发调试等)
      public final static String API_BASE_URL = "https://api.xinjiaoyu.com/";//正式接口url http://39.97.244.35:9000/

        //                public final static String BUGLY_APPID = "9d8da463d2"; //bugly dev环境
        public final static String BUGLY_APPID = "1b12d45d0b"; //bugly prod环境
        public static final String SOBOT_APPKEY = "b72117a422d54e9b9a2afdb33d54af0a";//智齿key

        public static String getBuglyAppid() {
            if (API_BASE_URL.equals("https://test.api.xinjiaoyu.com/")) {
                return "9d8da463d2";
            } else {
                return "1b12d45d0b";
            }
        }

        public static boolean isDevEnv() {
            return API_BASE_URL.equals("https://test.api.xinjiaoyu.com/");
        }

        public static String getVersionEnvType() {
            if (API_BASE_URL.equals("https://test.api.xinjiaoyu.com/")) {
                return AwBaseConstant.VERSION_ENVIRONTYPE_TEST;
            } else {
                return AwBaseConstant.VERSION_ENVIRONTYPE_PROD;
            }
        }
    }

    public class LocalConstant {
        public static final int CORNER_COMMON = 5;
        public static final int CORNER__IMG_COMMON = 15;

        public static final int TAG_CONTRACT = 0;
        public static final int TAG_NEWS_DETAIL = 1;
        public static final int TAG_NAME = 3;
        public static final int TAG_PHONE = 4;
        public static final int TAG_ABOUT_US = 5;

        /**
         * 通用每页获取数据量
         */
        public static final int REQUEST_PAGE_SIZE = 10;
        /**
         * 热点资讯获取数据量
         */
        public static final int REQUEST_PAGE_SIZE_NEWS_HOT = 2;
        /**
         * 轮播图获取数据量
         */
        public static final int REQUEST_PAGE_SIZE_BANNER = 50;
        /**
         * 倒计时默认时长
         */
        public static final long CODE_COUNT_DOWN_DEFALUT_VALUE = 60000;

        public static final int TAG_STATISTICS_HOMEWORK = 1;
        public static final int TAG_STATISTICS_SCORE = 2;
        public static final int TAG_STATISTICS_PRACTICE = 3;

    }

    public class ViewConstant {
        public static final int VIEW_EMPTY_COMMON = 0;


    }
}
