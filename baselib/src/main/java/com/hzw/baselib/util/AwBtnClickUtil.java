package com.hzw.baselib.util;

/**
 * Created by hzw on 2018/5/31.
 */

public class AwBtnClickUtil {

    /**
     * 点击间隔
     */
    public static final long CLICK_INTERVAL = 1000;
    private static long lastClickTime = 0;
    private static int lastButtonId = -1;

    /**
     * 判断两次点击的间隔，如果小于指定值，则认为是多次无效点击
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(-1, CLICK_INTERVAL);
    }

    /**
     * 判断两次点击的间隔，如果小于指定值，则认为是多次无效点击
     *
     * @return
     */
    public static boolean isFastDoubleClick(int buttonId) {
        return isFastDoubleClick(buttonId, CLICK_INTERVAL);
    }

    /**
     * 判断两次点击的间隔，如果小于diff，则认为是多次无效点击
     *
     * @param diff
     * @return
     */
    public static boolean isFastDoubleClick(int buttonId, long diff) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (lastButtonId == buttonId && lastClickTime > 0 && timeD < diff) {
            AwLog.d("按钮点击: 短时间内按钮多次触发, 不执行任何操作");
            return true;
        }
        AwLog.d("按钮点击: 非短时间内按钮多次触发, 执行指定操作");
        lastClickTime = time;
        lastButtonId = buttonId;
        return false;
    }
}
