package com.ihjklj.pdc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import com.ihjklj.pdc.R;
import com.ihjklj.pdc.adapter.ImoocAdapter;
import com.ihjklj.pdc.adapter.ImoocSpinnerAdapter;
import com.ihjklj.pdc.model.ImoocJson;
import com.ihjklj.pdc.model.ImoocSpinnerItem;
import com.ihjklj.pdc.set.DefineSet;
import com.ihjklj.pdc.util.LOG;
import com.ihjklj.pdc.util.UtilMethod;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ihjklj on 2018/5/7.
 */

public class ImoocActivity extends AppCompatActivity {

    private Spinner mSpinner;
    private ListView mListview;
    private ImoocAdapter mAdapter;
    private List<ImoocJson.ImoocCourse> mCourseList;
    private List<ImoocSpinnerItem> mSpinnerItemList;
    private ImoocSpinnerAdapter mSpinnerAdapter;
    private boolean mIsInit;
    private int mCurrTypeFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.imooc_layout);

        init();

        initView();

        runSpinner();
    }

    private void initView() {
        mSpinner = (Spinner)findViewById(R.id.imooc_layout_spinner);
        mListview = (ListView)findViewById(R.id.imooc_layout_listview);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ImoocSpinnerItem item = (ImoocSpinnerItem)mSpinner.getSelectedItem();
                LOG.d("selected item " + item);
                if (!mIsInit) {
                    getCourses();
                    mIsInit = true;
                }
                else {
                    notifyList(item.getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                LOG.d("nothing selected.");
            }
        });
    }

    private void init() {
        mCourseList = new ArrayList<ImoocJson.ImoocCourse>();
        mIsInit = false;
        mSpinnerItemList = new ArrayList<ImoocSpinnerItem>();
        mCurrTypeFlag = 0;
    }

    private void runSpinner() {
        //mSpinnerItemList.add(new ImoocSpinnerItem(DefineSet.IMOOC_COURSE_TYPE_NET, "网站顺序"));
        //mSpinnerItemList.add(new ImoocSpinnerItem(DefineSet.IMOOC_COURSE_TYPE_NUM, "学习人数"));
        //mSpinnerItemList.add(new ImoocSpinnerItem(DefineSet.IMOOC_COURSE_TYPE_UP, "上升最快"));
        //mSpinnerItemList.add(new ImoocSpinnerItem(DefineSet.IMOOC_COURSE_TYPE_DOWN, "下降最快"));
        mSpinnerItemList.add(new ImoocSpinnerItem(DefineSet.IMOOC_COURSE_TYPE_NET, "net order"));
        mSpinnerItemList.add(new ImoocSpinnerItem(DefineSet.IMOOC_COURSE_TYPE_NUM, "student"));
        mSpinnerItemList.add(new ImoocSpinnerItem(DefineSet.IMOOC_COURSE_TYPE_UP, "rising"));
        mSpinnerItemList.add(new ImoocSpinnerItem(DefineSet.IMOOC_COURSE_TYPE_DOWN, "falling"));
        mSpinnerAdapter = new ImoocSpinnerAdapter(this, mSpinnerItemList);
        mSpinner.setAdapter(mSpinnerAdapter);
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

    private void getCourses() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<ImoocJson.ImoocCourse> coursesList = UtilMethod.imoocGet(DefineSet.IMOOC_URL);
                if (coursesList != null) {
                    for (ImoocJson.ImoocCourse course : coursesList) {
                        mCourseList.add(course);
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        runListView(mCourseList);
                    }
                });
            }
        }).start();
    }

    private void notifyList(final int flag) {
        Collections.sort(mCourseList, new Comparator<ImoocJson.ImoocCourse>() {
            @Override
            public int compare(ImoocJson.ImoocCourse objs, ImoocJson.ImoocCourse objt) {
                int ret = 0;
                switch (flag) {
                    case DefineSet.IMOOC_COURSE_TYPE_NET:
                        ret = objs.getId() - objt.getId();
                        break;
                    case DefineSet.IMOOC_COURSE_TYPE_NUM:
                        ret = objt.getStudent() - objs.getStudent();
                        break;
                    case DefineSet.IMOOC_COURSE_TYPE_UP:
                        ret = objt.getIndex_sign() - objs.getIndex_sign();
                        break;
                    case DefineSet.IMOOC_COURSE_TYPE_DOWN:
                        ret = objs.getIndex_sign() - objt.getIndex_sign();
                        break;
                    default:
                        ret = objs.getId() - objt.getId();
                }
                return ret;
            }
        });
        mAdapter.notifyDataSetChanged();
    }
}
