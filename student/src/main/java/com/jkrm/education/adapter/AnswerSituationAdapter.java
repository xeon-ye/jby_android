package com.jkrm.education.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.AnswerSheetBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/29 15:06
 */

public class AnswerSituationAdapter extends BaseQuickAdapter<AnswerSheetBean.QuestionsBean, BaseViewHolder> {
    private List<AnswerSheetBean.QuestionsBean> mList = new ArrayList<>();
    OnAdapterItemClickListener mOnAdapterItemClickListener;

    public void setOnAdapterItemClickListener(OnAdapterItemClickListener onAdapterItemClickListener) {
        mOnAdapterItemClickListener = onAdapterItemClickListener;
    }

    public AnswerSituationAdapter() {
        super(R.layout.answer_sheet_item_layout);
    }


    @Override
    protected void convert(BaseViewHolder helper, AnswerSheetBean.QuestionsBean item) {
        helper.setGone(R.id.tv_title, !AwDataUtil.isEmpty(item.getTitle()));
        helper.setText(R.id.tv_title, item.getTitle() + "");
        RecyclerView recyclerView = helper.getView(R.id.rcv_data_child);
        AnswerSituationChildAdapter answerSheetChildAdapter = new AnswerSituationChildAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout((Activity) mContext, recyclerView, answerSheetChildAdapter, false);
        answerSheetChildAdapter.addAllData(item.getSubQuestions());
        answerSheetChildAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                AnswerSheetBean.QuestionsBean.SubQuestionsBean data = (AnswerSheetBean.QuestionsBean.SubQuestionsBean) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.tv_explain:
                        if("0".equals(data.getStudentAnswer().getExplain())){
                            mOnAdapterItemClickListener.explainClick(data);
                        }else{
                            mOnAdapterItemClickListener.unExplainClick(data);
                        }
                        break;
                    case R.id.tv_join_error:
                        if("0".equals(data.getStudentAnswer().getMistake())){
                            mOnAdapterItemClickListener.joinErrorClick(data);
                        }else{
                            mOnAdapterItemClickListener.deleteErrorClick(data);
                        }
                        break;

                }

            }
        });

    }

    public void addAllData(List<AnswerSheetBean.QuestionsBean> dataList) {
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

    /**
     * 点击事件
     */
    public interface OnAdapterItemClickListener {
        void explainClick(AnswerSheetBean.QuestionsBean.SubQuestionsBean data);

        void unExplainClick(AnswerSheetBean.QuestionsBean.SubQuestionsBean data);

        void joinErrorClick(AnswerSheetBean.QuestionsBean.SubQuestionsBean data);

        void deleteErrorClick(AnswerSheetBean.QuestionsBean.SubQuestionsBean data);

    }
}
