package com.jkrm.education.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.AchievementBean;

import java.util.ArrayList;
import java.util.List;

public class OutExcelPDFAdapter extends BaseQuickAdapter<AchievementBean, BaseViewHolder> {
    private List<AchievementBean> mList = new ArrayList<>();

    public OutExcelPDFAdapter() {
        super(R.layout.item_out_excel_pdf);
    }

    @Override
    protected void convert(BaseViewHolder helper, AchievementBean item) {
        if(helper.getLayoutPosition()==0){
            helper.setVisible(R.id.ll_of_title,true);
        }
        helper.setText(R.id.tv_name,item.getStudCode());
        helper.setText(R.id.tv_num,item.getStudentName());
        helper.setText(R.id.tv_fraction,item.getTotalScore());
        helper.setText(R.id.tv_class,item.getClassRank());
        helper.setText(R.id.tv_school,item.getGradeRank());

    }

    public void addAllData(List<AchievementBean> dataList) {
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
