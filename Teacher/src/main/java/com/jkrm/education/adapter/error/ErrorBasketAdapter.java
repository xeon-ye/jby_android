package com.jkrm.education.adapter.error;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwMathViewUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.QuestionBasketOptionsAdapter;
import com.jkrm.education.bean.ErrorBasketBean;
import com.jkrm.education.bean.MistakeBean;
import com.jkrm.education.bean.QuestionOptionBean;
import com.jkrm.education.constants.MyConstant;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.github.kexanie.library.MathView;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/23 11:28
 */

public class ErrorBasketAdapter extends BaseQuickAdapter<ErrorBasketBean, BaseViewHolder> {
    private List<ErrorBasketBean> mList = new ArrayList<>();

    public ErrorBasketAdapter() {
        super(R.layout.adapter_error_basket_layout);
    }

    public void addAllData(List<ErrorBasketBean> dataList) {
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
    protected void convert(BaseViewHolder helper, ErrorBasketBean item) {
        helper.setText(R.id.tv_num, "第" + (helper.getLayoutPosition() + 1) + "题")
                .setText(R.id.tv_title, item.getHomeName())
                .addOnClickListener(R.id.tv_remove);
        MathView mathView = helper.getView(R.id.tv_question_title);
        RecyclerView recyclerView = helper.getView(R.id.rcv_chose_data);
        QuestionBasketOptionsAdapter questionBasketOptionsAdapter = new QuestionBasketOptionsAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout((Activity) mContext, recyclerView, questionBasketOptionsAdapter, false);
        setChoiceListData(item, questionBasketOptionsAdapter, recyclerView);
        mathView.setText(item.getContent());
        AwMathViewUtil.setImgScan(mathView);
    }

    /**
     * 选择题选项列表数据填充.
     */
    private void setChoiceListData(ErrorBasketBean bean, QuestionBasketOptionsAdapter adapter, RecyclerView rcvData) {
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
}
