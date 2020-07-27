package com.hzw.baselib.mpchart.makerviews;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.List;

/**
 * Created by hzw on 2019/5/26.
 */

public class MultipleBarDataSetWithCorrectAnswer extends BarDataSet {


    public MultipleBarDataSetWithCorrectAnswer(List<BarEntry> yVals, String label) {
        super(yVals, label);
    }

    @Override
    public int getColor(int index) {
        if(index == 0) {
            return mColors.get(0);
        } else if(index==1){
            return mColors.get(1);
        }else {
            return mColors.get(2);
        }
    }
}
