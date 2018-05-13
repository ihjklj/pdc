package com.ihjklj.pdc.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.ihjklj.pdc.util.LOG;

/**
 * Created by Administrator on 2018/5/11.
 */

public class ImoocXValueFormatter implements IAxisValueFormatter {

    public ImoocXValueFormatter() {
        //
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        String showXData = null;
        if ((int)value == 0) {
            showXData = "";
        }
        else {
            showXData = (int)value + "";
        }
        LOG.d("value:" + value + ",show x data:" + showXData);
        return showXData;
    }
}
