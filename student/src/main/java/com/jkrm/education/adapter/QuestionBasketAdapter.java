package com.jkrm.education.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwEffectiveRequestViewUtil;
import com.hzw.baselib.util.AwImgUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.MyDateUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.QuestionOptionBean;
import com.jkrm.education.bean.result.ParcticeQuestBean;
import com.jkrm.education.bean.result.PracticeDataResultBean.PracticeBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.ui.activity.ImgActivity;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.OnImageClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.github.kexanie.library.MathView;

/**
 * Created by hzw on 2019/5/9.
 */

public class QuestionBasketAdapter extends BaseQuickAdapter<ParcticeQuestBean, BaseViewHolder> {

    private List<ParcticeQuestBean> mList = new ArrayList<>();

    public QuestionBasketAdapter() {
        super(R.layout.adapter_question_basket);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ParcticeQuestBean bean) {
        helper.setText(R.id.tv_questionNum, "第" + bean.getQuestionNum() + "题")
                .setText(R.id.tv_questionScore, "【" + MyDateUtil.replace(bean.getMaxScore()) + "分】")
                .setGone(R.id.ll_content, AwDataUtil.isEmpty(bean.getContent()) ? false : true)
                .addOnClickListener(R.id.btn_delFromBasket)
                .addOnClickListener(R.id.btn_seeAnswer)
                .addOnClickListener(R.id.btn_famousTeacherLecture);

        //名师讲题判断
        Button btn_famousTeacherLecture = helper.getView(R.id.btn_famousTeacherLecture);
        AwEffectiveRequestViewUtil.setButtonEnableChangeTxtColor(mContext, btn_famousTeacherLecture, !AwDataUtil.isEmpty(bean.getQuestionVideoId()));
        MathView mv_content = helper.getView(R.id.mv_content);
        mv_content.setText(bean.getContent());
        TextView tv_subTitle = helper.getView(R.id.tv_subTitle);
        //        AwLog.d("detail bean 本次题号 " + bean.getQuestionNum() + " 类型" + bean.getType());
        //        AwLog.d("detail bean 上一题号 " + mList.get(helper.getPosition() - 1).getQuestionNum() + " 类型" + mList.get(helper.getPosition() - 1).getType());
        if(helper.getPosition() != 0) {
           /* if(null==bean.getTypeName()){
                return;
            }*/
            if(bean.getTypeName().equals(mList.get(helper.getPosition() - 1).getTypeName())) {
                tv_subTitle.setVisibility(View.GONE);
            } else {
                tv_subTitle.setVisibility(View.VISIBLE);
            }
        } else {
            tv_subTitle.setVisibility(View.VISIBLE);
        }
        tv_subTitle.setText(bean.getTypeName());

        //设置选择题
        RecyclerView rcv_dataOptions = helper.getView(R.id.rcv_dataOptions);
        if(bean.isChoiceQuestion()) {
            if(null != bean.getOptions()) {
                rcv_dataOptions.setVisibility(View.VISIBLE);
                QuestionBasketOptionsAdapter questionBasketOptionsAdapter = new QuestionBasketOptionsAdapter();
                AwRecyclerViewUtil.setRecyclerViewLinearlayout((Activity) mContext, rcv_dataOptions, questionBasketOptionsAdapter, false);
                setChoiceListData(bean, questionBasketOptionsAdapter, rcv_dataOptions);
            } else {
                rcv_dataOptions.setVisibility(View.GONE);
            }
        } else {
            rcv_dataOptions.setVisibility(View.GONE);
        }
    }

    public void addAllData(List<ParcticeQuestBean> dataList) {
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

    /**
     * 选择题选项列表数据填充.
     */
    private void setChoiceListData(ParcticeQuestBean bean, QuestionBasketOptionsAdapter adapter, RecyclerView rcvData) {
        Map<String, String> mOptionsMap = (Map<String, String>) bean.getOptions();
        List<QuestionOptionBean>  mQuestionOptionBeanList = new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, String> entry : mOptionsMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            if(AwDataUtil.isEmpty(entry.getValue())) {
                continue;
            }
            mQuestionOptionBeanList.add(new QuestionOptionBean(QuestionOptionBean.SERIAL_NUMS[index], entry.getValue(), false));
            index++;
        }
        if (AwDataUtil.isEmpty(mQuestionOptionBeanList)) {
            adapter.clearData();
            rcvData.removeAllViews();
            adapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mContext, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
        } else {
            adapter.addAllData(mQuestionOptionBeanList);
            adapter.loadMoreComplete();
            adapter.setEnableLoadMore(false);
            adapter.disableLoadMoreIfNotFullPage(rcvData);
        }
    }
}
