package com.hzw.baselib.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.R;
import com.hzw.baselib.widgets.AwRecycleViewDivider;
import com.hzw.baselib.widgets.AwRecyclerViewNoBugLinearLayoutManager;
import com.hzw.baselib.widgets.AwViewCustomDivider;

/**
 * Created by hzw on 2018/7/20.
 */

public class AwRecyclerViewUtil {

    /**
     * 通用 SwipeRefreshLayout 设置
     * @param srlLayout
     * @param isEnable
     */
    public static void setSwipeRefreshLayout(SwipeRefreshLayout srlLayout, boolean isEnable) {
//        srlLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
//                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        srlLayout.setColorSchemeResources(R.color.colorPrimary);
        srlLayout.setDistanceToTriggerSync(300);
        srlLayout.setSize(SwipeRefreshLayout.DEFAULT);
        srlLayout.setEnabled(isEnable);
    }

    /**
     * 通用recyclerview设置
     * @param activity
     * @param recyclerView
     * @param mAdapter
     */
    public static void setRecyclerViewLinearlayoutHorizontal(Activity activity, RecyclerView recyclerView, BaseQuickAdapter mAdapter) {
        AwRecyclerViewNoBugLinearLayoutManager layoutManager = new AwRecyclerViewNoBugLinearLayoutManager(activity);
        layoutManager.setOrientation(AwRecyclerViewNoBugLinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    /**
     * 通用recyclerview设置
     * @param activity
     * @param recyclerView
     * @param mAdapter
     * @param showItemDecoration
     */
    public static void setRecyclerViewLinearlayout(Activity activity, RecyclerView recyclerView, BaseQuickAdapter mAdapter, boolean showItemDecoration) {
        AwRecyclerViewNoBugLinearLayoutManager layoutManager = new AwRecyclerViewNoBugLinearLayoutManager(activity);
        layoutManager.setOrientation(AwRecyclerViewNoBugLinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        if(showItemDecoration) {
            recyclerView.addItemDecoration(new AwRecycleViewDivider(
                    activity, LinearLayoutManager.HORIZONTAL,
                    AwDisplayUtil.dip2px(activity, 0.5f), activity.getResources().getColor(R.color.color_ebebeb)));
        }
        //        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        recyclerView.setAdapter(mAdapter);
    }

    /**
     * 通用recyclerview设置
     * @param activity
     * @param recyclerView
     * @param mAdapter
     * @param decorationHeightF
     * @param autoSetAdapter
     */
    public static void setRecyclerViewLinearlayoutWithDecorationHeight(Activity activity, RecyclerView recyclerView, BaseQuickAdapter mAdapter,
                                                                       float decorationHeightF, boolean autoSetAdapter) {
        AwRecyclerViewNoBugLinearLayoutManager layoutManager = new AwRecyclerViewNoBugLinearLayoutManager(activity);
        layoutManager.setOrientation(AwRecyclerViewNoBugLinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new AwRecycleViewDivider(
                activity, LinearLayoutManager.HORIZONTAL,
                AwDisplayUtil.dip2px(activity, decorationHeightF), activity.getResources().getColor(R.color.color_ebebeb)));
//        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        if(autoSetAdapter)
            recyclerView.setAdapter(mAdapter);
    }

    /**
     * 通用recyclerview设置
     * @param activity
     * @param recyclerView
     * @param mAdapter
     * @param showItemDecoration
     */
    public static void setRecyclerViewLinearlayout(Activity activity, RecyclerView recyclerView, BaseQuickAdapter mAdapter, LinearLayoutManager layoutManager, boolean showItemDecoration, boolean autoSetAdapter) {
        //        RecyclerViewNoBugLinearLayoutManager layoutManager = new RecyclerViewNoBugLinearLayoutManager(activity);
        layoutManager.setOrientation(AwRecyclerViewNoBugLinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        if(showItemDecoration) {
            recyclerView.addItemDecoration(new AwRecycleViewDivider(
                    activity, LinearLayoutManager.HORIZONTAL,
                    AwDisplayUtil.dip2px(activity, 0.5f), activity.getResources().getColor(R.color.color_ebebeb)));
        }
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        if(autoSetAdapter)
            recyclerView.setAdapter(mAdapter);
    }

    /**
     * 通用recyclerview设置
     * @param activity
     * @param recyclerView
     * @param adapter
     */
    public static void setRecyclerViewGridlayout(Activity activity, RecyclerView recyclerView, BaseQuickAdapter adapter, int spanCount) {
        GridLayoutManager manager = new GridLayoutManager(activity, spanCount);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.supportsPredictiveItemAnimations();
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration((new AwViewCustomDivider(AwDisplayUtil.dip2px(activity, 5))));
        DefaultItemAnimator animator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(animator);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 通用recyclerview设置
     * @param activity
     * @param recyclerView
     * @param adapter
     */
    public static void setRecyclerViewGridlayout(Activity activity, RecyclerView recyclerView, BaseQuickAdapter adapter, int spanCount, int dividerValue) {
        GridLayoutManager manager = new GridLayoutManager(activity, spanCount);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.supportsPredictiveItemAnimations();
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration((new AwViewCustomDivider(AwDisplayUtil.dip2px(activity, 0))));
        DefaultItemAnimator animator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(animator);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 流式布局
     * @param activity
     * @param recyclerView
     * @param adapter
     * @param spanCount
     */
    public static void setRecyclerViewFlowLayout(Activity activity,RecyclerView recyclerView,BaseQuickAdapter adapter,int spanCount){
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        recyclerView.addItemDecoration((new AwViewCustomDivider(AwDisplayUtil.dip2px(activity, spanCount))));
        recyclerView.setLayoutManager(flowLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 空数据页面展示
     * @param context
     * @return
     */
    public static View getEmptyDataViewOnlyImg(Context context, int drawableId) {
        View emptyView = LayoutInflater.from(context).inflate(R.layout.view_empty_img, null);
        ImageView iv_img = (ImageView) emptyView.findViewById(R.id.iv_img);
        if(-1 != drawableId) {
            iv_img.setImageResource(drawableId);
        }
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        return emptyView;
    }

    /**
     * 空数据页面展示
     * @param context
     * @param tag 0 未定时间 1 发起/参与  2 无信息 3 无数据
     * @return
     */
    public static View getEmptyDataView(Context context, int tag, int drawableId) {
        AwLog.d("getEmptyDataView tag: " + tag);
        View emptyView = LayoutInflater.from(context).inflate(R.layout.view_no_data, null);
        TextView tv_noData = (TextView) emptyView.findViewById(R.id.tv_noData);
        if(-1 != drawableId) {
//            setDrawableDirection(context, tv_noData,3, drawableId);
        }
        switch (tag) {
            default:
                tv_noData.setText(context.getResources().getString(R.string.common_no_data));
                break;
        }
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        return emptyView;
    }

    /**
     * 空数据页面展示
     * @param context
     * @return
     */
    public static View getEmptyDataView(Context context, String noDataTxt, int drawableId) {
        View emptyView = LayoutInflater.from(context).inflate(R.layout.view_no_data, null);
        TextView tv_noData = (TextView) emptyView.findViewById(R.id.tv_noData);
        if(-1 != drawableId) {
//            setDrawableDirection(context, tv_noData,3, drawableId);
        }
        tv_noData.setText(noDataTxt);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        return emptyView;
    }

    /**
     * 左1右2上3下4
     */
    public static void setDrawableDirection(Context context, TextView tv, int tag, int resId) {
        Drawable drawable = context.getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        switch (tag) {
            case 1:
                tv.setCompoundDrawables(drawable, null, null, null);
                break;
            case 2:
                tv.setCompoundDrawables(null, null, drawable, null);
                break;
            case 3:
                tv.setCompoundDrawables(null, drawable, null, null);
                break;
            case 4:
                tv.setCompoundDrawables(null, null, null, drawable);
                break;
        }
    }

    public static void cancelDrawableDirection(Context context, TextView tv) {
        tv.setCompoundDrawables(null, null, null, null);

    }
}
