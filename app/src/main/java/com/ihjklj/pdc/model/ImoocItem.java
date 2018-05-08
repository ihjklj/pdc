package com.ihjklj.pdc.model;

/**
 * Created by ihjklj on 2018/5/6.
 */

public class ImoocItem {

    private String mStudent;
    private String mTitle;

    public ImoocItem(String title, String student) {
        mTitle = title;
        mStudent = student;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getStudent() {
        return mStudent;
    }
}
