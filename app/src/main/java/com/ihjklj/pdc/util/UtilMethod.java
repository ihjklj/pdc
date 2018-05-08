package com.ihjklj.pdc.util;

import com.ihjklj.pdc.R;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2018/5/8.
 */

public class UtilMethod {

    public static int getDrawableId(String key) {
        try {
            String name = key;
            Field field = R.drawable.class.getField(name);
            return field.getInt(null);
        }
        catch (SecurityException e) {
            e.printStackTrace();
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
