package com.jkrm.education.adapter.mark;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.AnswerSheetProgressResultBean;
import com.jkrm.education.bean.test.TestMarkDetailWithStudentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 批阅详情 --- 按人批阅
 * Created by hzw on 2019/5/9.
 */

public class MarkDetailWithStudentSwitchAdapter extends BaseQuickAdapter<AnswerSheetProgressResultBean, BaseViewHolder> {

    private List<AnswerSheetProgressResultBean> mList = new ArrayList<>();

    public MarkDetailWithStudentSwitchAdapter() {
        super(R.layout.adapter_mark_detail_with_student_switch);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final AnswerSheetProgressResultBean bean) {
        helper.setText(R.id.tv_studentId, bean.getStudCode())
                .setText(R.id.tv_name, bean.getStudentName());
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
