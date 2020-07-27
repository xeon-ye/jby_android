package com.jkrm.education.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.CourseSuccessBean;
import com.jkrm.education.bean.result.MicroLessonResultBean;
import com.jkrm.education.db.DaoMicroLessonBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/5 15:28
 */

public class CourseSuccessAdapter extends BaseQuickAdapter<DaoMicroLessonBean, BaseViewHolder> {
    private List<DaoMicroLessonBean> mList = new ArrayList<>();
    private boolean isChose;
    private CheckBox mChoseAll;

    public CourseSuccessAdapter() {
        super(R.layout.course_cache_item_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, DaoMicroLessonBean item) {
        helper.addOnClickListener(R.id.iv_img)
                .addOnClickListener(R.id.ll_of_info)
                .addOnClickListener(R.id.cb_chose)
                .addOnClickListener(R.id.iv_img_center)
                .setVisible(R.id.iv_img_center, false);
        ImageView img = helper.getView(R.id.iv_img);
        Glide.with(mContext).load(item.getMlessonUrl()).into(img);
        helper.setText(R.id.tv_name, item.getMlessonName());
        helper.setText(R.id.tv_size, item.getCacheSize());
        helper.setText(R.id.tv_num, item.getCacheNum());
        ImageView iv_img_center = helper.getView(R.id.iv_img_center);


        // helper.getView(R.id.cb_chose).setSelected(item.isCheck());
        CheckBox checkBox = helper.getView(R.id.cb_chose);
        checkBox.setChecked(item.getIsCheck());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //checkBox.setChecked(b);
                item.setIsCheck(b);
                if (null != mChoseAll) {
                    /*boolean b1 = checkIsAllChose(mList);
                    if(checkIsAllChose(mList)){
                        mChoseAll.setChecked(true);
                    } else{
                        mChoseAll.setChecked(false);
                    }
*/
                }
            }
        });
        helper.setVisible(R.id.cb_chose, isChose);
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

    private boolean checkIsAllChose(List<CourseSuccessBean> courseSuccessBeanArrayList) {
        boolean allChose = true;
        for (CourseSuccessBean courseSuccessBean : courseSuccessBeanArrayList) {
            if (!courseSuccessBean.isCheck()) {
                allChose = false;
            }
        }
        return allChose;
    }
}
