package com.jkrm.education.adapter.mark;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.MyDateUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.HomeworkDetailResultBean.GradQusetionBean;
import com.jkrm.education.util.CustomFontStyleUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzw on 2019/5/9.
 */

public class MarkDetailAdapter extends BaseQuickAdapter<GradQusetionBean, BaseViewHolder> {

    private List<GradQusetionBean> mList = new ArrayList<>();

    public MarkDetailAdapter() {
        super(R.layout.adapter_mark_detail);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GradQusetionBean bean) {
        AwLog.d("TestMarkDetailBean MarkDetailAdapter bean: " + bean.toString());
        helper.setText(R.id.tv_questionNum, "第" + bean.getQuestionNum() + "题")
                .setText(R.id.tv_markedCount, "已批阅：" + MyDateUtil.getMarkProgress(bean.getProgress())[0])
                .setGone(R.id.btn_markNextOperate, AwDataUtil.isEmpty(bean.getTypeName()) ? false : true)
                .setTypeface(R.id.tv_questionNum, CustomFontStyleUtil.getNewRomenType())
                .setTypeface(R.id.tv_markedCount, CustomFontStyleUtil.getNewRomenType())
                .addOnClickListener(R.id.btn_markNextOperate);
        ProgressBar pb_progressBar = helper.getView(R.id.pb_progressBar);
        pb_progressBar.setMax(Integer.parseInt(MyDateUtil.getMarkProgress(bean.getProgress())[1]));
        pb_progressBar.setProgress(Integer.parseInt(MyDateUtil.getMarkProgress(bean.getProgress())[0]));
        if(MyDateUtil.getMarkRoteLessThan50(bean.getProgress())) {
            pb_progressBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progress_bar_red));
        } else {
            pb_progressBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progress_bar_blue));
        }
        AwLog.d("TestMarkDetailBean currentPosition: " + helper.getPosition() + " ,前一个position: " + (helper.getPosition() - 1));
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
            helper.setGone(R.id.ll_progressBar, false)
                    .setGone(R.id.tv_markedCount, false);
            helper.setText(R.id.btn_markNextOperate, "查阅");
        } else {
            helper.setGone(R.id.ll_progressBar, true)
                    .setGone(R.id.tv_markedCount, true);
            if(AwDataUtil.isEmpty(bean.getProgress())) {
                helper.setText(R.id.btn_markNextOperate, "批阅");
            } else {
                if(MyDateUtil.getMarkProgress(bean.getProgress())[0].equals(MyDateUtil.getMarkProgress(bean.getProgress())[1])) {
                    helper.setText(R.id.btn_markNextOperate, "回评");
                } else {
                    helper.setText(R.id.btn_markNextOperate, "批阅");
                }
            }
        }
        tv_subTitle.setText(AwDataUtil.isEmpty(bean.getTypeName()) ? "未知题型" : bean.getTypeName());
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
