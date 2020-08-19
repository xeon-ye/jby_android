package com.jkrm.education.adapter.mark;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.HomeworkDetailResultBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/8/19 18:25
 */

public class MarkHomeWordDetailGroupAdapter extends BaseQuickAdapter<HomeworkDetailResultBean.GradQusetionBean, BaseViewHolder> {
    private List<HomeworkDetailResultBean.GradQusetionBean> mList = new ArrayList<>();

    public MarkHomeWordDetailGroupAdapter() {
        super(R.layout.adapter_homework_group_detail);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeworkDetailResultBean.GradQusetionBean item) {

    }
    public void addAllData(List<HomeworkDetailResultBean.GradQusetionBean> dataList) {
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
