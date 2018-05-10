package com.ihjklj.pdc.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Spinner;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.ihjklj.pdc.R;
import com.ihjklj.pdc.adapter.ImoocSpinnerAdapter;
import com.ihjklj.pdc.model.ImoocJson;
import com.ihjklj.pdc.model.ImoocSpinnerItem;
import com.ihjklj.pdc.model.LinechartItem;
import com.ihjklj.pdc.set.DefineSet;
import com.ihjklj.pdc.util.LOG;
import com.ihjklj.pdc.util.UtilMethod;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ihjklj on 2018/5/8.
 */

public class ImoocLinechartActivity extends AppCompatActivity {

    private LineChart mLineChart;
    private Spinner mSpinner;
    private ImoocSpinnerAdapter mSpinnerAdapter;
    private List<ImoocSpinnerItem> mSpinnerItemList;
    private ImoocJson.ImoocCourse mImoocCourse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.linechart_layout);

        getIntentData();

        init();

        initView();

        runSpinner();

        //runLineChart(getItem());
    }

    private void getIntentData() {
        if (mImoocCourse == null) {
            mImoocCourse = getIntent().getParcelableExtra("imoocCourse");
            LOG.d("title " + mImoocCourse.getTitle());
        }
    }

    private void init() {
        mSpinnerItemList = new ArrayList<ImoocSpinnerItem>();
    }

    private void initView() {
        mLineChart = (LineChart)findViewById(R.id.linechart_layout_linechart);
        mSpinner = (Spinner)findViewById(R.id.linechart_layout_spinner);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ImoocSpinnerItem spinnerItem = (ImoocSpinnerItem)mSpinner.getSelectedItem();
                LOG.d("spinner selected " + spinnerItem.getStr());
                //mSpinner.setEnabled(false);
                getLineChartData("title_" + mImoocCourse.getTitle() + "_" + spinnerItem.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //如果适配器中的list被清空且调用了notifyDataSetChanged时会调用本方法
                LOG.d("nothing selected.");
            }
        });
    }

    private void runSpinner() {
        mSpinnerItemList.add(new ImoocSpinnerItem(7, "近一个星期"));
        mSpinnerItemList.add(new ImoocSpinnerItem(30, "近一个月"));
        mSpinnerItemList.add(new ImoocSpinnerItem(90, "近一个季度"));
        mSpinnerItemList.add(new ImoocSpinnerItem(180, "近半年"));
        mSpinnerItemList.add(new ImoocSpinnerItem(360, "近一年"));
        mSpinnerAdapter = new ImoocSpinnerAdapter(this, mSpinnerItemList);
        mSpinner.setAdapter(mSpinnerAdapter);
    }

    private void getLineChartData(final String keyname) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<ImoocJson.ImoocCourse> coursesList = UtilMethod.imoocGet(DefineSet.IMOOC_KEY + keyname);
                if (coursesList != null) {
                    final List<LinechartItem> linchartitemList = new ArrayList<LinechartItem>();
                    for (ImoocJson.ImoocCourse course : coursesList) {
                        int datatime = Integer.parseInt(course.getAtime().replace("-", ""));
                        linchartitemList.add(new LinechartItem(course.getStudent(), datatime));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            runLineChart(linchartitemList);
                            //mSpinner.setEnabled(true);
                        }
                    });
                }
            }
        }).start();
    }

    private void runLineChart(List<LinechartItem> items) {
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
        Description desc = new Description();
        desc.setText("慕课网数据");
        mLineChart.setDescription(desc);
        mLineChart.invalidate();
    }

    private List<LinechartItem> getItem() {
        List<LinechartItem> linechartItemList = new ArrayList<LinechartItem>();
        for (int i=0; i<10; i++) {
            linechartItemList.add(new LinechartItem(new Random().nextInt(1000), i));
        }
        return linechartItemList;
    }
}
