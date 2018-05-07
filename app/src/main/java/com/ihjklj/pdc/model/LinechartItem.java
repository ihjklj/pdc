package com.ihjklj.pdc.model;

/**
 * Created by ihjklj on 2018/5/8.
 */

public class LinechartItem {

    private int mNum;
    private int mTime;

    public LinechartItem(int num, int time) {
        mNum = num;
        mTime = time;
    }

    public int getNum() {
        return mNum;
    }

    public int getTime() {
        return mTime;
    }
}
