package com.jkrm.education.adapter;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.OrderBean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 查看答案
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

        TextView mTv_valid_time = helper.getView(R.id.tv_valid_time);
        if ("1".equals(item.getStep())) {
            helper.setText(R.id.tv_step, "待支付");
            helper.setTextColor(R.id.tv_step, mContext.getResources().getColor(R.color.colorPrimary));
            helper.setVisible(R.id.ll_of_pay, true);
            CountDownTimer timer = new CountDownTimer(item.getValidTime(), 1000) {
                @Override
                public void onTick(long l) {
                    mTv_valid_time.setText(getGapTime(l) + "后自动取消");

                }

                @Override
                public void onFinish() {
                    mTv_valid_time.setText("已取消");
                }
            };
            timer.start();
        } else if ("2".equals(item.getStep())) {
            helper.setVisible(R.id.iv_paided, true);
            helper.setGone(R.id.tv_step, false);
            helper.setGone(R.id.ll_of_pay, false);
        } else if ("3".equals(item.getStep())) {
            helper.setText(R.id.tv_step, "已取消");
            helper.setTextColor(R.id.tv_step, mContext.getResources().getColor(R.color.color_999999));
            helper.setGone(R.id.ll_of_pay, false);
        }
        helper.setText(R.id.tv_time, "下单时间:" + item.getCreateTime())
                .setText(R.id.tv_price, "￥" + item.getGoodsPrice())
                .setText(R.id.tv_content, "共" + item.getDetaiList().get(0).getComboNum() + "节课")
                .setText(R.id.tv_course, item.getDetaiList().get(0).getCourseName())
                .setText(R.id.tv_title, item.getDetaiList().get(0).getGoodsName())
                .addOnClickListener(R.id.tv_title)
                .addOnClickListener(R.id.tv_time)
                .addOnClickListener(R.id.tv_content)
                .addOnClickListener(R.id.tv_course)
                .addOnClickListener(R.id.tv_price)
                .addOnClickListener(R.id.tv_pay);
    }

    public String getGapTime(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");//这里想要只保留分秒可以写成"mm:ss"
        return formatter.format(time);
    }
}
