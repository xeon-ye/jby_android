package com.hzw.baselib.mpchart;

import android.graphics.Color;
import android.os.Handler;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.hzw.baselib.R;
import com.hzw.baselib.util.AwDataUtil;

/**
 * Created by hzw on 2019/6/25.
 */

public class ChartNoDataUtil {

    public static void setBarChartNoDataStatus(final BarChart chart, final String noDataText) {
        if(null == chart)
            return;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chart.setData(null);
                chart.setNoDataText(AwDataUtil.isEmpty(noDataText) ? chart.getContext().getResources().getString(R.string.common_no_data) : noDataText);
                chart.setNoDataTextColor(Color.GRAY);
                chart.invalidate();
            }
        },100);
    }

    public static void setLineChartNoDataStatus(final LineChart chart, final String noDataText) {
        if(null == chart)
            return;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chart.setData(null);
                chart.setNoDataText(AwDataUtil.isEmpty(noDataText) ? chart.getContext().getResources().getString(R.string.common_no_data) : noDataText);
                chart.setNoDataTextColor(Color.GRAY);
                chart.invalidate();
            }
        },100);
    }

    public static void setCombinedChartNoDataStatus(final CombinedChart chart, final String noDataText) {
        if(null == chart)
            return;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chart.setData(null);
                chart.setNoDataText(AwDataUtil.isEmpty(noDataText) ? chart.getContext().getResources().getString(R.string.common_no_data) : noDataText);
                chart.setNoDataTextColor(Color.GRAY);
                chart.invalidate();
            }
        },100);
    }
}
