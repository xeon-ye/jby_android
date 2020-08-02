package com.jkrm.education.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.CourseAttrBean;
import com.jkrm.education.bean.result.CourseTypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/13 10:47
 */

public class CourseTypeAdapter extends  BaseQuickAdapter<CourseTypeBean, BaseViewHolder> {
    private List<CourseTypeBean> mList = new ArrayList<>();

    public CourseTypeAdapter() {
        super(R.layout.adapter_microlesson_item_layout);
    }



    @Override
    protected void convert(BaseViewHolder helper, CourseTypeBean bean) {
        TextView tv_name = helper.getView(R.id.tv_name);
        if(bean.isChecked()) {
            tv_name.setSelected(true);
            tv_name.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.aw_bg_blue_radius_13));

        } else {
            tv_name.setSelected(false);
            tv_name.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.aw_bg_gray_f4f4f4_radius_13));
        }
        helper.setText(R.id.tv_name, bean.getName())
                .addOnClickListener(R.id.tv_name);
    }
    public void addAllData(List<CourseTypeBean> dataList) {
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
