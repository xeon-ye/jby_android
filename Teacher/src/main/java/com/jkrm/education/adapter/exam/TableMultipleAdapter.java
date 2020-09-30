package com.jkrm.education.adapter.exam;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jkrm.education.R;
import com.jkrm.education.bean.exam.ExamCourseBean;
import com.jkrm.education.bean.exam.MultipleAchievementBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.receivers.event.MessageEvent;
import com.jkrm.education.ui.activity.exam.StudentAnalyseActivity;
import com.jkrm.education.ui.activity.exam.ViewStudentAnswerSheetActivity;
import com.jkrm.education.widget.SynScrollerLayout;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/3 19:02
 * Description: 综合成绩表adapter
 */
public class TableMultipleAdapter extends RecyclerView.Adapter<TableMultipleAdapter.ScrollViewHolder> {

    private final SynScrollerLayout mSynScrollerView;
    private final Map<String, List<String>> mDataMap;
    private List<String> mList;
    private MultipleAchievementBean achievementBean;
    private int tag;

    public TableMultipleAdapter(@Nullable Map<String, List<String>> data,
                                SynScrollerLayout synScrollerView, MultipleAchievementBean bean,int tag) {
        mSynScrollerView = synScrollerView;
        mDataMap = data;
        achievementBean = bean;
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

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ScrollViewHolder holder, int position) {
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
            holder.mView.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.color_0A93FC));
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra(Extras.EXAM_ID, achievementBean.getRows().get(position).getExamId());
                    intent.putExtra(Extras.STUDENT_ID, achievementBean.getRows().get(position).getStudId());
                    intent.setClass(holder.itemView.getContext(), StudentAnalyseActivity.class);
                    holder.itemView.getContext().startActivity(intent);
                }
            });

            List<String> values = mDataMap.get(mList.get(position));
            if (values != null) {
                for (int i = 0; i < values.size(); i++) {
                    if (i >= 5)
                        initText(holder.itemView.getContext(), holder.mChildRoot,
                                mDataMap.get(mList.get(position)).get(i), i, position);
                    else
                        initText(holder.itemView.getContext(), holder.mChildRoot, mDataMap.get(mList.get(position)).get(i));
                }
            } else {
                Toast.makeText(holder.itemView.getContext(), "Map数据异常！", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void initText(Context context, LinearLayout linearLayout, String text) {
        View inflate = View.inflate(context, R.layout.item_table_child_layout, null);
        TextView name = inflate.findViewById(R.id.item_table_child_tv);
        name.setText(text);
        linearLayout.addView(inflate);
    }

    private void initText(Context context, LinearLayout linearLayout, String text,
                          int index, int position) {
        View inflate = View.inflate(context, R.layout.item_table_child_layout, null);
        TextView name = inflate.findViewById(R.id.item_table_child_tv);
        name.setText(text);
        if (index % 2 != 0) {
            name.setTextColor(context.getResources().getColor(R.color.color_0A93FC));
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (name.getText().equals("-"))
                        Toast.makeText(context, "成绩未发布！", Toast.LENGTH_SHORT).show();
                    else {
                        EventBus.getDefault().post(new MessageEvent(0, text+"," + position,tag));
                    }
                }
            });
        }
        linearLayout.addView(inflate);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
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
//            for (int i = 0; i < 15; i++) {
//                View inflate = View.inflate(itemView.getContext(), R.layout.item_table_child_layout, null);
//                TextView name = inflate.findViewById(R.id.item_table_child_tv);
//                name.setText("内容" + i);
//                mChildRoot.addView(inflate);
//            }
        }
    }

}
