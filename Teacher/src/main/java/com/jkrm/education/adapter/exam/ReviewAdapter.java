package com.jkrm.education.adapter.exam;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.ExamReviewBean;
import com.jkrm.education.bean.ReViewTaskBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.ui.activity.exam.ReviewActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/8/28 15:03
 */

public class ReviewAdapter extends BaseQuickAdapter<ExamReviewBean, BaseViewHolder> {
    private List<ExamReviewBean> mList = new ArrayList<>();


    public ReviewAdapter() {
        super(R.layout.adapter_review_item_layout);
    }

    public void addAllData(List<ExamReviewBean> dataList) {
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
    protected void convert(BaseViewHolder helper, ExamReviewBean item) {
        if(null!=item.getReaList()&&item.getReaList().size()>0){
            float totalScore=0;
            for (ExamReviewBean.reaListBean reaListBean : item.getReaList()) {
                if(!AwDataUtil.isEmpty(reaListBean.getScore())){
                    float v = Float.parseFloat(reaListBean.getScore());
                    totalScore+=v;
                }

            }
            item.setScore(totalScore+"");
        }

        ImageView img = helper.getView(R.id.img);
        Glide.with(mContext).load(item.getGradedScan()).into(img);
        helper.setText(R.id.tv_title, "得分: " + item.getScore())
                .setText(R.id.tv_type, item.getFinishTime())
                .addOnClickListener(R.id.img);

    }
}
