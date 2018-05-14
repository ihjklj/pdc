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
        LOG.e("rquest url : " + url);
        String data = new IkOkhttp().sget(url);
        if (data != null) {
            try {
                LOG.e("data : " + data);
                Gson jsonParse = new Gson();
                ImoocJson imoocJsonObj = jsonParse.fromJson(data, ImoocJson.class);
                List<ImoocJson.ImoocCourse> coursesList = imoocJsonObj.getData();
                LOG.e("course size:" + coursesList.size());
                return coursesList;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void newDialog(Context context, String data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("坐标");
        builder.setMessage(data);
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

    public static int dateToInt(String time) {
        time = time.replace("-", "")
                .replace("_", "")
                .replace(":", "")
                .replace(" ", "");
       return Integer.parseInt(time);
    }

    public static interface imoocParse {
        public List<ImoocJson.ImoocCourse> parse();
    }
}
