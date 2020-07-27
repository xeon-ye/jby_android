package com.hzw.baselib.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hzw.baselib.R;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwScreenUtils;

import org.w3c.dom.Text;


/**
 * Created by hzw on 2018/7/20.
 */
public class AwViewDialog {
    private TextView mTvContent, mTvCancel,  mTvConfirm;
    private TextView tv_progress, btn_cancel;
    private ProgressBar pb_loading;

    private EditText et_content;
    private TextView tv_title;
    private View mSpiltLine;
    private Dialog dialog;
    private Context mContext;

    public Dialog getDialog() {
        return dialog;
    }

    public AwViewDialog(Context context) {
        mContext = context;
        dialog = new Dialog(context, R.style.prompt_dialog);
    }

    public void showMsgDialog(String msg) {
        dismiss();
        dialog = new Dialog(mContext, R.style.prompt_dialog);
        dialog.setContentView(R.layout.view_dialog_prompt);
        initView(dialog);
        mTvContent.setText(msg);
        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvCancel.setVisibility(View.GONE);
        mTvConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        if(AwScreenUtils.isLandscape(mContext)) {
            setDialogSize(mContext, 0.6, 0.5);
        } else {
            setDialogSize(mContext, 0.8, 0.5);
        }
        dialog.show();
    }

    public void showToastDialog(String msg) {
        dismiss();
        dialog = new Dialog(mContext, R.style.toast_dialog);
        dialog.setContentView(R.layout.view_dialog_toast);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        if(AwScreenUtils.isLandscape(mContext)) {
            setDialogSize(mContext, 0.6, 0.5);
        } else {
            setDialogSize(mContext, 0.6, 0.1);
        }
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        dialog.show();
    }

    public void showToastDialog2(String msg) {
        dismiss();
        dialog = new Dialog(mContext, R.style.toast_dialog);
        dialog.setContentView(R.layout.view_dialog_toast);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        if(AwScreenUtils.isLandscape(mContext)) {
            setDialogSize(mContext, 0.6, 0.5);
        } else {
            setDialogSize(mContext, 0.6, 0.2);
        }
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        dialog.show();
    }

    /**
     * 版本更新对话框
     * @param msg
     * @param confirmListener
     */
    public void showUpdateDialog(String msg, OnClickListener confirmListener, OnClickListener cancelListener) {
        dismiss();
        dialog = new Dialog(mContext, R.style.prompt_dialog);
        dialog.setContentView(R.layout.view_dialog_update);
        initView(dialog);
        mTvContent.setText(msg);
        dialog.setCanceledOnTouchOutside(false);
        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvConfirm.setOnClickListener(confirmListener);
        mTvCancel.setOnClickListener(cancelListener);
        if(AwScreenUtils.isLandscape(mContext)) {
            setDialogSize(mContext, 0.6, 0.5);
        } else {
            setDialogSize(mContext, 0.8, 0.3);
        }
        dialog.show();
    }

    /**
     * 版本下载对话框
     */
    public void showDownloadDialog() {
        dismiss();
        dialog = new Dialog(mContext, R.style.prompt_dialog);
        dialog.setContentView(R.layout.view_dialog_download);
        initUpdateDownloadView(dialog);
        btn_cancel.setVisibility(View.GONE);
        if(AwScreenUtils.isLandscape(mContext)) {
            setDialogSize(mContext, 0.6, 0.5);
        } else {
            setDialogSize(mContext, 0.8, 0.5);
        }
        dialog.show();
    }

    public void showDialog(String msg, OnClickListener confirmListener, boolean showCancelBtn) {
        dismiss();
        dialog = new Dialog(mContext, R.style.prompt_dialog);
        dialog.setContentView(R.layout.view_dialog_prompt);
        initView(dialog);
        mTvContent.setText(msg);
        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvCancel.setVisibility(showCancelBtn ? View.VISIBLE : View.GONE);
        mTvConfirm.setOnClickListener(confirmListener);
        mTvCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        if(AwScreenUtils.isLandscape(mContext)) {
            setDialogSize(mContext, 0.6, 0.5);
        } else {
            setDialogSize(mContext, 0.8, 0.5);
        }
        dialog.show();
    }

    public void showDialog(String msg, boolean showCancel, OnClickListener confirmListener, OnClickListener cancelListener) {
        dismiss();
        dialog = new Dialog(mContext, R.style.prompt_dialog);
        dialog.setContentView(R.layout.view_dialog_prompt);
        initView(dialog);
        mTvContent.setText(msg);
        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvCancel.setOnClickListener(cancelListener);
        mTvConfirm.setOnClickListener(confirmListener);
        if(AwScreenUtils.isLandscape(mContext)) {
            setDialogSize(mContext, 0.6, 0.5);
        } else {
            setDialogSize(mContext, 0.8, 0.5);
        }
        dialog.show();
    }

    public void showDialog(String msg, String leftTxt, String rightTxt, OnClickListener leftListener, OnClickListener rightListener) {
        dismiss();
        dialog = new Dialog(mContext, R.style.prompt_dialog);
        dialog.setContentView(R.layout.view_dialog_prompt);
        initView(dialog);
        mTvContent.setText(msg);
        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvCancel.setText(leftTxt);
        mTvConfirm.setText(rightTxt);
        if(leftListener == null) {
            mTvCancel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        } else {
            mTvCancel.setOnClickListener(leftListener);
        }
        mTvConfirm.setOnClickListener(rightListener);
        if(AwScreenUtils.isLandscape(mContext)) {
            setDialogSize(mContext, 0.6, 0.5);
        } else {
            setDialogSize(mContext, 0.8, 0.5);
        }
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public void showDialogCustomLeftColor(String msg, String leftTxt, String rightTxt, OnClickListener leftListener, OnClickListener rightListener) {
        dismiss();
        dialog = new Dialog(mContext, R.style.prompt_dialog);
        dialog.setContentView(R.layout.view_dialog_prompt);
        initView(dialog);
        mTvContent.setText(msg);
        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvCancel.setText(leftTxt);
        mTvCancel.setTextColor(mContext.getResources().getColor(R.color.color_248EEE));
        mTvConfirm.setText(rightTxt);
        if(leftListener == null) {
            mTvCancel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        } else {
            mTvCancel.setOnClickListener(leftListener);
        }
        mTvConfirm.setOnClickListener(rightListener);
        if(AwScreenUtils.isLandscape(mContext)) {
            setDialogSize(mContext, 0.6, 0.5);
        } else {
            setDialogSize(mContext, 0.8, 0.5);
        }
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public void showInputDialog(String title, String hintMsg, final InputCallback callback) {
        dismiss();
        dialog = new Dialog(mContext, R.style.prompt_dialog);
        dialog.setContentView(R.layout.view_dialog_input);
        initInputView(dialog);
        tv_title.setText(title);
        et_content.setHint(hintMsg);
        et_content.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mTvConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AwDataUtil.isEmpty(et_content.getText().toString())) {
                    Toast.makeText(mContext, "请输入内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                callback.onInputCallback(et_content.getText().toString());
                dismiss();
            }
        });
        if(AwScreenUtils.isLandscape(mContext)) {
            setDialogSize(mContext, 0.6, 0.5);
        } else {
            setDialogSize(mContext, 0.8, 0.5);
        }
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public void showInputDialogWithOld(String title, String text, final InputCallback callback) {
        dismiss();
        dialog = new Dialog(mContext, R.style.prompt_dialog);
        dialog.setContentView(R.layout.view_dialog_input);
        initInputView(dialog);
        tv_title.setText(title);
        et_content.setText(text);
        et_content.setSelection(text.length());
        et_content.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mTvConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onInputCallback(et_content.getText().toString());
                dismiss();
            }
        });
        if(AwScreenUtils.isLandscape(mContext)) {
            setDialogSize(mContext, 0.6, 0.5);
        } else {
            setDialogSize(mContext, 0.8, 0.5);
        }
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void initView(Dialog dialog) {
        mTvContent = (TextView) dialog.findViewById(R.id.tv_content);
        mTvCancel = (TextView) dialog.findViewById(R.id.tv_cancel);
        mSpiltLine = dialog.findViewById(R.id.spilt_line);
        mTvConfirm = (TextView) dialog.findViewById(R.id.tv_confirm);
    }

    private void initUpdateDownloadView(Dialog dialog) {
        pb_loading = (ProgressBar) dialog.findViewById(R.id.pb_loading);
        btn_cancel = (TextView) dialog.findViewById(R.id.btn_cancel);
        tv_progress = (TextView) dialog.findViewById(R.id.tv_progress);
    }

    private void initInputView(Dialog dialog) {
        tv_title = (TextView) dialog.findViewById(R.id.tv_title);
        et_content = (EditText) dialog.findViewById(R.id.et_content);
        mTvCancel = (TextView) dialog.findViewById(R.id.tv_cancel);
        mSpiltLine = dialog.findViewById(R.id.spilt_line);
        mTvConfirm = (TextView) dialog.findViewById(R.id.tv_confirm);
    }

    public void dismiss() {
        if(dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void setDialogSize(Context context) {
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager m = ((Activity) context).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的weight
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(p);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
    }

    /**
     * 自定义布局dialog
     */
    public void setDialogSize(Context context, double weight, double height) {
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager m = ((Activity) context).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() * weight); // 宽度设置为屏幕的weight
        p.height = (int) (d.getHeight() * height); // 宽度设置为屏幕的weight
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(p);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
    }

    public interface InputCallback {
        public void onInputCallback(String str);
    }
}
