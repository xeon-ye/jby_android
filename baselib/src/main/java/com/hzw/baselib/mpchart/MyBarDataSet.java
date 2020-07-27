package com.hzw.baselib.mpchart;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.hzw.baselib.util.AwLog;

import java.util.List;

/**
 * Created by hzw on 2019/5/26.
 */

public class MyBarDataSet extends BarDataSet {

    public MyBarDataSet(List<BarEntry> yVals, String label) {
        super(yVals, label);
    }

    @Override
    public int getColor(int index) {
        AwLog.d("MyBarDataSet getY: " + getEntryForIndex(index).getY() + " ,getX: " + getEntryForIndex(index).getX() + " ,data: " + getEntryForIndex(index).getData() + " ,index: " + index);
        //根据getEntryForXIndex(index).getVal()获得Y值,然后去对比,判断
        //我这1000 4000是根据自己的需求写的,可以随便设,判断条件if根据自己需求
        if(getEntryForIndex(index).getY() < 50) {
            return mColors.get(0);
        } else {
            return mColors.get(1);
        }
    }
}
