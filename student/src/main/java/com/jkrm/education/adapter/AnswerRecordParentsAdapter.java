package com.jkrm.education.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.ReinfoRoceCouseBean;
import com.jkrm.education.bean.ReinforBean;
import com.jkrm.education.bean.result.BookExercisesBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/1 18:33
 */

public class AnswerRecordParentsAdapter extends BaseQuickAdapter<ReinforBean.ListBean, BaseViewHolder> {
    private List<ReinforBean.ListBean> mList=new ArrayList<>();
    public AnswerRecordParentsAdapter() {
        super(R.layout.answer_record_child_layout);
    }
    @Override
    protected void convert(BaseViewHolder helper, ReinforBean.ListBean item) {
        helper.setText(R.id.tv_title,item.getMonthy()+"月");
        AnswerRecordChildAdapter answerRecordChildAdapter=new AnswerRecordChildAdapter();
        RecyclerView recyclerView = helper.getView(R.id.rcv_data_child);
        AwRecyclerViewUtil.setRecyclerViewLinearlayout((Activity)mContext,recyclerView,answerRecordChildAdapter,true);
        answerRecordChildAdapter.addAllData(item.getReList());
    }
    public void addAllData(List<ReinforBean.ListBean> dataList) {
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
