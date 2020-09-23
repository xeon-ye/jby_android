package com.jkrm.education.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.TaskBean;
import com.jkrm.education.bean.result.ExplainStudentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/8/26 16:02
 */

public class TaskBeanAdapter extends BaseQuickAdapter<TaskBean.Bean, BaseViewHolder> {
    private List<TaskBean.Bean> mList = new ArrayList<>();

    public TaskBeanAdapter() {
        super(R.layout.adapter_exam_item_layout);
    }


    @Override
    protected void convert(BaseViewHolder helper, TaskBean.Bean item) {
        helper.setText(R.id.tv_title, item.getExamName())
                .setText(R.id.tv_course, item.getCourseName())
                .setText(R.id.tv_time, item.getExamStartTime() + "  " + item.getExamEndTime())
                .addOnClickListener(R.id.tv_step);
        TextView tv_done_num = helper.getView(R.id.tv_done_num);
        tv_done_num.setText(item.getCompReadNum() + "");
        TextView tv_not_done = helper.getView(R.id.tv_not_done);
        tv_not_done.setText(item.getNoCompReadNum());
        TextView tv_step = helper.getView(R.id.tv_step);
        if ("1".equals(item.getIsRead())) {
            tv_step.setText("开始阅卷");
            tv_step.setBackground(mContext.getResources().getDrawable(R.drawable.aw_bg_blue_radius_15));
            tv_step.setTextColor(Color.WHITE);
        } else if ("2".equals(item.getIsRead())) {
            tv_step.setText("暂停阅卷");
            tv_step.setTextColor(Color.parseColor("#FFA900"));
            tv_step.setBackground(null);

        }/* else{
            tv_step.setText("阅卷完成");
            tv_step.setTextColor(Color.parseColor("#3DC823"));
            tv_step.setBackground(null);
        }*/

        else if ("3".equals(item.getIsRead())) {
            tv_step.setText("阅卷完成");
            tv_step.setTextColor(Color.parseColor("#3DC823"));
            tv_step.setBackground(null);

        }else if("4".equals(item.getIsRead())){
            tv_step.setText("查看分析");
            tv_step.setTextColor(Color.parseColor("#FF0000"));
            tv_step.setBackground(null);

        }


    }

    public void addAllData(List<TaskBean.Bean> dataList) {
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
}
