package com.jkrm.education.adapter.error;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.mpchart.helpers.BarChartCommonSingleYMultipleChoiceWithDiffColorCorrectAnswerHelper;
import com.hzw.baselib.mpchart.helpers.BarChartCommonSingleYWithDiffColorCorrectAnswerHelper;
import com.hzw.baselib.mpchart.helpers.PieChartHomeworkDetailCommonHelper;
import com.hzw.baselib.util.AwCommonUtil;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwMathViewUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.MyDateUtil;
import com.jkrm.education.adapter.QuestionBasketOptionsAdapter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.MistakeBean;
import com.jkrm.education.R;
import com.jkrm.education.bean.QuestionOptionBean;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.ErrorChoiceStatisticsBean;
import com.jkrm.education.bean.result.ErrorSubStatisticsBean;
import com.jkrm.education.bean.result.HomeworkDetailResultBean;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.ui.activity.ScanQrcodeActivity;
import com.jkrm.education.ui.activity.login.ChoiceClassesActivity;
import com.jkrm.education.ui.fragment.ErrorQuestionFragment;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.github.kexanie.library.MathView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/23 11:28
 */

public class ErrorMistakeAdapter extends BaseQuickAdapter<MistakeBean, BaseViewHolder> {
    private List<MistakeBean> mList = new ArrayList<>();

    public ErrorMistakeAdapter() {
        super(R.layout.item_error_question_layout);
    }

    public void addAllData(List<MistakeBean> dataList) {
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
    protected void convert(BaseViewHolder helper, MistakeBean item) {
        DecimalFormat df = new DecimalFormat("0.00%");
        helper.setText(R.id.tv_num, "第" + item.getQuestionNum() + "题")
                .setText(R.id.tv_aver, "班级得分率:" + df.format(Float.parseFloat(item.getClassRatio())))
                .addOnClickListener(R.id.tv_join)
                .addOnClickListener(R.id.tv_analyse);

        MathView mathView = helper.getView(R.id.tv_question_title);
        RecyclerView recyclerView = helper.getView(R.id.rcv_chose_data);
        TextView tv_analyse = helper.getView(R.id.tv_analyse);
        BarChart barChart = helper.getView(R.id.barchart);
        PieChart piechart = helper.getView(R.id.piechart);
        TextView tv_join = helper.getView(R.id.tv_join);
        TextView tv_exPlat = helper.getView(R.id.tv_exPlat);
        MathView mathview_content = helper.getView(R.id.mathview_content);
        if(!AwDataUtil.isEmpty(item.getParentId())){
            helper.setVisible(R.id.tv_exPlat,true);
            helper.setVisible(R.id.mathview_content,true);
            mathview_content.setText(item.getParentContent());
        }
        QuestionBasketOptionsAdapter questionBasketOptionsAdapter = new QuestionBasketOptionsAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout((Activity) mContext, recyclerView, questionBasketOptionsAdapter, false);
        setChoiceListData(item, questionBasketOptionsAdapter, recyclerView);
        mathView.setText(item.getContent());
        AwMathViewUtil.setImgScan(mathView);
        if ("1".equals(item.getIsBasket())) {
            tv_join.setText("移除题篮");
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.aw_bg_gray_radius_30);
            tv_join.setBackground(drawable);
            tv_join.setTextColor(Color.BLACK);
        } else {
            tv_join.setText("加入题篮");
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.aw_bg_blue_radius_30);
            tv_join.setBackground(drawable);
            tv_join.setTextColor(Color.WHITE);

        }
        tv_analyse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setExpend(!item.isExpend());
                Drawable sniper_normal = mContext.getResources().getDrawable(R.mipmap.sniper_normal);
                Drawable sniper_select = mContext.getResources().getDrawable(R.mipmap.sniper_select);
                sniper_normal.setBounds(0, 0, sniper_normal.getMinimumWidth(), sniper_normal.getMinimumHeight());
                sniper_select.setBounds(0, 0, sniper_select.getMinimumWidth(), sniper_select.getMinimumHeight());
                tv_analyse.setCompoundDrawables(null, null, !item.isExpend() ? sniper_normal : sniper_select, null);
                if ("2".equals(item.getType().getIsOption())) {
                    helper.setGone(R.id.barchart, item.isExpend());
                } else {
                    helper.setGone(R.id.piechart, item.isExpend());
                }
            }
        });
        tv_exPlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setParentExpend(!item.isParentExpend());
                Drawable sniper_normal = mContext.getResources().getDrawable(R.mipmap.sniper_normal);
                Drawable sniper_select = mContext.getResources().getDrawable(R.mipmap.sniper_select);
                sniper_normal.setBounds(0, 0, sniper_normal.getMinimumWidth(), sniper_normal.getMinimumHeight());
                sniper_select.setBounds(0, 0, sniper_select.getMinimumWidth(), sniper_select.getMinimumHeight());
                tv_exPlat.setCompoundDrawables(null, null, !item.isParentExpend() ? sniper_normal : sniper_select, null);
                helper.setGone(R.id.mathview_content, item.isParentExpend());
            }
        });
        if ("2".equals(item.getType().getIsOption())) {
            RetrofitClient.builderRetrofit()
                    .create(APIService.class)
                    .getErrorStatistics(RequestUtil.getErrorStatisticsBody(ErrorQuestionFragment.mStrClassIds, item.getTemplateId(), item.getId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new AwApiSubscriber(new AwApiCallback<ErrorChoiceStatisticsBean>() {
                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onSuccess(ErrorChoiceStatisticsBean data) {
                            //设置柱图
                            List<String> xAxisValues = new ArrayList<>();
                            List<Float> yAxisValues = new ArrayList<>();
                            int correctAnswerIndex = -1;
                            Set<String> keyset = data.getOptionsStatistic().keySet();
                            ArrayList arrayList = new ArrayList<String>(keyset);

                            for (int i = 0; i < arrayList.size(); i++) {
                                if ("unchoose".equals(arrayList.get(i).toString())) {
                                    xAxisValues.add("未答");
                                } else {
                                    xAxisValues.add(arrayList.get(i).toString());
                                }
                                yAxisValues.add((float) data.getOptionsStatistic().get(arrayList.get(i)).size());
                            }
                            BarChartCommonSingleYWithDiffColorCorrectAnswerHelper.setBarChartByAnswer(barChart, xAxisValues, yAxisValues, "", 1, 0, "", 0, AwBaseConstant.COMMON_SUFFIX_PERSON, correctAnswerIndex, data.getAnswer());
                        }

                        @Override
                        public void onFailure(int code, String msg) {
                            int c = code;
                        }

                        @Override
                        public void onCompleted() {
                        }
                    }));
        } else {
            RetrofitClient.builderRetrofit()
                    .create(APIService.class)
                    .getErrorSubStatistics(item.getId(), item.getTemplateId(), ErrorQuestionFragment.mStrClassIds)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new AwApiSubscriber(new AwApiCallback<List<ErrorSubStatisticsBean>>() {
                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onSuccess(List<ErrorSubStatisticsBean> data) {
                            //设置饼图
                            Map<String, Float> pieValues = new LinkedHashMap<>();
                            String dur = "";
                            DecimalFormat decimalFormat = new DecimalFormat(".00");

                            for (ErrorSubStatisticsBean temp : data) {
                                dur = temp.getDuration();
                               /* if("满分".equals(temp.getDuration())){
                                    dur="满分("+MyDateUtil.replace(bean.getMaxScore())+")";
                                }else if("优".equals(temp.getDuration())){
                                    dur="优(>="+decimalFormat.format(Float.parseFloat(MyDateUtil.replace(bean.getMaxScore()))*0.8)+")";
                                } else if("良".equals(temp.getDuration())){
                                    dur="良(>="+decimalFormat.format(Float.parseFloat(MyDateUtil.replace(bean.getMaxScore()))*0.6)+")";
                                } else if("差".equals(temp.getDuration())){
                                    dur="差(<"+decimalFormat.format(Float.parseFloat(MyDateUtil.replace(bean.getMaxScore()))*0.6)+")";
                                } else if("零分".equals(temp.getDuration())){
                                    dur="零分(0)";
                                }*/
                                if (!"0".equals(temp.getCnt())) {
                                    //pieValues.put(MyDateUtil.replace(temp.getDuration()), Float.valueOf(temp.getCnt()));
                                    pieValues.put(dur, Float.valueOf(temp.getCnt()));
                                }
                            }

                            PieChartHomeworkDetailCommonHelper.setPieChartCommon(piechart, pieValues);
                        }

                        @Override
                        public void onFailure(int code, String msg) {
                            int c = code;
                        }

                        @Override
                        public void onCompleted() {
                        }
                    }));
        }


    }

    /**
     * 选择题选项列表数据填充.
     */
    private void setChoiceListData(MistakeBean bean, QuestionBasketOptionsAdapter adapter, RecyclerView rcvData) {
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
}
