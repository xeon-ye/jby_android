package com.jkrm.education.constants;

import android.os.Environment;

import com.hzw.baselib.constants.AwBaseConstant;

/**
 * Created by hzw on 2018/11/14.
 */

public class MyConstant {

    public class SPConstant {
        /**
         * 保存临时信息XML文件名
         */
        public static final String XML_TEMPORARY ="xml_temporary";
        /**
         * 保存用户信息的XML文件名
         */
        public static final String XML_USER_INFO ="xml_userinfo";
        /**
         * 保存用户信息的XML文件名
         */
        public static final String XML_APP_INFO ="xml_appinfo";
        /**
         * 设置页面xml
         */
        public static final String SWITCH_XML_NAME = "switch_xml_name";
         /**
         * 保存用户登录信息的key
         */
        public static final String KEY_USER ="key_userinfo";
        public static final String KEY_IS_FIRST = "isFirst";
        public static final String KEY_LOGIN_NAME = "key_loginName";
        public static final String KEY_TOKEN = "key_token";
        public static final String KEY_CLI="key_cli";
        public static final String ROLE_ID="role_id";
        public static final String SCHOOL_ID="school_id";
        public static final String KEY_ACC="user_acc";//账号

        /**
         * 临时手机号
         */
        public static final String KEY_TEMPORARY_MOBILE = "key_temporary_mobile";

        /**
         * 图书习题筛选记录
         */
        public static final String KEY_BOOKEXERCISES_SCREEN="key_bookexercises_screen";//右侧筛选记录
        public static final String KEY_BOOKEXERCISES_CLASS_HOUR="key_bookexercises_class_hour";//左  侧课时记录



    }

    public static class ServerConstant {
//        public final static String API_BASE_URL = "http://103.91.208.29:9000/app/";
//        public final static String API_BASE_URL = "https://mockapi.eolinker.com/";
            public final static String API_BASE_URL = "https://test.api.xinjiaoyu.com/"; //测试接口url(扫描清数据使用) http://39.106.225.181:9000/
//        public final static String API_BASE_URL = "http://39.97.238.219:9000/";//日常接口url(供开发调试等)
//          public final static String API_BASE_URL = "https://api.xinjiaoyu.com/";//正式接口url http://39.97.244.35:9000/

//        public final static String BUGLY_APPID = "8dc29fbdc4"; //bugly dev环境
        public final static String BUGLY_APPID = "970ed7b634"; //bugly prod环境

        public static final String SOBOT_APPKEY = "efd60ad74d2144bc921f18c7f313a6c6"; //智齿key

        public static final String UMENG_APPKEY="5e8fd811978eea0718fb6b5c";//友盟key
        public static final String WX_ID="wxfc095f07424b1c4d";//微信appid
        public static final String WX_APPKEY="baed88ad9ba40619d16baf9dadbd87c5";//微信appkey
        public static final String QQ_ID="1109715700";//qq id
        public static final String QQ_APPKEY="xxSkJOPpi53JGEzU";// qq key


        public static String getBuglyAppid() {
            if(API_BASE_URL.equals("https://test.api.xinjiaoyu.com/")) {
                return "8dc29fbdc4";
            } else {
                return "970ed7b634";
            }
        }

        public static boolean isDevEnv() {
            return API_BASE_URL.equals("https://test.api.xinjiaoyu.com/");
        }

        public static String getVersionEnvType() {
            if(API_BASE_URL.equals("https://test.api.xinjiaoyu.com/")) {
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

        /** 通用每页获取数据量 */
        public static final int REQUEST_PAGE_SIZE = 10;
        /** 热点资讯获取数据量 */
        public static final int REQUEST_PAGE_SIZE_NEWS_HOT = 2;
        /** 轮播图获取数据量 */
        public static final int REQUEST_PAGE_SIZE_BANNER = 50;
        /** 倒计时默认时长 */
        public static final long CODE_COUNT_DOWN_DEFALUT_VALUE = 60000;


    }

    public class ViewConstant {
        public static final int VIEW_EMPTY_COMMON = 0;
    }
}
