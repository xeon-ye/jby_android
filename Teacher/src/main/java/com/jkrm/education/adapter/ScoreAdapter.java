package com.jkrm.education.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.ExplainStudentBean;
import com.jkrm.education.bean.test.TestMarkCommonUseScoreBean;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/29 16:55
 */

public class ScoreAdapter extends  BaseQuickAdapter<TestMarkCommonUseScoreBean, BaseViewHolder>  {
    List<TestMarkCommonUseScoreBean> mList;

    public ScoreAdapter() {
        super(R.layout.adapter_score_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, TestMarkCommonUseScoreBean item) {
        helper.setText(R.id.tv_title,item.getScore()).addOnClickListener(R.id.tv_title);
        helper.setTextColor(R.id.tv_title,item.isHandleModify()? mContext.getResources().getColor(R.color.colorPrimary):Color.BLACK);
    }
    public void addAllData(List<TestMarkCommonUseScoreBean> dataList) {
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
