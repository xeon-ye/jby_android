package com.jkrm.education.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.util.AwMathViewUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.MyDateUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.BatchBean;
import com.jkrm.education.bean.ErrorQuestionTimeBean;
import com.jkrm.education.bean.QuestionOptionBean;
import com.jkrm.education.bean.rx.RxLQuestionType;
import com.jkrm.education.bean.rx.RxQuestionBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.ui.activity.AnswerAnalysisActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.github.kexanie.library.MathView;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/3 17:26
 */

public class ErrorQuesitonTimeAdapter extends BaseQuickAdapter<ErrorQuestionTimeBean, BaseViewHolder> {

    private List<ErrorQuestionTimeBean> mList = new ArrayList<>();
    private boolean mIsChose;

    public boolean isChose() {
        return mIsChose;
    }

    public void setChose(boolean chose) {
        mIsChose = chose;
    }

    public ErrorQuesitonTimeAdapter() {
        super(R.layout.error_question_item_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ErrorQuestionTimeBean bean) {
        helper.setVisible(R.id.cb_chose, mIsChose)
                .setGone(R.id.tv_subquestions_num, !AwDataUtil.isEmpty(bean.getSubQuestions()))
                .addOnClickListener(R.id.rcv_data)
                .addOnClickListener(R.id.math_view_title)
                .setGone(R.id.tv_video, "1".equals(bean.getIsNoVideo()))
                .setGone(R.id.tv_similar, "1".equals(bean.getSimilar()))
                .setText(R.id.tv_type, bean.getErrorTypeName())
                .setText(R.id.tv_time, AwDateUtils.getyyyyMMddHHmmss(bean.getShowTime()));
        CheckBox checkBox = helper.getView(R.id.cb_chose);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                bean.setChose(b);
                int i = 0;
                for (ErrorQuestionTimeBean errorQuestionTimeBean : mList) {
                    if (errorQuestionTimeBean.isChose()) {
                        i++;
                    }
                }
                RxLQuestionType rxLQuestionType = new RxLQuestionType("已选择" + i + "题(" + mList.size() + ")", 1);
                rxLQuestionType.setChoseNum(i);
                EventBus.getDefault().post(rxLQuestionType);
            }
        });

        checkBox.setChecked(bean.isChose());
        if (!AwDataUtil.isEmpty(bean.getSubQuestions())) {
            helper.setText(R.id.tv_subquestions_num, "共" + bean.getSubQuestions().size() + "小题");
        }

        MathView mathView = helper.getView(R.id.math_view_title);
        mathView.setText(bean.getContent());
        AwMathViewUtil.setImgScan(mathView);

        if (null != bean && null != bean.getType() && null != bean.getType().getName()) {

            //设置选择题
            RecyclerView rcv_dataOptions = helper.getView(R.id.rcv_data);
            if (MyDateUtil.isChoiceQuestion(bean.getType().getName(), bean.getType().getIsOption())) {
                if (null != bean.getOptions()) {
                    rcv_dataOptions.setVisibility(View.VISIBLE);
                    QuestionBasketOptionsAdapter questionBasketOptionsAdapter = new QuestionBasketOptionsAdapter();
                    AwRecyclerViewUtil.setRecyclerViewLinearlayout((Activity) mContext, rcv_dataOptions, questionBasketOptionsAdapter, false);
                    setChoiceListData(bean, questionBasketOptionsAdapter, rcv_dataOptions);
                    questionBasketOptionsAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                        @Override
                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                            ErrorQuestionTimeBean errorQuestionTimeBean = bean;
                            Gson gson = new Gson();
                            ArrayList<BatchBean> batchBeans = new ArrayList<>();
                            BatchBean batchBean = gson.fromJson(gson.toJson(errorQuestionTimeBean), BatchBean.class);
                            batchBeans.add(batchBean);
                            Intent intent = new Intent();
                            intent.setClass(mContext, AnswerAnalysisActivity.class);
                            intent.putExtra(Extras.EXERCISEREPORT, (Serializable) batchBeans);
                            intent.putExtra(Extras.KEY_QUESTION_TYPE, errorQuestionTimeBean.getErrorTypeName());
                            intent.putExtra(Extras.KEY_QUESTION_TIME, errorQuestionTimeBean.getShowTime() + "");
                            mContext.startActivity(intent);
                        }
                    });
                } else {
                    rcv_dataOptions.setVisibility(View.GONE);
                }
            } else {
                rcv_dataOptions.setVisibility(View.GONE);
            }
        }

    }

    /**
     * 选择题选项列表数据填充.
     */
    private void setChoiceListData(ErrorQuestionTimeBean bean, QuestionBasketOptionsAdapter adapter, RecyclerView rcvData) {
        Map<String, String> mOptionsMap = (Map<String, String>) bean.getOptions();
        List<QuestionOptionBean> mQuestionOptionBeanList = new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, String> entry : mOptionsMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            if (AwDataUtil.isEmpty(entry.getValue())) {
                continue;
            }
            mQuestionOptionBeanList.add(new QuestionOptionBean(QuestionOptionBean.SERIAL_NUMS[index], entry.getValue(), false));
            index++;
        }
        if (AwDataUtil.isEmpty((Serializable) mQuestionOptionBeanList)) {
            adapter.clearData();
            rcvData.removeAllViews();
            adapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mContext, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
            rcvData.setVisibility(View.GONE);
        } else {
            adapter.addAllData(mQuestionOptionBeanList);
            adapter.loadMoreComplete();
            adapter.setEnableLoadMore(false);
            adapter.disableLoadMoreIfNotFullPage(rcvData);
        }
    }

    public void addAllData(List<ErrorQuestionTimeBean> dataList) {
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
