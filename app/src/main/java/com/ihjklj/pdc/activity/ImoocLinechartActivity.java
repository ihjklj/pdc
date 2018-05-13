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
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.ihjklj.pdc.R;
import com.ihjklj.pdc.adapter.IkSpinnerAdapter;
import com.ihjklj.pdc.application.MyApplication;
import com.ihjklj.pdc.chart.IkChartDataSelected;
import com.ihjklj.pdc.chart.IkChartGesture;
import com.ihjklj.pdc.chart.IkValueFormatter;
import com.ihjklj.pdc.chart.IkXValueListFormatter;
import com.ihjklj.pdc.model.ImoocJson;
import com.ihjklj.pdc.model.IkSpinnerItem;
import com.ihjklj.pdc.model.LinechartItem;
import com.ihjklj.pdc.set.ImoocConstSet;
import com.ihjklj.pdc.util.LOG;
import com.ihjklj.pdc.util.UtilMethod;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private ImoocJson.ImoocCourse mImoocCourse;
    private boolean mIsChartExists;
    private IkXValueListFormatter mListFormatter;

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
        mSpinnerItemList = new ArrayList<IkSpinnerItem>();
        mIsChartExists = false;
    }

    private void initView() {
        mLineChart = (LineChart)findViewById(R.id.linechart_layout_linechart);
        mSpinner = (Spinner)findViewById(R.id.linechart_layout_spinner);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                IkSpinnerItem spinnerItem = (IkSpinnerItem)mSpinner.getSelectedItem();
                LOG.d("spinner selected " + spinnerItem.getStr());
                //mSpinner.setEnabled(false);
                getLineChartData("title_" + mImoocCourse.getTitle() + "_" + spinnerItem.getId(), mIsChartExists);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //如果适配器中的list被清空且调用了notifyDataSetChanged时会调用本方法
                LOG.d("nothing selected.");
            }
        });
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

    private void getLineChartData(final String keyname, final boolean isChartExists) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<ImoocJson.ImoocCourse> coursesList = UtilMethod.imoocGet(ImoocConstSet.IMOOC_KEY + keyname);
                if (coursesList != null) {
                    final List<LinechartItem> linchartitemList = new ArrayList<LinechartItem>();
                    for (ImoocJson.ImoocCourse course : coursesList) {
                        int datatime = Integer.parseInt(course.getAtime().replace("-", ""));
                        LOG.d("add LinechartItem item(" + (float)course.getStudent() + "," + (float)datatime + ")");
                        linchartitemList.add(new LinechartItem(course.getStudent(), datatime));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            sort(linchartitemList);
                            if (isChartExists) {
                                refreshChartData(linchartitemList);
                            }
                            else {
                                runLineChart(linchartitemList);
                            }
                            //mSpinner.setEnabled(true);
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

    private LineData getData(List<LinechartItem> items) {
        LineDataSet dataset = new LineDataSet(getEntry(items), ImoocConstSet.IMOOC_LABEL_CN);
        dataset.setColor(Color.BLACK);
        dataset.setValueTextColor(Color.RED);
        dataset.setValueFormatter(new IkValueFormatter());
        //线条平滑,如果不设置默认区间之间用直线连接，转弯处很直接
        //dataset.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        LineData linedata = new LineData(dataset);
        return linedata;
    }

    private void refreshChartData(List<LinechartItem> list) {
        LOG.d("refresh linechar.");
        ILineDataSet dataset = mLineChart.getLineData().getDataSetByLabel(ImoocConstSet.IMOOC_LABEL_CN, true);
        LineData linedata = mLineChart.getLineData();
        linedata.removeDataSet(dataset);
        linedata.addDataSet(new LineDataSet(getEntry(list), ImoocConstSet.IMOOC_LABEL_CN));
        //记得要更新一下X轴要替换的字符串，不然数据更新以后会出现奇怪情况
        List<String> strList = new ArrayList<String>();
        for (LinechartItem item : list) {
            strList.add(item.getTime() + "");
        }
        mListFormatter.setList(strList);
        mLineChart.notifyDataSetChanged();
        mLineChart.invalidate();
    }

    private void sort(List<LinechartItem> items) {
        Collections.sort(items, new Comparator<LinechartItem>() {
            @Override
            public int compare(LinechartItem objs, LinechartItem objt) {
                return objs.getTime() - objt.getTime();
            }
        });
    }

    private void runLineChart(List<LinechartItem> items) {

        mLineChart.setData(getData(items));

        //设置X轴参数
        setChartXAxis(items);

        //设置Y轴参数
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
        mLineChart.setScaleEnabled(true);

        //设置是否可以触摸
        mLineChart.setTouchEnabled(true);

        //那么，我们该如何设置当前窗口只展示固定的坐标点个数，剩余的通过滑动展现出来？
        // 这个时候可以使用chart的一个方法：chart.setVisibleXRangeMaximum(float f),
        // 该方法表示最大的展示区域,即显示不超过该值的点。需要注意的是必须在setData(data)后才生效
        mLineChart.setVisibleXRangeMaximum(20f);

        //设置Y轴不可缩放
        mLineChart.setScaleYEnabled(false);

        //设置X轴不可缩放
        mLineChart.setScaleXEnabled(false);

        //设置chart选择项接口
        List<String> strList = new ArrayList<String>();
        for (LinechartItem item : items) {
            strList.add(item.getTime() + "");
        }
        IkChartDataSelected dataselect = new IkChartDataSelected(this, strList);
        //dataselect.setDataSetList(items);
        mLineChart.setOnChartValueSelectedListener(dataselect);

        //设置触摸效果接口
        mLineChart.setOnChartGestureListener(new IkChartGesture());

        //刷新数据
        mLineChart.invalidate();

        mIsChartExists = true;
    }

    private void setChartXAxis(List<LinechartItem> list) {
        //获取X轴对象
        XAxis xAxis = mLineChart.getXAxis();

        //设置X轴底部显示
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //自定义设置横坐标,设置自定义X轴值
        List<String> datalist = new ArrayList<String>();
        for (LinechartItem item : list) {
            datalist.add(item.getTime() + "");
        }
        mListFormatter = new IkXValueListFormatter(datalist);
        xAxis.setValueFormatter(mListFormatter);

        //一个界面显示6个Lable，那么这里要设置11个
        xAxis.setLabelCount(6);

        //设置X轴的宽度
        xAxis.setAxisLineWidth(1f);

        //设置字体大小10sp
        xAxis.setTextSize(5f);

        //绘制网格线，默认为true
        xAxis.setDrawGridLines(true);

        //设置该轴的网格线颜色。
        xAxis.setGridColor(Color.BLACK);

        //设置该轴网格线的宽度。
        xAxis.setGridLineWidth(1f);

        //设置轴线的轴的颜色
        xAxis.setAxisLineColor(Color.BLACK);

        //设置该轴轴行的宽度
        xAxis.setAxisLineWidth(1f);

        //设置X轴坐标之间的最小间隔
        //xAxis.setGranularity(1f);

        //
        //xAxis.setAxisMinimum(1f);

        //
        xAxis.setLabelRotationAngle(90);

        // 设置x轴的LimitLine
        //xAxis.addLimitLine(getLimitline(50f,"Xlimit 测试"));
    }

    private void setChartYAxis() {
        //获取Y轴对象
        YAxis leftAxis = mLineChart.getAxisLeft();

        //是否隐藏右轴，false隐藏，true不隐藏，不设置默认显示
        mLineChart.getAxisRight().setEnabled(false);

        //设置Y轴最大最小值，不设置chart会自己计算
        //leftAxis.setAxisMinimum(0f);
        //leftAxis.setAxisMaximum(100f);

        //绘制网格线 默认为true
        leftAxis.setDrawGridLines(true);

        // 设置x轴的LimitLine，index是从0开始的
        //leftAxis.addLimitLine(getLimitline(50f,"Ylimit 测试"));
    }

    private LimitLine getLimitline(float limitValue, String label) {
        LimitLine limitLine = new LimitLine(limitValue, label);
        limitLine.setLineColor(Color.RED);
        limitLine.setTextColor(Color.GREEN);
        return limitLine;
    }

    private List<LinechartItem> getItem() {
        List<LinechartItem> linechartItemList = new ArrayList<LinechartItem>();
        for (int i=0; i<10; i++) {
            linechartItemList.add(new LinechartItem(new Random().nextInt(1000), i));
        }
        return linechartItemList;
    }
}
