package com.hornet.magnificentchartdemo;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MagnificentChartItem firstItem = new  MagnificentChartItem("first", 23, Color.parseColor("#BAF0A2"));
        MagnificentChartItem secondItem = new  MagnificentChartItem("second", 37, Color.parseColor("#2F6994"));
        MagnificentChartItem thirdItem = new  MagnificentChartItem("third", 2, Color.parseColor("#FF6600"));
        MagnificentChartItem fourthItem = new  MagnificentChartItem("fourth", 32, Color.parseColor("#EE5E63"));

        List<MagnificentChartItem> chartItemsList = new ArrayList<MagnificentChartItem>();
        chartItemsList.add(firstItem);
        chartItemsList.add(secondItem);
        chartItemsList.add(thirdItem);
        chartItemsList.add(fourthItem);

        MagnificentChart magnificentChart = (MagnificentChart) findViewById(R.id.magnificentChart);

        magnificentChart.setChartItemsList(chartItemsList);
        magnificentChart.setMaxValue(100);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
