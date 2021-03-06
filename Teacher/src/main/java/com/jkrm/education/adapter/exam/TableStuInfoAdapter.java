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
import android.widget.Toast;

import com.jkrm.education.R;
import com.jkrm.education.widget.SynScrollerLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/7 10:05
 * Description: 小题得分表adapter
 */
public class TableStuInfoAdapter extends RecyclerView.Adapter<TableStuInfoAdapter.ScrollViewHolder>{

    private final SynScrollerLayout mSynScrollerView;
    private final Map<String, List<String>> mDataMap;
    private List<String> mList;



    public TableStuInfoAdapter(@Nullable Map<String, List<String>> data, SynScrollerLayout synScrollerView) {
        mSynScrollerView = synScrollerView;
        mDataMap = data;
        if (mDataMap != null) {
            mList = new ArrayList<>(mDataMap.keySet());
        }
    }

    @NonNull
    @Override
    public ScrollViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = View.inflate(parent.getContext(), R.layout.item_stu_scroll_layout, null);
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
            List<String> values = mDataMap.get(mList.get(position));
            if (values != null) {
                for (int i = 0; i < values.size(); i++) {
                    initText(holder.itemView.getContext(), holder.mChildRoot,mDataMap.get(mList.get(position)).get(i),i);
                }
            }else {
                Toast.makeText(holder.itemView.getContext(),"Map数据异常！",Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    private void initText(Context context, LinearLayout linearLayout, String text,int num) {
        View inflate = View.inflate(context, R.layout.item_table_stu_info_child_layout, null);
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
