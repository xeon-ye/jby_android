package com.jkrm.education.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.ErrorQuestionResultBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzw on 2019/5/9.
 */

public class ErrorQuestionStatisticsAdapter extends BaseQuickAdapter<ErrorQuestionResultBean, BaseViewHolder> {

    private List<ErrorQuestionResultBean> mList = new ArrayList<>();

    public ErrorQuestionStatisticsAdapter() {
        super(R.layout.adapter_error_question_statistics);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ErrorQuestionResultBean bean) {
        helper.setText(R.id.tv_num, "第" + bean.getQuestionNum() + "题")
                .setText(R.id.tv_type, bean.getTypeName())
                .addOnClickListener(R.id.iv_switch);
        ImageView iv_switch = helper.getView(R.id.iv_switch);
        if(bean.isSelect()) {
            iv_switch.setImageResource(R.mipmap.icon_switch_open);
        } else {
            iv_switch.setImageResource(R.mipmap.icon_switch_close);
        }

    }

    public void addAllData(List<ErrorQuestionResultBean> dataList) {
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
