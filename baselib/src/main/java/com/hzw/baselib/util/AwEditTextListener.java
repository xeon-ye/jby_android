package com.hzw.baselib.util;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.hzw.baselib.R;

/**
 * Created by hzw on 2019/4/2.
 */

public class AwEditTextListener {

    public static void setEditTextListener(final Context context, EditText et, final View line) {
        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    line.setBackgroundColor(context.getResources().getColor(R.color.red));
                } else {
                    line.setBackgroundColor(context.getResources().getColor(R.color.color_ebebeb));
                }
            }
        });
    }

    public static void isChangeListener(EditText et, final EditTextChangeListenerCallback callback) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s != null) {
                    callback.result(true);
                } else {
                    callback.result(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public interface EditTextChangeListenerCallback {
        void result(boolean changed);
    }
}
