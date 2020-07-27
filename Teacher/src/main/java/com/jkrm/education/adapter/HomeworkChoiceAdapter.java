package com.jkrm.education.adapter;

import android.annotation.SuppressLint;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.RowsStatisticsHomeworkResultBean;
import com.jkrm.education.bean.result.TeacherClassBean;
import com.jkrm.education.util.CustomFontStyleUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 班级列表
 * Created by hzw on 2019/5/9.
 */

public class HomeworkChoiceAdapter extends BaseQuickAdapter<RowsStatisticsHomeworkResultBean, BaseViewHolder> {

    private List<RowsStatisticsHomeworkResultBean> mList = new ArrayList<>();

    public HomeworkChoiceAdapter() {
        super(R.layout.adapter_homework_choice);
    }

    @SuppressLint("ResourceType")
    @Override
    protected void convert(final BaseViewHolder helper, final RowsStatisticsHomeworkResultBean bean) {
        helper.setText(R.id.tv_name, bean.getHwName());
        helper.setTypeface(R.id.tv_name, CustomFontStyleUtil.getNewRomenType());
        if(bean.isSelect()) {
            helper.getView(R.id.iv_select).setBackground(mContext.getResources().getDrawable(R.mipmap.tongji_btn_yixuanze));
        } else {
            helper.getView(R.id.iv_select).setBackground(mContext.getResources().getDrawable(R.mipmap.tongji_btn_weixuanze));
        }
    }

    public void addAllData(List<RowsStatisticsHomeworkResultBean> dataList) {
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
