package com.jkrm.education.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.ErrorQuestionClassifyBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.bean.result.ErrorQuestionResultBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.ui.activity.ErrorQuestionClassifyActivity;


import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/4 16:26
 */

public class ErrorQuestionClassifyAdapter extends BaseQuickAdapter<ErrorQuestionClassifyBean, BaseViewHolder> {
    private List<ErrorQuestionClassifyBean> mList = new ArrayList<>();

    public ErrorQuestionClassifyAdapter() {
        super(R.layout.error_question_classify_item_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ErrorQuestionClassifyBean item) {
        helper.setText(R.id.tv_time, item.getMonthy() + "月");
        RecyclerView recyclerView = helper.getView(R.id.rcv_data);
        ErrorQuestionClassifyChildrenAdapter errorQuestionClassifyChildrenAdapter = new ErrorQuestionClassifyChildrenAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout((Activity) mContext, recyclerView, errorQuestionClassifyChildrenAdapter, true);
        if (AwDataUtil.isEmpty(item.getReaList())) {
            errorQuestionClassifyChildrenAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mContext, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
        } else {
            errorQuestionClassifyChildrenAdapter.addAllData(item.getReaList());
        }
        errorQuestionClassifyChildrenAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<ErrorQuestionClassifyBean.ReaListBean> data = adapter.getData();
                Intent intent = new Intent(mContext, ErrorQuestionClassifyActivity.class);
                intent.putExtra(Extras.KEY_TEMPLATE_ID, data.get(position).getTemplateId());
                intent.putExtra(Extras.KEY_TEMPLATE_TITLE,data.get(position).getTitleName());
                mContext.startActivity(intent);
            }
        });
    }

    public void addAllData(List<ErrorQuestionClassifyBean> dataList) {
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
