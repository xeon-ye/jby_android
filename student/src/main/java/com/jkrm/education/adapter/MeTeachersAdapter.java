package com.jkrm.education.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwImgUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.TeachersResultBean;
import com.jkrm.education.bean.test.TestMeTeachersBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的教师列表
 * Created by hzw on 2019/5/9.
 */

public class MeTeachersAdapter extends BaseQuickAdapter<TeachersResultBean, BaseViewHolder> {

    private List<TeachersResultBean> mList = new ArrayList<>();

    public MeTeachersAdapter() {
        super(R.layout.adapter_me_teachers);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final TeachersResultBean bean) {
        helper.setText(R.id.tv_name, bean.getTeacherName())
                .setText(R.id.tv_mobile, AwDataUtil.isEmpty(bean.getPhone()) ? "暂无手机号" : bean.getPhone())
                .addOnClickListener(R.id.tv_mobile);
        if(!AwDataUtil.isEmpty(bean.getClassList()) && bean.getClassList().get(0) != null) {
            helper.setText(R.id.tv_class, bean.getClassList().get(0).getClassName());
            helper.setText(R.id.tv_course, bean.getClassList().get(0).getCourseName());
        } else {
            helper.setText(R.id.tv_class, "未知班级");
            helper.setText(R.id.tv_course, "未知学科");
        }
//        AwImgUtil.setImgAvatar(mContext, helper.getView(R.id.iv_avatar), "");
    }

    public void addAllData(List<TeachersResultBean> dataList) {
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
