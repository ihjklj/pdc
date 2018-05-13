package com.ihjklj.pdc.chart;

import android.content.Context;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.ihjklj.pdc.model.LinechartItem;
import com.ihjklj.pdc.util.LOG;
import java.util.List;

/**
 * Created by Administrator on 2018/5/11.
 */

public class IkChartDataSelected implements OnChartValueSelectedListener {

    private Context mContext;
    private List<String> mList;

    public IkChartDataSelected(Context context, List<String> list) {
        this.mContext = context;
        this.mList = list;
    }

    public void setDataSetList(List<String> list) {
        this.mList = list;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        LOG.d("selected data(" + e.getX() + ", " + e.getY() + "\n");
        String datatime = null;
        if (mList != null && mList.size() > 0) {
            datatime = mList.get((int)e.getX() % mList.size());
        }
        else {
            datatime = (int)e.getX() + "";
        }
        LOG.d(datatime + ", " + e.getY());
        //UtilMethod.newDialog(mContext, datatime + ", " + e.getY());
    }

    @Override
    public void onNothingSelected() {
        LOG.e("nothing selected.");
    }
}
