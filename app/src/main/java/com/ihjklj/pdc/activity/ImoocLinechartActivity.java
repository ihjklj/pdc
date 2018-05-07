package com.ihjklj.pdc.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.ihjklj.pdc.R;
import com.ihjklj.pdc.model.LinechartItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ihjklj on 2018/5/8.
 */

public class ImoocLinechartActivity extends AppCompatActivity {

    private LineChart mLineChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linechart_layout);
    }

    private void initView() {
        mLineChart = (LineChart)findViewById(R.id.linechart_layout_linechart);
    }

    private void run() {
        LinechartItem[] items = getItem();
        List<Entry> entries = new ArrayList<Entry>();
        for (LinechartItem item : items) {
            entries.add(new Entry(item.getTime(), item.getNum()));
        }
        LineDataSet dataset = new LineDataSet(entries, "Label");
        dataset.setColor(Color.BLACK);
        dataset.setValueTextColor(Color.RED);
        LineData linedata = new LineData(dataset);
        mLineChart.setData(linedata);
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        mLineChart.invalidate();
    }

    private LinechartItem[] getItem() {
        LinechartItem[] items = new LinechartItem[10];
        for (int i=0; i<10; i++) {
            items[i] = new LinechartItem(new Random().nextInt(1000), i);
        }
        return items;
    }
}
