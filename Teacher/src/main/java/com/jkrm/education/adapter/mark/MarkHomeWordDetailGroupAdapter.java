package com.jkrm.education.adapter.mark;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.HomeworkDetailResultBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/8/19 18:25
 */

public class MarkHomeWordDetailGroupAdapter extends BaseQuickAdapter<List<HomeworkDetailResultBean.GradQusetionBean>, BaseViewHolder> {
    private List<List<HomeworkDetailResultBean.GradQusetionBean>> mList = new ArrayList<>();

    public MarkHomeWordDetailGroupAdapter() {
        super(R.layout.adapter_homework_group_detail);
    }


    public void addAllData(List<List<HomeworkDetailResultBean.GradQusetionBean>> dataList) {
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
    protected void convert(BaseViewHolder helper, List<HomeworkDetailResultBean.GradQusetionBean> item) {
        helper.setText(R.id.tv_subTitle, AwDataUtil.isEmpty(item.get(0).getTitle())?"答题情况":item.get(0).getTitle());
        RecyclerView rcv_child_data = helper.getView(R.id.rcv_child_data);
        MarkHomeWordDetailChildAdapter markHomeWordDetailChildAdapter=new MarkHomeWordDetailChildAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout((Activity) mContext,rcv_child_data,markHomeWordDetailChildAdapter,false);
        markHomeWordDetailChildAdapter.addAllData(item);
    }
}
