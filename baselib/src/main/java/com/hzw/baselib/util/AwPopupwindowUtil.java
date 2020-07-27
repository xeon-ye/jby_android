package com.hzw.baselib.util;

import android.app.Activity;
import android.view.View;
import android.widget.PopupWindow;

import com.hzw.baselib.widgets.AwCommonBottomListPopupWindow;
import com.hzw.baselib.widgets.AwCommonListPopupWindow;
import com.hzw.baselib.widgets.AwCommonListWithTitlePopupWindow;
import com.hzw.baselib.widgets.AwCommonTopListPopupWindow;
import com.hzw.baselib.widgets.AwCommonTopListPopupWithIconWindow;
import com.hzw.baselib.widgets.AwCommonTopListWrapPopupWindow;

import java.util.List;

/**
 * Created by hzw on 2018/11/20.
 */

public class AwPopupwindowUtil {

    public static void showCommonListWithTitlePopupWindow(Activity mActivity, String title, List<? extends Object> list, View locationView, AwCommonListWithTitlePopupWindow.OnItemClickListener onItemClickListener) {
        AwCommonListWithTitlePopupWindow commonListPopupWindow = new AwCommonListWithTitlePopupWindow(mActivity, title,null);
        commonListPopupWindow.setListData(list);
        commonListPopupWindow.setOnItemClickListener(onItemClickListener);
        commonListPopupWindow.showAsLocationBottom(locationView);
    }

    public static void showCommonListPopupWindow(Activity mActivity, List<? extends Object> list, View locationView, AwCommonListPopupWindow.OnItemClickListener onItemClickListener) {
        AwCommonListPopupWindow commonListPopupWindow = new AwCommonListPopupWindow(mActivity, null);
        commonListPopupWindow.setListData(list);
        commonListPopupWindow.setOnItemClickListener(onItemClickListener);
        commonListPopupWindow.showAsLocationBottom(locationView);
    }

    public static void showCommonListPopupWindowWithParent(Activity mActivity, List<? extends Object> list, View locationView, AwCommonListPopupWindow.OnItemClickListener onItemClickListener) {
        AwCommonListPopupWindow commonListPopupWindow = new AwCommonListPopupWindow(mActivity, null);
        commonListPopupWindow.setListData(list);
        commonListPopupWindow.setOnItemClickListener(onItemClickListener);
        commonListPopupWindow.showAsLocation(locationView);
    }

    public static void showCommonTopListPopupWindowWithParentAndDismissNoAlpha(Activity mActivity, List<? extends Object> list, View locationView, PopupWindow.OnDismissListener dismissListener, AwCommonTopListPopupWindow.OnItemClickListener onItemClickListener) {
        AwCommonTopListPopupWindow commonListPopupWindow = new AwCommonTopListPopupWindow(mActivity, dismissListener);
        commonListPopupWindow.setListData(list);
        commonListPopupWindow.setOnItemClickListener(onItemClickListener);
        commonListPopupWindow.showAsLocationNoAlpha(locationView);
    }

    public static void showCommonTopListWarpPopupWindowWithParentAndDismissNoAlpha(Activity mActivity, List<? extends Object> list, View locationView, PopupWindow.OnDismissListener dismissListener, AwCommonTopListWrapPopupWindow.OnItemClickListener onItemClickListener) {
        AwCommonTopListWrapPopupWindow commonListPopupWindow = new AwCommonTopListWrapPopupWindow(mActivity, dismissListener);
        commonListPopupWindow.setListData(list);
        commonListPopupWindow.setOnItemClickListener(onItemClickListener);
        commonListPopupWindow.showAsLocationNoAlpha(locationView);
    }

    public static void notifyDataSetChanged(AwCommonTopListWrapPopupWindow topListWrapPopupWindow){
        topListWrapPopupWindow.notifyDataSetChanged();
    }

    public static void showCommonTopListPopupWindowWithParentAndDismissNoAlphaWithIcon(Activity mActivity, List<? extends Object> list, View locationView, PopupWindow.OnDismissListener dismissListener, AwCommonTopListPopupWithIconWindow.OnItemClickListener onItemClickListener) {
        AwCommonTopListPopupWithIconWindow commonListPopupWindow = new AwCommonTopListPopupWithIconWindow(mActivity, dismissListener);
        commonListPopupWindow.setListData(list);
        commonListPopupWindow.setOnItemClickListener(onItemClickListener);
        commonListPopupWindow.showAsLocationNoAlpha(locationView);
    }
    public static void showCommonPopupWindowWithParent(Activity mActivity,List<? extends Object> list,View locationView,AwCommonBottomListPopupWindow.OnItemClickListener onItemClickListener){
        AwCommonBottomListPopupWindow commonListPopupWindow = new AwCommonBottomListPopupWindow(mActivity, null);
        commonListPopupWindow.setListData(list);
        commonListPopupWindow.setOnItemClickListener(onItemClickListener);
        commonListPopupWindow.showAsCenterLocation(locationView);
    }

    /**
     * 计算出来的位置，y方向就在anchorView的上面和下面对齐显示，x方向就是与屏幕右边对齐显示
     * 如果anchorView的位置有变化，就可以适当自己额外加入偏移来修正
     * @param anchorView  呼出window的view
     * @param contentView   window的内容布局
     * @return window显示的左上角的xOff,yOff坐标
     */
    public static int[] calculatePopWindowPos(final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        //获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        // 获取屏幕的高宽
        final int screenHeight = AwScreenUtils.getScreenHeight(anchorView.getContext());
        final int screenWidth = AwScreenUtils.getScreenWidth(anchorView.getContext());
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        final int windowHeight = contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();
        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        if (isNeedShowUp) {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] - windowHeight;
        } else {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] + anchorHeight;
        }
        return windowPos;
    }
    /**
     * 计算出来的位置，y方向就在anchorView的上面和下面对齐显示，x方向就是与View的中心点对齐
     *
     * @param anchorView  呼出window的view
     * @param contentView window的内容布局
     * @return window显示的左上角的xOff, yOff坐标
     */
    public static int[] calculatePopWindowCenterPos(final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        final int anchorWidth = anchorView.getWidth();
        final int screenHeight = anchorView.getContext().getResources().getDisplayMetrics().heightPixels;
        final int screenWidth = anchorView.getContext().getResources().getDisplayMetrics().widthPixels;
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        final int windowHeight = contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();
        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        //偏移，否则会弹出在屏幕外
        int offset = windowWidth < anchorWidth ? (windowWidth - anchorWidth) : 0;
        //实际坐标中心点为触发view的中间
        windowPos[0] = (anchorLoc[0] + anchorWidth / 2) - (windowWidth / 2) + offset;
        windowPos[1] = isNeedShowUp ? anchorLoc[1] - windowHeight : anchorLoc[1] + anchorHeight;
        return windowPos;
    }
}
