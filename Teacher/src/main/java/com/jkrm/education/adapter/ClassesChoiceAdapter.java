package com.jkrm.education.adapter;

import android.annotation.SuppressLint;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.TeacherClassBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 班级列表
 * Created by hzw on 2019/5/9.
 */

public class ClassesChoiceAdapter extends BaseQuickAdapter<TeacherClassBean, BaseViewHolder> {

    private List<TeacherClassBean> mList = new ArrayList<>();

    public ClassesChoiceAdapter() {
        super(R.layout.adapter_classes_choice);
    }

    @SuppressLint("ResourceType")
    @Override
    protected void convert(final BaseViewHolder helper, final TeacherClassBean bean) {
        helper.setText(R.id.tv_name, bean.getClassName());
        if(bean.isSelect()) {
            helper.getView(R.id.iv_select).setBackground(mContext.getResources().getDrawable(R.mipmap.tongji_btn_yixuanze));
        } else {
            helper.getView(R.id.iv_select).setBackground(mContext.getResources().getDrawable(R.mipmap.tongji_btn_weixuanze));
        }
    }

    public void addAllData(List<TeacherClassBean> dataList) {
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
