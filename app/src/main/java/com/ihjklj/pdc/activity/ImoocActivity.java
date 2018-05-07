package com.ihjklj.pdc.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import com.ihjklj.pdc.R;
import com.ihjklj.pdc.adapter.ImoocAdapter;
import com.ihjklj.pdc.model.ImoocItem;
import com.ihjklj.pdc.okhttp.MyOkhttp;
import com.ihjklj.pdc.util.LOG;
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

        //
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (!mIsInit) {
                        init();
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

    private void init() {
        mList = new ArrayList<ImoocItem>();
        mAdapter = new ImoocAdapter(this, R.layout.imooc_item_layout, mList);
        mListview.setAdapter(mAdapter);
        mIsInit = true;
    }

    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = new MyOkhttp().sget("http://47.98.153.250:3001/imooc/");
                if (data == null) {
                    LOG.d("requet imooc data failed!");
                }
                else {

                }
            }
        }).start();
    }
}
