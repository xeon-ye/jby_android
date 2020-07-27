package com.hzw.baselib.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.hzw.baselib.R;

/**
 * Created by hzw on 2018/11/15.
 */

public class AwViewUtil {

    /**
     * @category 让EditText内的密码显示明文或显示mask符号
     */
    public static void setEditTxtVisibility(EditText et, boolean flag) {
        int type = flag ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                : (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        et.setInputType(type);
    }

    public static void setTopView(Context context, RadioButton view, int drawableId) {
        Drawable top = context.getResources().getDrawable(drawableId);
        top.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());
        view.setCompoundDrawables(null, top, null, null);
    }
}
