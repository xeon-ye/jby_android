package com.jkrm.education.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.MicroLessonResultBean;
import com.jkrm.education.bean.result.TeachersResultBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/5 15:28
 */

public class MicroLessonAdapter extends BaseQuickAdapter<MicroLessonResultBean, BaseViewHolder> {
    private List<MicroLessonResultBean> mList = new ArrayList<>();
    public MicroLessonAdapter(){
        super(R.layout.microlesson_item_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, MicroLessonResultBean item) {
        helper.addOnClickListener(R.id.iv_img).addOnClickListener(R.id.ll_of_info);
        if(null!=item.getMlessonName()){
            helper.setText(R.id.tv_name,item.getMlessonName()+""+item.getTypeName());
        }
        helper.setText(R.id.tv_count,"共"+item.getMlessonCount()+"讲");
        ImageView ivImg = helper.getView(R.id.iv_img);
        Glide.with(mContext).load(item.getMlessonUrl()).into(ivImg);
        if(null!=item.getWhetherBuy()){
            helper.setVisible(R.id.tv_purchased,item.getWhetherBuy().equals("1"));
        }
        String price="";
        if("0".equals(item.getWhetherFree())){
            price="免费";
            helper.setText(R.id.tv_price,price);
            helper.setTextColor(R.id.tv_price,Color.parseColor("#FFC71C"));
        }else{
            price=item.getMlessonPrice()+" 储值";
            SpannableStringBuilder builder=new SpannableStringBuilder(price);
            ForegroundColorSpan yellow=new ForegroundColorSpan(mContext.getResources().getColor(R.color.colorPrimary));
            builder.setSpan(yellow,0,item.getMlessonPrice().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            helper.setText(R.id.tv_price,builder);
        }
    }
    public void addAllData(List<MicroLessonResultBean> dataList) {
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
