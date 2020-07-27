package com.hzw.baselib.mpchart.makerviews;

import android.content.Context;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.hzw.baselib.R;
import com.hzw.baselib.mpchart.ArrowTextView;
import com.hzw.baselib.util.AwConvertUtil;
import com.hzw.baselib.mpchart.formatters.ValueFormatter;


/**
 * Created by hzw on 2019/5/7.
 */

public class CommonStrEnd0MarkerView extends MarkerView {

    private ArrowTextView tvContent;
    private ValueFormatter xAxisValueFormatter;
    private String suffix;

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public CommonStrEnd0MarkerView(Context context, int layoutResource, ValueFormatter xAxisValueFormatter, String suffix) {
        super(context, layoutResource);
        this.xAxisValueFormatter = xAxisValueFormatter;
        this.suffix = suffix;
        tvContent = (ArrowTextView) findViewById(R.id.custom_marker_view_arrowtxt);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;

            tvContent.setText(AwConvertUtil.double2String(e.getY(), 1) + suffix);
        } else {
            tvContent.setText(AwConvertUtil.double2String(e.getY(), 1) + suffix);
        }

        super.refreshContent(e, highlight);//必须加上该句话；This sentence must be added.
    }

    private MPPointF mOffset;

    @Override
    public MPPointF getOffset() {
        if(mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
        }

        return mOffset;
    }
}
