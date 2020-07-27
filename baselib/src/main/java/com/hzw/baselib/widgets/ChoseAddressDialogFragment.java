package com.hzw.baselib.widgets;

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
import com.hzw.baselib.R;
import com.hzw.baselib.adapter.AddressAdapter;
import com.hzw.baselib.bean.AddressBean;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;

import java.util.List;

/**
 * @Description:  选择学校
 * @Author: xiangqian
 * @CreateDate: 2020/7/8 19:01
 */

public class ChoseAddressDialogFragment extends DialogFragment {

    private List<AddressBean> mList;
    private RecyclerView mRcv_data;
    private AddressAdapter mAddressAdapter;
    private onItemClickListener mOnItemClickListener;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mList = (List<AddressBean>) arguments.getSerializable("key_address");
        mAddressAdapter=new AddressAdapter();
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_chose_address_layout, null);
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
        mRcv_data = view.findViewById(R.id.rcv_data);
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(getActivity(),mRcv_data,mAddressAdapter,true);
        mAddressAdapter.addAllData(mList);
        mAddressAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<AddressBean> data = adapter.getData();
                mOnItemClickListener.onItemChickListener(data.get(position));
                dismiss();
            }
        });
    }

    public interface  onItemClickListener{
        void onItemChickListener(AddressBean addressBean);
    }
}
