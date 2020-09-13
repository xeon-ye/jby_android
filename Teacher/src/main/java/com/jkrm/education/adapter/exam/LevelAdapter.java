package com.jkrm.education.adapter.exam;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.project.student.bean.MarkBean;
import com.jkrm.education.R;
import com.jkrm.education.bean.exam.ClassBean;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/9/11 17:29
 */

public class LevelAdapter extends BaseQuickAdapter<MarkBean, BaseViewHolder> {
    List<MarkBean> mList;

    public LevelAdapter() {
        super(R.layout.adapter__item_level_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, MarkBean item) {
        helper.setText(R.id.tv_title,item.getTitle()).addOnClickListener(R.id.tv_title);
        if(item.isSelect()){
            helper.setTextColor(R.id.tv_title,mContext.getResources().getColor(R.color.colorPrimary));
            helper.setGone(R.id.iv_level_tri,true);
        }else{
            helper.setTextColor(R.id.tv_title,mContext.getResources().getColor(R.color.black));
            helper.setGone(R.id.iv_level_tri,false);
        }
       // helper.setTextColor(R.id.tv_title,item.isHandleModify()? mContext.getResources().getColor(R.color.colorPrimary): Color.BLACK);
    }
    public void addAllData(List<MarkBean> dataList) {
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
