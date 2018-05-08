package com.ihjklj.pdc.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.ihjklj.pdc.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_layout);

        Intent intent = new Intent(this, ImoocActivity.class);
        startActivity(intent);
        finish();
    }
}
