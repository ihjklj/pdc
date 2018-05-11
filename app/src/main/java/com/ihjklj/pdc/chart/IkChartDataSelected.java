package com.ihjklj.pdc.chart;

import android.content.Context;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.ihjklj.pdc.util.LOG;
import com.ihjklj.pdc.util.UtilMethod;

/**
 * Created by Administrator on 2018/5/11.
 */

public class IkChartDataSelected implements OnChartValueSelectedListener {

    private Context mContext;

    public IkChartDataSelected(Context context) {
        this.mContext = context;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        LOG.e("selected data(" + e.getX() + ", " + e.getY() + "\n");
        UtilMethod.newDialog(mContext, e.getX() + ", " + e.getY());
    }

    @Override
    public void onNothingSelected() {
        LOG.e("nothing selected.");
    }
}
