package com.ihjklj.pdc.okhttp;

import com.ihjklj.pdc.ikInterface.ImoocInterface;
import com.ihjklj.pdc.util.FileMethod;
import com.ihjklj.pdc.util.LOG;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ihjklj on 2018/5/6.
 */

public class IkOkhttp {

    private static OkHttpClient mClient;

    public IkOkhttp() {
        if (mClient == null) {
            synchronized (IkOkhttp.class) {
                if (mClient == null) {
                    mClient = new OkHttpClient();
                }
            }
        }
    }

    public void aget(String url) {
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LOG.d("get failed!");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LOG.d("get success.");
                String xmldata = response.body().string();
                FileMethod.saveXml("imooc.xml", xmldata);
            }
        });
    }

    public String sget(String url) {
        String responseData = null;
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        try {
            Response res = mClient.newCall(request).execute();
            responseData = res.body().string();
            LOG.d("get success.");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return responseData;
    }

    public void httpGet(String url, final ImoocInterface minterface) {
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LOG.d("get failed!");
                if (minterface != null) {
                    minterface.getFailed();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LOG.d("get success.");
                if (minterface != null) {
                    minterface.getCourse(response.body().string());
                }
            }
        });
    }
}
