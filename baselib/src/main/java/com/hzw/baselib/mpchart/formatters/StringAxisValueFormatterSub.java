package com.hzw.baselib.mpchart.formatters;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

/**
 * Created by eway on 2017/12/16.
 * 对字符串类型的坐标轴标记进行格式化
 */
public class StringAxisValueFormatterSub implements IAxisValueFormatter {

    //区域值
    private List<String> mStrs;
    private boolean addSpace;

    /**
     * 对字符串类型的坐标轴标记进行格式化
     * @param strs
     */
    public StringAxisValueFormatterSub(List<String> strs, boolean addSpace){
        this.mStrs =strs;
        this.addSpace = addSpace;
    }

    @Override
    public String getFormattedValue(float v, AxisBase axisBase) {
        if(v < mStrs.size() && v >= 0) {
            if(addSpace) {
                if(v % 2 == 0) {
                    return mStrs.get((int)v);
                } else {
                    return "";
                }
            } else {
                String str = mStrs.get((int) v);
                if(str.length() > 10) {
                    str.substring(0, 10);
                }
                return str + "...";
            }
        } else {
            return "";
        }
    }
}
