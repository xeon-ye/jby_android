package com.jkrm.education.adapter.error;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.error.ErrorCourseBean;
import com.jkrm.education.bean.result.error.ErrorHomeWork;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/23 11:28
 */

public class ErrorHomeWordAdapter extends BaseQuickAdapter<ErrorHomeWork, BaseViewHolder> {
    private List<ErrorHomeWork> mList = new ArrayList<>();

    public ErrorHomeWordAdapter() {
        super(R.layout.item_error_course_layout);
    }

    public void addAllData(List<ErrorHomeWork> dataList) {
        this.mList = dataList;
        this.setNewData(mList);
        notifyDataSetChanged();
    }

    public void clearData() {
        if (mList != null) {
            int startPosition = 0;//hasHeader is 1
            int preSize = this.mList.size();
            if (preSize > 0) {
                this.mList.clear();
                notifyItemRangeRemoved(startPosition, preSize);
            }
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, ErrorHomeWork item) {
        helper.setText(R.id.tv_course,item.getName()).addOnClickListener(R.id.tv_course);
        TextView tvcourse = helper.getView(R.id.tv_course);
        if(item.isChecked()) {
            tvcourse.setSelected(true);
            tvcourse.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            tvcourse.setSelected(false);
            tvcourse.setTextColor(mContext.getResources().getColor(R.color.black));
        }
    }
}
