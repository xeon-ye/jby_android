package com.jkrm.education.adapter;


import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.InvestResultBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/4/21 14:17
 */

public class RepaidBalanceAdapter extends BaseQuickAdapter<InvestResultBean, BaseViewHolder> {
    private List<InvestResultBean> mList = new ArrayList<>();

    public RepaidBalanceAdapter() {
        super(R.layout.adapter_repaid_balance_item_layout);
    }


    @Override
    protected void convert(BaseViewHolder helper, InvestResultBean item) {
        helper.setText(R.id.tv_num,item.getMsg()+"\n"+item.getPrice()).addOnClickListener(R.id.tv_num);
        TextView tv_name = helper.getView(R.id.tv_num);
        if(item.isSelect()) {
            tv_name.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.aw_bg_blue_c8eaff_radius4));
        } else {
            //tv_name.setSelected(false);
            tv_name.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.aw_bg_gray_f4f4f4_radius4));
        }
    }
    public void addAllData(List<InvestResultBean> dataList) {
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
