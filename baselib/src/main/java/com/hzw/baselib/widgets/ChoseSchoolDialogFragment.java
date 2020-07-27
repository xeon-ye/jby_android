package com.hzw.baselib.widgets;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.R;
import com.hzw.baselib.adapter.SchoolAdapter;
import com.hzw.baselib.bean.AddressBean;
import com.hzw.baselib.bean.SchoolBean;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:  选择学校
 * @Author: xiangqian
 * @CreateDate: 2020/7/8 19:01
 */

public class ChoseSchoolDialogFragment extends DialogFragment {

    private List<SchoolBean.RowsBean> mSchools;
    private SchoolAdapter mSchoolAdapter;
    private RecyclerView mMRcv_data;
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
        Bundle arguments = getArguments();
        mSchools = (List<SchoolBean.RowsBean>) arguments.getSerializable("school");
        mSchoolAdapter=new SchoolAdapter();

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_chose_school_layout, null);
        init(view);
        final Dialog dialog = new Dialog(getActivity(), R.style.style_dialog);
        dialog.setContentView(view);
        dialog.show();

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM); //可设置dialog的位置
        window.getDecorView().setPadding(0, 200, 0, 0); //消除边距

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;   //设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
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
        mMRcv_data = view.findViewById(R.id.rcv_data);
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(getActivity(), mMRcv_data,mSchoolAdapter,true);
        if(AwDataUtil.isEmpty(mSchools)){
            mSchoolAdapter.clearData();

            mSchoolAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(getActivity(),0, -1));
        }else{
            mSchoolAdapter.addAllData(mSchools);
        }
        mSchoolAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<SchoolBean.RowsBean> data = adapter.getData();
                mOnItemClickListener.onItemChickListener(data.get(position));
                dismiss();
            }
        });
        final EditText et_input_class = view.findViewById(R.id.et_input_class);
        final Button btn_next = view.findViewById(R.id.btn_next);
        et_input_class.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                btn_next.setEnabled(editable.toString().length()>0);
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SchoolBean.RowsBean school=new SchoolBean.RowsBean();
                school.setId("");
                school.setSchName(et_input_class.getText().toString().trim());
                mOnItemClickListener.onCrateSchoolListener(school);
                dismiss();
            }
        });
    }

    public interface  onItemClickListener{
        void onItemChickListener(SchoolBean.RowsBean school);
        void onCrateSchoolListener(SchoolBean.RowsBean school);
    }
}
