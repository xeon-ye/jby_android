package com.hzw.baselib.widgets;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * https://blog.csdn.net/RELY_ON_YOURSELF/article/details/90750105
 * Created by hzw on 2019/9/10.
 */

public class MySwipeRefreshLayout extends SwipeRefreshLayout {

    private float mInitialDownYValue = 0;
    private int miniTouchSlop;

    public MySwipeRefreshLayout (Context context) {
        super(context);
    }

    public MySwipeRefreshLayout (Context context, AttributeSet attrs){
        super(context,attrs);
        miniTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop()*20;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isEnabled()){
            return false;
        }
        final int action = ev.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mInitialDownYValue = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float  yDiff = ev.getY() - mInitialDownYValue;
                if (yDiff < miniTouchSlop ){
                    return false;
                }
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
