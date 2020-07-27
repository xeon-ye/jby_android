package com.jkrm.education.adapter.mark;

import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.MyDateUtil;
import com.hzw.baselib.util.RegexUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.HomeworkStudentAnswerWithSingleQuestionResultBean;
import com.jkrm.education.bean.test.TestMarkHomeworkQuestionSingleScoreBean;
import com.jkrm.education.util.CustomFontStyleUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 作业详情 --- 学生作答列表
 * Created by hzw on 2019/5/9.
 */

public class MarkHomeworkDetailStudentAnswerAdapter extends BaseQuickAdapter<HomeworkStudentAnswerWithSingleQuestionResultBean, BaseViewHolder> {

    private List<HomeworkStudentAnswerWithSingleQuestionResultBean> mList = new ArrayList<>();
    private String rightResult;

    public MarkHomeworkDetailStudentAnswerAdapter() {
        super(R.layout.adapter_homework_detail_student_answer);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final HomeworkStudentAnswerWithSingleQuestionResultBean bean) {
        helper.setText(R.id.tv_studentId, bean.getStudCode() + "")
                .setText(R.id.tv_name, bean.getStudentName())
                .setText(R.id.tv_score, AwDataUtil.isEmpty(bean.getAnswerScore()) ? "*" : MyDateUtil.replace(bean.getAnswerScore()));
        helper.setTypeface(R.id.tv_score, CustomFontStyleUtil.getNewRomenType());
        helper.setTypeface(R.id.tv_studentId, CustomFontStyleUtil.getNewRomenType());

        if(helper.getPosition() % 2 == 0) {
            helper.setBackgroundColor(R.id.ll_layout, mContext.getResources().getColor(R.color.white));
        } else {
            helper.setBackgroundColor(R.id.ll_layout, mContext.getResources().getColor(R.color.color_F2F9FF));
        }
        if(AwDataUtil.isEmpty(rightResult)) {
            return;
        }
        rightResult= Html.fromHtml(rightResult).toString();
        if(rightResult.equals(MyDateUtil.replace(bean.getAnswerScore()))) {
            helper.setTextColor(R.id.tv_score, mContext.getResources().getColor(R.color.black));
        } else {
            //非全是数字  并且长度不一样  为多选题  半对   橙色     2020/04/24
            if(!RegexUtil.isNumeric(bean.getAnswerScore())&&bean.getAnswerScore().length()<rightResult.length()){
                char[] answerArr  = bean.getAnswerScore().toCharArray();
                int flag = 1 ;
                for(int i=0;i<answerArr.length;i++)
                {
                    //标志，一旦有一个字符不在正确答案中 就变为 0,不得分
                    if(rightResult.indexOf(answerArr[i])==-1)
                    {
                        flag = 0;
                    }

                }
                if(flag==1){
                    helper.setTextColor(R.id.tv_score, mContext.getResources().getColor(R.color.color_F19F10));
                }
                }else{
                helper.setTextColor(R.id.tv_score, mContext.getResources().getColor(R.color.red));
            }
        }

    }

    public void addAllData(String rightResult, List<HomeworkStudentAnswerWithSingleQuestionResultBean> dataList) {
        this.mList = dataList;
        this.rightResult = rightResult;
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
