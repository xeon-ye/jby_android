package com.jkrm.education.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwDateUtils;
import com.jkrm.education.R;
import com.jkrm.education.bean.AnswerSheetBean;
import com.jkrm.education.bean.OrderBean;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/8/11 16:56
 */

public class BalanceDetailsAdapter extends BaseQuickAdapter<OrderBean.RowsBean, BaseViewHolder> {
    private List<OrderBean.RowsBean> mList = new ArrayList<>();

    public BalanceDetailsAdapter() {
        super(R.layout.adapter_balance_details_layout);
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
        ImageView iv_type = helper.getView(R.id.iv_type);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date parse = null;
        try {
            parse = simpleDateFormat.parse(item.getCreateTime());
            helper.setText(R.id.tv_time,AwDateUtils.getyyyyMMddHHmmssWithNo(parse.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DecimalFormat decimalFormat=new DecimalFormat("#0.00");

        if ("1".equals(item.getOrderType())) {
            helper.setText(R.id.tv_type, "课程购买")
                    .setText(R.id.tv_num, "-" +  decimalFormat.format(Double.parseDouble(item.getGoodsPrice())))
                    .setTextColor(R.id.tv_num, mContext.getResources().getColor(R.color.red));
            iv_type.setImageResource(R.mipmap.purchase_icon);

        } else if ("2".equals(item.getOrderType())) {
            helper.setText(R.id.tv_type, "储蓄充值")
                    .setText(R.id.tv_num, "+" +  decimalFormat.format(Double.parseDouble(item.getGoodsPrice())))
                    .setTextColor(R.id.tv_num, mContext.getResources().getColor(R.color.colorPrimary));
            iv_type.setImageResource(R.mipmap.invest_icon);

        }

    }
}
