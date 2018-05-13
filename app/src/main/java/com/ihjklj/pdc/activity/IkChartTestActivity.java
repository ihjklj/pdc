package com.ihjklj.pdc.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.ihjklj.pdc.R;
import com.ihjklj.pdc.adapter.IkSpinnerAdapter;
import com.ihjklj.pdc.application.MyApplication;
import com.ihjklj.pdc.chart.IkLineChart;
import com.ihjklj.pdc.model.IkSpinnerItem;
import com.ihjklj.pdc.model.ImoocJson;
import com.ihjklj.pdc.model.LinechartItem;
import com.ihjklj.pdc.set.ImoocConstSet;
import com.ihjklj.pdc.util.LOG;
import com.ihjklj.pdc.util.UtilMethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ihjklj on 2018/5/14.
 */

public class IkChartTestActivity extends AppCompatActivity {

    private IkLineChart mIkLineChart;
    private LineChart mLineChart;
    private List<IkSpinnerItem> mSpinnerItemList;
    private IkSpinnerAdapter mSpinnerAdapter;
    private Spinner mSpinner;
    private ImoocJson.ImoocCourse mImoocCourse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ik_chart_layout);

        init();

        initView();

        runSpinner();
    }

    private void getIntentData() {
        if (mImoocCourse == null) {
            mImoocCourse = getIntent().getParcelableExtra("imoocCourse");
            LOG.d("title " + mImoocCourse.getTitle());
        }
    }

    private void init() {
        mSpinnerItemList = new ArrayList<IkSpinnerItem>();
    }

    private void initView() {
        mSpinner = (Spinner)findViewById(R.id.ik_chart_layout_spinner);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                IkSpinnerItem spinnerItem = (IkSpinnerItem)mSpinner.getSelectedItem();
                LOG.d("spinner selected " + spinnerItem.getStr());
                //mSpinner.setEnabled(false);
                getData("title_" + mImoocCourse.getTitle() + "_" + spinnerItem.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //如果适配器中的list被清空且调用了notifyDataSetChanged时会调用本方法
                LOG.d("nothing selected.");
            }
        });
        mLineChart = (LineChart)findViewById(R.id.ik_chart_layout_linechart);
        mIkLineChart = new IkLineChart(this, mLineChart, "ikTest");
    }

    private void runSpinner() {
        if (MyApplication.IS_USE_CN) {
            mSpinnerItemList.add(new IkSpinnerItem(7, "近一个星期"));
            mSpinnerItemList.add(new IkSpinnerItem(30, "近一个月"));
            mSpinnerItemList.add(new IkSpinnerItem(90, "近一个季度"));
            mSpinnerItemList.add(new IkSpinnerItem(180, "近半年"));
            mSpinnerItemList.add(new IkSpinnerItem(360, "近一年"));
        }
        else {
            mSpinnerItemList.add(new IkSpinnerItem(7, "week"));
            mSpinnerItemList.add(new IkSpinnerItem(30, "month"));
            mSpinnerItemList.add(new IkSpinnerItem(90, "quarter"));
            mSpinnerItemList.add(new IkSpinnerItem(180, "half a year"));
            mSpinnerItemList.add(new IkSpinnerItem(360, "year"));
        }
        mSpinnerAdapter = new IkSpinnerAdapter(this, mSpinnerItemList);
        mSpinner.setAdapter(mSpinnerAdapter);
    }

    private void getData(final String keyname) {
        //mIkLineChart.run();
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<ImoocJson.ImoocCourse> coursesList = UtilMethod.imoocGet(ImoocConstSet.IMOOC_KEY + keyname);
                if (coursesList != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //sort(linchartitemList);
                        }
                    });
                }
            }
        }).start();
    }

    private List<Entry> getEntry(List<LinechartItem> items) {
        List<Entry> entries = new ArrayList<Entry>();
        int i = 0;
        for (LinechartItem item : items) {
            //entries.add(new Entry(item.getTime(), item.getNum()));
            LOG.d("time:" + item.getTime() + ",num:" + item.getNum());
            entries.add(new Entry(i++, item.getNum()));
        }
        return entries;
    }

    private void sort(List<LinechartItem> items) {
        Collections.sort(items, new Comparator<LinechartItem>() {
            @Override
            public int compare(LinechartItem objs, LinechartItem objt) {
                return objs.getTime() - objt.getTime();
            }
        });
    }
}
