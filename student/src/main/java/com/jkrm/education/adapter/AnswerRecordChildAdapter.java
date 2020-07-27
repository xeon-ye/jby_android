package com.jkrm.education.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.util.RegexUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.ReinforBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.ui.activity.ExerciseReportActivity;
import com.jkrm.education.widgets.ProgressBarCoverAdapter;
import com.jkrm.education.widgets.TextWithStyle;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/1 18:33
 */

public class AnswerRecordChildAdapter extends BaseQuickAdapter<ReinforBean.ListBean.ReListBean, BaseViewHolder> {
    private List<ReinforBean.ListBean.ReListBean> mList=new ArrayList<>();
    public AnswerRecordChildAdapter() {
        super(R.layout.answer_record_item_layout);
    }
    @Override
    protected void convert(BaseViewHolder helper, ReinforBean.ListBean.ReListBean item) {
        helper.setText(R.id.tv_course,item.getCourseName());
        helper.setText(R.id.tv_type,item.getTypeName());
        helper.setText(R.id.tv_time, AwDateUtils.getMMddHHmm(item.getCreateTime())+" 共"+item.getQuestNum()+"题用时"+AwDateUtils.getDate(Integer.parseInt(item.getUseTime())));
        com.jkrm.education.widgets.ProgressBar progressBar = helper.getView(R.id.progress);
        final List<TextWithStyle> coverTextWithStyleList = new ArrayList<>();
        helper.getView(R.id.ll_of_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext,ExerciseReportActivity.class);
                intent.putExtra(Extras.BATCHID,item.getBatchId());
                mContext.startActivity(intent);
            }
        });
        TextWithStyle text1 = new TextWithStyle("正确率", 8f, new int[]{0xFF3CB371,0xFF3CB371});
        coverTextWithStyleList.add(text1);
        TextWithStyle text2 = new TextWithStyle(RegexUtil.calculatePercentage(item.getCorrectRate()), 10f, 0xFF3CB371);
        coverTextWithStyleList.add(text2);



        progressBar.setCoverAdapter(new ProgressBarCoverAdapter() {
            @Override
            public List<TextWithStyle> getTextList() {
                return coverTextWithStyleList;
            }

            @Override
            public float getLineSpace() {
                return 5;
            }

            @Override
            public boolean isShowDotFront() {
                return true;
            }

            @Override
            public int getStartAngle() {
                return 90;
            }

            @Override
            public float getProgressPercent() {
                DecimalFormat decimalFormat = new DecimalFormat("##.00");
                String format = decimalFormat.format(Double.parseDouble(item.getCorrectRate()));
                return Float.parseFloat(format)*100/ (80 + 20);

            }

            @Override
            public int getTextGravity() {
                return ProgressBarCoverAdapter.TEXT_GRAVITY_CENTER;
            }
        });

    }
    public void addAllData(List<ReinforBean.ListBean.ReListBean> dataList) {
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
