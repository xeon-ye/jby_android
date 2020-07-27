package com.hzw.baselib.util;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * Created by hzw on 2019/6/16.
 */

public class AwAnimationUtil {

    /**
     *   闪烁动画
     开始闪烁
     setDuration 设置闪烁一次的时间
     setRepeatCount 设置闪烁次数 可以是具体数值，也可以是Animation.INFINITE（重复多次）
     setRepeatMode 动画结束后从头开始或从末尾开始
     Animation.REVERSE（从末尾开始） Animation.RESTART（从头开始）
     setAnimation将设置的动画添加到view上
     * @param view
     */
    public static void startAlphaAnimation(View view) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
        alphaAnimation.setDuration(1500);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.RESTART);
        view.setAnimation(alphaAnimation);
        alphaAnimation.start();
    }
}
