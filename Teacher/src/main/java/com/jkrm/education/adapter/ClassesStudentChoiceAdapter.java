package com.jkrm.education.adapter;

import android.annotation.SuppressLint;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.ClassStudentBean;
import com.jkrm.education.bean.result.TeacherClassBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 学生列表
 * Created by hzw on 2019/5/9.
 */

public class ClassesStudentChoiceAdapter extends BaseQuickAdapter<ClassStudentBean, BaseViewHolder> {

    private List<ClassStudentBean> mList = new ArrayList<>();

    public ClassesStudentChoiceAdapter() {
        super(R.layout.adapter_classes_student_choice);
    }

    @SuppressLint("ResourceType")
    @Override
    protected void convert(final BaseViewHolder helper, final ClassStudentBean bean) {
        helper.setText(R.id.tv_name, bean.getStudentName())
            .setText(R.id.tv_studentId, bean.getStudCode());
        if(bean.isSelect()) {
            helper.getView(R.id.iv_select).setBackground(mContext.getResources().getDrawable(R.mipmap.tongji_btn_yixuanze));
        } else {
            helper.getView(R.id.iv_select).setBackground(mContext.getResources().getDrawable(R.mipmap.tongji_btn_weixuanze));
        }
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
