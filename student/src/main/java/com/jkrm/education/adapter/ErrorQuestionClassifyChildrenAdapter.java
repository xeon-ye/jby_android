package com.jkrm.education.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.ErrorQuestionClassifyBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/4 16:26
 */

public class ErrorQuestionClassifyChildrenAdapter extends BaseQuickAdapter<ErrorQuestionClassifyBean.ReaListBean,BaseViewHolder> {
    private List<ErrorQuestionClassifyBean.ReaListBean> mList = new ArrayList<>();

    public ErrorQuestionClassifyChildrenAdapter() {
        super(R.layout.error_question_classify_children_item_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ErrorQuestionClassifyBean.ReaListBean item) {
        helper.addOnClickListener(R.id.ll_of_setting);
        helper.setText(R.id.tv_title,item.getTitleName()+"");
        helper.setText(R.id.tv_look,item.getErrorNum()+"题");
        helper.setText(R.id.tv_num,item.getErrorNum());
    }

    public void addAllData(List<ErrorQuestionClassifyBean.ReaListBean> dataList) {
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
