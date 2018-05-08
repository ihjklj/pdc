package com.ihjklj.pdc.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import com.google.gson.Gson;
import com.ihjklj.pdc.R;
import com.ihjklj.pdc.adapter.ImoocAdapter;
import com.ihjklj.pdc.model.ImoocCourse;
import com.ihjklj.pdc.model.ImoocItem;
import com.ihjklj.pdc.model.ImoocJson;
import com.ihjklj.pdc.okhttp.MyOkhttp;
import com.ihjklj.pdc.util.LOG;
import com.ihjklj.pdc.util.UtilMethod;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ihjklj on 2018/5/7.
 */

public class ImoocActivity extends AppCompatActivity {

    private ListView mListview;
    private ImoocAdapter mAdapter;
    private List<ImoocItem> mList;
    private boolean mIsInit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imooc_layout);

        initView();

        getData();
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (!mIsInit) {
                        List<ImoocItem> list = new ArrayList<ImoocItem>();
                        //List<ImoocCourse> courelist = msg.obj;
                        //run();
                    }
                    else {
                        //
                    }
                break;
                default:
                    break;
            }
        }
    };

    private void initView() {
        mListview = (ListView)findViewById(R.id.imooc_layout_listview);
    }

    private void startRun() {
        //
    }

    private void runListView(List<ImoocItem> list) {
        mAdapter = new ImoocAdapter(this, R.layout.imooc_item_layout, list);
        mListview.setAdapter(mAdapter);
        mIsInit = true;
    }

    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = new MyOkhttp().sget("http://47.98.153.250:30001/imooc/");
                if (data == null) {
                    LOG.d("requet imooc data failed!");
                }
                else {
                    Gson jsonParse = new Gson();
                    ImoocJson imoocJsonObj = jsonParse.fromJson(data, ImoocJson.class);
                    final List<ImoocCourse> list = new ArrayList<ImoocCourse>();
                    List<String> items = imoocJsonObj.getData();
                    for (String item : items){
                        ImoocCourse course = jsonParse.fromJson(item, ImoocCourse.class);
                        list.add(course);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            List<ImoocItem> itemlist = new ArrayList<ImoocItem>();
                            for (ImoocCourse course : list) {
                                itemlist.add(new ImoocItem(UtilMethod.getDrawableId("test"), course.getTitle(), course.getStudent(), UtilMethod.getDrawableId("test")));
                            }
                            runListView(itemlist);
                        }
                    });
                }
            }
        }).start();
    }
}
