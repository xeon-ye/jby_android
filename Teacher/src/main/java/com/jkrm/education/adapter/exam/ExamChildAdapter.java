package com.jkrm.education.adapter.exam;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.ReViewTaskBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:  批阅外部列表
 * @Author: xiangqian
 * @CreateDate: 2020/8/27 16:36
 */

public class ExamChildAdapter extends BaseQuickAdapter<ReViewTaskBean.Bean, BaseViewHolder> {
    private List<ReViewTaskBean.Bean> mList = new ArrayList<>();

    public ExamChildAdapter() {
        super(R.layout.adapter_exam_task_child_item_layout);
    }

    public void addAllData(List<ReViewTaskBean.Bean> dataList) {
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
    protected void convert(BaseViewHolder helper, ReViewTaskBean.Bean item) {
        helper.setText(R.id.tv_title,item.getQuestionType()+":"+item.getQuestionNum()+"题("+item.getMaxScore()+")分")
                .setText(R.id.tv_type,item.getReadDistName())
                .setText(R.id.tv_done_num,item.getReadNum())
                .setText(R.id.tv_not_done,item.getToBeRead())
                .addOnClickListener(R.id.tv_review)
                .addOnClickListener(R.id.tv_mark);

    }
}
