package com.hzw.baselib.mpchart;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.hzw.baselib.util.AwLog;

import java.util.List;

/**
 * Created by hzw on 2019/5/26.
 */

public class NormalBarDataSet extends BarDataSet {

    public NormalBarDataSet(List<BarEntry> yVals, String label) {
        super(yVals, label);
    }

    @Override
    public int getColor(int index) {
        return mColors.get(0);
    }
}
