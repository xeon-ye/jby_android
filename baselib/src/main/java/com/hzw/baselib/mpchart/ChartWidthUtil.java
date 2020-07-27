package com.hzw.baselib.mpchart;

/**
 * Created by hzw on 2019/8/23.
 */

public class ChartWidthUtil {

    public static float getBarWidth(int size) {
        float width = 0f;
        switch (size) {
            case 1:
                width = 0.2f;
                break;
            case 2:
                width = 0.3f;
                break;
            default:
                width = 0.5f;
                break;
        }
        return width;
    }

    public static float getBubbleWidth(int size) {
        float width = 0f;
        switch (size) {
            case 1:
                width = 0.05f;
                break;
            case 2:
                width = 0.08f;
                break;
            default:
                width = 0.12f;
                break;
        }
        return width;
    }
}
