package com.jkrm.education.adapter;


import android.graphics.Color;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.BatchBean;
import com.jkrm.education.bean.result.SimilarResultBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/5/25 11:50
 */

public class OnlineExerciseReportSubmitChildQuestionAdapter extends BaseQuickAdapter<BatchBean.SubQuestionsBean, BaseViewHolder> {
    private List<BatchBean.SubQuestionsBean>  mList = new ArrayList<>();

    public OnlineExerciseReportSubmitChildQuestionAdapter() {
        super(R.layout.adapter_submit_quetio_child_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, BatchBean.SubQuestionsBean item) {
        helper.addOnClickListener(R.id.tv_choice);
        TextView tvChoice = helper.getView(R.id.tv_choice);
        helper.setText(R.id.tv_choice,helper.getAdapterPosition() + 1+"");
        if("1".equals(item.getQuestStatus())){
            tvChoice.setBackground(mContext.getResources().getDrawable(R.mipmap.choise_green));
            tvChoice.setTextColor(Color.WHITE);
        }else if("2".equals(item.getQuestStatus())){
            tvChoice.setBackground(mContext.getResources().getDrawable(R.mipmap.choise_yellow));
            tvChoice.setTextColor(Color.WHITE);
        }else if("3".equals(item.getQuestStatus())){
            tvChoice.setBackground(mContext.getResources().getDrawable(R.mipmap.choise_red));
            tvChoice.setTextColor(Color.WHITE);
        }else if("5".equals(item.getQuestStatus())){
            tvChoice.setBackground(mContext.getResources().getDrawable(R.mipmap.choice_gray));
            tvChoice.setTextColor(Color.WHITE);
        }else if("6".equals(item.getQuestStatus())){
            tvChoice.setBackground(mContext.getResources().getDrawable(R.mipmap.choice_normal));
            tvChoice.setTextColor(Color.BLUE);
        }
       // tv_choice.setSelected(item.isAnswer());
    }


    public void addAllData(List<BatchBean.SubQuestionsBean> dataList) {
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
