package com.jkrm.education.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.MyDateUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.VideoPointResultBean;
import com.jkrm.education.util.CustomFontStyleUtil;
import com.zzhoujay.richtext.RichText;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 作业详情 --- 名师讲题/微课视频
 * Created by hzw on 2019/5/9.
 */


public class VideoPointAdapter extends BaseQuickAdapter<VideoPointResultBean, BaseViewHolder> {

    private List<VideoPointResultBean> mList = new ArrayList<>();

    public VideoPointAdapter() {
        super(R.layout.adapter_famous_teacher_lecture);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final VideoPointResultBean bean) {
        helper.setText(R.id.tv_title, MyDateUtil.getFileName(bean.getName())).setTypeface(R.id.tv_title, CustomFontStyleUtil.getNewRomenType());
        TextView tv_desc = helper.getView(R.id.tv_desc);
        ImageView ivImg = helper.getView(R.id.iv_img);
        if(!AwDataUtil.isEmpty(bean.getBrief())) {
            RichText.from("").bind(mContext).into(tv_desc);
            RichText.from(bean.getBrief()).bind(mContext).into(tv_desc);
        } else {
            helper.setText(R.id.tv_desc, "暂无简介");
        }
        //Glide.with(mContext).load(bean.getFace()).crossFade().into(ivImg);
        //        AwImgUtil.setImg(mContext, helper.getView(R.id.iv_img), "");
    }

    public void addAllData(List<VideoPointResultBean> dataList) {
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
