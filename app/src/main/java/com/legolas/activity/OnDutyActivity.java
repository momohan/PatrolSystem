package com.legolas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * Created by legolas on 2016/3/28.
 */
public class OnDutyActivity extends AppCompatActivity{
    private Intent intent;
    private String identify;
    private TextView backTV;
    private ImageView onDutyIV;
    private ImageView batteryIV;
    private ImageView dutyLogIV;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.onduty_view);
        intent = getIntent();
        identify = intent.getStringExtra("identify");
        backTV = (TextView)findViewById(R.id.backTV);
        onDutyIV = (ImageView)findViewById(R.id.onDutyIV);
        batteryIV = (ImageView)findViewById(R.id.batteryIV);
        dutyLogIV = (ImageView)findViewById(R.id.dutyLogIV);
        backTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(OnDutyActivity.this, MainActivity.class);
                //重新启动MainActivity时不再调用onCreate，直接调用onStart,保证之前活动的数据不被更改。
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        onDutyIV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if("worker".equals(identify))
                {
                    Intent intent = new Intent(OnDutyActivity.this,OnDutyWorkerActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(OnDutyActivity.this,OnDutyAdministratorActivity.class);
                    startActivity(intent);
                }

            }
        });
        batteryIV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if("worker".equals(identify))
                {
                    Intent intent = new Intent(OnDutyActivity.this,BatteryWorkerActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(OnDutyActivity.this,BatteryAdministratorActivity.class);
                    startActivity(intent);
                }
            }
        });
        dutyLogIV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if("worker".equals(identify))
                {
                    Intent intent = new Intent(OnDutyActivity.this,DutyLogWorkerActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(OnDutyActivity.this,DutyLogAdministratorActivity.class);
                    startActivity(intent);
                }
            }
        });

    }//OnCreate()

}
