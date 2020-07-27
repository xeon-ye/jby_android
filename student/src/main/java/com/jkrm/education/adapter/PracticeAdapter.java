package com.jkrm.education.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwEffectiveRequestViewUtil;
import com.hzw.baselib.util.AwImgUtil;
import com.hzw.baselib.util.MyDateUtil;
import com.hzw.baselib.util.RandomValueUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.ErrorQuestionResultBean;
import com.jkrm.education.bean.result.PracticeDataResultBean;
import com.jkrm.education.bean.result.PracticeResultBean;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.bean.test.TestQuestionBean;
import com.jkrm.education.util.TestDataUtil;
import com.jkrm.education.bean.result.PracticeDataResultBean.PracticeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzw on 2019/5/9.
 */

public class PracticeAdapter extends BaseQuickAdapter<PracticeDataResultBean.PracticeBean, BaseViewHolder> {

    private List<PracticeBean> mList = new ArrayList<>();

    public PracticeAdapter() {
        super(R.layout.adapter_practice);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final PracticeBean bean) {
        helper.setText(R.id.tv_questionNum, "第" + bean.getQuestionNum() + "题")
//                .setText(R.id.tv_questionScore, "【" + MyDateUtil.replace(bean.getGrade().getMaxScore()) + "分】")
                .setGone(R.id.ll_img, bean.isChoiceQuestion() ? false : true)
                .setGone(R.id.ll_img, AwDataUtil.isEmpty(bean.getScanImage()) ? false : true)
                .addOnClickListener(R.id.iv_img)
                .addOnClickListener(R.id.btn_studentAnswer)
                .addOnClickListener(R.id.btn_questionExpand)
                .addOnClickListener(R.id.btn_seeAnswer)
                .addOnClickListener(R.id.btn_famousTeacherLecture);

        //类题加练判断
//        Button btn_questionExpand = helper.getView(R.id.btn_questionExpand);
//        AwEffectiveRequestViewUtil.setButtonEnableChangeTxtColor(mContext, btn_questionExpand, "1".equals(bean.getSimilar()));
        //学霸答卷判断
//        Button btn_studentAnswer = helper.getView(R.id.btn_studentAnswer);
//        AwEffectiveRequestViewUtil.setButtonEnableChangeTxtColor(mContext, btn_studentAnswer, "1".equals(bean.getIsNoMake()));
        //名师讲题判断
        Button btn_famousTeacherLecture = helper.getView(R.id.btn_famousTeacherLecture);
        AwEffectiveRequestViewUtil.setButtonEnableChangeTxtColor(mContext, btn_famousTeacherLecture, !AwDataUtil.isEmpty(bean.getQuestionVideoId()));
        if(bean.isChoiceQuestion()) {
            helper.setGone(R.id.tv_questionAnswer, true)
                    .setGone(R.id.btn_studentAnswer, false) //学霸答卷
                    .setText(R.id.tv_scoreRate,"我的答案：" + bean.getAnswer());
            helper.setText(R.id.tv_questionAnswer, "正确答案：" + bean.getCorrectAnswer());
        } else {
            helper.setGone(R.id.tv_questionAnswer, false)
                    .setGone(R.id.btn_studentAnswer, false)
//                    .setText(R.id.tv_scoreRate,"得分：" + MyDateUtil.replace(bean.getGrade().getScore()));
                    .setGone(R.id.tv_scoreRate, false);
        }
        TextView tv_scoreRate = helper.getView(R.id.tv_scoreRate);
        if(!AwDataUtil.isEmpty(bean.getAnswer()) && !AwDataUtil.isEmpty(bean.getCorrectAnswer()) && bean.getAnswer().equals(bean.getCorrectAnswer())) {
            MyDateUtil.setScoreStyle(mContext, tv_scoreRate, tv_scoreRate.getText().toString(), R.color.black);
        } else {
            MyDateUtil.setScoreStyle(mContext, tv_scoreRate, tv_scoreRate.getText().toString(), R.color.red);
        }

        AwImgUtil.setImg(mContext, helper.getView(R.id.iv_img), bean.getScanImage());
        TextView tv_subTitle = helper.getView(R.id.tv_subTitle);
        //        AwLog.d("detail bean 本次题号 " + bean.getQuestionNum() + " 类型" + bean.getType());
        //        AwLog.d("detail bean 上一题号 " + mList.get(helper.getPosition() - 1).getQuestionNum() + " 类型" + mList.get(helper.getPosition() - 1).getType());
        if(helper.getPosition() != 0 && helper.getPosition() != 1) { //添加了headerview, 注意position位置
            if(bean.getQuestionTypeName().equals(mList.get(helper.getPosition() - 2).getQuestionTypeName())) {
                tv_subTitle.setVisibility(View.GONE);
            } else {
                tv_subTitle.setVisibility(View.VISIBLE);
            }
        } else {
            tv_subTitle.setVisibility(View.VISIBLE);
        }
        tv_subTitle.setText(bean.getQuestionTypeName());
    }

    public void addAllData(List<PracticeBean> dataList) {
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
