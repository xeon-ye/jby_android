package com.jkrm.education.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.MyDateUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.BatchBean;
import com.jkrm.education.bean.ErrorQuestionDetailBean;
import com.jkrm.education.bean.QuestionOptionBean;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.ui.activity.AnswerAnalysisActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.github.kexanie.library.MathView;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/11 19:07
 */

public class AnswerAnalyExpendAdapter extends BaseQuickAdapter<SimilarResultBean, BaseViewHolder> {
    private List<SimilarResultBean> mList = new ArrayList<>();

    public AnswerAnalyExpendAdapter() {
        super(R.layout.answer_analy_expand_item_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, SimilarResultBean item) {
        if(!AwDataUtil.isEmpty(item.getSubQuestions())){
            helper.setText(R.id.tv_num,"共"+item.getSubQuestions()+"小题");
        }
        MathView mathView = helper.getView(R.id.math_view_title);
        //内容
        mathView.setText(item.getContent());
        //设置选择题
        RecyclerView rcv_dataOptions = helper.getView(R.id.rcv_data);
        if (MyDateUtil.isChoiceQuestion(item.getType().getName(), item.getType().getIsOption())) {
            if (null != item.getOptions()) {
                rcv_dataOptions.setVisibility(View.VISIBLE);
                QuestionBasketOptionsAdapter questionBasketOptionsAdapter = new QuestionBasketOptionsAdapter();
                AwRecyclerViewUtil.setRecyclerViewLinearlayout((Activity) mContext, rcv_dataOptions, questionBasketOptionsAdapter, false);
                setChoiceListData(item, questionBasketOptionsAdapter, rcv_dataOptions);
            } else {
                rcv_dataOptions.setVisibility(View.GONE);
            }
        } else {
            rcv_dataOptions.setVisibility(View.GONE);
        }
    }
    /**
     * 选择题选项列表数据填充.
     */
    private void setChoiceListData(SimilarResultBean bean, QuestionBasketOptionsAdapter adapter, RecyclerView rcvData) {
        Map<String, String> mOptionsMap = (Map<String, String>) bean.getOptions();
        List<QuestionOptionBean> mQuestionOptionBeanList = new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, String> entry : mOptionsMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            if (AwDataUtil.isEmpty(entry.getValue())) {
                continue;
            }
            mQuestionOptionBeanList.add(new QuestionOptionBean(QuestionOptionBean.SERIAL_NUMS[index], entry.getValue(), false));
            index++;
        }
        if (AwDataUtil.isEmpty((Serializable) mQuestionOptionBeanList)) {
            adapter.clearData();
            rcvData.removeAllViews();
            adapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mContext, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
            rcvData.setVisibility(View.GONE);
        } else {
            adapter.addAllData(mQuestionOptionBeanList);
            adapter.loadMoreComplete();
            adapter.setEnableLoadMore(false);
            adapter.disableLoadMoreIfNotFullPage(rcvData);
        }
    }


    public void addAllData(List<SimilarResultBean> dataList) {
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
