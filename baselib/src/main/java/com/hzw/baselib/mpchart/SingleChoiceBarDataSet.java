package com.hzw.baselib.mpchart;

import android.util.Log;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.hzw.baselib.util.AwDataUtil;

import java.util.List;

/**
 * Created by hzw on 2019/5/26.
 */

public class SingleChoiceBarDataSet extends BarDataSet {

    private String answer;
    List<String> xAxisValue;

    public List<String> getxAxisValue() {
        return xAxisValue;
    }

    public void setxAxisValue(List<String> xAxisValue) {
        this.xAxisValue = xAxisValue;
    }

    public SingleChoiceBarDataSet(List<BarEntry> yVals, String label, String answer) {
        super(yVals, label);
        this.answer = answer;
    }

    @Override
    public int getColor(int index) {
        if(!AwDataUtil.isEmpty(answer)&&answer.equals(xAxisValue.get(index))){
            return mColors.get(0);
        }
        return mColors.get(1);
    }
}
