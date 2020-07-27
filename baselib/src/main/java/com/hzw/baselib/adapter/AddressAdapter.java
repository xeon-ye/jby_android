package com.hzw.baselib.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.R;
import com.hzw.baselib.bean.AddressBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/9 13:47
 */

public class AddressAdapter extends BaseQuickAdapter<AddressBean, BaseViewHolder> {
    private List<AddressBean> mList = new ArrayList<>();

    public AddressAdapter() {
        super(R.layout.view_item_common_pupupwidow);
    }
    @Override
    protected void convert(BaseViewHolder helper, AddressBean item) {
        helper.addOnClickListener(R.id.tv_name).setText(R.id.tv_name,item.getName());
    }
    public void addAllData(List<AddressBean> dataList) {
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

}
