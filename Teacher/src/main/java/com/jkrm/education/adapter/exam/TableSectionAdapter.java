package com.jkrm.education.adapter.exam;

import android.annotation.SuppressLint;
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
import com.jkrm.education.widget.SynScrollerLayout;

import java.util.List;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/7 10:05
 * Description: 成绩分段表adapter
 */
public class TableSectionAdapter extends RecyclerView.Adapter<TableSectionAdapter.ScrollViewHolder>{


    private final SynScrollerLayout mSynScrollerView;
    private final List<String> mData;

    public TableSectionAdapter(@Nullable List<String> data, SynScrollerLayout synScrollerView) {
        mSynScrollerView = synScrollerView;
        mData = data;

    }


    @NonNull
    @Override
    public ScrollViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = View.inflate(parent.getContext(), R.layout.item_scroll_layout, null);
        return new ScrollViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ScrollViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.mView.setText(mData != null ? mData.get(position) : "");
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
//            ll_view = itemView.findViewById(R.id.ll_view);
            for (int i = 0; i < 18; i++) {
                View inflate = View.inflate(itemView.getContext(), R.layout.item_table_child_layout, null);
                TextView name = inflate.findViewById(R.id.item_table_child_tv);
                name.setText("内容" + i);
                mChildRoot.addView(inflate);
            }
        }
    }
}
