package com.jkrm.education.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.jkrm.education.R;
import com.jkrm.education.bean.exam.ClassBean;

import java.util.List;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/16 11:14
 * Description:
 */
public class SelectDialog extends PopupWindow {

    private Context context;
    private int layoutId;
    private int mHeightPixels;


    public SelectDialog(Context context, int layoutId) {
        super(context);
        this.context = context;
        this.layoutId = layoutId;
        initViews();
    }

    private void initViews() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutId, null);
        setContentView(view);
        initWindow();
    }

    private void initWindow() {
        DisplayMetrics outMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getRealMetrics(outMetrics);
        setWidth(outMetrics.widthPixels);
        mHeightPixels = outMetrics.heightPixels;
        setHeight(mHeightPixels);

    }

    @Override
    public void showAsDropDown(View anchor) {
        if(Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            DisplayMetrics outMetrics = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getRealMetrics(outMetrics);
            int h = outMetrics.heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }


}
