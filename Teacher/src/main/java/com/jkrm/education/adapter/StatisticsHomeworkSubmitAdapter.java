package com.jkrm.education.adapter;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.util.MyDateUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.StatisticsHomeworkSubmitTableResultBean;
import com.jkrm.education.bean.result.StatisticsHomeworkSubmitTableResultBeanNew;
import com.jkrm.education.bean.result.TeacherTodoBean;
import com.jkrm.education.util.CustomFontStyleUtil;
import com.jkrm.education.widget.CustomProgressView;

import java.util.ArrayList;
import java.util.List;

/**
 * 统计---作业统计---提交报表
 * Created by hzw on 2019/5/9.
 */

public class StatisticsHomeworkSubmitAdapter extends BaseQuickAdapter<StatisticsHomeworkSubmitTableResultBeanNew.RowsBean, BaseViewHolder> {

    private  List<StatisticsHomeworkSubmitTableResultBeanNew.RowsBean> mList = new ArrayList<>();

    public StatisticsHomeworkSubmitAdapter() {
        super(R.layout.adapter_statistics_homework_submit);
    }


    @Override
    protected void convert(BaseViewHolder helper, StatisticsHomeworkSubmitTableResultBeanNew.RowsBean bean) {
        TextView tv_studentNames = helper.getView(R.id.tv_studentNames);
     /*   tv_studentNames.setMovementMethod(ScrollingMovementMethod.getInstance());
        tv_studentNames.setHorizontallyScrolling(true);*/
        helper.setText(R.id.tv_homeworkName, bean.getCourseName())
                .setTypeface(R.id.tv_homeworkName, CustomFontStyleUtil.getNewRomenType())
                .setText(R.id.tv_className, bean.getClassName())
                .setText(R.id.tv_submitNotCount, "未交：" + bean.getMissing())
                .setGone(R.id.tv_studentNames,!AwDataUtil.isEmpty(bean.getStudentName()))
                .setText(R.id.tv_studentNames, bean.getStudentName());
        tv_studentNames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tv_studentNames.getMaxLines()==Integer.MAX_VALUE){
                    tv_studentNames.setMaxLines(1);
                }else{
                    tv_studentNames.setMaxLines(Integer.MAX_VALUE);
                }
            }
        });
       /* if("0".equals(bean.getMissing())){
            tv_studentNames.setVisibility(View.GONE);
        }*/
    }



    public void addAllData(List<StatisticsHomeworkSubmitTableResultBeanNew.RowsBean> dataList) {
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
