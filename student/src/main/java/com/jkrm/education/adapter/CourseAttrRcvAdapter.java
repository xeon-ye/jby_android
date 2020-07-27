package com.jkrm.education.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.BookExercisesBean;
import com.jkrm.education.bean.result.CourseAttrBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/13 17:19
 */

public class CourseAttrRcvAdapter extends BaseQuickAdapter<CourseAttrBean.Value, BaseViewHolder> {
    private List<CourseAttrBean.Value> mList = new ArrayList<>();

    public CourseAttrRcvAdapter() {
        super(R.layout.adapter_mark_choice_common);
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseAttrBean.Value bean) {
        TextView tv_name = helper.getView(R.id.tv_name);
        if(bean.isChecked()) {
            tv_name.setSelected(true);
            tv_name.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.aw_bg_blue_c8eaff_radius4));
        } else {
            tv_name.setSelected(false);
            tv_name.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.aw_bg_gray_f4f4f4_radius4));
        }
        helper.setText(R.id.tv_name, bean.getValueName())
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
