package com.hzw.baselib.mpchart.formatters;

import java.util.List;

/**
 * Created by hzw on 2019/5/7.
 */

public class StringValueFormatter extends ValueFormatter {

    //区域值
    private List<String> mStrs;

    public StringValueFormatter(List<String> strs){
        this.mStrs =strs;
    }

    @Override
    public String getFormattedValue(float value) {
        return mStrs.get((int)value);
    }
}
