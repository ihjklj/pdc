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
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.ihjklj.pdc.R;
import com.ihjklj.pdc.adapter.ImoocSpinnerAdapter;
import com.ihjklj.pdc.chart.ImoocXValueFormatter;
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

        setChartXAxis(items);

        setChartYAxis();

        //设置描述文本显示
        //Description desc = new Description();
        //desc.setText("慕课网数据");
        //mLineChart.setDescription(desc);

        //设置描述文本不显示
        mLineChart.getDescription().setEnabled(false);

        //设置是否显示表格背景
        mLineChart.setDrawGridBackground(true);

        //设置是否可以缩放
        mLineChart.setScaleEnabled(false);

        //
        mLineChart.invalidate();
    }

    private void setChartXAxis(List<LinechartItem> list) {
        //获取X轴对象
        XAxis xAxis = mLineChart.getXAxis();

        //设置X轴底部显示
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //自定义设置横坐标,设置自定义X轴值
        List<String> labelList = new ArrayList<String>();
        for (LinechartItem item : list) {
            labelList.add(item.getTime() + "日");
        }
        xAxis.setValueFormatter(new ImoocXValueFormatter(labelList));

        //一个界面显示6个Lable，那么这里要设置11个
        xAxis.setLabelCount(6);

        //设置X轴的宽度
        xAxis.setAxisLineWidth(1f);

        //设置字体大小10sp
        xAxis.setTextSize(10f);

        //绘制网格线，默认为true
        xAxis.setDrawGridLines(true);

        //设置该轴的网格线颜色。
        xAxis.setGridColor(Color.BLUE);

        //设置该轴网格线的宽度。
        xAxis.setGridLineWidth(1f);

        //设置轴线的轴的颜色
        xAxis.setAxisLineColor(Color.BLACK);

        //设置该轴轴行的宽度
        xAxis.setAxisLineWidth(2f);

        // 设置x轴的LimitLine
        LimitLine yLimitLine = new LimitLine(58f,"yLimit 测试");
        yLimitLine.setLineColor(Color.RED);
        yLimitLine.setTextColor(Color.RED);
        xAxis.addLimitLine(yLimitLine);
    }

    private void setChartYAxis() {
        //获取Y轴对象
        YAxis leftAxis = mLineChart.getAxisLeft();

        //是否隐藏右轴，false隐藏，true不隐藏，不设置默认显示
        mLineChart.getAxisRight().setEnabled(true);

        //设置Y轴最大最小值，不设置chart会自己计算
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(70f);

        //绘制网格线 默认为true
        leftAxis.setDrawGridLines(true);

        // 设置x轴的LimitLine，index是从0开始的
        LimitLine xLimitLine = new LimitLine(4f,"xL 测试");
        xLimitLine.setLineColor(Color.GREEN);
        xLimitLine.setTextColor(Color.GREEN);
        leftAxis.addLimitLine(xLimitLine);
    }

    private List<LinechartItem> getItem() {
        List<LinechartItem> linechartItemList = new ArrayList<LinechartItem>();
        for (int i=0; i<10; i++) {
            linechartItemList.add(new LinechartItem(new Random().nextInt(1000), i));
        }
        return linechartItemList;
    }
}
