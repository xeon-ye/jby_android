package com.jkrm.education.adapter;

import android.app.Activity;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.Message;
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
import com.hzw.baselib.util.NetWorkSpeedUtils;
import com.jkrm.education.R;
import com.jkrm.education.db.DaoCatalogueBean;
import com.jkrm.education.db.DaoMicroLessonBean;
import com.jkrm.education.db.DaoVideoBean;
import com.jkrm.education.db.util.DaoUtil;
import com.sobot.chat.camera.util.FileUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/8/3 15:12
 */

public class CourseCacheChildAdapter extends BaseQuickAdapter<DaoVideoBean, BaseViewHolder> {
    private List<DaoVideoBean> mList = new ArrayList<>();
    private boolean isChose;
    private CheckBox mChoseAll;
    private long rxtxTotal = 0;
    private Activity mActivity;
    private DecimalFormat showFloatFormat = new DecimalFormat("0.00");
    private TextView tvAll;

    public TextView getTvAll() {
        return tvAll;
    }

    public void setTvAll(TextView tvAll) {
        this.tvAll = tvAll;
    }

    public CourseCacheChildAdapter() {
        super(R.layout.adapter_course_cache_item_layout);
    }

    public Activity getActivity() {
        return mActivity;
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, DaoVideoBean item) {
        ImageView iv_img = helper.getView(R.id.iv_img);
        TextView mTv_state = helper.getView(R.id.tv_state);
        TextView tv_pro = helper.getView(R.id.tv_pro);
        ProgressBar progress = helper.getView(R.id.progress);
        Glide.with(mContext).load(item.getFace()).into(iv_img);
        helper.setText(R.id.tv_title, item.getName())
                .setGone(R.id.cb_chose, isChose)
                .setGone(R.id.progress, !DaoVideoBean.DOWNLOAD_OVER.equals(item.getDownloadStatus()));
        CheckBox checkBox = helper.getView(R.id.cb_chose);
        checkBox.setChecked(item.getIsCheck());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                item.setIsCheck(b);
                getChoseNum();

            }
        });
        //下载中
        if (DaoVideoBean.DOWNLOAD.equals(item.getDownloadStatus())) {
            progress.setMax(Integer.parseInt(item.getSize()));
            progress.setProgress((int) item.getProgress());
            tv_pro.setText(FileUtils.getPrintSize(item.getProgress()) + "/" + FileUtils.getPrintSize(Long.parseLong(item.getSize())));
            updateViewData(mTv_state);

        } else if (DaoVideoBean.DOWNLOAD_OVER.equals(item.getDownloadStatus())) {
            tv_pro.setVisibility(View.GONE);
            mTv_state.setText(FileUtils.getPrintSize(Long.parseLong(item.getSize())));
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
        if (DaoVideoBean.DOWNLOAD_PAUSE.equals(info.getDownloadStatus())) {
            notifyDataSetChanged();
        }
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getId().equals(info.getId())) {
                DaoVideoBean daoVideoBean = mList.get(i);
                daoVideoBean.setProgress(info.getProgress());
                daoVideoBean.setDownloadStatus(info.getDownloadStatus());
                daoVideoBean.setTotal(info.getTotal());
                notifyItemChanged(i);
                break;
            }

        }

    }

    public void updateViewData(TextView textView) {
        long tempSum = TrafficStats.getTotalRxBytes()
                + TrafficStats.getTotalTxBytes();
        long rxtxLast = tempSum - rxtxTotal;
        double totalSpeed = rxtxLast * 1000 / 2000d;
        rxtxTotal = tempSum;
        //设置显示当前网速
        textView.setText(showSpeed(totalSpeed));
    }

    private String showSpeed(double speed) {

        String speedString;
        if (speed >= 1048576d) {
            speedString = showFloatFormat.format(speed / 1048576d) + "MB/s";
        } else {
            speedString = showFloatFormat.format(speed / 1024d) + "KB/s";
        }
        return speedString;
    }

    private void getChoseNum() {
        int num = 0;
        for (DaoVideoBean daoVideoBean : mList) {
            if(daoVideoBean.getIsCheck()){
                num++;
            }
        }
        tvAll.setText("全选（" + num + "）");
    }

}
