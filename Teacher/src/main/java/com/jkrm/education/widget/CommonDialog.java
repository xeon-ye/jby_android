package com.jkrm.education.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bin.david.form.utils.DensityUtils;
import com.jkrm.education.R;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/15 14:58
 * Description:
 */
public class CommonDialog  extends Dialog {
    private Context context;
    private int layoutId;
    private int mHeightPixels;
//    private OnDialogClickListener dialogClickListener;

    /**
     *
     * @param context
     * @param layoutId
     * @param type 控制显示位置，type=0居中，type=1底部,type=2顶部
     */
    public CommonDialog(Context context, int layoutId, int type) {
        super(context);
        this.context = context;
        this.layoutId = layoutId;
        initView(type);
    }

    //初始化View
    private void initView(int mType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutId, null);
        setContentView(view);
        initWindow(mType);

    }

    /**
     * 添加黑色半透明背景
     */
    private void initWindow(int type) {
        Window dialogWindow = getWindow();
        assert dialogWindow != null;
//        dialogWindow.setBackgroundDrawable(new ColorDrawable(0));//设置window背景
        dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        dialogWindow.setWindowAnimations(R.style.dialog_fragment_style); //设置背景和动画
//        dialogWindow.getDecorView().setPadding(0, 0, 0, 0); //取消dialog自带的padding造成的边框
        //设置输入法显示模式
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics();//获取屏幕尺寸
        if(type==0){
            lp.width = (int) (d.widthPixels * 0.9); //宽度为屏幕90%
            lp.gravity = Gravity.CENTER;     //中央居中
        }else if(type==1) {
            lp.width = d.widthPixels; //宽度为屏幕
            lp.gravity = Gravity.BOTTOM;     //底部
        }else if (type==2){
            lp.width = d.widthPixels; //宽度为屏幕
            lp.gravity = Gravity.TOP;     //顶部
        }
        dialogWindow.setAttributes(lp);
    }

//    public void setOnDialogClickListener(OnDialogClickListener clickListener) {
//        dialogClickListener = clickListener;
//    }

    /**
     * 添加按钮点击事件
     */
//    public interface OnDialogClickListener {
//        void onOKClick();
//
//        void onCancelClick();
//    }
}