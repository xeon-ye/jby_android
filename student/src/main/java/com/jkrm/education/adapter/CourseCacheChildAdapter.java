package com.jkrm.education.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwFileUtil;
import com.jkrm.education.R;
import com.jkrm.education.db.DaoCatalogueBean;
import com.jkrm.education.db.DaoMicroLessonBean;
import com.jkrm.education.db.DaoVideoBean;
import com.jkrm.education.db.util.DaoUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/8/3 15:12
 */

public class CourseCacheChildAdapter extends BaseQuickAdapter<DaoVideoBean, BaseViewHolder> {
    private List<DaoVideoBean> mList = new ArrayList<>();
    private boolean isChose;
    private CheckBox mChoseAll;

    public CourseCacheChildAdapter() {
        super(R.layout.adapter_course_cache_item_layout);
    }
    @Override
    protected void convert(BaseViewHolder helper, DaoVideoBean item) {
        ImageView iv_img = helper.getView(R.id.iv_img);
        Glide.with(mContext).load(item.getFace()).into(iv_img);
        helper.setText(R.id.tv_title,item.getName())  .setGone(R.id.cb_chose, isChose);;
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

}
