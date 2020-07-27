package com.jkrm.education.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.FileUtils;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.CourseSuccessBean;
import com.jkrm.education.db.DaoVideoBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/5 15:28
 */

public class CourseSuccessActAdapter extends BaseQuickAdapter<DaoVideoBean, BaseViewHolder> {
    private List<DaoVideoBean> mList = new CopyOnWriteArrayList<>();
    private boolean isChose;
    private CheckBox mChoseAll;

    public CourseSuccessActAdapter() {
        super(R.layout.course_cache_success_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, DaoVideoBean item) {
        helper.setText(R.id.tv_title,item.getName());
        helper.setText(R.id.tv_size, FileUtils.getPrintSize(Long.parseLong(item.getSize())));
        helper.addOnClickListener(R.id.tv_title);
        helper.setVisible(R.id.cb_chose, isChose);
        helper.setVisible(R.id.iv_down_success,!isChose);
        CheckBox checkBox = helper.getView(R.id.cb_chose);
        checkBox.setChecked(item.getIsCheck());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                item.setIsCheck(b);
            }
        });
    }

    public void addAllData(List<DaoVideoBean> dataList) {
        this.mList = dataList;
        this.setNewData(mList);
        notifyDataSetChanged();
    }

    public boolean isChose() {
        return isChose;
    }

    public CheckBox getChoseAll() {
        return mChoseAll;
    }

    public void setChoseAll(CheckBox choseAll) {
        mChoseAll = choseAll;
    }

    public void setChose(boolean chose) {
        isChose = chose;
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

    private boolean checkIsAllChose(List<CourseSuccessBean> courseSuccessBeanArrayList) {
        boolean allChose=true;
        for (CourseSuccessBean courseSuccessBean : courseSuccessBeanArrayList) {
            if(!courseSuccessBean.isCheck()){
                allChose=false;
            }
        }
        return allChose;
    }
}
