package com.jkrm.education.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.ClassStudentBean;
import com.jkrm.education.bean.test.TestMeClassesContactBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的班级列表
 * Created by hzw on 2019/5/9.
 */

public class MeContactListAdapter extends BaseQuickAdapter<ClassStudentBean, BaseViewHolder> {

    private List<ClassStudentBean> mList = new ArrayList<>();

    public MeContactListAdapter() {
        super(R.layout.adapter_me_contact_list);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ClassStudentBean bean) {
        helper.setText(R.id.tv_name, bean.getStudentName())
                .setText(R.id.tv_studentId, bean.getStudCode())
                .setText(R.id.tv_mobile, bean.getPhone())
                .addOnClickListener(R.id.tv_mobile);
    }

    public void addAllData(List<ClassStudentBean> dataList) {
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
