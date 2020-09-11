package com.jkrm.education.adapter.exam;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.exam.GradeBean;
import com.jkrm.education.bean.test.TestMarkCommonUseScoreBean;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/9/11 17:29
 */

public class GradeAdapter extends BaseQuickAdapter<GradeBean, BaseViewHolder> {
    List<GradeBean> mList;

    public GradeAdapter() {
        super(R.layout.adapter_grade_item_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, GradeBean item) {
        helper.setText(R.id.tv_title,item.getGradeName()).addOnClickListener(R.id.tv_title);
       // helper.setTextColor(R.id.tv_title,item.isHandleModify()? mContext.getResources().getColor(R.color.colorPrimary): Color.BLACK);
    }
    public void addAllData(List<GradeBean> dataList) {
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
