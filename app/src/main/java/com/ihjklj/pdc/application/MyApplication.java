package com.ihjklj.pdc.application;

import android.app.Application;
import com.ihjklj.pdc.util.LOG;

/**
 * Created by ihjklj on 2018/5/6.
 */

public class MyApplication extends Application {

    public static final String TAG = "pdc";

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    private void init() {
        LOG.d("application init.");
    }
}
