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

public class CourseAttrSniAdapter  extends BaseQuickAdapter<CourseAttrBean.Value, BaseViewHolder> {
    private List<CourseAttrBean.Value> mList = new ArrayList<>();

    public CourseAttrSniAdapter() {
        super(R.layout.adapter_microlesson_item_layout);
    }






    @Override
    protected void convert(BaseViewHolder helper, CourseAttrBean.Value item) {
        TextView tv_name = helper.getView(R.id.tv_name);
        if(item.isChecked()) {
            tv_name.setSelected(true);
            tv_name.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.aw_bg_blue_radius_13));

        } else {
            tv_name.setSelected(false);
            tv_name.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.aw_bg_gray_f4f4f4_radius_13));
        }
        helper.setText(R.id.tv_name, item.getValueName())
                .addOnClickListener(R.id.tv_name);
    }



    public void addAllData(List<CourseAttrBean.Value> dataList) {
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
