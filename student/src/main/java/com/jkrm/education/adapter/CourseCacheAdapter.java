package com.jkrm.education.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

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

public class CourseCacheAdapter extends BaseQuickAdapter<DaoMicroLessonBean, BaseViewHolder> {
    private List<DaoMicroLessonBean> mList = new ArrayList<>();
    private boolean isChose;
    private CheckBox mChoseAll;

    public CourseCacheAdapter() {
        super(R.layout.adapter_course_cache_item_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, DaoMicroLessonBean item) {
        helper.setText(R.id.tv_title, item.getMlessonName())
                .setGone(R.id.cb_chose, isChose);
        ImageView iv_img = helper.getView(R.id.iv_img);
        ImageView iv_state = helper.getView(R.id.iv_state);
        CheckBox checkBox = helper.getView(R.id.cb_chose);
        ProgressBar progress = helper.getView(R.id.progress);

        Glide.with(mContext).load(item.getMlessonUrl()).into(iv_img);

        checkBox.setChecked(item.getIsCheck());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                item.setIsCheck(b);
            }
        });
        List<DaoCatalogueBean> daoCatalogueBeans = DaoUtil.getInstance().queryCatalogueListByQueryBuilder(item.getId());
        for (DaoCatalogueBean daoCatalogueBean : daoCatalogueBeans) {
            List<DaoVideoBean> daoVideoBeans = DaoUtil.getInstance().queryVideoListByQueryBuilder(daoCatalogueBean.getId());
            int i = checkIsDownLoading(daoVideoBeans);
            switch (i) {
                //下载中
                case 0:
                    Glide.with(mContext).load(mContext.getResources().getDrawable(R.mipmap.load_down_white)).into(iv_state);

                    break;
                //下载完成
                case 1:
                    helper.setText(R.id.text_num, "共" + daoCatalogueBeans.size() + "个视频");
                    long size = 0;
                    for (DaoVideoBean daoVideoBean : daoVideoBeans) {
                        size += Long.parseLong(daoVideoBean.getSize());
                    }
                    helper.setGone(R.id.progress, false);
                    helper.setText(R.id.tv_state, AwFileUtil.FormetFileSize(size));
                    break;
                //下载暂停
                case 2:
                    Glide.with(mContext).load(mContext.getResources().getDrawable(R.mipmap.load_pause_white)).into(iv_state);
                    helper.setText(R.id.tv_state,"已暂停");
                    helper.setText(R.id.tv_pro  ,getDownDownLoadPro(daoVideoBeans,progress));
                    break;
            }
        }

    }

    public void addAllData(List<DaoMicroLessonBean> dataList) {
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
     * 判断当前目录下视频状态
     * @param daoVideoBeans
     * @return
     */
    private int checkIsDownLoading(List<DaoVideoBean> daoVideoBeans) {
        int is_all_com = 0;

        for (DaoVideoBean daoVideoBean : daoVideoBeans) {
            if (DaoVideoBean.DOWNLOAD_OVER.equals(daoVideoBean.getDownloadStatus())) {
                is_all_com = 1;
            }
        }
        for (DaoVideoBean daoVideoBean : daoVideoBeans) {
            if (DaoVideoBean.DOWNLOAD_PAUSE.equals(daoVideoBean.getDownloadStatus())) {
                is_all_com = 2;
            }
        }
        return is_all_com;
    }

    /**
     * 获取当前下载进度
     * @param daoVideoBeans
     * @return
     */
    public String getDownDownLoadPro(List<DaoVideoBean> daoVideoBeans, ProgressBar progressBar){
         int pro=0;
        for (DaoVideoBean daoVideoBean : daoVideoBeans) {
            if(DaoVideoBean.DOWNLOAD_OVER.equals(daoVideoBean.getDownloadStatus())){
                pro=pro++;
            }
        }
        progressBar.setMax(daoVideoBeans.size());
        progressBar.setProgress(pro);
        return "已下载"+"("+pro+"/"+daoVideoBeans.size()+")";
    }
}
