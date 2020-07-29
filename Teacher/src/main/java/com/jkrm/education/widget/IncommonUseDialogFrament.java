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
import com.jkrm.education.adapter.ScoreAdapter;
import com.jkrm.education.adapter.StudentAdapter;
import com.jkrm.education.bean.result.ExplainStudentBean;
import com.jkrm.education.bean.test.TestMarkCommonUseScoreBean;
import com.jkrm.education.constants.Extras;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/29 14:45
 */

public class IncommonUseDialogFrament extends DialogFragment {
    List<TestMarkCommonUseScoreBean> mCommonUseScoreList;
    private ScoreAdapter mScoreAdapter;
    private onSureChickListener mOnSureChickListener;
    private OnItemClickListener mOnItemClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public onSureChickListener getOnSureChickListener() {
        return mOnSureChickListener;
    }

    public void setOnSureChickListener(onSureChickListener onSureChickListener) {
        mOnSureChickListener = onSureChickListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mCommonUseScoreList = (List<TestMarkCommonUseScoreBean>) arguments.getSerializable(Extras.KEY_SCORE_LIST);
        mScoreAdapter=new ScoreAdapter();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_in_common_use_layout, null);
        init(view);
        final Dialog dialog = new Dialog(getActivity(), R.style.dialog_fragment_style);
        dialog.setContentView(view);
        dialog.show();

        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER); //可设置dialog的位置
        window.getDecorView().setPadding(300, 0, 300, 0); //消除边距

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;   //设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }


    private void init(View view) {
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnSureChickListener.onCancelChick();
                dismiss();
            }
        });
        view.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null!=mOnSureChickListener){
                    mOnSureChickListener.onSureChick();
                    dismiss();
                }
            }
        });
        RecyclerView rcv_data = view.findViewById(R.id.rcv_data);
        AwRecyclerViewUtil.setRecyclerViewGridlayout(getActivity(),rcv_data,mScoreAdapter,5);
        mScoreAdapter.addAllData(mCommonUseScoreList);
        if(null!=mOnItemClickListener){
            mScoreAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    mOnItemClickListener.onItemChick(adapter,position);
                    mScoreAdapter.notifyDataSetChanged();
                }
            });
        }
    }
    public interface onSureChickListener{
        void onSureChick();
        void onCancelChick();
    }
    public  interface OnItemClickListener{
        void onItemChick(BaseQuickAdapter adapter,int position);
    }
}
