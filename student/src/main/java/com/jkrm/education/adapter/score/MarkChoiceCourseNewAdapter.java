package com.jkrm.education.adapter.score;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.type.TypeCourseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 筛选---分类
 * Created by hzw on 2019/5/9.
 */

public class MarkChoiceCourseNewAdapter extends BaseQuickAdapter<TypeCourseBean, BaseViewHolder> {

    private List<TypeCourseBean> mList = new ArrayList<>();

    public MarkChoiceCourseNewAdapter() {
        super(R.layout.adapter_mark_choice_common_new);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final TypeCourseBean bean) {
        TextView tv_name = helper.getView(R.id.tv_name);
        if(bean.isSelect()) {
            tv_name.setSelected(true);
            tv_name.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.aw_bg_blue_c8eaff_radius10));
        } else {
            tv_name.setSelected(false);
            tv_name.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.aw_bg_gray_f4f4f4_radius10));
        }
        helper.setText(R.id.tv_name, bean.getCourseName())
                .addOnClickListener(R.id.tv_name);
    }

    public void addAllData(List<TypeCourseBean> dataList) {
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
