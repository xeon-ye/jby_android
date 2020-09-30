package com.jkrm.education.adapter.exam;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.exam.ClassBean;
import com.jkrm.education.bean.exam.ExamQuestionsBean;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/9/25 18:54
 */

public class ComAdapter  extends BaseQuickAdapter<ExamQuestionsBean.reaListBean, BaseViewHolder> {
    List<ExamQuestionsBean.reaListBean> mList;

    public ComAdapter() {
        super(R.layout.adapter_com_item_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExamQuestionsBean.reaListBean item) {
        helper.setIsRecyclable(false);
        helper.setText(R.id.tv_com_title,item.getQuestionNum()+"题").addOnClickListener(R.id.ll_of_com);
        LinearLayout ll_of_com = helper.getView(R.id.ll_of_com);
        TextView tv_com_title = helper.getView(R.id.tv_com_title);
        TextView tv_com_content = helper.getView(R.id.tv_com_content);
        if(item.isSelect()){
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.com_chosed);
            ll_of_com.setBackground(drawable);
            helper.setTextColor(R.id.tv_com_title,mContext.getResources().getColor(R.color.white));
        }else{
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.com_normal);
            ll_of_com.setBackground(drawable);
            helper.setTextColor(R.id.tv_com_title,mContext.getResources().getColor(R.color.colorPrimary));
        }
        if(!AwDataUtil.isEmpty(item.getScore())){
            helper.setText(R.id.tv_com_content,item.getScore());
        }
        // helper.setTextColor(R.id.tv_title,item.isHandleModify()? mContext.getResources().getColor(R.color.colorPrimary): Color.BLACK);
    }
    public void addAllData(List<ExamQuestionsBean.reaListBean> dataList) {
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
