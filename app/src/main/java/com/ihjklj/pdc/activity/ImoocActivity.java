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
    private List<ImoocCourse> mCourseList;
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
        mCourseList = new ArrayList<ImoocCourse>();
    }

    private void runListView(List<ImoocCourse> list) {
        mAdapter = new ImoocAdapter(this, R.layout.imooc_item_layout, list);
        mListview.setAdapter(mAdapter);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImoocCourse imooccourse = mCourseList.get(position);
                Intent intent = new Intent(ImoocActivity.this, ImoocLinechartActivity.class);
                intent.putExtra("title", imooccourse.getTitle());
                startActivity(intent);
            }
        });
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
                    List<String> items = imoocJsonObj.getData();
                    for (String item : items){
                        ImoocCourse course = jsonParse.fromJson(item, ImoocCourse.class);
                        mCourseList.add(course);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            runListView(mCourseList);
                        }
                    });
                }
            }
        }).start();
    }
}
