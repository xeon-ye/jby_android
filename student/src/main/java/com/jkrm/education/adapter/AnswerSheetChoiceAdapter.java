package com.jkrm.education.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwMathViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.QuestionOptionBean;

import java.util.ArrayList;
import java.util.List;

import io.github.kexanie.library.MathView;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/4/22 17:28
 */

public class AnswerSheetChoiceAdapter extends BaseQuickAdapter<QuestionOptionBean, BaseViewHolder> {
    private List<QuestionOptionBean> mList = new ArrayList<>();

    public AnswerSheetChoiceAdapter() {
        super(R.layout.answer_sheet_choise_item_layout);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final QuestionOptionBean bean) {
        TextView tv_choice = helper.getView(R.id.tv_choice);
        MathView math_view = helper.getView(R.id.math_view);
        helper.addOnClickListener(R.id.tv_choice);
        AwMathViewUtil.setImgScan(math_view);
       // helper.setIsRecyclable(false);
        //html 设置罗马字体
        tv_choice.setText(bean.getSerialNum());
        // bean.setSerialNum("<span style=\"font-family: 'Times New Roman';\"><span style=\"font-family: 'Times New Roman';\">"+bean.getSerialNum()+"</span></span>");
        math_view.setText(bean.getContent());
        tv_choice.setSelected(bean.isSelect());
    }

    public void addAllData(List<QuestionOptionBean> dataList) {
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


}
