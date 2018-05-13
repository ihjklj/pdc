package com.ihjklj.pdc.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.ihjklj.pdc.util.LOG;
import java.util.List;

/**
 * Created by ihjklj on 2018/5/11.
 */

public class ImoocXValueListFormatter implements IAxisValueFormatter {

    private List<String> mLabelList;

    public ImoocXValueListFormatter(List<String> list) {
        this.mLabelList = list;
    }

    public void setList(List<String> list) {
        this.mLabelList = list;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        String showData = null;
        if (mLabelList != null) {
            if ((int)value == 0) {
                showData = "";
            }
            else {
                showData = mLabelList.get((int)value % mLabelList.size());
            }
        }
        else {
            showData = "" + value;
        }
        LOG.d("value:" + value + ",show x data:" + showData);
        return showData;
    }
}
