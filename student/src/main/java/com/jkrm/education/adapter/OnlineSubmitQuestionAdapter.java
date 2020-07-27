package com.jkrm.education.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.PracticeDataResultBean;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.bean.rx.RxOnlineJumpType;
import com.jkrm.education.bean.rx.RxQuestionBean;
import com.sobot.chat.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 有组题
 * @Author: xiangqian
 * @CreateDate: 2020/5/13 11:21
 */

public class OnlineSubmitQuestionAdapter extends BaseQuickAdapter<SimilarResultBean, BaseViewHolder> {
    private List<SimilarResultBean> mList = new ArrayList<>();
    private List<SimilarResultBean.SubQuestionsBean> mSubQuestionsBeanList=new ArrayList<>();
    private Activity mContext;
    private OnlineSubmitChildQuestionAdapter mOnlineSubmitQuestionAdapter;

    public OnlineSubmitQuestionAdapter(Activity context) {
        super(R.layout.adapter_submit_quetion_layout);
        mContext = context;
    }



    @Override
    protected void convert(BaseViewHolder helper, SimilarResultBean item) {
        helper.setText(R.id.tv_title,"第"+(helper.getAdapterPosition() + 1)+"题");
        helper.setGone(R.id.rcv_data_child,item.isGroupQuestion());
        helper.setGone(R.id.tv_choice,!item.isGroupQuestion());
        helper.addOnClickListener(R.id.tv_choice);
        TextView tvChoice = helper.getView(R.id.tv_choice);
        tvChoice.setSelected(item.isAnswer());

        //组题
        RecyclerView recyclerView = helper.getView(R.id.rcv_data_child);
        helper.setIsRecyclable(false);
        //子选项
        mOnlineSubmitQuestionAdapter = new OnlineSubmitChildQuestionAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mContext, recyclerView, mOnlineSubmitQuestionAdapter,5);
        mSubQuestionsBeanList=item.getSubQuestions();
        mOnlineSubmitQuestionAdapter.addAllData(mSubQuestionsBeanList);
        mOnlineSubmitQuestionAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //组题跳转
                EventBus.getDefault().post(new RxOnlineJumpType(helper.getAdapterPosition(),position));
            }
        });
    }


    public void addAllData(List<SimilarResultBean> dataList) {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByQuestionID(RxQuestionBean bean) {
        if(null!=bean){
            for (SimilarResultBean.SubQuestionsBean subQuestionsBean : mSubQuestionsBeanList) {
                if(subQuestionsBean.getId().equals(bean.getId())){
                    subQuestionsBean.setAnswer(bean.isAnswer());
                }
            }
            mOnlineSubmitQuestionAdapter.notifyDataSetChanged();
        }
    }
}
