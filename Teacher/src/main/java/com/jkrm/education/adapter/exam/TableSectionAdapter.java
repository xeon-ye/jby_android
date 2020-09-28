package com.jkrm.education.adapter.exam;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hzw.baselib.util.AwSpUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.exam.SectionAchievementBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.ui.activity.exam.StuInfoTableActivity;
import com.jkrm.education.widget.SynScrollerLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/7 10:05
 * Description: 成绩分段表adapter
 */
public class TableSectionAdapter extends RecyclerView.Adapter<TableSectionAdapter.ScrollViewHolder> {


    private final SynScrollerLayout mSynScrollerView;
    private final Map<String, List<String>> mDataMap;
    private List<String> mList;
    private List<String> titleList;
    private String classId, courseId, examId;
    private SectionAchievementBean sectionBean;

    public TableSectionAdapter(@Nullable Map<String, List<String>> data,
                               SynScrollerLayout synScrollerView,
                               List<String> tList,
                               SectionAchievementBean section) {
        mSynScrollerView = synScrollerView;
        mDataMap = data;
        titleList = tList;
        sectionBean = section;
        if (mDataMap != null) {
            mList = new ArrayList<>(mDataMap.keySet());
        }
        examId = AwSpUtil.getString("TableString", Extras.EXAM_ID, "");

    }

    @NonNull
    @Override
    public ScrollViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = View.inflate(parent.getContext(), R.layout.item_scroll_layout, null);
        return new ScrollViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ScrollViewHolder holder, @SuppressLint("RecyclerView") int position) {
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
            List<String> values = mDataMap.get(mList.get(position));

            classId = sectionBean.getRows().get(position).getClassId();
            courseId = sectionBean.getRows().get(position).getCourseId();
            if (values != null) {
                for (int i = 0; i < values.size(); i++) {
                    if (i >= 1)
                        initText(holder.itemView.getContext(), holder.mChildRoot, mDataMap.get(mList.get(position)).get(i), i);
                    else
                        initText(holder.itemView.getContext(), holder.mChildRoot, mDataMap.get(mList.get(position)).get(i));
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

    private void initText(Context context, LinearLayout linearLayout, String text, int index) {
        View inflate = View.inflate(context, R.layout.item_table_child_layout, null);
        TextView name = inflate.findViewById(R.id.item_table_child_tv);
        name.setText(text);
        if (index % 2 != 0) {
            name.setTextColor(context.getResources().getColor(R.color.blue));
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (text.equals("0"))
                        Toast.makeText(context, "人数为0，无法查看！", Toast.LENGTH_SHORT).show();
                    else {
                        int num = (index + 1) / 2;
//                        toClass(StuInfoTableActivity.class, false,
//                                Extras.EXAM_ID, examId,
//                            Extras.KEY_COURSE_ID,courseId,
//                            Extras.KEY_CLASS_ID,classId,
//                                Extras.KEY_EXAM_STU_PARAM,titleList.get(num - 1));
                        Intent intent = new Intent();
                        intent.putExtra(Extras.EXAM_ID, examId);
                        intent.putExtra(Extras.KEY_COURSE_ID, courseId);
                        intent.putExtra(Extras.KEY_CLASS_ID, classId);
                        intent.putExtra(Extras.KEY_EXAM_STU_PARAM, titleList.get(num - 1));
                        intent.setClass(context,StuInfoTableActivity.class);
                        context.startActivity(intent);
                    }

                }
            });
        }
        linearLayout.addView(inflate);
    }

    private void initText(Context context, LinearLayout linearLayout, String text) {
        View inflate = View.inflate(context, R.layout.item_table_child_layout, null);
        TextView name = inflate.findViewById(R.id.item_table_child_tv);
        name.setText(text);
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
        }
    }
}
