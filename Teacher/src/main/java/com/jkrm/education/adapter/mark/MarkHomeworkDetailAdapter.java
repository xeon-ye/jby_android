package com.jkrm.education.adapter.mark;

import android.graphics.Typeface;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.mpchart.helpers.BarChartCommonSingleYMultipleChoiceWithDiffColorCorrectAnswerHelper;
import com.hzw.baselib.mpchart.helpers.BarChartCommonSingleYWithDiffColorCorrectAnswerHelper;
import com.hzw.baselib.mpchart.helpers.PieChartHomeworkDetailCommonHelper;
import com.hzw.baselib.util.AwCommonUtil;
import com.hzw.baselib.util.AwConvertUtil;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.util.AwEffectiveRequestViewUtil;
import com.hzw.baselib.util.MyDateUtil;
import com.jkrm.education.R;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.result.HomeworkDetailResultBean.GradQusetionBean;
import com.jkrm.education.bean.result.HomeworkDetailResultBean.GradQusetionBean.HdDtoBean;
import com.jkrm.education.util.CustomFontStyleUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hzw on 2019/5/9.
 */

public class MarkHomeworkDetailAdapter extends BaseQuickAdapter<GradQusetionBean, BaseViewHolder> {

    private List<GradQusetionBean> mList = new ArrayList<>();

    public MarkHomeworkDetailAdapter() {
        super(R.layout.adapter_homework_detail);

    }

    @Override
    protected void convert(final BaseViewHolder helper, final GradQusetionBean bean) {
        helper.setText(R.id.tv_questionNum, "第" + bean.getQuestionNum() + "题")
                .setText(R.id.tv_questionScore, "【" + MyDateUtil.replace(bean.getMaxScore()) + "分】")
                .setText(R.id.tv_scoreRate, "得分率：" + AwConvertUtil.double2String(Double.parseDouble(bean.getRatio() == null ? "0" : bean.getRatio()) * 100, 2) + "%")
                .setText(R.id.tv_subTitle, AwDataUtil.isEmpty(bean.getTypeName()) ? "未知题型" : bean.getTypeName())
                .setText(R.id.tv_exPlat,bean.getExPlat()+"人需要讲解")
                .setGone(R.id.tv_exPlat,!"0".equals(bean.getExPlat()))
                .addOnClickListener(R.id.btn_studentAnswer)
                .addOnClickListener(R.id.btn_seeSpecial)
                .addOnClickListener(R.id.btn_questionExpand)
                .addOnClickListener(R.id.btn_famousTeacherLecture)
                .setTypeface(R.id.tv_questionNum,CustomFontStyleUtil.getNewRomenType())
                .setTypeface(R.id.tv_questionScore,CustomFontStyleUtil.getNewRomenType())
                .addOnClickListener(R.id.rl_questionTitle)
                .addOnClickListener(R.id.tv_exPlat);

        //典型题判断
        Button btn_seeSpecial = helper.getView(R.id.btn_seeSpecial);
        //AwEffectiveRequestViewUtil.setButtonEnableChangeTxtColor(mContext, btn_seeSpecial, "1".equals(bean.getTypical()));
        helper.setGone(R.id.btn_seeSpecial,"1".equals(bean.getTypical()));

        //类题加练判断
        Button btn_questionExpand = helper.getView(R.id.btn_questionExpand);
        //AwEffectiveRequestViewUtil.setButtonEnableChangeTxtColor(mContext, btn_questionExpand, "1".equals(bean.getSimilar()));
        helper.setGone(R.id.btn_questionExpand,"1".equals(bean.getSimilar()));

        //名师讲题判断
        Button btn_famousTeacherLecture = helper.getView(R.id.btn_famousTeacherLecture);
       // AwEffectiveRequestViewUtil.setButtonEnableChangeTxtColor(mContext, btn_famousTeacherLecture, !AwDataUtil.isEmpty(bean.getQuestionVideoId()));
        helper.setGone(R.id.btn_famousTeacherLecture,!AwDataUtil.isEmpty(bean.getQuestionVideoId()));

        if(bean.isChoiceQuestion()) {
            helper.setGone(R.id.tv_questionAnswer, true)
                    .setGone(R.id.tv_ProdAnswer,true)
                    .setGone(R.id.btn_seeSpecial, false); //查看典型
             if(!AwDataUtil.isEmpty(bean.getProdAnswer())){
                 helper.setTypeface(R.id.tv_ProdAnswer, CustomFontStyleUtil.getNewRomenType());
                 helper.setText(R.id.tv_questionAnswer, "正确答案：" );
                 helper.setText(R.id.tv_ProdAnswer, Html.fromHtml(bean.getProdAnswer()));
             }

        } else {
            helper.setGone(R.id.tv_questionAnswer, false)
                    .setGone(R.id.tv_ProdAnswer,false)
                    .setGone(R.id.btn_seeSpecial, true);
        }

        TextView tv_subTitle = helper.getView(R.id.tv_subTitle);
        //添加了headerview. 注意position位置处理
        if(helper.getPosition() != 0 && helper.getPosition() != 1) {
            if(bean.getTypeName().equals(mList.get(helper.getPosition() - 2).getTypeName())) {
                tv_subTitle.setVisibility(View.GONE);
            } else {
                tv_subTitle.setVisibility(View.VISIBLE);
            }
        } else {
            tv_subTitle.setVisibility(View.VISIBLE);
        }

        BarChart barchart = helper.getView(R.id.barchart);
        PieChart piechart = helper.getView(R.id.piechart);
        TextView tv_noPieChart = helper.getView(R.id.tv_noPieChart);
        List<HdDtoBean> tempList = bean.getHdDto();
        if(AwDataUtil.isEmpty(tempList)) {
            AwCommonUtil.showView(barchart, false);
            AwCommonUtil.showView(piechart, false);
            AwCommonUtil.showView(tv_noPieChart, false);
            return;
        }
        if(bean.isChoiceQuestion()) {
            AwCommonUtil.showView(barchart, true);
            AwCommonUtil.showView(piechart, false);
            AwCommonUtil.showView(tv_noPieChart, false);
            //设置柱图
            List<String> xAxisValues = new ArrayList<>();
            List<Float> yAxisValues = new ArrayList<>();

            int correctAnswerIndex = -1;
            for(int i=0; i<tempList.size(); i++) {
                HdDtoBean temp = tempList.get(i);
                xAxisValues.add(MyDateUtil.replace(temp.getDuration()));
                yAxisValues.add(Float.valueOf(temp.getCnt()));
                if(temp.getDuration().equals(bean.getProdAnswer())) {
                    correctAnswerIndex = i;
                }
            }
            if("多选题".equals(bean.getTypeName())){
                BarChartCommonSingleYMultipleChoiceWithDiffColorCorrectAnswerHelper.setBarChart(barchart,xAxisValues,yAxisValues,"",1,0,"",0,AwBaseConstant.COMMON_SUFFIX_PERSON, correctAnswerIndex);
                return;
            }
           // BarChartCommonSingleYWithDiffColorCorrectAnswerHelper.setBarChart(barchart, xAxisValues, yAxisValues, "", 1, 0, "", 0, AwBaseConstant.COMMON_SUFFIX_PERSON, correctAnswerIndex);
            BarChartCommonSingleYWithDiffColorCorrectAnswerHelper.setBarChartByAnswer(barchart, xAxisValues, yAxisValues, "", 1, 0, "", 0, AwBaseConstant.COMMON_SUFFIX_PERSON, correctAnswerIndex,Html.fromHtml(bean.getProdAnswer()).toString());

        } else {
            AwCommonUtil.showView(barchart, false);
            AwCommonUtil.showView(piechart, true);

            //设置饼图
            Map<String,Float> pieValues =new LinkedHashMap<>();
            String dur="";
            DecimalFormat decimalFormat=new DecimalFormat(".00");
            for(HdDtoBean temp : tempList) {
                if(AwDataUtil.isEmpty(bean.getMaxScore())){
                    return;
                }
                if("满分".equals(temp.getDuration())){
                    dur="满分("+MyDateUtil.replace(bean.getMaxScore())+")";
                }else if("优".equals(temp.getDuration())){
                    dur="优(>="+decimalFormat.format(Float.parseFloat(MyDateUtil.replace(bean.getMaxScore()))*0.8)+")";
                } else if("良".equals(temp.getDuration())){
                    dur="良(>="+decimalFormat.format(Float.parseFloat(MyDateUtil.replace(bean.getMaxScore()))*0.6)+")";
                } else if("差".equals(temp.getDuration())){
                    dur="差(<"+decimalFormat.format(Float.parseFloat(MyDateUtil.replace(bean.getMaxScore()))*0.6)+")";
                } else if("零分".equals(temp.getDuration())){
                    dur="零分(0)";
                }
                if(!"0".equals(temp.getCnt())) {
                    //pieValues.put(MyDateUtil.replace(temp.getDuration()), Float.valueOf(temp.getCnt()));
                    pieValues.put(dur, Float.valueOf(temp.getCnt()));
                }
            }
            if(AwDataUtil.isEmpty(pieValues)) {
                AwCommonUtil.showView(piechart, false);
                AwCommonUtil.showView(tv_noPieChart, true);
            }
            PieChartHomeworkDetailCommonHelper.setPieChartCommon(piechart, pieValues);
        }
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
