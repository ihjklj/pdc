package com.ihjklj.pdc.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Spinner;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.ihjklj.pdc.R;
import com.ihjklj.pdc.adapter.IkSpinnerAdapter;
import com.ihjklj.pdc.model.IkSpinnerItem;
import com.ihjklj.pdc.model.LinechartItem;
import com.ihjklj.pdc.okhttp.MyOkhttp;
import com.ihjklj.pdc.util.LOG;
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
    private List<IkSpinnerItem> mSpinnerItemList;
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

        runLineChart(getItem());
    }

    private void getIntentData() {
        if (mTitle == null) {
            Intent intent = getIntent();
            mTitle = intent.getStringExtra("title");
        }
    }

    private void initView() {
        mLineChart = (LineChart)findViewById(R.id.linechart_layout_linechart);
        mSpinner = (Spinner)findViewById(R.id.linechart_layout_spinner);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                IkSpinnerItem spinnerItem = (IkSpinnerItem)mSpinner.getSelectedItem();
                LOG.e("spinner selected " + spinnerItem.getStr());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //如果适配器中的list被清空且调用了notifyDataSetChanged时会调用本方法
                LOG.e("nothing selected.");
            }
        });
    }

    private void init() {
        mSpinnerItemList = new ArrayList<IkSpinnerItem>();
    }

    private void runSpinner() {
        mSpinnerItemList.add(new IkSpinnerItem(1, "近一个星期"));
        mSpinnerItemList.add(new IkSpinnerItem(2, "近一个月"));
        mSpinnerItemList.add(new IkSpinnerItem(3, "近一个季度"));
        mSpinnerItemList.add(new IkSpinnerItem(4, "近半年"));
        mSpinnerItemList.add(new IkSpinnerItem(5, "近一年"));
        mSpinnerAdapter = new IkSpinnerAdapter(this, mSpinnerItemList);
        mSpinner.setAdapter(mSpinnerAdapter);
    }

    private void getLineChartData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = new MyOkhttp().sget("http://47.98.153.250/imooc/");
                if (data == null) {
                    LOG.d("data is empty!");
                }
                else {
                    //
                }
            }
        }).start();
    }

    private void runLineChart(LinechartItem[] items) {
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
