package com.jkrm.education.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.jkrm.education.constants.UrlConstant;
import com.jkrm.education.ui.activity.CourseNotpurchasedActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/4/15 14:23
 */

public class ShareSDKUtils {

    public static void shareWX(Activity context, String url, String title, String description) {
        UMWeb umWeb = new UMWeb(url);
        umWeb.setTitle(title);//标题
        //umWeb.setThumb();//缩略图
        umWeb.setDescription(description);//描述
        new ShareAction(context).setPlatform(SHARE_MEDIA.WEIXIN)
                .withMedia(umWeb)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA platform) {
                        // 分享开始的回调
                    }

                    @Override
                    public void onResult(SHARE_MEDIA platform) {
                        Toast.makeText(context,   "微信分享成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(SHARE_MEDIA platform, Throwable t) {
                        Toast.makeText(context, "微信分享失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA platform) {
                        Toast.makeText(context,  "微信分享取消", Toast.LENGTH_SHORT).show();
                    }
                })
                .share();
    }

    public static void shareWXC(Activity context, String url, String title, String description) {
        UMWeb umWeb = new UMWeb(url);
        umWeb.setTitle(title);
        umWeb.setDescription(description);
        new ShareAction(context).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                .withMedia(umWeb)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA platform) {
                        // 分享开始的回调
                    }

                    @Override
                    public void onResult(SHARE_MEDIA platform) {
                        Toast.makeText(context,   "微信分享成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(SHARE_MEDIA platform, Throwable t) {
                        Toast.makeText(context, "微信分享失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA platform) {
                        Toast.makeText(context,  "微信分享取消", Toast.LENGTH_SHORT).show();
                    }
                })
                .share();
    }

    public static void shareQQ(Activity context, String url, String title, String description) {
        UMWeb umWeb = new UMWeb(url);
        umWeb.setTitle(title);
        umWeb.setDescription(description);
        new ShareAction(context).setPlatform(SHARE_MEDIA.QQ)
                .withMedia(umWeb)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA platform) {
                        // 分享开始的回调
                    }

                    @Override
                    public void onResult(SHARE_MEDIA platform) {
                        Toast.makeText(context, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(SHARE_MEDIA platform, Throwable t) {
                        Toast.makeText(context, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA platform) {
                        Toast.makeText(context, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
                    }
                })
                .share();
    }

    public static void shareQQZone(Activity context, String url, String title, String description) {
        UMWeb umWeb = new UMWeb(url);
        umWeb.setTitle(title);
        umWeb.setDescription(description);
        new ShareAction(context).setPlatform(SHARE_MEDIA.QZONE)
                .withMedia(umWeb)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA platform) {
                        // 分享开始的回调
                    }

                    @Override
                    public void onResult(SHARE_MEDIA platform) {
                        Toast.makeText(context, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(SHARE_MEDIA platform, Throwable t) {
                        Toast.makeText(context, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA platform) {
                        Toast.makeText(context, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
                    }
                })
                .share();
    }
}
