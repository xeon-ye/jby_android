package com.jkrm.education.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

import java.util.ArrayList;

/**
 * -------- 日期 ---------- 维护人 ------------ 变更内容 --------
 * 2018/9/14    16:55	    刘泽			    新增 类
 * 2018/9/14	16:55	    刘泽			    增加yyy属性
 */
public class SynScrollerLayout extends HorizontalScrollView {
    private ItemObservable sObservable = new ItemObservable();
    private int mPosition = -1;
    private View mItemView;
    private OnItemClickListener mOnItemClickListener;
//    private int mNormalColor = getDrawingCacheBackgroundColor();
    //这里不需要选中item变色，与本身的背景冲突，先禁止
//    private int mSelectColor = Color.BLUE;

    private float mStartX;
    private float mStartY;



    public SynScrollerLayout(Context context) {
        super(context);
    }

    public SynScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SynScrollerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    public void setNormalColor(int normalColor) {
//        mNormalColor = normalColor;
//    }

//    public void setSelectColor(int selectColor) {
//        mSelectColor = selectColor;
//    }

    @Override
    protected void onScrollChanged(int l, int t, int old1, int old2) {
        super.onScrollChanged(l, t, old1, old2);
        if (sObservable != null) {
            sObservable.notifyItemView(l, t, old1, old2);
        }
    }

    /**
     * 点击监听
     *
     * @param view
     * @param position
     * @param event
     */
    public synchronized void onTouchEvent(View view, int position, MotionEvent event) {
        if (mItemView == null || position == -1) {
            mItemView = view;
            mPosition = position;
            onTouchEvent(event);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getX();
                mStartY = event.getY();
                postDelayed(mMoveAction, 200);
                break;
            case MotionEvent.ACTION_MOVE:
                actionMove(event);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                actionUp(event);
                mStartX = 0;
                mStartY = 0;
                break;
            default:
                break;
        }
        return false;
    }

    private void actionMove(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        float dy = Math.abs(y - mStartY);
        float dx = Math.abs(x - mStartX);
        if (dx > 50 || dy > 0) {
            if (mItemView != null) {
//                mItemView.setBackgroundColor(mNormalColor);
                removeCallbacks(mMoveAction);
            }
        }
    }

    private void actionUp(MotionEvent event) {
        removeCallbacks(mMoveAction);
        float x = event.getX();
        float y = event.getY();
        float dy = Math.abs(y - mStartY);
        float dx = Math.abs(x - mStartX);
        if (dx < 50 && dy < 50) {
            if (mPosition != -1 && mItemView != null) {
//                mItemView.setBackgroundColor(mSelectColor);

                postDelayed(mUpAction, 50);
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(mItemView, mPosition);
                }
            }
        } else {
//            if (mItemView != null) {
//                mItemView.setBackgroundColor(mNormalColor);
//            }
            mPosition = -1;
            mItemView = null;
        }
    }

    Runnable mUpAction = new Runnable() {
        @Override
        public void run() {
            if (mItemView != null) {
//                mItemView.setBackgroundColor(mNormalColor);
                mPosition = -1;
                mItemView = null;
                removeCallbacks(mUpAction);
            }
        }
    };

    Runnable mMoveAction = new Runnable() {
        @Override
        public void run() {
//            if (mItemView != null) {
//                mItemView.setBackgroundColor(mSelectColor);
//            }

        }
    };

    /**
     * 点击监听
     *
     * @param onItemClickListener
     */

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;

    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    /**
     * 滚动监听
     *
     * @param listener
     */
    public void setOnScrollListener(OnItemScrollView listener) {
        if (sObservable != null) {
            sObservable.setListener(listener);
        }
    }

    public interface OnItemScrollView {
        void OnScroll(int l, int t, int old1, int old2);

    }

    static class ItemObservable {

        private final ArrayList<OnItemScrollView> mItemScrollViews;

        public ItemObservable() {
            mItemScrollViews = new ArrayList<>();
        }

        private void setListener(OnItemScrollView listener) {
            mItemScrollViews.add(listener);
        }


        private void notifyItemView(int l, int t, int old1, int old2) {
            for (OnItemScrollView itemScrollView : mItemScrollViews) {
                itemScrollView.OnScroll(l, t, old1, old2);
            }
        }
    }



}
