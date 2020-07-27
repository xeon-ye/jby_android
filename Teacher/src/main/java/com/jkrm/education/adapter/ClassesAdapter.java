package com.jkrm.education.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.request.RequestClassesBean;
import com.jkrm.education.bean.result.TeacherClassBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/17 17:59
 */

public class ClassesAdapter extends BaseQuickAdapter<RequestClassesBean, BaseViewHolder> {
    private List<RequestClassesBean> mList = new ArrayList<>();

    public ClassesAdapter() {
        super(R.layout.adapter_item_joined_classes_layout);
    }
    @Override
    protected void convert(BaseViewHolder helper, RequestClassesBean item) {
        helper.setText(R.id.tv_name,item.getClassName()).addOnClickListener(R.id.quit);
    }

    public void addAllData(List<RequestClassesBean> dataList) {
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
