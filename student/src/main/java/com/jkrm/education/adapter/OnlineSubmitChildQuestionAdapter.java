package com.jkrm.education.adapter;


import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.SimilarResultBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/5/25 11:50
 */

public class OnlineSubmitChildQuestionAdapter extends BaseQuickAdapter<SimilarResultBean.SubQuestionsBean, BaseViewHolder> {
    private List<SimilarResultBean.SubQuestionsBean>  mList = new ArrayList<>();

    public OnlineSubmitChildQuestionAdapter() {
        super(R.layout.adapter_submit_quetio_child_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, SimilarResultBean.SubQuestionsBean item) {
        helper.addOnClickListener(R.id.tv_choice);
        TextView tv_choice = helper.getView(R.id.tv_choice);
        helper.setText(R.id.tv_choice,helper.getAdapterPosition() + 1+"");
        tv_choice.setSelected(item.isAnswer());
    }


    public void addAllData(List<SimilarResultBean.SubQuestionsBean> dataList) {
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
