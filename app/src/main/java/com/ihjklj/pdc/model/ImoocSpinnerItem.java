package com.ihjklj.pdc.model;

/**
 * Created by ihjklj on 2018/5/9.
 */

public class ImoocSpinnerItem {

    private int mId;
    private String mStr;

    public ImoocSpinnerItem(int id, String str) {
        this.mId = id;
        this.mStr = str;
    }

    public int getId() {
        return mId;
    }

    public String getStr() {
        return mStr;
    }
}
