package com.jkrm.education.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.google.gson.Gson;
import com.hzw.baselib.base.AwBaseApplication;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwSpUtil;
import com.jkrm.education.bean.user.UserBean;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.download.DownLoadService;
import com.jkrm.education.util.SobotUtil;
import com.sobot.chat.SobotApi;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;


/**
 * Created by hzw on 2018/7/18.
 */
public class MyApp extends AwBaseApplication {

    private static MyApp instance;
    public static String token;
    private UserBean mUserBean;
    public static long codeConuntDown;
    public static String WX_APP_ID="wxfc095f07424b1c4d";
    public static String mStrSection,mStrYear,mStrPsw,mStrVerCode;

    public static MyApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // 第三个参数测试时候设成true ,发布时候设置成false。
        CrashReport.initCrashReport(getApplicationContext(), MyConstant.ServerConstant.getBuglyAppid(), MyConstant.ServerConstant.isDevEnv());
        initUserInfo();
        //启动清除临时数据
        AwSpUtil.clearAll(MyConstant.SPConstant.XML_TEMPORARY);
        setDefaultCountdown();
//        LitePal.initialize(this);
        //注册智齿服务
        SobotApi.initSobotSDK(this, MyConstant.ServerConstant.SOBOT_APPKEY, "");
        SobotUtil.setSobotResourse();
        //友盟
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this, MyConstant.ServerConstant.UMENG_APPKEY, "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        //微信信息
        PlatformConfig.setWeixin(MyConstant.ServerConstant.WX_ID,MyConstant.ServerConstant.WX_APPKEY);
        //qq信息
        PlatformConfig.setQQZone(MyConstant.ServerConstant.QQ_ID,MyConstant.ServerConstant.QQ_APPKEY);
    }

    /**
     * 初始化用户对象
     */
    private void initUserInfo() {
        String data = AwSpUtil.getString(MyConstant.SPConstant.XML_USER_INFO, MyConstant.SPConstant.KEY_USER, "");
        token = AwSpUtil.getString(MyConstant.SPConstant.XML_USER_INFO, MyConstant.SPConstant.KEY_TOKEN, "");
        AwLog.d("initUserInfo data: " + data  + " \ntoken: " + token);
        if (data != null) {
            Gson gson = new Gson();
            mUserBean = gson.fromJson(data, UserBean.class);
        } else {
            mUserBean = null;
        }
    }

    /**
     * 保存用户登录信息
     */
    public void saveLoginUser(UserBean userInfoBean) {
        if (userInfoBean == null) {
            return;
        }
        Gson gson = new Gson();
        String data = gson.toJson(userInfoBean);
        AwSpUtil.saveString(MyConstant.SPConstant.XML_USER_INFO, MyConstant.SPConstant.KEY_USER, data);
        initUserInfo();
    }

    public void saveToken(String token) {
        AwLog.d("MyApp saveToken: " + token);
        AwSpUtil.saveString(MyConstant.SPConstant.XML_USER_INFO, MyConstant.SPConstant.KEY_TOKEN, token);
        this.token = token;
        AwLog.d("MyApp saveToken after: " + this.token);
    }

    public void saveRoleID(String roleID){
        AwSpUtil.saveString(MyConstant.SPConstant.XML_USER_INFO,MyConstant.SPConstant.ROLE_ID,roleID);
    }
    //时间戳
    public void saveCli(String cli){
        AwSpUtil.saveString(MyConstant.SPConstant.XML_USER_INFO,MyConstant.SPConstant.KEY_CLI,cli);
    }
    //账户  登录接口未返回
    public void saveAcc(String acc){
        AwSpUtil.saveString(MyConstant.SPConstant.XML_USER_INFO,MyConstant.SPConstant.KEY_ACC,acc);
    }

    /**
     * 清除用户信息
     */
    public void clearLoginUser() {
        AwSpUtil.clearAll(MyConstant.SPConstant.XML_USER_INFO);
        mUserBean = null;
        token = null;
    }


    /**
     * 取出用户信息
     *
     * @return
     */
    public UserBean getAppUser() {
        return mUserBean;
    }

    public void setDefaultCountdown() {
        codeConuntDown = MyConstant.LocalConstant.CODE_COUNT_DOWN_DEFALUT_VALUE;
    }

    public boolean isDefaultCountdown() {
        return MyConstant.LocalConstant.CODE_COUNT_DOWN_DEFALUT_VALUE == codeConuntDown;
    }
}
