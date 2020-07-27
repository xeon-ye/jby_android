package com.hzw.baselib.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.R;
import com.hzw.baselib.project.student.bean.AnswerSheetAllDataResultBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzw on 2018/03/29.
 */

public class CommonListBlueAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

    private List<? extends Object> mList = new ArrayList<>();

    public CommonListBlueAdapter() {
        super(R.layout.view_item_common_blue_pupupwidow);
        if(mList == null)
            mList = new ArrayList<>();
    }

    @Override
    protected void convert(BaseViewHolder helper, final Object bean) {
        if(bean == null)
            return;
        if(bean instanceof String) {
            helper.setText(R.id.tv_name, (String) bean);
        } else if(bean instanceof AnswerSheetAllDataResultBean) {
            helper.setText(R.id.tv_name, ((AnswerSheetAllDataResultBean) bean).getHomeworkName());
        }
    }

    public void addAllData(List<? extends Object> dataList) {
        this.mList = dataList;
        this.setNewData((List<Object>) mList);
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
