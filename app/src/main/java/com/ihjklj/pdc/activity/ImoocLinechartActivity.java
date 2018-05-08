package com.ihjklj.pdc.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Spinner;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.ihjklj.pdc.R;
import com.ihjklj.pdc.adapter.IkSpinnerAdapter;
import com.ihjklj.pdc.model.LinechartItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ihjklj on 2018/5/8.
 */

public class ImoocLinechartActivity extends AppCompatActivity {

    private LineChart mLineChart;
    private Spinner mSpinner;
    private IkSpinnerAdapter mSpinnerAdapter;
    private List<String> mStringList;
    private String mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.linechart_layout);

        getIntentData();

        initView();

        init();

        runSpinner();

        runLineChart();
    }

    private void getIntentData() {
        if (mTitle == null) {
            Intent intent = getIntent();
            mTitle = intent.getStringExtra("title");
        }
    }

    private void runSpinner() {
        mStringList.add("近一个星期");
        mStringList.add("近一个月");
        mStringList.add("近一个季度");
        mStringList.add("近半年");
        mStringList.add("近一年");
        mSpinnerAdapter = new IkSpinnerAdapter(this, mStringList);
        mSpinner.setAdapter(mSpinnerAdapter);
    }

    private void initView() {
        mLineChart = (LineChart)findViewById(R.id.linechart_layout_linechart);
        mSpinner = (Spinner)findViewById(R.id.linechart_layout_spinner);
    }

    private void init() {
        mStringList = new ArrayList<String>();
    }

    private void runLineChart() {
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
