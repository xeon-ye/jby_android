package com.jkrm.education.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.RegexUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.AnswerSheetBean;
import com.jkrm.education.bean.QuestionOptionBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.ui.activity.AnswerSituationActivity;
import com.jkrm.education.ui.activity.ImgActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/29 11:13
 */

public class AnswerSituationChildAdapter extends BaseQuickAdapter<AnswerSheetBean.QuestionsBean.SubQuestionsBean, BaseViewHolder> {
    private List<AnswerSheetBean.QuestionsBean.SubQuestionsBean> mList = new ArrayList<>();

    public AnswerSituationChildAdapter() {
        super(R.layout.answer_situation_item_layout);
    }


    @Override
    protected void convert(BaseViewHolder helper, AnswerSheetBean.QuestionsBean.SubQuestionsBean item) {
        helper.setText(R.id.tv_num, item.getQuestionNum() + "")
                .addOnClickListener(R.id.tv_explain)
                .addOnClickListener(R.id.tv_join_error);
        RecyclerView rcv_data_child = helper.getView(R.id.rcv_data_child);
        if (AwDataUtil.isEmpty(item.getStudentAnswer())) {
            AnswerSheetBean.QuestionsBean.SubQuestionsBean.StudentAnswer studentAnswer = new AnswerSheetBean.QuestionsBean.SubQuestionsBean.StudentAnswer();
            studentAnswer.setExplain("0");
            studentAnswer.setMistake("0");
            item.setStudentAnswer(studentAnswer);
        }
        item.setStuAnswer(item.getStudentAnswer().getAnswer());
        //选择题
        TextView tv_answer = helper.getView(R.id.tv_answer);
        if ("2".equals(item.getIsOption())) {
            helper.setGone(R.id.tv_join_error, false);
            if (!AwDataUtil.isEmpty(item.getStudentAnswer().getAnswer())) {
                helper.setText(R.id.tv_answer, item.getStudentAnswer().getAnswer());
            }
            if (!AwDataUtil.isEmpty(item.getType()) && !AwDataUtil.isEmpty(item.getType().getTotalId()) && "2".equals(item.getType().getTotalId())) {
                //未作答
                helper.setText(R.id.tv_right_answer, Html.fromHtml(item.getAnswer()).toString());

                if (AwDataUtil.isEmpty(item.getStuAnswer()) && AwDataUtil.isEmpty(item.getStudentAnswer().getAnswer())) {
                    helper.setTextColor(R.id.tv_answer, Color.RED);
                    helper.setText(R.id.tv_answer, "未作答");
                } else {
                    if (!AwDataUtil.isEmpty(item.getStuAnswer())) {
                        helper.setText(R.id.tv_answer, item.getStuAnswer());
                    }

                    switch (RegexUtil.isAllRight(Html.fromHtml(item.getAnswer()).toString(), item.getStuAnswer())) {
                        case 0:
                            tv_answer.setBackground(mContext.getResources().getDrawable(R.drawable.aw_bg_color_5de941_radius_30));
                            break;
                        case 1:
                            tv_answer.setBackground(mContext.getResources().getDrawable(R.drawable.aw_bg_color_ffad35_radius_30));
                            break;
                        case 2:
                            tv_answer.setBackground(mContext.getResources().getDrawable(R.drawable.aw_bg_color_red_radius_30));
                            break;
                    }
                }

            } else {
                helper.setText(R.id.tv_right_answer, Html.fromHtml(item.getAnswer()).toString());
                //未作答
                if (AwDataUtil.isEmpty(item.getStuAnswer()) && AwDataUtil.isEmpty(item.getStudentAnswer().getAnswer())) {
                    helper.setTextColor(R.id.tv_answer, Color.RED);
                    helper.setText(R.id.tv_answer, "未作答");
                } else {
                    helper.setTextColor(R.id.tv_answer, Color.WHITE);
                    helper.setText(R.id.tv_answer, item.getStuAnswer());

                    //回答正确
                    if (!AwDataUtil.isEmpty(item.getStuAnswer()) && item.getStuAnswer().equals(Html.fromHtml(item.getAnswer()).toString())) {
                        helper.setBackgroundRes(R.id.tv_answer, R.mipmap.choise_green);
                    } else {
                        //回答错误
                        helper.setBackgroundRes(R.id.tv_answer, R.mipmap.choise_red);
                    }
                }
            }
        } else {
            helper.setGone(R.id.tv_answer, false).setGone(R.id.tv_right_answer, false);
            if (AwDataUtil.isEmpty(item.getImageList())&&AwDataUtil.isEmpty(item.getStudentAnswer())) {
                helper.setTextColor(R.id.tv_hint_answer, Color.RED);
                helper.setText(R.id.tv_hint_answer, "未作答");
            } else {
                helper.setText(R.id.tv_hint_answer, "我的作答");
                AnswerAnalyImgAdapter answerAnalyImgAdapter = new AnswerAnalyImgAdapter();
                if(!AwDataUtil.isEmpty(item.getStudentAnswer().getRawScan())){
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.add(item.getStudentAnswer().getRawScan());
                    answerAnalyImgAdapter.addAllData(arrayList);
                }else if(!AwDataUtil.isEmpty(item.getImageList())){
                    answerAnalyImgAdapter.addAllData(item.getImageList());
                }
                AwRecyclerViewUtil.setRecyclerViewGridlayout((Activity) mContext, rcv_data_child, answerAnalyImgAdapter, 3);
                answerAnalyImgAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        Intent intent = new Intent();
                        List<String> data = adapter.getData();
                        intent.putExtra(Extras.IMG_URL,data.get(position));
                        intent.setClass(mContext, ImgActivity.class);
                        mContext.startActivity(intent);
                    }
                });
            }

        }
        TextView tv_explain = helper.getView(R.id.tv_explain);
        if ("0".equals(item.getStudentAnswer().getExplain())) {
            helper.setText(R.id.tv_explain, "需讲解");
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.situation_explain);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            tv_explain.setCompoundDrawables(drawable, null, null, null);
            tv_explain.setBackground(mContext.getResources().getDrawable(R.drawable.aw_bg_dcf1ff_radius_30));
            tv_explain.setTextColor(Color.parseColor("#2995E0"));
        } else {
            helper.setText(R.id.tv_explain, "不讲解 ");
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.situation_unexplain);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            tv_explain.setCompoundDrawables(drawable, null, null, null);
            tv_explain.setBackground(mContext.getResources().getDrawable(R.drawable.aw_bg_fff0e5_radius_30));
            tv_explain.setTextColor(Color.parseColor("#E49555"));

        }
        TextView tv_join_error = helper.getView(R.id.tv_join_error);
        if ("0".equals(item.getStudentAnswer().getMistake())) {
            helper.setText(R.id.tv_join_error, "加入错题本");
            tv_join_error.setTextColor(Color.WHITE);
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.situation_error);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            tv_join_error.setCompoundDrawables(drawable, null, null, null);
            tv_join_error.setBackground(mContext.getResources().getDrawable(R.drawable.aw_bg_dcf1ff_radius_30));
            tv_join_error.setTextColor(Color.parseColor("#2995E0"));

        } else {
            helper.setText(R.id.tv_join_error, "  移除错题本  ");
            tv_join_error.setTextColor(Color.GRAY);
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.situation_unerror);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            tv_join_error.setCompoundDrawables(drawable, null, null, null);
            tv_join_error.setBackground(mContext.getResources().getDrawable(R.drawable.aw_bg_fff0e5_radius_30));
            tv_join_error.setTextColor(Color.parseColor("#E49555"));

        }
    }

    public void addAllData(List<AnswerSheetBean.QuestionsBean.SubQuestionsBean> dataList) {
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


    /**
     * 选择题选项列表数据填充.
     */
    private void setChoiceListData(AnswerSheetBean.QuestionsBean.SubQuestionsBean bean, AnswerSheetChoiceAdapter adapter, RecyclerView rcvData) {
        Map<String, String> mOptionsMap = (Map<String, String>) bean.getOptions();
        System.out.println("setChoiceListData" + mOptionsMap.toString());
        List<QuestionOptionBean> mMQuestionOptionBeanList = new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, String> entry : mOptionsMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            if (AwDataUtil.isEmpty(entry.getValue())) {
                continue;
            }
            mMQuestionOptionBeanList.add(new QuestionOptionBean(QuestionOptionBean.SERIAL_NUMS[index], entry.getValue(), false));
            index++;
        }
        adapter.addAllData(mMQuestionOptionBeanList);
        adapter.loadMoreComplete();
        adapter.setEnableLoadMore(false);
        adapter.disableLoadMoreIfNotFullPage(rcvData);
    }

    /**
     * 选择题选项列表数据填充.
     */
    private void setMultipleChoiceListData(AnswerSheetBean.QuestionsBean.SubQuestionsBean bean, AnswerSheetMultipleChoiceAdapter adapter, RecyclerView rcvData) {
        Map<String, String> mOptionsMap = (Map<String, String>) bean.getOptions();
        System.out.println("setChoiceListData" + mOptionsMap.toString());
        List<QuestionOptionBean> mMQuestionOptionBeanList = new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, String> entry : mOptionsMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            if (AwDataUtil.isEmpty(entry.getValue())) {
                continue;
            }
            mMQuestionOptionBeanList.add(new QuestionOptionBean(QuestionOptionBean.SERIAL_NUMS[index], entry.getValue(), false));
            index++;
        }
        adapter.addAllData(mMQuestionOptionBeanList);
        adapter.loadMoreComplete();
        adapter.setEnableLoadMore(false);
        adapter.disableLoadMoreIfNotFullPage(rcvData);
    }
}
