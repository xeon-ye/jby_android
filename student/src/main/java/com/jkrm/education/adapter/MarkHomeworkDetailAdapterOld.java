package com.jkrm.education.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwImgUtil;
import com.hzw.baselib.util.RandomValueUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.test.TestQuestionBean;
import com.jkrm.education.util.TestDataUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzw on 2019/5/9.
 */

public class MarkHomeworkDetailAdapterOld extends BaseQuickAdapter<TestQuestionBean, BaseViewHolder> {

    private List<TestQuestionBean> mList = new ArrayList<>();

    public MarkHomeworkDetailAdapterOld() {
        super(R.layout.adapter_homework_detail);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final TestQuestionBean bean) {
        helper.setText(R.id.tv_questionNum, bean.getQuestionNum())
                .setText(R.id.tv_questionScore, "【" + bean.getTotalScore() + "分】")
                .setText(R.id.tv_questionAnswer, "正确答案：" + bean.getRightAnswer())
                .setText(R.id.tv_scoreRate, bean.getType() == 1 ? "我的答案：C" : "得分：5")
                .setGone(R.id.tv_questionAnswer, bean.getRightAnswer() == null ? false : true)
                .setGone(R.id.btn_studentAnswer, bean.getType() == 1 ? false : true)
                .setGone(R.id.ll_img, bean.getType() == 1 ? false : true)
                .addOnClickListener(R.id.btn_studentAnswer)
                .addOnClickListener(R.id.btn_questionExpand)
                .addOnClickListener(R.id.btn_seeAnswer)
                .addOnClickListener(R.id.btn_famousTeacherLecture);
        AwImgUtil.setImg(mContext, helper.getView(R.id.iv_img), TestDataUtil.imgUris[RandomValueUtil.getNum(0, TestDataUtil.imgUris.length - 1)]);
        TextView tv_subTitle = helper.getView(R.id.tv_subTitle);
//        AwLog.d("detail bean 本次题号 " + bean.getQuestionNum() + " 类型" + bean.getType());
//        AwLog.d("detail bean 上一题号 " + mList.get(helper.getPosition() - 1).getQuestionNum() + " 类型" + mList.get(helper.getPosition() - 1).getType());
        if(helper.getPosition() != 0 && helper.getPosition() != 1) { //添加了headerview, 注意position位置
            if(bean.getType() == mList.get(helper.getPosition() - 2).getType()) {
                tv_subTitle.setVisibility(View.GONE);
            } else {
                tv_subTitle.setVisibility(View.VISIBLE);
            }
        } else {
            tv_subTitle.setVisibility(View.VISIBLE);
        }
        String questionType = "未知题型";
        switch (bean.getType()) {
            case 1:
                questionType = "选择题";
                break;
            case 2:
                questionType = "填空题";
                break;
            case 3:
                questionType = "解答题";
                break;
            case 4:
                questionType = "作文题";
                break;
            default:
                break;
        }
        tv_subTitle.setText(questionType);
    }

    public void addAllData(List<TestQuestionBean> dataList) {
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
