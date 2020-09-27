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
import android.widget.TextView;

import com.hzw.baselib.adapter.AddressAdapter;
import com.hzw.baselib.bean.AddressBean;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.StudentAdapter;
import com.jkrm.education.bean.result.ExplainStudentBean;
import com.jkrm.education.constants.Extras;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/28 19:15
 */

public class StudentListDialogFrament extends DialogFragment {

    private List<ExplainStudentBean> mList;
    private StudentAdapter mStudentAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mList = (List<ExplainStudentBean>) arguments.getSerializable(Extras.KEY_STUDENT_LIST);
        mStudentAdapter=new StudentAdapter();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_student_list_layout, null);
        init(view);
        final Dialog dialog = new Dialog(getActivity(), R.style.dialog_fragment_style);
        dialog.setContentView(view);
        dialog.show();

        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER); //可设置dialog的位置
        window.getDecorView().setPadding(10, 0, 10, 0); //消除边距

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
        TextView tv_num = view.findViewById(R.id.tv_num);
        tv_num.setText("学生名单("+mList.size()+")人");
        RecyclerView rcv_data = view.findViewById(R.id.rcv_data);
        AwRecyclerViewUtil.setRecyclerViewFlowLayout(getActivity(),rcv_data,mStudentAdapter,5);
        mStudentAdapter.addAllData(mList);
    }
}
