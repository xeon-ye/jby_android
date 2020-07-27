package com.jkrm.education.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.ErrorQuestionBean;
import com.jkrm.education.bean.result.ErrorQuestionResultBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/3 11:18
 */

public class ErrorQuestionNewAdapter  extends BaseQuickAdapter<ErrorQuestionBean, BaseViewHolder> {
    private List<ErrorQuestionBean> mList = new ArrayList<>();

    public ErrorQuestionNewAdapter() {
        super(R.layout.errer_question_item_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ErrorQuestionBean item) {
        ImageView imageView = helper.getView(R.id.iv_img);
        helper.setText(R.id.tv_course,item.getCourseName()+"");
        helper.setText(R.id.tv_num,item.getErrorNum()+"题");
        helper.addOnClickListener(R.id.ll_of_course);
        Glide.with(mContext).load(item.getDataMsg()).into(imageView);
    }

    public void addAllData(List<ErrorQuestionBean> dataList) {
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
