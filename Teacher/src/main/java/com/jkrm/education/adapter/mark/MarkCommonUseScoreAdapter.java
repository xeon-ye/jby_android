package com.jkrm.education.adapter.mark;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.test.TestMarkCommonUseScoreBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzw on 2019/5/9.
 */

public class MarkCommonUseScoreAdapter extends BaseQuickAdapter<TestMarkCommonUseScoreBean, BaseViewHolder> {

    private List<TestMarkCommonUseScoreBean> mList = new ArrayList<>();

    public MarkCommonUseScoreAdapter() {
        super(R.layout.adapter_mark_common_use_score);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final TestMarkCommonUseScoreBean bean) {
        TextView tv_score = helper.getView(R.id.tv_score);
        helper.setText(R.id.tv_score, bean.getScore() + " ");
        if(bean.isHandleModify()) {
            if(bean.isSelect()) {
                tv_score.setTextColor(mContext.getResources().getColor(R.color.white));
                tv_score.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
            } else {
                tv_score.setTextColor(mContext.getResources().getColor(R.color.red));
                tv_score.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }
        } else {
            if(bean.isSelect()) {
                tv_score.setTextColor(mContext.getResources().getColor(R.color.white));
                tv_score.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
            } else {
                tv_score.setTextColor(mContext.getResources().getColor(R.color.black));
                tv_score.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }
        }

    }

    public void addAllData(List<TestMarkCommonUseScoreBean> dataList) {
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
