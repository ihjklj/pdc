package com.ihjklj.pdc.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.ihjklj.pdc.R;
import com.ihjklj.pdc.model.ImoocItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private LineChart mLineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_layout);

        initView();

        run();
    }

    private void initView() {
        mLineChart = (LineChart)findViewById(R.id.chart);
    }

    private void run() {
        ImoocItem[] items = getItem();
        List<Entry> entries = new ArrayList<Entry>();
        for (ImoocItem item : items) {
            entries.add(new Entry(item.getDate(), item.getStudentNum()));
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

    private ImoocItem[] getItem() {
        ImoocItem[] items = new ImoocItem[10];
        for (int i=0; i<10; i++) {
            items[i] = new ImoocItem(new Random().nextInt(1000), i);
        }
        return items;
    }
}
