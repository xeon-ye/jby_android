package com.hzw.baselib.util;

import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzw.baselib.interfaces.TextChangeListener;

/**
 * Created by hzw on 2019/5/26.
 */

public class AwTextListenerUtil {

    public static final int HANDLER_MSG_EDITED_LISTENER = 2;

    public static void addEditedListener(TextView tv, ImageView iv, boolean isInitData, final Handler handler) {
        tv.addTextChangedListener(new TextChangeListener(tv, iv, isInitData, new TextChangeListener.OnTextEditListener() {
            @Override
            public void onEdited() {
                AwLog.d("addEditedListener onEdited");
                handler.sendEmptyMessage(HANDLER_MSG_EDITED_LISTENER);
            }
        }));
    }

    public static void addEditedMaxLengthListener(TextView tv, TextView tv2, boolean isInitData, final Handler handler, int maxLength) {
        tv.addTextChangedListener(new TextChangeListener(tv, tv2, maxLength, isInitData, new TextChangeListener.OnTextEditListener() {
            @Override
            public void onEdited() {
                AwLog.d("addEditedListener onEdited");
                handler.sendEmptyMessage(HANDLER_MSG_EDITED_LISTENER);
            }
        }));
    }

    public static void addEditedHasInitIvListener(TextView tv, ImageView iv_init, ImageView iv, boolean isInitData, final Handler handler) {
        tv.addTextChangedListener(new TextChangeListener(tv, iv_init, iv, isInitData, new TextChangeListener.OnTextEditListener() {
            @Override
            public void onEdited() {
                AwLog.d("addEditedListener onEdited");
                handler.sendEmptyMessage(HANDLER_MSG_EDITED_LISTENER);
            }
        }));
    }

    public static void addEditedLimitLengthListener(TextView tv, ImageView iv, boolean isInitData, final Handler handler, boolean limitLength, int default_decimal_length, int defalut_max_length) {
        tv.addTextChangedListener(new TextChangeListener(tv, iv, limitLength, default_decimal_length, defalut_max_length, isInitData, new TextChangeListener.OnTextEditListener() {
            @Override
            public void onEdited() {
                AwLog.d("addEditedLimitLengthListener onEdited");
                handler.sendEmptyMessage(HANDLER_MSG_EDITED_LISTENER);
            }
        }));
    }
}
