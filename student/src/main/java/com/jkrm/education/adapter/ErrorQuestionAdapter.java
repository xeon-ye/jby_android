package com.jkrm.education.adapter;

import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwEffectiveRequestViewUtil;
import com.hzw.baselib.util.AwImgUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.MyDateUtil;
import com.hzw.baselib.util.RandomValueUtil;
import com.hzw.baselib.util.RegexUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.AnswerSheetDataDetailResultBean;
import com.jkrm.education.bean.result.ErrorQuestionResultBean;
import com.jkrm.education.bean.test.TestQuestionBean;
import com.jkrm.education.util.CustomFontStyleUtil;
import com.jkrm.education.util.TestDataUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzw on 2019/5/9.
 */

public class ErrorQuestionAdapter extends BaseQuickAdapter<ErrorQuestionResultBean, BaseViewHolder> {

    private List<ErrorQuestionResultBean> mList = new ArrayList<>();

    public ErrorQuestionAdapter() {
        super(R.layout.adapter_error_question);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ErrorQuestionResultBean bean) {
        if(bean.isChoiceQuestion()) {
            if(AwDataUtil.isEmpty(bean.getAnswer())){
                bean.setAnswer("*");
            }
        }else{
            if(AwDataUtil.isEmpty(bean.getScore())){
                bean.setScore("0");
            }
        }
        helper.setText(R.id.tv_questionNum, "第" + bean.getQuestionNum() + "题")
                .setText(R.id.tv_questionScore, "【" + MyDateUtil.replace(bean.getGrade().getMaxScore()) + "分】")
                .setText(R.id.btn_addQuestionBasket, "1".equals(bean.getPractice()) ? "移出题篮" : "加入题篮")
                .setGone(R.id.ll_img, bean.isChoiceQuestion() ? false : true)
                .setTypeface(R.id.tv_questionAnswer, CustomFontStyleUtil.getNewRomenType())
                .setTypeface(R.id.tv_scoreRate,CustomFontStyleUtil.getNewRomenType())
                .setTypeface(R.id.tv_questionNum,CustomFontStyleUtil.getNewRomenType())
                .setTypeface(R.id.tv_questionScore,CustomFontStyleUtil.getNewRomenType())
                .addOnClickListener(R.id.iv_img)
                .addOnClickListener(R.id.btn_studentAnswer)
                .addOnClickListener(R.id.btn_questionExpand)
                .addOnClickListener(R.id.btn_addQuestionBasket)
                .addOnClickListener(R.id.btn_famousTeacherLecture)
                .addOnClickListener(R.id.rl_questionTitle);

        //类题加练判断
        Button btn_questionExpand = helper.getView(R.id.btn_questionExpand);
       // AwEffectiveRequestViewUtil.setButtonEnableChangeTxtColor(mContext, btn_questionExpand, "1".equals(bean.getSimilar()));
        helper.setGone(R.id.btn_questionExpand,"1".equals(bean.getSimilar()));
        //学霸答卷判断
        // AwEffectiveRequestViewUtil.setButtonEnableChangeTxtColor(mContext, btn_studentAnswer, "1".equals(bean.getIsNoMake()));
        helper.setGone(R.id.btn_studentAnswer,"1".equals(bean.getIsNoMake()));
        //名师讲题判断
        // AwEffectiveRequestViewUtil.setButtonEnableChangeTxtColor(mContext, btn_famousTeacherLecture, !AwDataUtil.isEmpty(bean.getQuestionVideoId()));
        helper.setGone(R.id.btn_famousTeacherLecture,!AwDataUtil.isEmpty(bean.getQuestionVideoId()));
        //学霸答卷判断
        Button btn_studentAnswer = helper.getView(R.id.btn_studentAnswer);
       // AwEffectiveRequestViewUtil.setButtonEnableChangeTxtColor(mContext, btn_studentAnswer, "1".equals(bean.getIsNoMake()));
        //名师讲题判断
        Button btn_famousTeacherLecture = helper.getView(R.id.btn_famousTeacherLecture);
       // AwEffectiveRequestViewUtil.setButtonEnableChangeTxtColor(mContext, btn_famousTeacherLecture, !AwDataUtil.isEmpty(bean.getQuestionVideoId()));
        if(bean.isChoiceQuestion()) {
            helper.setGone(R.id.tv_questionAnswer, true)
                    .setGone(R.id.btn_studentAnswer, false) //学霸答卷
                    .setText(R.id.tv_scoreRate,"我的答案：" + bean.getAnswer());
            helper.setText(R.id.tv_questionAnswer, "正确答案：" + Html.fromHtml( bean.getCorrectAnswer()));
        } else {
            helper.setGone(R.id.tv_questionAnswer, false)
                    .setGone(R.id.btn_studentAnswer, true)
                    .setText(R.id.tv_scoreRate,"得分：" + MyDateUtil.replace(bean.getGrade().getScore()));
        }
        TextView tv_scoreRate = helper.getView(R.id.tv_scoreRate);
        if(!AwDataUtil.isEmpty(bean.getAnswer()) && !AwDataUtil.isEmpty(bean.getCorrectAnswer()) && bean.getAnswer().equals(bean.getCorrectAnswer())||bean.getAnswer().equals(Html.fromHtml(bean.getCorrectAnswer()).toString())) {
            MyDateUtil.setScoreStyle(mContext, tv_scoreRate, tv_scoreRate.getText().toString(), R.color.black);
        } else {
            MyDateUtil.setScoreStyle(mContext, tv_scoreRate, tv_scoreRate.getText().toString(), R.color.red);
        }
        if(bean.isChoiceQuestion()){
            //非全是数字  并且长度不一样  为多选题  半对   橙色     2020/04/24
            if(!RegexUtil.isNumeric( bean.getAnswer())&& bean.getAnswer().length()<(Html.fromHtml(bean.getCorrectAnswer()).length())){
                char[] answerArr  = bean.getAnswer().toCharArray();
                int flag = 1 ;
                for(int i=0;i<answerArr.length;i++)
                {
                    //标志，一旦有一个字符不在正确答案中 就变为 0,不得分
                    if((Html.fromHtml(bean.getCorrectAnswer()).toString()).indexOf(answerArr[i])==-1)
                    {
                        flag = 0;
                    }

                }
                if(flag==1){
                    MyDateUtil.setScoreStyle(mContext, tv_scoreRate, tv_scoreRate.getText().toString(), R.color.color_F19F10);
                }

            }
        }
        if(!AwDataUtil.isEmpty(bean.getGradedScan())) {
            AwImgUtil.setImg(mContext, helper.getView(R.id.iv_img), bean.getGradedScan());
        }else{
            AwImgUtil.setImg(mContext, helper.getView(R.id.iv_img), bean.getRawScan());
        }

        TextView tv_subTitle = helper.getView(R.id.tv_subTitle);
        //        AwLog.d("detail bean 本次题号 " + bean.getQuestionNum() + " 类型" + bean.getType());
        //        AwLog.d("detail bean 上一题号 " + mList.get(helper.getPosition() - 1).getQuestionNum() + " 类型" + mList.get(helper.getPosition() - 1).getType());
        if(helper.getPosition() != 0) { //添加了headerview, 注意position位置
            if(bean.getTypeName().equals(mList.get(helper.getPosition() - 1).getTypeName())) {
                tv_subTitle.setVisibility(View.GONE);
            } else {
                tv_subTitle.setVisibility(View.VISIBLE);
            }
        } else {
            tv_subTitle.setVisibility(View.VISIBLE);
        }
        tv_subTitle.setText(bean.getTypeName());
    }

    public void addAllData(List<ErrorQuestionResultBean> dataList) {
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
