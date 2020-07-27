package com.hzw.baselib.mpchart.formatters;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.hzw.baselib.util.AwConvertUtil;

/**
 * Created by hzw on 2019/5/7.
 */

public class CommonStrFormatter implements IValueFormatter, IAxisValueFormatter {

    private int endNum;
    private String suffix;

    public CommonStrFormatter(int endNum, String suffix) {
        this.endNum = endNum;
        this.suffix = suffix;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return  AwConvertUtil.double2String(value, endNum) + suffix;
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return  AwConvertUtil.double2String(value, endNum) + suffix;
    }
}
