package com.hzw.baselib.util;

import com.hzw.baselib.bean.FirUpdateBean;
import com.hzw.baselib.bean.UpdateBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by hzw on 2019/4/9.
 */

public class AwFirUtil {

    public static void checkUpdateInfo(String firApiId, final UpdateCheckCallback callback) {
        final String url = FirUpdateBean.URL + firApiId + "?api_token=" + FirUpdateBean.API_TOKEN + "&type=android";
        AwLog.d("update url: " + url);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL httpUrl = new URL(url);

                    HttpURLConnection conn = (HttpURLConnection) httpUrl
                            .openConnection();
                    conn.setRequestMethod("GET");
                    conn.setReadTimeout(3000);

                    if (conn.getResponseCode() == 200) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(
                                conn.getInputStream()));
                        StringBuffer sb = new StringBuffer();
                        String str;

                        while ((str = reader.readLine()) != null) {
                            sb.append(str);
                        }
                        String result = new String(sb.toString().getBytes(), "utf-8");

                        try {
                            JSONObject object = new JSONObject(result);
                            UpdateBean updateBean = new UpdateBean();
                            updateBean = new UpdateBean();
                            updateBean.setVersion(object.getString("versionShort"));
                            updateBean.setUrl(object.getString("installUrl"));
                            updateBean.setUpdateContent(object.getString("changelog"));

                            AwLog.d("检测更新服务器返回结果整合后 updateBean: " + updateBean.toString());
                            callback.updateCheckResult(updateBean);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.updateCheckResult(null);
                        }
                    } else {
                        callback.updateCheckResult(null);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    callback.updateCheckResult(null);
                } catch (IOException e) {
                    e.printStackTrace();
                    callback.updateCheckResult(null);
                }
            }
        }).start();
    }

    public interface UpdateCheckCallback {
        void updateCheckResult(UpdateBean updateBean);
    }
}
