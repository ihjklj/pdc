package com.ihjklj.pdc.chart;

import android.view.MotionEvent;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.ihjklj.pdc.util.LOG;

/**
 * Created by Administrator on 2018/5/11.
 */

public class IkChartGesture implements OnChartGestureListener {

    public IkChartGesture() {
        LOG.e("create IkChartGesture");
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        LOG.e("onChartGestureStart");
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        LOG.e("onChartGestureEnd");
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        LOG.e("onChartLongPressed");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        LOG.e("onChartDoubleTapped");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        LOG.e("onChartSingleTapped");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        LOG.e("onChartFling");
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        LOG.e("onChartScale");
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        LOG.e("onChartTranslate");
    }
}
