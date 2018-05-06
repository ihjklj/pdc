package com.ihjklj.pdc.model;

/**
 * Created by ihjklj on 2018/5/6.
 */

public class ImoocItem {

    private int studentNum;
    private int date;

    public ImoocItem(int num, int date) {
        studentNum = num;
        this.date = date;
    }

    public ImoocItem() {
        studentNum = 0;
        date = 0;
    }

    public void setStudentNum(int num) {
        this.studentNum = num;
    }

    public int getStudentNum() {
        return studentNum;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getDate() {
        return date;
    }
}
