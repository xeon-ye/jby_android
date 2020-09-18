package com.jkrm.education.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.ExplainStudentBean;
import com.jkrm.education.bean.result.TeacherTodoBean;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends BaseQuickAdapter<ExplainStudentBean, BaseViewHolder> {
    private List<ExplainStudentBean> mList = new ArrayList<>();

    public StudentAdapter() {
        super(R.layout.adapter_student_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExplainStudentBean item) {
        helper.setText(R.id.tv_name,item.getStudentName());
    }
    public void addAllData(List<ExplainStudentBean> dataList) {
        this.mList = dataList;
        this.setNewData(mList);
        notifyDataSetChanged();
    }

    public void clearData() {
        if(mList != null) {
            int startPosition = 0;//hasHeader is 1
            int preSize = this.mList.size();
            if(preSize > 0) {
                this.mList.clear();
                notifyItemRangeRemoved(startPosition, preSize);
            }
        }
    }

}
