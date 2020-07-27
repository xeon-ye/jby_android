package com.jkrm.education.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.AwSpUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.BatchBean;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.bean.rx.RxOnlineJumpType;
import com.jkrm.education.bean.rx.RxQuestionBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.ui.activity.AnswerAnalysisActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 有组题
 * @Author: xiangqian
 * @CreateDate: 2020/5/13 11:21
 */

public class OnlineExerciseReportSubmitQuestionAdapter extends BaseQuickAdapter<BatchBean, BaseViewHolder> {
    private List<BatchBean> mList = new ArrayList<>();
    private List<BatchBean.SubQuestionsBean> mSubQuestionsBeanList=new ArrayList<>();
    private Activity mContext;
    private OnlineExerciseReportSubmitChildQuestionAdapter mOnlineSubmitQuestionAdapter;

    public OnlineExerciseReportSubmitQuestionAdapter(Activity context) {
        super(R.layout.adapter_submit_quetion_layout);
        mContext = context;
    }



    @Override
    protected void convert(BaseViewHolder helper, BatchBean item) {
        helper.setText(R.id.tv_title,"第"+(helper.getAdapterPosition() + 1)+"题");
        helper.setGone(R.id.rcv_data_child,item.isGroupQuestion());
        helper.setGone(R.id.tv_choice,!item.isGroupQuestion());
        helper.addOnClickListener(R.id.tv_choice);
        TextView tvChoice = helper.getView(R.id.tv_choice);
        if("1".equals(item.getQuestStatus())){
            tvChoice.setBackground(mContext.getResources().getDrawable(R.mipmap.choise_green));
            tvChoice.setTextColor(Color.WHITE);
        }else if("2".equals(item.getQuestStatus())){
            tvChoice.setBackground(mContext.getResources().getDrawable(R.mipmap.choise_yellow));
            tvChoice.setTextColor(Color.WHITE);
        }else if("3".equals(item.getQuestStatus())){
            tvChoice.setBackground(mContext.getResources().getDrawable(R.mipmap.choise_red));
            tvChoice.setTextColor(Color.WHITE);
        }else if("5".equals(item.getQuestStatus())){
            tvChoice.setBackground(mContext.getResources().getDrawable(R.mipmap.choice_gray));
            tvChoice.setTextColor(Color.WHITE);
        }else if("6".equals(item.getQuestStatus())){
            tvChoice.setBackground(mContext.getResources().getDrawable(R.mipmap.choice_normal));
            tvChoice.setTextColor(Color.BLUE);
        }
        //组题
        RecyclerView recyclerView = helper.getView(R.id.rcv_data_child);
        helper.setIsRecyclable(false);
        //子选项
        mOnlineSubmitQuestionAdapter = new OnlineExerciseReportSubmitChildQuestionAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mContext, recyclerView, mOnlineSubmitQuestionAdapter,5);
        mSubQuestionsBeanList=item.getSubQuestions();
        mOnlineSubmitQuestionAdapter.addAllData(mSubQuestionsBeanList);
        mOnlineSubmitQuestionAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(!AwDataUtil.isEmpty(mList)){
                    int layoutPosition = helper.getLayoutPosition();
                    //组题跳转
                    Intent intent = new Intent(mContext,AnswerAnalysisActivity.class);
                    intent.putExtra(Extras.EXERCISEREPORT,(Serializable)mList);
                    AwSpUtil.saveInt(Extras.KEY_INSIDEPOS,Extras.KEY_INSIDEPOS,position);
                    AwSpUtil.saveString(Extras.KEY_BATQUESTION,Extras.KEY_BATQUESTION,mList.get(helper.getLayoutPosition()).getId());
                    AwSpUtil.clearOne(Extras.OUTSIDE_POS,Extras.OUTSIDE_POS);
                    AwSpUtil.saveInt(Extras.OUTSIDE_POS,Extras.OUTSIDE_POS,helper.getLayoutPosition());
                    mContext.startActivity(intent);
                }
            }
        });
    }


    public void addAllData(List<BatchBean> dataList) {
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

/*    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByQuestionID(RxQuestionBean bean) {
        if(null!=bean){
            for (SimilarResultBean.SubQuestionsBean subQuestionsBean : mSubQuestionsBeanList) {
                if(subQuestionsBean.getId().equals(bean.getId())){
                    subQuestionsBean.setAnswer(bean.isAnswer());
                }
            }
            mOnlineSubmitQuestionAdapter.notifyDataSetChanged();
        }
    }*/
}
