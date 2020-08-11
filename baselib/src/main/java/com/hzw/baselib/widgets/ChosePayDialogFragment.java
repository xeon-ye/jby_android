package com.hzw.baselib.widgets;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.text.BoringLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.R;
import com.hzw.baselib.adapter.SchoolAdapter;
import com.hzw.baselib.bean.SchoolBean;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;

import java.util.List;

/**
 * @Description:  选址支付方式
 * @Author: xiangqian
 * @CreateDate: 2020/7/8 19:01
 */

public class ChosePayDialogFragment extends DialogFragment {

        private onChosePayListener mOnChosePayListener;

    public boolean isHasPurse() {
        return hasPurse;
    }

    public void setHasPurse(boolean hasPurse) {
        this.hasPurse = hasPurse;
    }

    private boolean hasPurse;

    public onChosePayListener getOnChosePayListener() {
        return mOnChosePayListener;
    }

    public void setOnChosePayListener(onChosePayListener onChosePayListener) {
        mOnChosePayListener = onChosePayListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();


    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout_of_chose_pay, null);
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
        view.findViewById(R.id.ll_of_ali).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnChosePayListener.choseAliPay();
                dismiss();
            }
        });
        view.findViewById(R.id.ll_of_wechat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnChosePayListener.choseWechatPay();
                dismiss();
            }
        });
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        if(isHasPurse()){
            LinearLayout ll_of_purse = view.findViewById(R.id.ll_of_purse);
            ll_of_purse.setVisibility(View.VISIBLE);
        }
        view.findViewById(R.id.ll_of_purse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnChosePayListener.chosePursePay();
                dismiss();
            }
        });
    }

    public interface  onChosePayListener{
        void choseWechatPay();
        void choseAliPay();
        void chosePursePay();
    }
}
