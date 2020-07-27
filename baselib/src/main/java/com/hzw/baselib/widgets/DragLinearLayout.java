package com.hzw.baselib.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/4/24 12:12
 */

public class DragLinearLayout extends LinearLayout {
    private int screenWidth;

    private int screenHeight;

    int lastX, lastY;
    // 滑动监听

    public TouchListener touchListener;

    public DragLinearLayout(Context context) {

        super(context);

        init();

    }

    public DragLinearLayout(Context context, AttributeSet attrs) {

        super(context, attrs);

        init();

    }

    public void setTouchListener(TouchListener touchListener) {

        this.touchListener = touchListener;

    }

    public void init() {

        DisplayMetrics dm = getResources().getDisplayMetrics();

        screenWidth = dm.widthPixels;

        screenHeight = dm.heightPixels;

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
// TODO Auto-generated method stub
        super.onLayout(changed, l, t, r, b);
    }

    /**
     *     * @param groupViewHeight 父容器高度-拖拽按钮高度
     * <p>
     *     * @param v
     * <p>
     *     * @param event
     * <p>
     *    
     */

    public void slideView(int groupViewHeight, View v, MotionEvent event) {

        int action = event.getAction();

        switch (action) {

            case MotionEvent.ACTION_DOWN:

                lastX = (int) event.getRawX();

                lastY = (int) event.getRawY();

                break;

            case MotionEvent.ACTION_MOVE:

                int dx = (int) event.getRawX() - lastX;

                int dy = (int) event.getRawY() - lastY;

                int left = DragLinearLayout.this.getLeft() + dx;

                int top = DragLinearLayout.this.getTop() + dy;

                int right = DragLinearLayout.this.getRight() + dx;

                int bottom = DragLinearLayout.this.getBottom() + dy;

                if (left < 0) {

                    left = 0;

                    right = left + DragLinearLayout.this.getWidth();

                }

                if (right > screenWidth) {

                    right = screenWidth;

                    left = right - DragLinearLayout.this.getWidth();

                }

                if (top < 0) {

                    top = 0;

                    bottom = top + DragLinearLayout.this.getHeight();

                }

                if (top > groupViewHeight) {

                    RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) getLayoutParams();

                    top = groupViewHeight;

                    rl.topMargin = top;

                    setLayoutParams(rl);

                    callBackMessage(left, top, right, bottom);

                    return;

                } else {

                    callBackMessage(left, top, right, bottom);

                }

                RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) getLayoutParams();

                rl.topMargin = top;

                setLayoutParams(rl);

                lastX = (int) event.getRawX();

                lastY = (int) event.getRawY();

                break;

        }

    }

    private void callBackMessage(int left, int top, int right, int bottom) {

        if (touchListener != null) {

            touchListener.backTouchState(left, top, right, bottom);

        }

    }

    /**
     * Touch监听接口
     */

    public interface TouchListener {
        public void backTouchState(int left, int top, int right, int bottom);
    }

}
