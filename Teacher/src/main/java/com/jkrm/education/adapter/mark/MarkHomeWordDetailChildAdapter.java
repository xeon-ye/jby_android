package com.jkrm.education.adapter.mark;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.mpchart.helpers.BarChartCommonSingleYWithDiffColorCorrectAnswerHelper;
import com.hzw.baselib.mpchart.helpers.PieChartHomeworkDetailCommonHelper;
import com.hzw.baselib.util.AwCommonUtil;
import com.hzw.baselib.util.AwConvertUtil;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.MyDateUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.HomeworkDetailResultBean;
import com.jkrm.education.util.CustomFontStyleUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/8/19 18:25
 */

public class MarkHomeWordDetailChildAdapter extends BaseQuickAdapter<HomeworkDetailResultBean.GradQusetionBean, BaseViewHolder> {
    private List<HomeworkDetailResultBean.GradQusetionBean> mList = new ArrayList<>();

    public MarkHomeWordDetailChildAdapter() {
        super(R.layout.adapter_homework_child_detail);
    }


    public void addAllData(List<HomeworkDetailResultBean.GradQusetionBean> dataList) {
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


    @Override
    protected void convert(BaseViewHolder helper, HomeworkDetailResultBean.GradQusetionBean bean) {
        helper.setText(R.id.tv_questionNum, "第" + bean.getQuestionNum() + "题")
                .setText(R.id.tv_questionScore, "【" + MyDateUtil.replace(bean.getMaxScore()) + "分】")
                .setText(R.id.tv_scoreRate, "得分率：" + AwConvertUtil.double2String(Double.parseDouble(bean.getRatio() == null ? "0" : bean.getRatio()) * 100, 2) + "%")
                .setText(R.id.tv_subTitle, AwDataUtil.isEmpty(bean.getTitle()) ? "答题详情" : bean.getTitle())
                .setText(R.id.tv_exPlat,bean.getExPlat()+"人需要讲解")
                .addOnClickListener(R.id.btn_studentAnswer)
                .addOnClickListener(R.id.btn_seeSpecial)
                .addOnClickListener(R.id.btn_questionExpand)
                .addOnClickListener(R.id.btn_famousTeacherLecture)
                .addOnClickListener(R.id.rl_questionTitle);
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
        BarChart barchart = helper.getView(R.id.barchart);
        PieChart piechart = helper.getView(R.id.piechart);
        TextView tv_noPieChart = helper.getView(R.id.tv_noPieChart);
        List<HomeworkDetailResultBean.GradQusetionBean.HdDtoBean> tempList = bean.getHdDto();
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
                HomeworkDetailResultBean.GradQusetionBean.HdDtoBean temp = tempList.get(i);
                xAxisValues.add(MyDateUtil.replace(temp.getDuration()));
                yAxisValues.add(Float.valueOf(temp.getCnt()));
                if(temp.getDuration().equals(bean.getProdAnswer())) {
                    correctAnswerIndex = i;
                }
            }
            BarChartCommonSingleYWithDiffColorCorrectAnswerHelper.setBarChart(barchart, xAxisValues, yAxisValues, "", 1, 0, "", 0, AwBaseConstant.COMMON_SUFFIX_PERSON, correctAnswerIndex);
        } else {
            AwCommonUtil.showView(barchart, false);
            AwCommonUtil.showView(piechart, true);

            //设置饼图
            Map<String,Float> pieValues =new LinkedHashMap<>();
            for(HomeworkDetailResultBean.GradQusetionBean.HdDtoBean temp : tempList) {
                if(!"0".equals(temp.getCnt())) {
                    pieValues.put(MyDateUtil.replace(temp.getDuration()), Float.valueOf(temp.getCnt()));
                }
            }
            if(AwDataUtil.isEmpty(pieValues)) {
                AwCommonUtil.showView(piechart, false);
                AwCommonUtil.showView(tv_noPieChart, true);
            }
            PieChartHomeworkDetailCommonHelper.setPieChartCommon(piechart, pieValues);
        }
    }
}
