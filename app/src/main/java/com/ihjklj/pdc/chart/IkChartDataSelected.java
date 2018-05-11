package com.ihjklj.pdc.chart;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.ihjklj.pdc.util.LOG;

/**
 * Created by Administrator on 2018/5/11.
 */

public class IkChartDataSelected implements OnChartValueSelectedListener {

    public IkChartDataSelected() {
        //
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        LOG.e("selected data(" + e.getX() + ", " + e.getY() + "\n");
    }

    @Override
    public void onNothingSelected() {
        LOG.e("nothing selected.");
    }
}
