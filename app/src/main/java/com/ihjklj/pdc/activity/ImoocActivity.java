package com.ihjklj.pdc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import com.google.gson.Gson;
import com.ihjklj.pdc.R;
import com.ihjklj.pdc.adapter.ImoocAdapter;
import com.ihjklj.pdc.ikInterface.ImoocInterface;
import com.ihjklj.pdc.model.ImoocJson;
import com.ihjklj.pdc.okhttp.IkOkhttp;
import com.ihjklj.pdc.util.LOG;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ihjklj on 2018/5/7.
 */

public class ImoocActivity extends AppCompatActivity {

    private ListView mListview;
    private ImoocAdapter mAdapter;
    private List<ImoocJson.ImoocCourse> mCourseList;
    private boolean mIsInit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.imooc_layout);

        initView();

        init();

        getData();
    }

    private void initView() {
        mListview = (ListView)findViewById(R.id.imooc_layout_listview);
    }

    private void init() {
        mCourseList = new ArrayList<ImoocJson.ImoocCourse>();
    }

    private void runListView(List<ImoocJson.ImoocCourse> list) {
        mAdapter = new ImoocAdapter(this, R.layout.imooc_item_layout, list);
        mListview.setAdapter(mAdapter);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImoocJson.ImoocCourse imooccourse = mCourseList.get(position);
                Intent intent = new Intent(ImoocActivity.this, ImoocLinechartActivity.class);
                intent.putExtra("imoocCourse", imooccourse);
                startActivity(intent);
            }
        });
        mIsInit = true;
    }

    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new IkOkhttp().httpGet("http://47.98.153.250:30001/imooc/", new ImoocInterface() {
                    @Override
                    public void getCourse(String data) {
                        if (data != null) {
                            try {
                                Gson jsonParse = new Gson();
                                ImoocJson imoocJsonObj = jsonParse.fromJson(data, ImoocJson.class);
                                List<ImoocJson.ImoocCourse> courses = imoocJsonObj.getData();
                                if (!courses.isEmpty()) {
                                    //mCourseList.clear();
                                    for (ImoocJson.ImoocCourse course :courses ) {
                                        mCourseList.add(course);
                                    }
                                }
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                                return ;
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    runListView(mCourseList);
                                }
                            });
                        }
                    }

                    @Override
                    public int getFailed() {
                        LOG.d("request url failed!");
                        return 0;
                    }
                });
            }
        }).start();
    }
}
