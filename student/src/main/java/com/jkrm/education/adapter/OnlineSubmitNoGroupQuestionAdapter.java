package com.jkrm.education.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.bean.rx.RxOnlineJumpType;
import com.jkrm.education.bean.rx.RxQuestionBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 无组题
 * @Author: xiangqian
 * @CreateDate: 2020/5/13 11:21
 */

public class OnlineSubmitNoGroupQuestionAdapter extends BaseQuickAdapter<SimilarResultBean, BaseViewHolder> {
    private List<SimilarResultBean> mList = new ArrayList<>();
    private List<SimilarResultBean.SubQuestionsBean> mSubQuestionsBeanList=new ArrayList<>();
    private Activity mContext;
    private OnlineSubmitChildQuestionAdapter mOnlineSubmitQuestionAdapter;
    //用来记录所有checkbox的状态
    private Map<Integer, Boolean> checkStatus = new HashMap<>();

    public OnlineSubmitNoGroupQuestionAdapter(Activity context) {
        super(R.layout.adapter_submit_nogroup_quetion_layout);
        mContext = context;
    }



    @Override
    protected void convert(BaseViewHolder helper, SimilarResultBean item) {
        if(helper.getAdapterPosition()>0){
            helper.setVisible(R.id.tv_title,false);
        }
        //helper.setText(R.id.tv_title,"所有题目");
        helper.setIsRecyclable(false);
        helper.addOnClickListener(R.id.tv_choice);
        TextView tvChoice = helper.getView(R.id.tv_choice);
        helper.setText(R.id.tv_choice,helper.getAdapterPosition()+1+"");
        tvChoice.setSelected(item.isAnswer());


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

        @Override
        public int getItemViewType(int position) {
            return position;
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
