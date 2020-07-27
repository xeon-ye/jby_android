package com.jkrm.education.adapter.error;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.error.ErrorCourseBean;
import com.jkrm.education.bean.test.TestMarkKindsBean;
import com.sobot.chat.adapter.base.SobotBaseGvAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/23 11:28
 */

public class ErrorCourseAdapter  extends BaseQuickAdapter<ErrorCourseBean, BaseViewHolder> {
    private List<ErrorCourseBean> mList = new ArrayList<>();

    public ErrorCourseAdapter() {
        super(R.layout.item_error_course_layout);
    }

    public void addAllData(List<ErrorCourseBean> dataList) {
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
    protected void convert(BaseViewHolder helper, ErrorCourseBean item) {
        helper.setText(R.id.tv_course,item.getCourseName()).addOnClickListener(R.id.tv_course);
        helper.getView(R.id.tv_course).performClick();
        TextView tvcourse = helper.getView(R.id.tv_course);
        if(item.isChecked()) {
            tvcourse.setSelected(true);
            tvcourse.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.aw_bg_blue_radius_stroke));
        } else {
            tvcourse.setSelected(false);
            tvcourse.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.aw_bg_gray_radius_30));
        }
    }
}
