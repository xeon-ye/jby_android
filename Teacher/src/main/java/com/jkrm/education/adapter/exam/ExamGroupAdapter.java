package com.jkrm.education.adapter.exam;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.ReViewTaskBean;
import com.jkrm.education.bean.test.TestMarkKindsBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.ui.activity.exam.CorrectingActivity;
import com.jkrm.education.ui.activity.exam.ReviewActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:  批阅外部列表
 * @Author: xiangqian
 * @CreateDate: 2020/8/27 16:36
 */

public class ExamGroupAdapter extends BaseQuickAdapter<ReViewTaskBean, BaseViewHolder> {
    private List<ReViewTaskBean> mList = new ArrayList<>();



    public ExamGroupAdapter() {
        super(R.layout.adapter_exam_task_item_layout);
    }

    public void addAllData(List<ReViewTaskBean> dataList) {
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

    @Override
    protected void convert(BaseViewHolder helper, ReViewTaskBean item) {
        TextView tv_type = helper.getView(R.id.tv_type);
        RecyclerView rcv_child_data = helper.getView(R.id.rcv_child_data);
        if("1".equals(item.getReadWay())){
            tv_type.setText("单评题号");
        }else if("2".equals(item.getReadWay())){
            tv_type.setText("双评题号");
        }else if("3".equals(item.getReadWay())){
            tv_type.setText("终评题号");
        }
        helper.setText(R.id.tv_done_num,item.getFinishRead()+"")
                .setText(R.id.tv_not_done,item.getTotalRead()+"");
        ExamChildAdapter examChildAdapter=new ExamChildAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout((Activity) mContext,rcv_child_data,examChildAdapter,false);
        examChildAdapter.addAllData(item.getQuesList());
        examChildAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ReViewTaskBean.Bean bean = (ReViewTaskBean.Bean) adapter.getData().get(position);
                switch (view.getId()){
                    //回评
                    case R.id.tv_review:
                        if("0".equals(bean.getReadNum())){
                            Toast.makeText(mContext,"暂无回评卷子",Toast.LENGTH_LONG).show();
                            return;
                        }
                        Intent intent = new Intent();
                        intent.setClass(mContext,ReviewActivity.class);
                        intent.putExtra(Extras.EXAM_ID,bean.getExamId());
                        intent.putExtra(Extras.PAPER_ID,bean.getPaperId());
                        intent.putExtra(Extras.READ_WAY,bean.getReadWay());
                        intent.putExtra(Extras.QUESTION_ID,bean.getQuestionId());
                        intent.putExtra(Extras.REVIEW_TASK_BEAN,(Serializable) bean);

                        mContext.startActivity(intent);
                        break;
                        //批阅
                    case R.id.tv_mark:
                        if("0".equals(bean.getToBeRead())){
                            Toast.makeText(mContext,"所有卷子已批阅完",Toast.LENGTH_LONG).show();
                            return;
                        }
                        Intent intent1 = new Intent();
                        intent1.setClass(mContext, CorrectingActivity.class);
                        intent1.putExtra(Extras.REVIEW_TASK_BEAN,(Serializable) bean);
                        intent1.putExtra(Extras.KEY_MACK_POS,position);
                        mContext.startActivity(intent1);
                        break;
                }
            }
        });

    }
}
