package com.jkrm.education.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwImgUtil;
import com.hzw.baselib.util.MyDateUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.VideoResultBean.CataVideosBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作业详情 --- 名师讲卷相关
 * Created by hzw on 2019/5/9.
 */

public class MarkHomeworkFamousTeacherAdapter extends BaseQuickAdapter<CataVideosBean, BaseViewHolder> {

    private List<CataVideosBean> mList = new ArrayList<>();

    public MarkHomeworkFamousTeacherAdapter() {
        super(R.layout.adapter_famous_teacher_lecture);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CataVideosBean bean) {
        helper.setText(R.id.tv_title, MyDateUtil.getFileName(bean.getFilename()))
                .setText(R.id.tv_desc, "暂无简介");
//        AwImgUtil.setImg(mContext, helper.getView(R.id.iv_img), "");
    }

    public void addAllData(List<CataVideosBean> dataList) {
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
