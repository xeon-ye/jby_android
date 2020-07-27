package com.jkrm.education.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.ReinfoRoceCouseBean;
import com.jkrm.education.bean.result.BookExercisesBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/1 14:42
 */

public class AnswerRecordAdapter extends BaseQuickAdapter<ReinfoRoceCouseBean, BaseViewHolder> {
    List<ReinfoRoceCouseBean> mList=new ArrayList<>();

    public AnswerRecordAdapter() {
        super(R.layout.view_item_answer_record_layout);
    }
    @Override
    protected void convert(BaseViewHolder helper, ReinfoRoceCouseBean item) {
        TextView tvname = helper.getView(R.id.tv_name);
        tvname.setText(item.getCourseName());
        helper.addOnClickListener(R.id.tv_name);
        if(item.isSelect()){
            helper.setTextColor(R.id.tv_name,mContext.getResources().getColor(R.color.colorAccent));
        }else{
            helper.setTextColor(R.id.tv_name,mContext.getResources().getColor(R.color.black));

        }
       // helper.setTextColor(R.id.tv_name,item.isSelect()?mContext.getResources().getColor(R.color.colorAccent):mContext.getResources().getColor(R.color.black));

    }

    public void addAllData(List<ReinfoRoceCouseBean> dataList) {
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
