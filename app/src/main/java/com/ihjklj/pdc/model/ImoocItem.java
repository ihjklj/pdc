package com.ihjklj.pdc.model;

/**
 * Created by ihjklj on 2018/5/6.
 */

public class ImoocItem {

    private int mImageId;
    private String mStudent;
    private String mTitle;
    private int mArrowId;

    public ImoocItem(int imageid, String title, String student, int arrowid) {
        mImageId = imageid;
        mTitle = title;
        mStudent = student;
        mArrowId = arrowid;
    }

    public int getImageId() {
        return mImageId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getStudent() {
        return mStudent;
    }

    public int getArrow() {
        return mArrowId;
    }
}
