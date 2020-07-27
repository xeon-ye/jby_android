package com.jkrm.education.adapter.mark;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.MyDateUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.HomeworkDetailResultBean.GradQusetionBean;
import com.jkrm.education.util.CustomFontStyleUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzw on 2019/5/9.
 */

public class MarkDetailByStudentAdapter extends BaseQuickAdapter<GradQusetionBean, BaseViewHolder> {

    private List<GradQusetionBean> mList = new ArrayList<>();

    public MarkDetailByStudentAdapter() {
        super(R.layout.adapter_mark_detail_by_student);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GradQusetionBean bean) {
        helper.setText(R.id.tv_questionNum, "第" + bean.getQuestionNum() + "题")
                .addOnClickListener(R.id.btn_markNextOperate);
        helper.setTypeface(R.id.tv_questionNum, CustomFontStyleUtil.getNewRomenType());
        TextView tv_subTitle = helper.getView(R.id.tv_subTitle);
        if(helper.getPosition() != 0) {
            if(bean.getTypeName().equals(mList.get(helper.getPosition() - 1).getTypeName())) {
                tv_subTitle.setVisibility(View.GONE);
                helper.setGone(R.id.view_space, true);
            } else {
                tv_subTitle.setVisibility(View.VISIBLE);
                helper.setGone(R.id.view_space, false);
            }
        } else {
            tv_subTitle.setVisibility(View.VISIBLE);
        }
        if(bean.isChoiceQuestion()) {
            helper.setGone(R.id.tv_markStatus, false)
                    .setGone(R.id.btn_markNextOperate, true)
                    .setText(R.id.btn_markNextOperate, "查阅");
        } else {
            helper.setGone(R.id.tv_markStatus, true)
                    .setGone(R.id.btn_markNextOperate, true);
            if("1".equals(bean.getStatus())) {
                helper.setText(R.id.btn_markNextOperate, "回评");
                helper.setText(R.id.tv_markStatus, "已批阅")
                        .setTextColor(R.id.tv_markStatus, mContext.getResources().getColor(R.color.color_B9B9B9));
            } else if("0".equals(bean.getStatus())) {
                helper.setText(R.id.btn_markNextOperate, "批阅");
                helper.setText(R.id.tv_markStatus, "未批阅")
                        .setTextColor(R.id.tv_markStatus, mContext.getResources().getColor(R.color.red));
            } else {
                helper.setText(R.id.btn_markNextOperate, "批阅");
                helper.setText(R.id.tv_markStatus, "批阅状态未知")
                        .setTextColor(R.id.tv_markStatus, mContext.getResources().getColor(R.color.red));
            }

        }
        tv_subTitle.setText(bean.getTypeName());
    }

    public void addAllData(List<GradQusetionBean> dataList) {
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
