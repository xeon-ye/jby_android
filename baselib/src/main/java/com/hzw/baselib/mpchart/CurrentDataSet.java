package com.hzw.baselib.mpchart;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.hzw.baselib.util.AwDataUtil;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/19 16:47
 */

public class CurrentDataSet extends BarDataSet {

    double mDouCurrent;

    List<String> xAxisValue;

    public List<String> getxAxisValue() {
        return xAxisValue;
    }

    public void setxAxisValue(List<String> xAxisValue) {
        this.xAxisValue = xAxisValue;
    }

    public double getDouCurrent() {
        return mDouCurrent;
    }

    public void setDouCurrent(double douCurrent) {
        mDouCurrent = douCurrent;
    }

    public CurrentDataSet(List<BarEntry> yVals, String label) {
        super(yVals, label);

    }

    @Override
    public int getColor(int index) {
        if (!AwDataUtil.isEmpty(xAxisValue) && !AwDataUtil.isEmpty(mDouCurrent)) {
            String data = xAxisValue.get(index);
            String[] split = data.split("-");
            if (mDouCurrent <= Double.parseDouble(split[0]) && mDouCurrent >= Double.parseDouble(split[1])) {
                return mColors.get(0);
            } else {
                return mColors.get(1);
            }
        }
        return mColors.get(0);
    }
}
