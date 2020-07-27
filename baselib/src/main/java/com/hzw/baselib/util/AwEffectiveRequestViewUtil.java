package com.hzw.baselib.util;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzw.baselib.R;


/**
 * Created by hzw on 2018/4/19.
 */

public class AwEffectiveRequestViewUtil {

    public static void setButtonEnable(Context context, Button view, boolean isAllowClick) {
        if(isAllowClick) {
            view.setEnabled(true);
            view.setBackgroundResource(R.drawable.aw_bg_red);
            view.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            view.setEnabled(false);
            view.setBackgroundResource(R.drawable.aw_bg_gray);
            view.setTextColor(context.getResources().getColor(R.color.white));
        }
    }

    public static void setButtonEnableBlue(Context context, Button view, boolean isAllowClick) {
        if(isAllowClick) {
            view.setEnabled(true);
            view.setBackgroundResource(R.drawable.aw_bg_blue_radius_15);
//            view.setTextColor(R.drawable.aw_selector_text_default_white_with_black);
            view.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            view.setEnabled(false);
            view.setBackgroundResource(R.drawable.aw_bg_gray_eaeef7);
            view.setTextColor(context.getResources().getColor(R.color.gray));
        }
    }

    public static void setButtonEnableChangeTxtColor(Context context, Button view, boolean isAllowClick) {
        if(isAllowClick) {
            view.setEnabled(true);
            view.setTextColor(context.getResources().getColor(R.color.blue));
        } else {
            view.setEnabled(false);
            view.setTextColor(context.getResources().getColor(R.color.gray));
        }
    }

    public static void setTextViewEnable(Context context, TextView view, boolean isAllowClick) {
        if(isAllowClick) {
            view.setEnabled(true);
            view.setBackgroundResource(R.drawable.aw_bg_red);
            view.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            view.setEnabled(false);
            view.setBackgroundResource(R.drawable.aw_bg_gray);
            view.setTextColor(context.getResources().getColor(R.color.white));
        }
    }

    public static void setTextViewEnableBlue(Context context, TextView view, boolean isAllowClick) {
        if(isAllowClick) {
            view.setEnabled(true);
            view.setTextColor(context.getResources().getColor(R.color.blue));
        } else {
            view.setEnabled(false);
            view.setTextColor(context.getResources().getColor(R.color.gray));
        }
    }

    public static void setLayoutEnable(Context context, LinearLayout view, TextView tv, boolean isAllowClick) {
        if(isAllowClick) {
            view.setEnabled(true);
            view.setBackgroundResource(R.drawable.aw_bg_red);
            if(tv != null)
                tv.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            view.setEnabled(false);
            view.setBackgroundResource(R.drawable.aw_bg_gray);
            if(tv != null)
                tv.setTextColor(context.getResources().getColor(R.color.white));
        }
    }

    public static void setTextViewBgBlueAndWhite(Context context, TextView tv, boolean effective) {
        if(effective) {
            tv.setBackground(context.getResources().getDrawable(R.drawable.aw_bg_blue_radius_15));
            tv.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            tv.setBackground(context.getResources().getDrawable(R.drawable.aw_bg_white_stroke_b9b9b9));
            tv.setTextColor(context.getResources().getColor(R.color.black));
        }
    }

}
