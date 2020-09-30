package com.jkrm.education.adapter.exam;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.exam.ClassBean;
import com.jkrm.education.bean.exam.ExamQuestionBean;

import java.util.List;

import io.github.kexanie.library.MathView;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/9/11 17:29
 */

public class SeeExamQuestionAdapter extends BaseQuickAdapter<ExamQuestionBean, BaseViewHolder> {
    List<ExamQuestionBean> mList;

    public SeeExamQuestionAdapter() {
        super(R.layout.adapter_see_exam_quesiton_item_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExamQuestionBean item) {
        MathView mathview_content = helper.getView(R.id.mathview_content);
        mathview_content.setText(item.getContent());
        MathView mathview_answer = helper.getView(R.id.mathview_answer);
        mathview_answer.setText(item.getAnswer());
        MathView mathview_answerExplanation = helper.getView(R.id.mathview_answerExplanation);
        mathview_answerExplanation.setText(item.getAnswerExplanation());
    }
    public void addAllData(List<ExamQuestionBean> dataList) {
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
