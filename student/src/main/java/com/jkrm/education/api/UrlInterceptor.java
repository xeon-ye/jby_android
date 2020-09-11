package com.jkrm.education.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwMd5Util;
import com.hzw.baselib.util.AwSpUtil;
import com.hzw.baselib.util.SecurityUtils;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.util.UserUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hzw on 2019/4/25.
 */

public class UrlInterceptor implements Interceptor {

    public static final String APPLICATION_JSON = "application/json";//返回类型
    private static final String TAG = "UrlInterceptor";
    private static String sT;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Log.d(TAG, "UrlInterceptor_ " + System.currentTimeMillis());
        Request oldRequest = chain.request();
        // 添加新的参数
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host());
        AwLog.d(TAG, "请求时的token: " + getToken());
        // 新的请求
        Request newRequest = oldRequest.newBuilder()
                .addHeader("Content-Type", APPLICATION_JSON)
                .addHeader("Authorization",  getToken())
//                .addHeader("Authorization", "Bearer " + token)
                .addHeader("Client", "android")
                .addHeader("App", "student")
                .addHeader("CLIENTSESSIONID", getCli())
                .addHeader("Role", getRoleId())
                .addHeader("t",getT())
                .addHeader("encrypt", getSafe())
                .method(oldRequest.method(), oldRequest.body())
                .url(authorizedUrlBuilder.build())
                .build();
        AwLog.d(TAG, newRequest.toString());
        AwLog.d(TAG, "请求时的时间戳 " + getCli());
        //响应信息拦截
        return chain.proceed(newRequest);
    }

    /**
     * 获取token对象
     *
     * @return strToken
     */
    static String getToken() {
        String token = AwSpUtil.getString(MyConstant.SPConstant.XML_USER_INFO, MyConstant.SPConstant.KEY_TOKEN, "");
        AwLog.d(TAG, token);
        //        if(AwDataUtil.isEmpty(token)) {
//            UUID uuid = UUID.randomUUID();
//            String strUUID = uuid.toString();
//            AwSpUtil.saveString(MyConstant.SPConstant.XML_USER_INFO, MyConstant.SPConstant.KEY_TOKEN, strUUID);
//        }
        return AwSpUtil.getString(MyConstant.SPConstant.XML_USER_INFO, MyConstant.SPConstant.KEY_TOKEN, "");
    }

    /**
     * 获取时间戳并且MD5加密
     * @return
     */
    /**
     * 获取时间戳并且MD5加密
     *
     * @return
     */
    public static String getCli() {
        String cli = "";
        cli = AwSpUtil.getString(MyConstant.SPConstant.XML_USER_INFO, MyConstant.SPConstant.KEY_CLI, "");
        if (TextUtils.isEmpty(cli)) {
            cli = AwMd5Util.md5(String.valueOf(System.currentTimeMillis()));
            MyApp.getInstance().saveCli(cli);
            //AwSpUtil.saveString(MyConstant.SPConstant.XML_USER_INFO,MyConstant.SPConstant.KEY_CLI,AwMd5Util.md5(String.valueOf(System.currentTimeMillis())));
        }
        //cli = AwMd5Util.md5(String.valueOf(System.currentTimeMillis()));
        return cli;
    }

    public static String getT(){
        sT = String.valueOf(System.currentTimeMillis());
        return sT;
    }

    public static String getSafe() {
        SharedPreferences sharedPreferences = MyApp.getInstance().getSharedPreferences(MyConstant.SPConstant.KEY_SAFE, Context.MODE_PRIVATE);
        String safe_code = sharedPreferences.getString(MyConstant.SPConstant.KEY_SAFE, "");
        String s = AwMd5Util.md5(safe_code + sT + getCli());
        return s;
    }

    /**
     * roldid
     *
     * @return
     */
   public static String getRoleId() {
        String encrypt = "";
        try {
            encrypt = SecurityUtils.encrypt(UserUtil.getRoleld());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return encrypt;
    }

}
