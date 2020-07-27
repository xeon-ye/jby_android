package com.jkrm.education.widgets;

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
import com.hzw.baselib.adapter.CommonTopListAdapter;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.AnswerRecordAdapter;
import com.jkrm.education.bean.ReinfoRoceCouseBean;
import com.jkrm.education.bean.result.CoursePlayResultBean;
import com.jkrm.education.constants.Extras;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/1 12:06
 */

public class AnswerRecordFragmentDialog extends DialogFragment {

    List<ReinfoRoceCouseBean> mRoceCouseBeanList=new ArrayList<>();
    private RecyclerView mRecyclerView;
    AnswerRecordAdapter mAdapter;
    onItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (null != arguments&& !AwDataUtil.isEmpty(arguments.getSerializable(Extras.KEY_ANS_COURSE_LIST))) {
            mRoceCouseBeanList= (List<ReinfoRoceCouseBean>) arguments.getSerializable(Extras.KEY_ANS_COURSE_LIST);
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.answer_record_layout, null);
        init(view);
        final Dialog dialog = new Dialog(getActivity(), com.hzw.baselib.R.style.video_style_dialog_progress);
        dialog.setContentView(view);
        dialog.show();

        Window window = dialog.getWindow();
        window.setGravity(Gravity.TOP|Gravity.RIGHT); //可设置dialog的位置
        window.getDecorView().setPadding(0, 0, 0, 0); //消除边距

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;   //设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.y=130;
        window.setAttributes(lp);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    private void init(View view) {
        mRecyclerView = view.findViewById(R.id.rcv_data);
        mAdapter = new AnswerRecordAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(getActivity(), mRecyclerView, mAdapter, false);
        mAdapter.addAllData(mRoceCouseBeanList);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mOnItemClickListener.onItemClick(position);
                dismiss();
            }
        });
    }
    public interface onItemClickListener{
        void onItemClick(int pos);
    }
}
