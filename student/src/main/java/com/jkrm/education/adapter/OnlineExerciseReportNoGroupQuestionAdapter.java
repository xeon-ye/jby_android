package com.jkrm.education.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.BatchBean;
import com.jkrm.education.bean.result.SimilarResultBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 无组题
 * @Author: xiangqian
 * @CreateDate: 2020/5/13 11:21
 */

public class OnlineExerciseReportNoGroupQuestionAdapter extends BaseQuickAdapter<BatchBean, BaseViewHolder> {
    private List<BatchBean> mList = new ArrayList<>();
    private Activity mContext;
    private OnlineSubmitChildQuestionAdapter mOnlineSubmitQuestionAdapter;

    public OnlineExerciseReportNoGroupQuestionAdapter(Activity context) {
        super(R.layout.adapter_submit_nogroup_quetion_layout);
        mContext = context;
    }



    @Override
    protected void convert(BaseViewHolder helper, BatchBean item) {
        if(helper.getAdapterPosition()>0){
            helper.setVisible(R.id.tv_title,false);
        }
        helper.setText(R.id.tv_title,"所有题目");
        helper.addOnClickListener(R.id.tv_choice);
        TextView tvChoice = helper.getView(R.id.tv_choice);
        if("1".equals(item.getQuestStatus())){
            tvChoice.setBackground(mContext.getResources().getDrawable(R.mipmap.choise_green));
            tvChoice.setTextColor(Color.WHITE);
        }else if("2".equals(item.getQuestStatus())){
            tvChoice.setBackground(mContext.getResources().getDrawable(R.mipmap.choise_yellow));
            tvChoice.setTextColor(Color.WHITE);
        }else if("3".equals(item.getQuestStatus())){
            tvChoice.setBackground(mContext.getResources().getDrawable(R.mipmap.choise_red));
            tvChoice.setTextColor(Color.WHITE);
        }else if("5".equals(item.getQuestStatus())){
            tvChoice.setBackground(mContext.getResources().getDrawable(R.mipmap.choice_gray));
            tvChoice.setTextColor(Color.WHITE);
        }else if("6".equals(item.getQuestStatus())){
            tvChoice.setBackground(mContext.getResources().getDrawable(R.mipmap.choice_normal));
            tvChoice.setTextColor(Color.BLUE);
        }

        helper.setText(R.id.tv_choice,helper.getAdapterPosition()+1+"");

    }


    public void addAllData(List<BatchBean> dataList) {
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

/*    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByQuestionID(RxQuestionBean bean) {
        if(null!=bean){
            for (SimilarResultBean.SubQuestionsBean subQuestionsBean : mSubQuestionsBeanList) {
                if(subQuestionsBean.getId().equals(bean.getId())){
                    subQuestionsBean.setAnswer(bean.isAnswer());
                }
            }
            mOnlineSubmitQuestionAdapter.notifyDataSetChanged();
        }
    }*/
}
