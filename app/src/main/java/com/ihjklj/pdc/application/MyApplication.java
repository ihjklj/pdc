package com.ihjklj.pdc.application;

import android.app.Application;
import com.ihjklj.pdc.util.LOG;

/**
 * Created by ihjklj on 2018/5/6.
 */

public class MyApplication extends Application {

    //本项目打印的log的TAG
    public static final String TAG = "pdc";

    //是否使用中文字符
    public static boolean IS_USE_CN = true;

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    private void init() {
        LOG.d("application init.");
    }
}
