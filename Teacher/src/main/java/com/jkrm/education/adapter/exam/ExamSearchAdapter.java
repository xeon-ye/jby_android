package com.jkrm.education.adapter.exam;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwDateUtils;
import com.jkrm.education.R;
import com.jkrm.education.bean.ExamReviewBean;
import com.jkrm.education.bean.exam.ExamSearchBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/9/11 11:43
 */

public class ExamSearchAdapter extends BaseQuickAdapter<ExamSearchBean.RowsBean, BaseViewHolder> {
    private List<ExamSearchBean.RowsBean> mList = new ArrayList<>();


    public ExamSearchAdapter() {
        super(R.layout.adapter_search_item_layout);
    }

    public void addAllData(List<ExamSearchBean.RowsBean> dataList) {
        this.mList = dataList;
        this.setNewData(mList);
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

    @Override
    protected void convert(BaseViewHolder helper, ExamSearchBean.RowsBean item) {
        helper.setText(R.id.tv_course,item.getExamTypeName())
                .setText(R.id.tv_title,item.getExamName())
                .setText(R.id.tv_grade,item.getGradeName())
                .addOnClickListener(R.id.tv_step);
        //格式化时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date parse = null;
        try {
            parse = simpleDateFormat.parse(item.getCreateTime());
            helper.setText(R.id.tv_time,AwDateUtils.getyyyyMMddHHmmssWithNo(parse.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String[] split = item.getCourseName().split(",");
        if(split.length>1){
            helper.setText(R.id.tv_course_num,split.length+"科");
        }else{
            helper.setText(R.id.tv_course_num,item.getCourseName());

        }
    }
}
