package com.jkrm.education.adapter;

import android.graphics.Color;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.OrderBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:  查看答案
 * @Author: xiangqian
 * @CreateDate: 2020/4/22 17:28
 */

public class OrderAdapter extends BaseQuickAdapter<OrderBean.RowsBean, BaseViewHolder> {
    private List<OrderBean.RowsBean> mList = new ArrayList<>();

    public OrderAdapter() {
        super(R.layout.adapter_order_layout);
    }



    public void addAllData(List<OrderBean.RowsBean> dataList) {
        this.mList = dataList;
        this.setNewData(mList);
        notifyDataSetChanged();
    }

    public void clearData() {
        if (mList != null) {
            int startPosition = 0;//hasHeader is 1
            int preSize = this.mList.size();
            if (preSize > 0) {
                this.mList.clear();
                notifyItemRangeRemoved(startPosition, preSize);
            }
        }
    }


    @Override
    protected void convert(BaseViewHolder helper, OrderBean.RowsBean item) {
        if("1".equals(item.getStep())){
            helper.setText(R.id.tv_step,"待支付");
            helper.setTextColor(R.id.tv_step, mContext.getResources().getColor(R.color.colorPrimary));
            helper.setVisible(R.id.ll_of_pay,true);
        }else if("2".equals(item.getStep())){
            helper.setVisible(R.id.iv_paided,true);
            helper.setGone(R.id.tv_step,false);
            helper.setGone(R.id.ll_of_pay,false);
        }else if("3".equals(item.getStep())){
            helper.setText(R.id.tv_step,"已取消");
            helper.setTextColor(R.id.tv_step, mContext.getResources().getColor(R.color.color_999999));
            helper.setGone(R.id.ll_of_pay,false);
        }
        helper.setText(R.id.tv_course,item.getClientName())
                .setText(R.id.tv_time,"下单时间:"+item.getCreateTime())
                .setText(R.id.tv_price,"￥"+item.getGoodsPrice());
    }
}
