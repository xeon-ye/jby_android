package com.jkrm.education.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwFileUtil;
import com.hzw.baselib.util.FileUtils;
import com.jkrm.education.R;
import com.jkrm.education.db.DaoCatalogueBean;
import com.jkrm.education.db.DaoMicroLessonBean;
import com.jkrm.education.db.DaoVideoBean;
import com.jkrm.education.db.util.DaoUtil;
import com.sobot.chat.camera.util.FileUtil;

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
        TextView tv_state = helper.getView(R.id.tv_state);
        TextView tv_pro = helper.getView(R.id.tv_pro);
        ProgressBar progress = helper.getView(R.id.progress);
        Glide.with(mContext).load(item.getFace()).into(iv_img);
        helper.setText(R.id.tv_title,item.getName())
                .setGone(R.id.cb_chose, isChose)
                .setGone(R.id.progress,!DaoVideoBean.DOWNLOAD_OVER.equals(item.getDownloadStatus()));
        CheckBox checkBox = helper.getView(R.id.cb_chose);
        checkBox.setChecked(item.getIsCheck());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                item.setIsCheck(b);
            }
        });
        //下载中
        if(DaoVideoBean.DOWNLOAD.equals(item.getDownloadStatus())){
            progress.setMax(Integer.parseInt(item.getSize()));
            progress.setProgress((int)item.getProgress());
            tv_pro.setText(FileUtils.getPrintSize(item.getProgress())+"/"+FileUtils.getPrintSize(Long.parseLong(item.getSize())));
        }else if(DaoVideoBean.DOWNLOAD_OVER.equals(item.getDownloadStatus())){
            tv_pro.setVisibility(View.GONE);
            tv_state.setText(FileUtils.getPrintSize(Long.parseLong(item.getSize())));
        }

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

    /**
     * 更新下载进度
     *
     * @param info
     */
    public void updateProgress(DaoVideoBean info) {
        if (DaoVideoBean.DOWNLOAD_CANCEL.equals(info.getDownloadStatus())) {
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i).getUrl().equals(info.getUrl())) {
                    mList.remove(i);
                    notifyDataSetChanged();
                }
            }
        }
        if(DaoVideoBean.DOWNLOAD_PAUSE.equals(info.getDownloadStatus())){
            notifyDataSetChanged();
        }
        for (int i = 0; i < mList.size(); i++) {
            if(mList.get(i).getId().equals(info.getId())){
                DaoVideoBean daoVideoBean = mList.get(i);
                daoVideoBean.setProgress(info.getProgress());
                daoVideoBean.setDownloadStatus(info.getDownloadStatus());
                daoVideoBean.setTotal(info.getTotal());
                notifyItemChanged(i);
                break;
            }

        }

    }

}
