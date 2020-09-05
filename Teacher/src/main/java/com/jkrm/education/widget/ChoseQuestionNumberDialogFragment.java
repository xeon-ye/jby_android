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
import com.hzw.baselib.bean.SchoolBean;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.ChooseQuestionNumlAdapter;
import com.jkrm.education.bean.exam.ExamReadHeaderBean;
import com.jkrm.education.constants.Extras;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 选择题号
 * @Author: xiangqian
 * @CreateDate: 2020/8/31 19:00
 */

public class ChoseQuestionNumberDialogFragment extends DialogFragment {

    private List<ExamReadHeaderBean> mList;

    private onItemClickListener mOnItemClickListener;

    public onItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = (List<ExamReadHeaderBean>) getArguments().getSerializable(Extras.KEY_QUESTION_NUM_LIST);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_chose_question_number_layout, null);
        init(view);
        final Dialog dialog = new Dialog(getActivity(), R.style.dialog_fragment_style);
        dialog.setContentView(view);
        dialog.show();

        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER); //可设置dialog的位置
        window.getDecorView().setPadding(50, 50, 50, 50); //消除边距

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;   //设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    private void init(View view) {
        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        RecyclerView rcv_data = view.findViewById(R.id.rcv_data);
        ChooseQuestionNumlAdapter chooseQuestionNumlAdapter=new ChooseQuestionNumlAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(getActivity(),rcv_data,chooseQuestionNumlAdapter,5);
        chooseQuestionNumlAdapter.addAllData(mList);
        chooseQuestionNumlAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                dismiss();
                for (int i = 0; i < mList.size(); i++) {
                    if(i==position){
                        mList.get(i).setChecked(true);
                    }else{
                        mList.get(i).setChecked(false);
                    }
                }
                mOnItemClickListener.onItemChickListener(mList.get(position));
            }
        });
    }

    public interface  onItemClickListener{
        void onItemChickListener(ExamReadHeaderBean bean);
    }
}
