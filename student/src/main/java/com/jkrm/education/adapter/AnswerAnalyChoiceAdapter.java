package com.jkrm.education.adapter;

import android.graphics.Color;
import android.widget.ImageView;
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
 * @Description:  查看答案
 * @Author: xiangqian
 * @CreateDate: 2020/4/22 17:28
 */

public class AnswerAnalyChoiceAdapter extends BaseQuickAdapter<QuestionOptionBean, BaseViewHolder> {
    private List<QuestionOptionBean> mList = new ArrayList<>();

    public AnswerAnalyChoiceAdapter() {
        super(R.layout.onlineanswer_analychoise_item_layout);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final QuestionOptionBean bean) {
        TextView tv_choice = helper.getView(R.id.tv_choice);
        MathView math_view = helper.getView(R.id.math_view);
        ImageView iv_choice = helper.getView(R.id.iv_choice);
        helper.addOnClickListener(R.id.tv_choice);
        // helper.setIsRecyclable(false);
        //html 设置罗马字体
        tv_choice.setText(bean.getSerialNum());
        AwMathViewUtil.setImgScan(math_view);
        // bean.setSerialNum("<span style=\"font-family: 'Times New Roman';\"><span style=\"font-family: 'Times New Roman';\">"+bean.getSerialNum()+"</span></span>");
        math_view.setText(bean.getContent());
        ///tv_choice.setSelected(bean.isSelect());

        if(1==bean.getChoose()){
            tv_choice.setBackground(mContext.getResources().getDrawable(R.mipmap.choise_red));
            tv_choice.setTextColor(Color.WHITE);
        }else if(2==bean.getChoose()){
            tv_choice.setBackground(mContext.getResources().getDrawable(R.mipmap.choise_green));
            tv_choice.setTextColor(Color.WHITE);
        }else {
            tv_choice.setBackground(mContext.getResources().getDrawable(R.mipmap.choice_normal));
            tv_choice.setTextColor(Color.BLUE);
        }

       /* if(0==bean.getChoose()){
            //未选中
            iv_choice.setImageResource(R.mipmap.choice_normal);
            tv_choice.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        }else if(1==bean.getChoose()){
            //选中
            iv_choice.setImageResource(R.mipmap.choice_choice);
            tv_choice.setTextColor(mContext.getResources().getColor(R.color.white));
        }else if(2==bean.getChoose()){
            //已选过 置灰
            iv_choice.setImageResource(R.mipmap.choice_choiced);
            tv_choice.setTextColor(mContext.getResources().getColor(R.color.gray));

        }*/
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
