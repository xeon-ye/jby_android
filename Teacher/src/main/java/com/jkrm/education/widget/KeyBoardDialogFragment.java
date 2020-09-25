package com.jkrm.education.widget;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/9/25 19:43
 */

public class KeyBoardDialogFragment extends DialogFragment {
    private OnItemClickListener mOnItemClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_keyboard_layout, null);
        init(view);
        final Dialog dialog = new Dialog(getActivity(), R.style.dialog_fragment_style);
        dialog.setContentView(view);
        dialog.show();

        Window window = dialog.getWindow();
        window.setGravity(Gravity.END); //可设置dialog的位置
        window.getDecorView().setPadding(0, 0, 200, 0); //消除边距

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = 1000;   //设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }


    private void init(View view) {
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null!=mOnItemClickListener){
                    mOnItemClickListener.onCancelChick();
                    dismiss();
                }

            }
        });
        view.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null!=mOnItemClickListener){
                    mOnItemClickListener.onSureChick();
                    dismiss();
                }
            }
        });
        view.findViewById(R.id.tv_num_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null!=mOnItemClickListener){
                    mOnItemClickListener.onNum_1_Chick();
                }
            }
        });
        view.findViewById(R.id.tv_num_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null!=mOnItemClickListener){
                    mOnItemClickListener.onNum_2_Chick();
                }
            }
        });
        view.findViewById(R.id.tv_num_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null!=mOnItemClickListener){
                    mOnItemClickListener.onNum_3_Chick();
                }
            }
        });
        view.findViewById(R.id.tv_num_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null!=mOnItemClickListener){
                    mOnItemClickListener.onNum_4_Chick();
                }
            }
        });
        view.findViewById(R.id.tv_num_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null!=mOnItemClickListener){
                    mOnItemClickListener.onNum_5_Chick();
                }
            }
        });
        view.findViewById(R.id.tv_num_6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null!=mOnItemClickListener){
                    mOnItemClickListener.onNum_6_Chick();
                }
            }
        });
        view.findViewById(R.id.tv_num_7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null!=mOnItemClickListener){
                    mOnItemClickListener.onNum_7_Chick();
                }
            }
        });
        view.findViewById(R.id.tv_num_8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null!=mOnItemClickListener){
                    mOnItemClickListener.onNum_8_Chick();
                }
            }
        });
        view.findViewById(R.id.tv_num_9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null!=mOnItemClickListener){
                    mOnItemClickListener.onNum_9_Chick();
                }
            }
        });
        view.findViewById(R.id.tv_num_0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null!=mOnItemClickListener){
                    mOnItemClickListener.onNum_0_Chick();
                }
            }
        });
        view.findViewById(R.id.tv_num_05).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null!=mOnItemClickListener){
                    mOnItemClickListener.onNum_05_Chick();
                }
            }
        });
        view.findViewById(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null!=mOnItemClickListener){
                    mOnItemClickListener.onDeleteChick();
                }
            }
        });

    }
    public  interface OnItemClickListener{
        void onNum_1_Chick();
        void onNum_2_Chick();
        void onNum_3_Chick();
        void onNum_4_Chick();
        void onNum_5_Chick();
        void onNum_6_Chick();
        void onNum_7_Chick();
        void onNum_8_Chick();
        void onNum_9_Chick();
        void onNum_05_Chick();
        void onNum_0_Chick();
        void onDeleteChick();
        void onCancelChick();
        void onSureChick();
    }
}
