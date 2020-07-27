package com.jkrm.education.adapter.mark;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.AnswerSheetProgressResultBean;
import com.jkrm.education.bean.test.TestMarkDetailWithStudentBean;
import com.jkrm.education.util.CustomFontStyleUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 批阅详情 --- 按人批阅
 * Created by hzw on 2019/5/9.
 */

public class MarkDetailWithStudentStatusAdapter extends BaseQuickAdapter<AnswerSheetProgressResultBean, BaseViewHolder> {

    private List<AnswerSheetProgressResultBean> mList = new ArrayList<>();

    public MarkDetailWithStudentStatusAdapter() {
        super(R.layout.adapter_mark_detail_with_student_status);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final AnswerSheetProgressResultBean bean) {
        helper.setText(R.id.tv_studentId, bean.getStudCode())
                .setText(R.id.tv_name, bean.getStudentName())
                .setText(R.id.tv_status, bean.getMarkProgress())
                .setTypeface(R.id.tv_studentId, CustomFontStyleUtil.getNewRomenType())
                .setTextColor(R.id.tv_status, "已批阅".equals(bean.getMarkProgress()) ? mContext.getResources().getColor(R.color.black) : mContext.getResources().getColor(R.color.red));
        if(helper.getPosition() % 2 == 0) {
            helper.setBackgroundColor(R.id.ll_layout, mContext.getResources().getColor(R.color.white));
        } else {
            helper.setBackgroundColor(R.id.ll_layout, mContext.getResources().getColor(R.color.color_F2F9FF));
        }
    }

    public void addAllData(List<AnswerSheetProgressResultBean> dataList) {
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
