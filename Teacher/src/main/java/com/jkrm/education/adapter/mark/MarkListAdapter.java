package com.jkrm.education.adapter.mark;

import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.util.AwEffectiveRequestViewUtil;
import com.hzw.baselib.util.MyDateUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.bean.test.TestMarkBean;
import com.jkrm.education.util.CustomFontStyleUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hzw on 2019/5/9.
 */

public class MarkListAdapter extends BaseQuickAdapter<RowsHomeworkBean, BaseViewHolder> {

    private List<RowsHomeworkBean> mList = new ArrayList<>();

    public MarkListAdapter() {
        super(R.layout.adapter_mark_main);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final RowsHomeworkBean bean) {
        helper.setText(R.id.tv_classes, bean.getClasses().getName() + " " + bean.getName())
                .setTypeface(R.id.tv_classes, CustomFontStyleUtil.getNewRomenType())
                .setText(R.id.tv_num, helper.getLayoutPosition() + 1 + "")
                .setText(R.id.tv_date, AwDateUtils.dealDateFormat(bean.getCreateTime()))
//                .setText(R.id.tv_date, AwDataUtil.isEmpty(bean.getCreateTime()) ? "未知时间" : AwDateUtils.formatDate16.format(new Date(Long.valueOf(bean.getCreateTime()))))
                .setText(R.id.tv_markingProgress, "已批阅： " + MyDateUtil.getMarkRote(bean.getStatistics().getProgress(), bean.getStatistics().getSubmitted()))
                .addOnClickListener(R.id.btn_markNextOperate);
        Button btn_markNextOperate = helper.getView(R.id.btn_markNextOperate);
        if(bean.isHandle()) {
            if(bean.isMarkFinish()) {
                btn_markNextOperate.setText("回评");
            } else {
                btn_markNextOperate.setText("批阅");
            }
        } else {
            btn_markNextOperate.setText("处理中");
            AwEffectiveRequestViewUtil.setButtonEnableChangeTxtColor(mContext, btn_markNextOperate, false);
        }

    }

    public void addAllData(List<RowsHomeworkBean> dataList) {
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
