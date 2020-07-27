package com.jkrm.education.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwEffectiveRequestViewUtil;
import com.hzw.baselib.util.AwMathViewUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.MyDateUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.QuestionOptionBean;
import com.jkrm.education.bean.result.BookExercisesListBean;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.util.CustomFontStyleUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.github.kexanie.library.MathView;

/**
 * Created by hzw on 2019/5/9.
 */

public class BookExercisesMainAdapter extends BaseQuickAdapter<BookExercisesListBean, BaseViewHolder> {

    private List<BookExercisesListBean> mList = new ArrayList<>();

    public BookExercisesMainAdapter() {
        super(R.layout.adapter_bookexercises_layout);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final BookExercisesListBean bean) {
        helper.setText(R.id.tv_questionNum, "第" + bean.getQuestionNum() + "题")
                .setText(R.id.tv_questionScore, "【" + MyDateUtil.replace(bean.getMaxScore()) + "分】")
                .setText(R.id.btn_delFromBasket, "1".equals(bean.getPractice()) ? "移出题篮" : "加入题篮")
                .setGone(R.id.ll_content, AwDataUtil.isEmpty(bean.getContent()) ? false : true)
                //2020 04  07 无名师讲题隐藏按钮
                .setGone(R.id.btn_famousTeacherLecture,AwDataUtil.isEmpty(bean.getQuestionVideo())?false:true)
                .addOnClickListener(R.id.btn_delFromBasket)
                .addOnClickListener(R.id.btn_seeAnswer)
                .addOnClickListener(R.id.btn_questionExpand)
                .setTypeface(R.id.tv_questionNum, CustomFontStyleUtil.getNewRomenType())
                .setTypeface(R.id.tv_questionScore, CustomFontStyleUtil.getNewRomenType())
                .addOnClickListener(R.id.btn_famousTeacherLecture);

        //名师讲题判断
        Button btn_famousTeacherLecture = helper.getView(R.id.btn_famousTeacherLecture);
        Button btn_questionExpand = helper.getView(R.id.btn_questionExpand);
        AwEffectiveRequestViewUtil.setButtonEnableChangeTxtColor(mContext, btn_famousTeacherLecture, !AwDataUtil.isEmpty(bean.getQuestionVideo()));
        AwEffectiveRequestViewUtil.setButtonEnableChangeTxtColor(mContext, btn_questionExpand, "1".equals(bean.getSimilar()));
        MathView mv_content = helper.getView(R.id.mv_content);
        AwMathViewUtil.setImgScan(mv_content);
        mv_content.setText(bean.getContent());
        TextView tv_subTitle = helper.getView(R.id.tv_subTitle);
        //        AwLog.d("detail bean 本次题号 " + bean.getQuestionNum() + " 类型" + bean.getType());
        //        AwLog.d("detail bean 上一题号 " + mList.get(helper.getPosition() - 1).getQuestionNum() + " 类型" + mList.get(helper.getPosition() - 1).getType());
        if (helper.getPosition() != 0) {
            if (null == bean || null == bean.getType() || null == bean.getType().getName()) {
                return;
            }
            if (bean.getType().getName().equals(mList.get(helper.getPosition() - 1).getType().getName())) {
                tv_subTitle.setVisibility(View.GONE);
            } else {
                tv_subTitle.setVisibility(View.VISIBLE);
            }
        } else {
            tv_subTitle.setVisibility(View.VISIBLE);

        }
        if (null != bean && null != bean.getType() && null != bean.getType().getName()) {
            tv_subTitle.setText(bean.getType().getName());
            //设置选择题
            RecyclerView rcv_dataOptions = helper.getView(R.id.rcv_dataOptions);
            if (MyDateUtil.isChoiceQuestion(bean.getType().getName(), bean.getType().getIsOption())) {
                if (null != bean.getOptions()) {
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


    }

    public void addAllData(List<BookExercisesListBean> dataList) {
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
    private void setChoiceListData(BookExercisesListBean bean, QuestionBasketOptionsAdapter adapter, RecyclerView rcvData) {
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
