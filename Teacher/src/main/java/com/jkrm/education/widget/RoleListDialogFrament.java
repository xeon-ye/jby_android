package com.jkrm.education.widget;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.jkrm.education.adapter.exam.ClassAdapter;
import com.jkrm.education.adapter.exam.RoleAdapter;
import com.jkrm.education.bean.exam.ClassBean;
import com.jkrm.education.bean.exam.RoleBean;
import com.jkrm.education.constants.Extras;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/29 14:45
 */

public class RoleListDialogFrament extends DialogFragment {
    List<RoleBean> mClassBeans;
    private RoleAdapter mClassAdapter;
    private OnItemClickListener mOnItemClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mClassBeans = (List<RoleBean>) arguments.getSerializable(Extras.KEY_ROLE_LIST);
        mClassAdapter=new RoleAdapter();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_grade_layout, null);
        init(view);
        final Dialog dialog = new Dialog(getActivity(), R.style.dialog_fragment_style);
        dialog.setContentView(view);
        dialog.show();

        Window window = dialog.getWindow();
        window.setGravity(Gravity.TOP); //可设置dialog的位置
        window.getDecorView().setPadding(0, 255, 0, 0); //消除边距

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;   //设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }


    private void init(View view) {


        RecyclerView rcv_data = view.findViewById(R.id.rcv_data);
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(getActivity(),rcv_data,mClassAdapter,false);
        mClassAdapter.addAllData(mClassBeans);
        if(null!=mOnItemClickListener){
            mClassAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    for (int i = 0; i < mClassBeans.size(); i++) {
                        if(i==position){
                            mClassBeans.get(i).setSelect(true);
                        }else{
                            mClassBeans.get(i).setSelect(false);
                        }
                    }
                    mOnItemClickListener.onItemChick(adapter,position);
                    mClassAdapter.notifyDataSetChanged();
                    dismiss();
                }
            });
        }
    }

    public  interface OnItemClickListener{
        void onItemChick(BaseQuickAdapter adapter, int position);
    }
}
