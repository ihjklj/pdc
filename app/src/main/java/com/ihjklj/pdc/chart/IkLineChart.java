package com.ihjklj.pdc.chart;

import android.content.Context;
import android.graphics.Color;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.ihjklj.pdc.util.LOG;
import java.util.List;

/**
 * Created by ihjklj on 2018/5/13.
 */

public class IkLineChart {

    private Context mContext;
    private LineChart mLineChart;
    private boolean mIsChartExists;
    private IkXValueListFormatter mListFormatter;
    private String mLabel;

    public IkLineChart(Context context, LineChart linechart, String label) {
        this.mContext = context;
        this.mLineChart = linechart;
        this.mIsChartExists = false;
        this.mLabel = label;
    }

    private void setChartXAxis(List<String> list) {
        //获取X轴对象
        XAxis xAxis = mLineChart.getXAxis();

        //设置X轴底部显示
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //自定义设置横坐标,设置自定义X轴值
        mListFormatter = new IkXValueListFormatter(list);
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

    public void run(List<Entry> entryList, List<String> strList) {
        if (mIsChartExists) {
            refreshChartData(entryList, strList);
        }
        else {
            runLineChart(entryList, strList);
        }
    }

    private void refreshChartData(List<Entry> entryList, List<String> strList) {
        LOG.d("refresh linechar.");
        ILineDataSet dataset = mLineChart.getLineData().getDataSetByLabel(mLabel, true);
        LineData linedata = mLineChart.getLineData();
        linedata.removeDataSet(dataset);
        linedata.addDataSet(new LineDataSet(entryList, mLabel));
        //记得要更新一下X轴要替换的字符串，不然数据更新以后会出现奇怪情况
        mListFormatter.setList(strList);
        mLineChart.notifyDataSetChanged();
        mLineChart.invalidate();
    }

    private LineData getData(List<Entry> entryList) {
        LineDataSet dataset = new LineDataSet(entryList, mLabel);
        dataset.setColor(Color.BLACK);
        dataset.setValueTextColor(Color.RED);
        dataset.setValueFormatter(new IkValueFormatter());
        //线条平滑,如果不设置默认区间之间用直线连接，转弯处很直接
        //dataset.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        LineData linedata = new LineData(dataset);
        return linedata;
    }

    private void runLineChart(List<Entry> entryList, List<String> strList) {

        mLineChart.setData(getData(entryList));

        //设置X轴参数
        setChartXAxis(strList);

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
        IkChartDataSelected dataselect = new IkChartDataSelected(mContext, strList);
        mLineChart.setOnChartValueSelectedListener(dataselect);

        //设置触摸效果接口
        mLineChart.setOnChartGestureListener(new IkChartGesture());

        //刷新数据
        mLineChart.invalidate();

        mIsChartExists = true;
    }
}
