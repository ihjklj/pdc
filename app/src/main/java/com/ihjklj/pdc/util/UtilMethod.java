package com.ihjklj.pdc.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.google.gson.Gson;
import com.ihjklj.pdc.R;
import com.ihjklj.pdc.model.ImoocJson;
import com.ihjklj.pdc.okhttp.IkOkhttp;
import java.lang.reflect.Field;
import java.util.List;

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

    public static List<ImoocJson.ImoocCourse> parseCourse(String data) {
        Gson jsonParse = new Gson();
        ImoocJson imoocJsonObj = jsonParse.fromJson(data, ImoocJson.class);
        return imoocJsonObj.getData();
    }

    public static List<ImoocJson.ImoocCourse> imoocGet(String url) {
        LOG.d("rquest url : " + url);
        String data = new IkOkhttp().sget(url);
        if (data != null) {
            try {
                //LOG.d("data : " + data);
                Gson jsonParse = new Gson();
                ImoocJson imoocJsonObj = jsonParse.fromJson(data, ImoocJson.class);
                List<ImoocJson.ImoocCourse> coursesList = imoocJsonObj.getData();
                return coursesList;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void newDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("普通的对话框的标题");
        builder.setMessage("这是一个普通的对话框的内容");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LOG.d("cancel");
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LOG.d("sure");
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
