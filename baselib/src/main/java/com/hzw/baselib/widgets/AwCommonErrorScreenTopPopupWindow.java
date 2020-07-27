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
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.R;
import com.hzw.baselib.adapter.CommonListAdapter;
import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.util.AwPopupwindowUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;

import java.util.List;

/**
 * Created by hzw on 2018/3/29.
 */
public class AwCommonErrorScreenTopPopupWindow extends PopupWindow {
    private Activity mActivity;
    private OnItemClickListener mOnItemClickListener;
    private CommonListAdapter mAdapter;
    private List<? extends Object> mList;
    private View contentView;

    public void setListData(List<? extends Object> list) {
        this.mList = list;
        mAdapter.addAllData(mList);
    }

    public AwCommonErrorScreenTopPopupWindow(final Activity mActivity, OnDismissListener dismissListener) {
        super(mActivity);
        this.mActivity = mActivity;
        contentView = View.inflate(mActivity, R.layout.error_screen_top_layout, null);
       /* RecyclerView rcv_data = (RecyclerView) contentView.findViewById(R.id.rcv_data);
        mAdapter = new CommonListAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, rcv_data, mAdapter, true);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (null != mOnItemClickListener)
                    mOnItemClickListener.onClick(mList.get(position));
                dismiss();
            }
        });*/

        BidirectionalSeekBar bidirectionalSeekBar = contentView.findViewById(R.id.bid_seekbar);
        final TextView tvReset = contentView.findViewById(R.id.tv_reset);
        final TextView tvPro = contentView.findViewById(R.id.tv_pro);
        bidirectionalSeekBar.setOnSeekBarChangeListener(new BidirectionalSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(int leftProgress, int rightProgress) {
                tvPro.setText(leftProgress+"-"+rightProgress+"%");
                if(leftProgress!=0||rightProgress!=100){
                    tvReset.setEnabled(true);
                }
            }
        });

        this.setContentView(contentView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setAnimationStyle(R.style.sytle_anim_pop_in_out);
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

    public void showAsCenterLocation(View parent){
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = AwBaseConstant.Popupwindow.POPUPWINDOW_DARK;
        mActivity.getWindow().setAttributes(lp);
        int windowPos[] = AwPopupwindowUtil.calculatePopWindowCenterPos(parent, contentView);
        int xOff = 0;// 可以自己调整偏移
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

    public void setWrapWigth(){
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
    }



    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        mActivity.getWindow().setAttributes(lp);
    }
}

