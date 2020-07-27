package com.jkrm.education.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.TeacherClassBean;
import com.jkrm.education.bean.test.TestMeClassesBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的班级列表
 * Created by hzw on 2019/5/9.
 */

public class MeClassesAdapter extends BaseQuickAdapter<TeacherClassBean, BaseViewHolder> {

    private List<TeacherClassBean> mList = new ArrayList<>();

    public MeClassesAdapter() {
        super(R.layout.adapter_me_classes);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final TeacherClassBean bean) {
        helper.setText(R.id.tv_classes, bean.getClassName())
                .setText(R.id.tv_course, bean.getCourseName())
                .setText(R.id.tv_num, helper.getLayoutPosition() + 1 + "")
                .addOnClickListener(R.id.tv_contact);
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
