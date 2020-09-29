package com.jkrm.education.adapter.exam;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hzw.baselib.util.AwImgUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.exam.ScoreAchievementBean;
import com.jkrm.education.bean.exam.ScoreQuestionBean;
import com.jkrm.education.receivers.event.MessageEvent;
import com.jkrm.education.ui.activity.exam.ViewStudentAnswerSheetActivity;
import com.jkrm.education.util.UserUtil;
import com.jkrm.education.widget.CommonDialog;
import com.jkrm.education.widget.SynScrollerLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/7 10:05
 * Description: 小题得分表adapter
 */
public class TableScoreAdapter extends RecyclerView.Adapter<TableScoreAdapter.ScrollViewHolder> {

    private final SynScrollerLayout mSynScrollerView;
    private final Map<String, List<ScoreQuestionBean>> mDataMap;
    private List<String> mList;
    private List<ScoreAchievementBean.RowsBean.QuestListBean> srqList;
    private int tag;
    private CommonDialog answerDialog,scoreDialog;


    public TableScoreAdapter(@Nullable Map<String, List<ScoreQuestionBean>> data,
                             SynScrollerLayout synScrollerView,
                             int tag) {
        mSynScrollerView = synScrollerView;
        mDataMap = data;
        this.tag = tag;
        if (mDataMap != null) {
            mList = new ArrayList<>(mDataMap.keySet());
        }
    }

    @NonNull
    @Override
    public ScrollViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = View.inflate(parent.getContext(), R.layout.item_scroll_layout, null);
        return new ScrollViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ScrollViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //列头
        mSynScrollerView.setOnScrollListener(new SynScrollerLayout.OnItemScrollView() {
            @Override
            public void OnScroll(int l, int t, int old1, int old2) {
                holder.mSynScrollerLayout.smoothScrollTo(l, 0);
            }
        });

        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mSynScrollerView.onTouchEvent(v, position, event);
                return false;
            }
        });

        if (position % 2 == 0)
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#F9FAFB"));

        if (mDataMap != null && !mDataMap.isEmpty()) {
            holder.mView.setText(mList.get(position));

            List<ScoreQuestionBean> values = mDataMap.get(mList.get(position));
            if (values != null) {
                for (int i = 0; i < values.size(); i++) {
                    if (i < 7) {
                        initText(holder.itemView.getContext(), holder.mChildRoot, values.get(i), i, position);
                    } else {
                        ScoreQuestionBean qBean = values.get(i);
                        if (qBean.getIsOption().equals("2")) { //客观题（得分，作答）
                            for (int k = 0; k < 2; k++) {
                                initText(holder.itemView.getContext(), holder.mChildRoot, qBean,mList.get(position), k);
                            }
                        } else
                            initText(holder.itemView.getContext(), holder.mChildRoot, qBean);
                    }
                }
            } else {
                Toast.makeText(holder.itemView.getContext(), "Map数据异常！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    @SuppressLint("SetTextI18n")
    private void initText(Context context, LinearLayout linearLayout, ScoreQuestionBean bean, int num, int position) {
        View inflate = View.inflate(context, R.layout.item_table_child_layout, null);
        TextView name = inflate.findViewById(R.id.item_table_child_tv);
        switch (num) {
            case 0:
                name.setText(bean.getStudCode());
                break;
            case 1:
                name.setText(bean.getStudExamCode());
                break;
            case 2:
                name.setText(bean.getClassName());
                break;
            case 3:
                name.setText(bean.getMyScore());
                name.setTextColor(context.getResources().getColor(R.color.color_0A93FC));
                name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new MessageEvent(1, "" + position, tag));
                    }
                });
                break;
            case 4:
                name.setText(bean.getObjectScore());
                break;
            case 5:
                name.setText(bean.getSubjectScore());
                break;
            case 6:
                if (bean.getExamCategory().equals("1"))
                    name.setText(bean.getGradeRank() + "/" + bean.getClassRank());
                else
                    name.setText(bean.getJointRank() + "/" + bean.getGradeRank() + "/" + bean.getClassRank());
                break;
        }
        linearLayout.addView(inflate);
    }

    @SuppressLint("SetTextI18n")
    private void initText(Context context, LinearLayout linearLayout, ScoreQuestionBean bean, String stuName,int index) {
        View inflate = View.inflate(context, R.layout.item_table_child_layout, null);
        TextView textView = inflate.findViewById(R.id.item_table_child_tv);

        switch (index) {
            case 0:
                textView.setText(bean.getScore());
                textView.setTextColor(context.getResources().getColor(R.color.color_0A93FC));
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //弹出 客观题（三行文字）的dialog
//                        Toast.makeText(context, ""+bean.getStudName(), Toast.LENGTH_SHORT).show();
                        answerDialog = new CommonDialog(context, R.layout.dialog_stu_answer_layout, 5);
                        answerDialog.setCanceledOnTouchOutside(true);

                        TextView stuTv = answerDialog.findViewById(R.id.dialog_stu_name_tv);
                        TextView myTv = answerDialog.findViewById(R.id.dialog_my_answer_tv);
                        TextView rightTv = answerDialog.findViewById(R.id.dialog_right_answer_tv);
                        stuTv.setText(stuName);
                        myTv.setText("我的答案："+bean.getStudAnswer());
                        rightTv.setText("正确答案："+bean.getNoSpanAnswer());
                        answerDialog.show();
                        Log.e("1111111111111",bean.getNoSpanAnswer());
                    }
                });
                break;
            case 1:
                textView.setText(bean.getStudAnswer());
                break;
        }
        linearLayout.addView(inflate);
    }

    private void initText(Context context, LinearLayout linearLayout, ScoreQuestionBean bean) {
        View inflate = View.inflate(context, R.layout.item_table_child_layout, null);
        TextView textView = inflate.findViewById(R.id.item_table_child_tv);
        textView.setText(bean.getScore());
        textView.setTextColor(context.getResources().getColor(R.color.color_0A93FC));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出 主观题（图片）的dialog
                scoreDialog = new CommonDialog(context, R.layout.dialog_stu_answer_object_layout, 5);
                scoreDialog.setCanceledOnTouchOutside(true);
                ImageView stuImv = scoreDialog.findViewById(R.id.dialog_answer_imv);
                AwImgUtil.setImg(context, stuImv, bean.getStudAnswer());
                scoreDialog.show();
            }
        });

        linearLayout.addView(inflate);
    }

    @SuppressLint("ResourceAsColor")
    static class ScrollViewHolder extends RecyclerView.ViewHolder {

        public final TextView mView;
        public final SynScrollerLayout mSynScrollerLayout;
        public final LinearLayout mChildRoot;

        public ScrollViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView.findViewById(R.id.item_title_tv);
            mSynScrollerLayout = itemView.findViewById(R.id.item_ssl);
            mChildRoot = itemView.findViewById(R.id.item_ll_child_root);
////            ll_view = itemView.findViewById(R.id.ll_view);
//            for (int i = 0; i < 18; i++) {
//                View inflate = View.inflate(itemView.getContext(), R.layout.item_table_child_layout, null);
//                TextView name = inflate.findViewById(R.id.item_table_child_tv);
//                name.setText("内容" + i);
//                mChildRoot.addView(inflate);
//            }

        }
    }
}
