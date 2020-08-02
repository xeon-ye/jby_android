package com.jkrm.education.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.FileUtils;
import com.hzw.baselib.widgets.RoundProgressBar;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.CourseSuccessBean;
import com.jkrm.education.db.DaoVideoBean;
import com.jkrm.education.download.DownloadLimitManager;
import com.sobot.chat.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/5 15:28
 */

public class CourseInProActAdapter extends BaseQuickAdapter<DaoVideoBean, BaseViewHolder> {
    private List<DaoVideoBean> mList = new CopyOnWriteArrayList<>();
    private boolean isChose;
    private CheckBox mChoseAll;

    public CourseInProActAdapter() {
        super(R.layout.course_cache_inpro_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, DaoVideoBean item) {
        RoundProgressBar rpb = helper.getView(R.id.rpb);
        String downloadStatus = item.getDownloadStatus();
        ImageView iv_pause = helper.getView(R.id.iv_pause);
        helper.setVisible(R.id.rpb, true);
        helper.setVisible(R.id.iv_con, true);
        CheckBox checkBox = helper.getView(R.id.cb_chose);
        helper.addOnClickListener(R.id.tv_title);
        helper.addOnClickListener(R.id.iv_pause);
        helper.addOnClickListener(R.id.iv_con);
        checkBox.setChecked(item.getIsCheck());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                item.setIsCheck(b);
            }
        });
        if (DownloadLimitManager.getInstance().getWaitUrl(item.getUrl())) {
            //("等待");
            helper.setVisible(R.id.iv_down_success, false);
            helper.setVisible(R.id.iv_pause, true);
            helper.setVisible(R.id.rpb, true);
            helper.setVisible(R.id.iv_con, false);
        } else if (DaoVideoBean.DOWNLOAD_CANCEL.equals(item.getDownloadStatus())) {
            // rpb.setProgress(0);
            //holder.main_btn_down.setText("下载");
        } else if (DaoVideoBean.DOWNLOAD_PAUSE.equals(item.getDownloadStatus())) {
            // iv_pause.setImageResource(R.mipmap.down_course);
            helper.setVisible(R.id.iv_down_success, false);
            helper.setVisible(R.id.iv_pause, false);
            helper.setVisible(R.id.rpb, false);
            helper.setVisible(R.id.iv_con, true);
        } else if (DaoVideoBean.DOWNLOAD_OVER.equals(item.getDownloadStatus())) {
            // holder.main_progress.setProgress(holder.main_progress.getMax());
            helper.setVisible(R.id.iv_pause, false);
            helper.setVisible(R.id.rpb, false);
            helper.setVisible(R.id.iv_con, false);
            helper.setVisible(R.id.iv_down_success, true);
            helper.setText(R.id.tv_size, FileUtils.getPrintSize(Long.parseLong(item.getSize())));
        } else {
            if (Long.parseLong(item.getSize()) == 0) {
                // holder.main_progress.setProgress(0);
            } else {
                float d = item.getProgress() * rpb.getMax() / Long.parseLong(item.getSize());
                rpb.setProgress((int) d);
                //holder.main_btn_down.setText("下载中");
                if(item.getProgress()<=Long.parseLong(item.getSize())){
                    helper.setText(R.id.tv_size, FileUtils.getPrintSize(item.getProgress()) + "/" + FileUtils.getPrintSize(Long.parseLong(item.getSize())));
                }else{
                    helper.setText(R.id.tv_size, FileUtils.getPrintSize(Long.parseLong(item.getSize())) + "/" + FileUtils.getPrintSize(Long.parseLong(item.getSize())));
                }
                helper.setVisible(R.id.iv_down_success, false);
                helper.setVisible(R.id.iv_con, false);
                helper.setVisible(R.id.iv_pause, true);
                helper.setVisible(R.id.rpb, true);
            }
        }
        helper.setText(R.id.tv_title, item.getName());
        helper.setVisible(R.id.cb_chose, isChose);
        helper.setVisible(R.id.rl, !isChose);
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
        boolean allChose = true;
        for (CourseSuccessBean courseSuccessBean : courseSuccessBeanArrayList) {
            if (!courseSuccessBean.isCheck()) {
                allChose = false;
            }
        }
        return allChose;
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
    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
