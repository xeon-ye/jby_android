package com.jkrm.education.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.AnswerSheetBean;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.bean.ReinforBean;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/29 11:13
 */

public class AnswerSheetAdapter extends  BaseQuickAdapter<AnswerSheetBean.QuestionsBean, BaseViewHolder>{
    private List<AnswerSheetBean.QuestionsBean> mList=new ArrayList<>();


    public AnswerSheetAdapter() {
        super(R.layout.answer_sheet_item_layout);
    }


    @Override
    protected void convert(BaseViewHolder helper, AnswerSheetBean.QuestionsBean item) {
        helper.setGone(R.id.tv_title, !AwDataUtil.isEmpty(item.getTitle()));
        helper.setText(R.id.tv_title,item.getTitle()+"");
        RecyclerView  recyclerView = helper.getView(R.id.rcv_data_child);
        AnswerSheetChildAdapter answerSheetChildAdapter = new AnswerSheetChildAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout((Activity) mContext,recyclerView,answerSheetChildAdapter,false);
        answerSheetChildAdapter.addAllData(item.getSubQuestions());
    }
    public void addAllData(List<AnswerSheetBean.QuestionsBean> dataList) {
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
