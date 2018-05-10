package com.ihjklj.pdc.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import java.util.List;

/**
 * Created by ihjklj on 2018/5/11.
 */

public class ImoocXValueFormatter implements IAxisValueFormatter {

    private List<String> mLabelList;

    public ImoocXValueFormatter(List<String> list) {
        this.mLabelList = list;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mLabelList.get((int) value % mLabelList.size());
    }
}
