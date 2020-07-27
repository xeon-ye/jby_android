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
import com.jkrm.education.bean.test.TestQuestionBean;
import com.jkrm.education.util.CustomFontStyleUtil;
import com.jkrm.education.util.TestDataUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzw on 2019/5/9.
 */

public class MarkHomeworkDetailAdapter extends BaseQuickAdapter<AnswerSheetDataDetailResultBean, BaseViewHolder> {

    private List<AnswerSheetDataDetailResultBean> mList = new ArrayList<>();

    public MarkHomeworkDetailAdapter() {
        super(R.layout.adapter_homework_detail);
    }

    //String.format(mContext.getResources().getString(R.string.question_num, bean.getQuestionNum()))
    //String.format(mContext.getResources().getString(R.string.question_score_total, bean.getMaxScore()))
    @Override
    protected void convert(final BaseViewHolder helper, final AnswerSheetDataDetailResultBean bean) {
        helper.setIsRecyclable(false);
        //答案为空处理
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
                .setText(R.id.tv_questionScore, "【" + MyDateUtil.replace(bean.getMaxScore()) + "分】")
                .setText(R.id.btn_addQuestionBasket, "1".equals(bean.getPractice()) ? "移出题篮" : "加入题篮")
                .setGone(R.id.ll_img, bean.isChoiceQuestion() ? false : true)
                //2020 04  07 无名师讲题隐藏按钮
                .setGone(R.id.btn_famousTeacherLecture,AwDataUtil.isEmpty(bean.getQuestionVideo())?false:true)
                .addOnClickListener(R.id.iv_img)
                .addOnClickListener(R.id.btn_studentAnswer)
                .addOnClickListener(R.id.btn_questionExpand)
                .addOnClickListener(R.id.btn_addQuestionBasket)
                .addOnClickListener(R.id.btn_famousTeacherLecture)
                .addOnClickListener(R.id.rl_questionTitle)
                .setTypeface(R.id.tv_questionAnswer, CustomFontStyleUtil.getNewRomenType())
                .setTypeface(R.id.tv_scoreRate,CustomFontStyleUtil.getNewRomenType())
                .setTypeface(R.id.tv_questionNum,CustomFontStyleUtil.getNewRomenType())
                .setTypeface(R.id.tv_questionScore,CustomFontStyleUtil.getNewRomenType());
        //类题加练判断
        Button btn_questionExpand = helper.getView(R.id.btn_questionExpand);
       // AwEffectiveRequestViewUtil.setButtonEnableChangeTxtColor(mContext, btn_questionExpand, "1".equals(bean.getSimilar()));
        helper.setGone(R.id.btn_questionExpand,"1".equals(bean.getSimilar()));
        //学霸答卷判断
        Button btn_studentAnswer = helper.getView(R.id.btn_studentAnswer);
       // AwEffectiveRequestViewUtil.setButtonEnableChangeTxtColor(mContext, btn_studentAnswer, "1".equals(bean.getIsNoMake()));
       // helper.setGone(R.id.btn_studentAnswer,"1".equals(bean.getIsNoMake()));
        if(bean.getSmDto() != null && !AwDataUtil.isEmpty(bean.getSmDto().getRawScan())){
            btn_studentAnswer.setVisibility(View.VISIBLE);
        }
        //名师讲题判断
        Button btn_famousTeacherLecture = helper.getView(R.id.btn_famousTeacherLecture);
       // AwEffectiveRequestViewUtil.setButtonEnableChangeTxtColor(mContext, btn_famousTeacherLecture, !AwDataUtil.isEmpty(bean.getQuestionVideoId()));
        helper.setGone(R.id.btn_famousTeacherLecture,!AwDataUtil.isEmpty(bean.getQuestionVideoId()));

        TextView tv_scoreRate = helper.getView(R.id.tv_scoreRate);
        if(bean.isChoiceQuestion()) {
            helper.setGone(R.id.tv_questionAnswer, true)
                    .setText(R.id.tv_scoreRate,"我的答案：" + bean.getAnswer());
            helper.setText(R.id.tv_questionAnswer, "正确答案：" + Html.fromHtml(bean.getProdAnswer()));
              //增加新罗马字体判断
            if(!AwDataUtil.isEmpty(bean.getAnswer()) && !AwDataUtil.isEmpty(bean.getProdAnswer()) && bean.getAnswer().equals(bean.getProdAnswer())||bean.getAnswer().equals(Html.fromHtml(bean.getProdAnswer()).toString())) {
                MyDateUtil.setScoreStyle(mContext, tv_scoreRate, tv_scoreRate.getText().toString(), R.color.black);
            } else {
                MyDateUtil.setScoreStyle(mContext, tv_scoreRate, tv_scoreRate.getText().toString(), R.color.red);
            }
            //非全是数字  并且长度不一样  为多选题  半对   橙色     2020/04/24
            if(!RegexUtil.isNumeric( bean.getAnswer())&& bean.getAnswer().length()<(Html.fromHtml(bean.getProdAnswer()).length())){
                char[] answerArr  = bean.getAnswer().toCharArray();
                int flag = 1 ;
                for(int i=0;i<answerArr.length;i++)
                {
                    //标志，一旦有一个字符不在正确答案中 就变为 0,不得分
                    if((Html.fromHtml(bean.getProdAnswer()).toString()).indexOf(answerArr[i])==-1)
                    {
                        flag = 0;
                    }

                }
                if(flag==1){
                    MyDateUtil.setScoreStyle(mContext, tv_scoreRate, tv_scoreRate.getText().toString(), R.color.color_F19F10);
                }

            }

        } else {
            helper.setGone(R.id.tv_questionAnswer, false)
                    .setText(R.id.tv_scoreRate,"得分：" + MyDateUtil.replace(bean.getScore()));
            helper.setText(R.id.tv_questionAnswer, "正确答案：" +Html.fromHtml(bean.getProdAnswer()));
            if(AwDataUtil.isEmpty(bean.getAnswer())){
                MyDateUtil.setScoreStyle(mContext, tv_scoreRate, tv_scoreRate.getText().toString(), R.color.red);
                helper.setText(R.id.tv_scoreRate,"未作答");
            }
            if(!AwDataUtil.isEmpty(bean.getMaxScore()) && !AwDataUtil.isEmpty(bean.getScore()) && bean.getMaxScore().equals(bean.getScore())) {
                MyDateUtil.setScoreStyle(mContext, tv_scoreRate, tv_scoreRate.getText().toString(), R.color.black);
            } else {
                MyDateUtil.setScoreStyle(mContext, tv_scoreRate, tv_scoreRate.getText().toString(), R.color.red);
            }

        }

        if(!AwDataUtil.isEmpty(bean.getGradedScan())){
            AwImgUtil.setImg(mContext, helper.getView(R.id.iv_img), bean.getGradedScan());
        }else if(!AwDataUtil.isEmpty(bean.getRawScan())){
            AwImgUtil.setImg(mContext, helper.getView(R.id.iv_img), bean.getRawScan());
        }else{
            helper.setGone(R.id.iv_img,false);
        }

        TextView tv_subTitle = helper.getView(R.id.tv_subTitle);
//        AwLog.d("detail bean 本次题号 " + bean.getQuestionNum() + " 类型" + bean.getType());
//        AwLog.d("detail bean 上一题号 " + mList.get(helper.getPosition() - 1).getQuestionNum() + " 类型" + mList.get(helper.getPosition() - 1).getType());
        if(helper.getPosition() != 0 && helper.getPosition() != 1) { //添加了headerview, 注意position位置
            if(bean.getTypeName().equals(mList.get(helper.getPosition() - 2).getTypeName())) {
                tv_subTitle.setVisibility(View.GONE);
            } else {
                tv_subTitle.setVisibility(View.VISIBLE);
            }
        } else {
            tv_subTitle.setVisibility(View.VISIBLE);
        }
        tv_subTitle.setText(bean.getTypeName());
    }

    public void addAllData(List<AnswerSheetDataDetailResultBean> dataList) {
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
