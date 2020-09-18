package com.jkrm.education.adapter.exam;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jkrm.education.R;
import com.jkrm.education.bean.exam.MultipleAchievementBean;
import com.jkrm.education.widget.SynScrollerLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/3 19:02
 * Description: 综合成绩表adapter
 */
public class TableMultipleAdapter extends RecyclerView.Adapter<TableMultipleAdapter.ScrollViewHolder> {

    private final SynScrollerLayout mSynScrollerView;
    private final List<MultipleAchievementBean.RowsBean> mData;
    private final List<String> subjectList;

    public TableMultipleAdapter(@Nullable List<MultipleAchievementBean.RowsBean> data, SynScrollerLayout synScrollerView, List<String> name) {
        mSynScrollerView = synScrollerView;
        mData = data;
        subjectList = name;
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
        holder.mView.setText(mData != null ? mData.get(position).getStudName() : "");
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

        if (mData != null) {
            List<String> mText = new ArrayList<>();
            mText.add(mData.get(position).getStudCode());
            mText.add(mData.get(position).getStudExamCode());
            mText.add(mData.get(position).getClassName());

            for (int i = 0; i < mData.get(position).getReaList().size(); i++) {
//               initText(holder.itemView.getContext(),holder.mChildRoot,"");
                String ss = mData.get(position).getReaList().get(i).getCourseName();
                for (int k = 0; k < subjectList.size(); k++) {
                    if (subjectList.get(k).equals(ss)) {
                        mText.add(mData.get(position).getReaList().get(i).getScore());
                        mText.add(mData.get(position).getReaList().get(i).getSchRank());
                    } else {
                        mText.add("-");
                        mText.add("-");
                    }

                }
            }

            for (int j = 0; j < mText.size(); j++) {
                initText(holder.itemView.getContext(), holder.mChildRoot, mText.get(j));
            }
            mText.clear();
        }

    }

    private void initText(Context context, LinearLayout linearLayout, String text) {
        View inflate = View.inflate(context, R.layout.item_table_child_layout, null);
        TextView name = inflate.findViewById(R.id.item_table_child_tv);
        name.setText(text);
        linearLayout.addView(inflate);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
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
