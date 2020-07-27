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
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.score.MarkChoiceCourseAdapter;
import com.jkrm.education.adapter.score.MarkChoiceCourseNewAdapter;
import com.jkrm.education.bean.ReinfoRoceCouseBean;
import com.jkrm.education.bean.type.TypeCourseBean;
import com.jkrm.education.constants.Extras;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 作业科目筛选
 * @Author: xiangqian
 * @CreateDate: 2020/6/19 10:01
 */

public class ScreenSubjectDialogFragment extends DialogFragment {

    private RecyclerView mRecyclerView;
    private List<TypeCourseBean> mTypeCourseBeanList = new ArrayList<>();


    public interface onItemClickListener{
        void onItemClick(int pos);
    }
    onItemClickListener mOnItemClickListener;
    MarkChoiceCourseNewAdapter mMarkChoiceCourseAdapter;


    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (null != arguments) {
            mTypeCourseBeanList= (List<TypeCourseBean>) arguments.getSerializable(Extras.KEY_SCREEN_COURSE);
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.top_chose_fragment_layout, null);
        init(view);
        final Dialog dialog = new Dialog(getActivity(),R.style.CustomDialogFragmentStyle);
        dialog.setContentView(view);
        dialog.show();

        Window window = dialog.getWindow();
        //可设置dialog的位置
        window.setGravity(Gravity.TOP|Gravity.RIGHT);
        //消除边距
        window.getDecorView().setPadding(0, 0, 0, 0);

        WindowManager.LayoutParams lp = window.getAttributes();
        //设置宽度充满屏幕
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.y=150;
        window.setAttributes(lp);
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
    }

    private void init(View view) {
        mRecyclerView = view.findViewById(R.id.rcv_data);
        mMarkChoiceCourseAdapter=new MarkChoiceCourseNewAdapter();
        mMarkChoiceCourseAdapter.addAllData(mTypeCourseBeanList);
        AwRecyclerViewUtil.setRecyclerViewFlowLayout(getActivity(),mRecyclerView,mMarkChoiceCourseAdapter,10);
        mMarkChoiceCourseAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mOnItemClickListener.onItemClick(position);
                TypeCourseBean kindsBean= (TypeCourseBean) adapter.getData().get(position);
                for(int i=0; i<mTypeCourseBeanList.size(); i++) {
                    TypeCourseBean tempBean = (TypeCourseBean) mTypeCourseBeanList.get(i);
                    tempBean.setSelect(false);
                }
                kindsBean.setSelect(true);
                mMarkChoiceCourseAdapter.notifyDataSetChanged();
                dismiss();
            }
        });
    }
}
