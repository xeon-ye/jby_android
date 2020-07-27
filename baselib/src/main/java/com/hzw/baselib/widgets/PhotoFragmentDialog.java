package com.hzw.baselib.widgets;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hzw.baselib.R;

/**
 * @Description: 弹出窗选择拍照
 * @Author: xiangqian
 * @CreateDate: 2020/4/28 16:44
 */

public class PhotoFragmentDialog extends DialogFragment {
    private OnDialogButtonClickListener onDialogButtonClickListener;

    public void setOnDialogButtonClickListener(OnDialogButtonClickListener onDialogButtonClickListener) {
        this.onDialogButtonClickListener = onDialogButtonClickListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.photo_choose_dialog, null);
        init(view);
        final Dialog dialog = new Dialog(getActivity(), R.style.video_style_dialog_progress);
        dialog.setContentView(view);
        dialog.show();

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM); //可设置dialog的位置
        window.getDecorView().setPadding(0, 0, 0, 0); //消除边距

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;   //设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    private void init(View view) {
        view.findViewById(R.id.btn_pz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDialogButtonClickListener.photoButtonClick();
                dismiss();
            }
        });
        view.findViewById(R.id.btn_chose_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDialogButtonClickListener.choseButtonClick();
                dismiss();
            }
        });
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDialogButtonClickListener.cancelButtonClick();
                dismiss();
            }
        });
    }

    //实现回调功能
    public interface OnDialogButtonClickListener {
         void photoButtonClick();
         void choseButtonClick();
         void cancelButtonClick();

    }
}
