package com.jkrm.education.receivers;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.hzw.baselib.util.AwLog;
import com.jkrm.education.bean.rx.RxAlipushDataResultType;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

/**
 * Created by hzw on 2019/6/15.
 */

public class AliPushReceiver extends MessageReceiver {
    // 消息接收部分的LOG_TAG
    public static final String REC_TAG = "receiver";
    @Override
    public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        // TODO 处理推送通知
        AwLog.d("alipush Receive notification, title: " + title + ", summary: " + summary + ", extraMap: " + extraMap);
        EventBus.getDefault().postSticky(new RxAlipushDataResultType(title, summary, extraMap));

    }
    @Override
    public void onMessage(Context context, CPushMessage cPushMessage) {
        AwLog.d("alipush onMessage, messageId: " + cPushMessage.getMessageId() + ", title: " + cPushMessage.getTitle() + ", content:" + cPushMessage.getContent());
//        EventBus.getDefault().postSticky(cPushMessage);
    }
    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        AwLog.d("alipush onNotificationOpened, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
    }
    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
        AwLog.d("alipush onNotificationClickedWithNoAction, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
    }
    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
        AwLog.d("alipush onNotificationReceivedInApp, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap + ", openType:" + openType + ", openActivity:" + openActivity + ", openUrl:" + openUrl);
    }
    @Override
    protected void onNotificationRemoved(Context context, String messageId) {
        AwLog.d("alipush onNotificationRemoved");
    }
}