package com.ihjklj.pdc.util;

import android.util.Log;
import com.ihjklj.pdc.application.MyApplication;

/**
 * Created by ihjklj on 2018/5/6.
 */

public class LOG {

    public static void d(String data) {
        Log.d(MyApplication.TAG, data);
    }
}
