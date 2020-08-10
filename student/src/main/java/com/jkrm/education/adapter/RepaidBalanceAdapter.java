package com.jkrm.education.adapter;


import android.view.View;
import android.widget.LinearLayout;
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
        helper.setText(R.id.tv_num,item.getMsg())
                .setText(R.id.tv_price,item.getPrice()+"元")
                .addOnClickListener(R.id.tv_num);
        TextView tv_name = helper.getView(R.id.tv_num);
        LinearLayout ll_of_pay = helper.getView(R.id.ll_of_pay);
        TextView tv_price = helper.getView(R.id.tv_price);
        if(item.isSelect()) {
            ll_of_pay.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.aw_bg_blue_c8eaff_radius4));
            tv_name.setTextColor(mContext.getResources().getColor(R.color.white));
            tv_price.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            //tv_name.setSelected(false);
            ll_of_pay.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.aw_bg_gray_f4f4f4_radius4));
            tv_name.setTextColor(mContext.getResources().getColor(R.color.black));
            tv_price.setTextColor(mContext.getResources().getColor(R.color.black));

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
