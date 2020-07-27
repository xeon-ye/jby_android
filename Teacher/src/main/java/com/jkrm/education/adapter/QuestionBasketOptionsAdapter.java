package com.jkrm.education.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwMathViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.QuestionOptionBean;

import java.util.ArrayList;
import java.util.List;

import io.github.kexanie.library.MathView;

/**
 * Created by hzw on 2019/5/9.
 */

public class QuestionBasketOptionsAdapter extends BaseQuickAdapter<QuestionOptionBean, BaseViewHolder> {

    private List<QuestionOptionBean> mList = new ArrayList<>();

    public QuestionBasketOptionsAdapter() {
        super(R.layout.adapter_question_basket_options);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final QuestionOptionBean bean) {
        MathView mv_options = helper.getView(R.id.mv_options);
        //html 设置罗马字体
        bean.setSerialNum("<span style=\"font-family: 'Times New Roman';\"><span style=\"font-family: 'Times New Roman';\">"+bean.getSerialNum()+"</span></span>");
        mv_options.setText(bean.getSerialNum() + " " + bean.getContent());
        AwMathViewUtil.setImgScan(mv_options);
    }

    public void addAllData(List<QuestionOptionBean> dataList) {
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
