package com.hzw.baselib.mpchart;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.List;

/**
 * Created by hzw on 2019/5/26.
 */

public class MyBarDataSetWithCorrectAnswer extends BarDataSet {

    private int correctAnswerIndex;

    public MyBarDataSetWithCorrectAnswer(List<BarEntry> yVals, String label, int correctAnswerIndex) {
        super(yVals, label);
        this.correctAnswerIndex = correctAnswerIndex;
    }

    @Override
    public int getColor(int index) {
        if(index == correctAnswerIndex) {
            return mColors.get(0);
        } else {
            return mColors.get(1);
        }
    }
}
