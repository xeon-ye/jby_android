package com.jkrm.education.base;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.google.gson.Gson;
import com.hzw.baselib.base.AwBaseApplication;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwSpUtil;
import com.jkrm.education.bean.result.TeacherClassBean;
import com.jkrm.education.bean.rx.RxAlipushRegisterResultType;
import com.jkrm.education.bean.user.UserBean;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.db.util.DaoScoreCommonUseUtil;
import com.jkrm.education.util.SobotUtil;
import com.sobot.chat.SobotApi;
import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hzw on 2018/7/18.
 */
public class MyApp extends AwBaseApplication {

    private static MyApp instance;
    public static String token;
    private UserBean mUserBean;
    public static long codeConuntDown;
    //教师作业班级
    public static List<TeacherClassBean> mTeacherClassHomeworkBeanList = new ArrayList<>();
    public static String mStrSection="",mStrYear="",mStrPsw="",mStrVerCode="";


    private CloudPushService mCloudPushService = null;
    private boolean isRegisterSuccessAliPush = false;

    public static MyApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initCloudChannel(this);
        // 第三个参数测试时候设成true ,发布时候设置成false。
        CrashReport.initCrashReport(getApplicationContext(), MyConstant.ServerConstant.getBuglyAppid(), MyConstant.ServerConstant.isDevEnv());
        //初始化用户数据, 然后绑定阿里推送云账号
        initUserInfo();
        //启动清除临时数据
        AwSpUtil.clearAll(MyConstant.SPConstant.XML_TEMPORARY);
        setDefaultCountdown();
        //重启后, 清空常用分数
        //DaoScoreCommonUseUtil.getInstance().deleteAllBean();
        //注册智齿服务
        SobotApi.initSobotSDK(this, MyConstant.ServerConstant.SOBOT_APPKEY, "");
        SobotUtil.setSobotResourse();
    }

    /**
     * 初始化云推送通道
     * @param applicationContext
     */
    private void initCloudChannel(Context applicationContext) {
        PushServiceFactory.init(applicationContext);
        mCloudPushService = PushServiceFactory.getCloudPushService();
        mCloudPushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                AwLog.d("alipush init cloudchannel success deviceId: " + PushServiceFactory.getCloudPushService().getDeviceId());
                //推送注册延时, 需全局处理通知, 绑定账号
                EventBus.getDefault().postSticky(new RxAlipushRegisterResultType(true));
                isRegisterSuccessAliPush = true;
            }
            @Override
            public void onFailed(String errorCode, String errorMessage) {
                AwLog.d("alipush init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
                EventBus.getDefault().postSticky(new RxAlipushRegisterResultType(false));
                isRegisterSuccessAliPush = false;
            }
        });
    }

    /**
     * 绑定阿里云推送account
     * @param isClearUsrInfo
     */
    public void bindAliPushAccount(boolean isClearUsrInfo) {
        if(mCloudPushService == null) {
            AwLog.d("alipush init bindAccount CloudPushService is null");
            return;
        }
        if(isClearUsrInfo) {
            mCloudPushService.unbindAccount(new CommonCallback() {
                @Override
                public void onSuccess(String s) {
                    AwLog.d("alipush init unbindAccount onSuccess");
                }

                @Override
                public void onFailed(String s, String s1) {
                    AwLog.d("alipush init unbindAccount onFailed");
                }
            });
            return;
        } else {
            if(null == getAppUser() || AwDataUtil.isEmpty(getAppUser().getTeacherId())) {
                AwLog.d("alipush init bindAccount teacherId is null");
                return;
            }
            mCloudPushService.bindAccount(getAppUser().getTeacherId(), new CommonCallback() {
                @Override
                public void onSuccess(String s) {
                    AwLog.d("alipush init bindAccount onSuccess teacherId: " + getAppUser().getTeacherId());
                }

                @Override
                public void onFailed(String s, String s1) {
                    AwLog.d("alipush init bindAccount onFailed teacherId: " + getAppUser().getTeacherId());
                }
            });
        }
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
        //登录后判断是否注册成功了阿里云推送, 没成功, 重新注册, 成功后直接绑定account
        if(isRegisterSuccessAliPush) {
            bindAliPushAccount(false);
        } else {
            initCloudChannel(this);
        }
    }

    public void saveToken(String token) {
        AwLog.d("MyApp saveToken: " + token);
        AwSpUtil.saveString(MyConstant.SPConstant.XML_USER_INFO, MyConstant.SPConstant.KEY_TOKEN, token);
        this.token = token;
        AwLog.d("MyApp saveToken after: " + this.token);
    }

    public void saveCli(String cli){
        AwSpUtil.saveString(MyConstant.SPConstant.XML_USER_INFO,MyConstant.SPConstant.KEY_CLI,cli);
    }
    /**
     * 清除用户信息
     */
    public void clearLoginUser() {
        AwSpUtil.clearAll(MyConstant.SPConstant.XML_USER_INFO);
        mUserBean = null;
        token = null;
        //退出登录, 取消阿里云推送绑定的account.
        bindAliPushAccount(true);
    }


    //账户  登录接口未返回
    public void saveAcc(String acc){
        AwSpUtil.saveString(MyConstant.SPConstant.XML_USER_INFO,MyConstant.SPConstant.KEY_ACC,acc);
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
    public void saveRoleID(String roleID){
        AwSpUtil.saveString(MyConstant.SPConstant.XML_USER_INFO,MyConstant.SPConstant.ROLE_ID,roleID);
    }
}
