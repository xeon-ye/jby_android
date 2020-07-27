package com.jkrm.education.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.QuestionOptionBean;

import java.util.ArrayList;
import java.util.List;

import io.github.kexanie.library.MathView;

/**
 * @Description:  查看答案
 * @Author: xiangqian
 * @CreateDate: 2020/4/22 17:28
 */

public class AnswerAnalyImgAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private List<String> mList = new ArrayList<>();

    public AnswerAnalyImgAdapter() {
        super(R.layout.answer_img_item_layout);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final String bean) {
        ImageView imageView = helper.getView(R.id.iv_img);
        Glide.with(mContext).load(bean).into(imageView);
        helper.addOnClickListener(R.id.iv_img);

    }

    public void addAllData(List<String> dataList) {
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


}
