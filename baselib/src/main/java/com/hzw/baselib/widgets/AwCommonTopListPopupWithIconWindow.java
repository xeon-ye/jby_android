package com.hzw.baselib.widgets;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.R;
import com.hzw.baselib.adapter.CommonTopListAdapter;
import com.hzw.baselib.adapter.CommonTopListWithIconAdapter;
import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.project.student.bean.MarkBean;
import com.hzw.baselib.util.AwPopupwindowUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;

import java.util.List;

/**
 * Created by hzw on 2018/3/29.
 */
public class AwCommonTopListPopupWithIconWindow extends PopupWindow {
    private Activity mActivity;
    private OnItemClickListener mOnItemClickListener;
    private CommonTopListWithIconAdapter mAdapter;
    private List<? extends Object> mList;
    private View contentView;

    public void setListData(List<? extends Object> list) {
        this.mList = list;
        mAdapter.addAllData(mList);
    }

    public AwCommonTopListPopupWithIconWindow(final Activity mActivity, OnDismissListener dismissListener) {
        super(mActivity);
        this.mActivity = mActivity;
        contentView = View.inflate(mActivity, R.layout.view_popupwindow_common_top_list, null);
        RecyclerView rcv_data = (RecyclerView) contentView.findViewById(R.id.rcv_data);
        mAdapter = new CommonTopListWithIconAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, rcv_data, mAdapter, true);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (null != mOnItemClickListener)
                    mOnItemClickListener.onClick(mList.get(position));
                for (int i = 0; i < mList.size(); i++) {
                    MarkBean bean=((MarkBean)mList.get(i));
                    if(i==position){
                        bean.setSelect(true);
                    }else{
                        bean.setSelect(false);
                    }
                }
                mAdapter.notifyDataSetChanged();
                dismiss();
            }
        });
        this.setContentView(contentView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setAnimationStyle(R.style.sytle_anim_pop_in_out2);
        this.setFocusable(true);
        this.setTouchable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.update();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.setOverlapAnchor(true);
        }
        if(dismissListener == null) {
            this.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                    lp.alpha = AwBaseConstant.Popupwindow.POPUPWINDOW_NORMAL;
                    mActivity.getWindow().setAttributes(lp);
                }
            });
        } else {
            this.setOnDismissListener(dismissListener);
        }
    }

    /**
     * 展示窗口
     */
    public void show(View parent) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = AwBaseConstant.Popupwindow.POPUPWINDOW_DARK;
        mActivity.getWindow().setAttributes(lp);
        this.showAsDropDown(parent, 0, 0);
    }

    public void showAsLocation(View parent) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = AwBaseConstant.Popupwindow.POPUPWINDOW_DARK;
        mActivity.getWindow().setAttributes(lp);
        int windowPos[] = AwPopupwindowUtil.calculatePopWindowPos(parent, contentView);
        int xOff = 20;// 可以自己调整偏移
        windowPos[0] -= xOff;
        this.showAtLocation(parent, Gravity.TOP | Gravity.START, windowPos[0], windowPos[1]);
    }

    public void showAsLocationNoAlpha(View parent) {
        int windowPos[] = AwPopupwindowUtil.calculatePopWindowPos(parent, contentView);
        int xOff = 20;// 可以自己调整偏移
        windowPos[0] -= xOff;
        this.showAtLocation(parent, Gravity.TOP | Gravity.START, windowPos[0], windowPos[1]);
    }

    public void showAsLocationBottom(View parent) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = AwBaseConstant.Popupwindow.POPUPWINDOW_DARK;
        mActivity.getWindow().setAttributes(lp);
        this.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * calendar菜单选择监听接口
     */
    public interface OnItemClickListener {
        void onClick(Object bean);
    }

    /**
     * 设置菜单监听
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}

