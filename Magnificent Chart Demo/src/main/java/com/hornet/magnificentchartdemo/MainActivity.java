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

    MagnificentChart magnificentChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MagnificentChartItem firstItem = new  MagnificentChartItem("first", 30, Color.parseColor("#BAF0A2"));
        MagnificentChartItem secondItem = new  MagnificentChartItem("second", 12, Color.parseColor("#2F6994"));
        MagnificentChartItem thirdItem = new  MagnificentChartItem("third", 3, Color.parseColor("#FF6600"));
        MagnificentChartItem fourthItem = new  MagnificentChartItem("fourth", 41, Color.parseColor("#800080"));
        MagnificentChartItem fifthItem = new  MagnificentChartItem("fifth", 14, Color.parseColor("#708090"));

        List<MagnificentChartItem> chartItemsList = new ArrayList<MagnificentChartItem>();
        chartItemsList.add(firstItem);
        chartItemsList.add(secondItem);
        chartItemsList.add(thirdItem);
        chartItemsList.add(fourthItem);
        chartItemsList.add(fifthItem);

        magnificentChart = (MagnificentChart) findViewById(R.id.magnificentChart);

        magnificentChart.setChartItemsList(chartItemsList);
        magnificentChart.setMaxValue(100);

    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.animationButton:
                if(magnificentChart.getAnimationState()){
                    magnificentChart.setAnimationState(false);
                } else {
                    magnificentChart.setAnimationState(true);
                }
                break;

            case R.id.roundButton:
                if(magnificentChart.getRound()){
                    magnificentChart.setRound(false);
                } else {
                    magnificentChart.setRound(true);
                }
                break;

            case R.id.shadowButton:
                if(magnificentChart.getShadowShowingState()){
                    magnificentChart.setShadowShowingState(false);
                } else {
                    magnificentChart.setShadowShowingState(true);
                }
                break;

            case R.id.animationSpeedDefault:
                magnificentChart.setAnimationSpeed(MagnificentChart.ANIMATION_SPEED_DEFAULT);
                break;

            case R.id.animationSpeedSlow:
                magnificentChart.setAnimationSpeed(MagnificentChart.ANIMATION_SPEED_SLOW);
                break;

            case R.id.animationSpeedFast:
                magnificentChart.setAnimationSpeed(MagnificentChart.ANIMATION_SPEED_FAST);
                break;

            case R.id.animationSpeedNormal:
                magnificentChart.setAnimationSpeed(MagnificentChart.ANIMATION_SPEED_NORMAL);
                break;
        }
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
