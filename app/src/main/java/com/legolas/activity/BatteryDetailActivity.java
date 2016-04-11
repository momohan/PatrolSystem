package com.legolas.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.legolas.VO.Battery;
import com.legolas.utils.HttpCallbackListener;
import com.legolas.utils.HttpUtil;


/**
 * Created by legolas on 2016/3/28.
 */
public class BatteryDetailActivity extends AppCompatActivity{
    private TextView idTV;
    private TextView deviceNameACTV;
    private TextView recordDateTV;
    private TextView submitPersonTV;
    private TextView workerTV;
    private TextView copyDateTV;
    private TextView highBatteryTV;
    private TextView middleBatteryTV;
    private TextView lowBatteryTV;
    private TextView backTV;
    private TextView auditTV;
    private Intent intent;
    private final Gson gson = new GsonBuilder().create();
    private Battery battery;
    private  String gsonData;


    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.battery_detail);
        intent =getIntent();
        idTV = (TextView)findViewById(R.id.idTV);
        deviceNameACTV = (AutoCompleteTextView)findViewById(R.id.deviceNameACTV);
        submitPersonTV = (TextView)findViewById(R.id.submitPersonTV);
        workerTV = (TextView)findViewById(R.id.workerTV);
        copyDateTV = (TextView)findViewById(R.id.copyDateTV);
        recordDateTV = (TextView)findViewById(R.id.recordDateTV);
        highBatteryTV = (TextView)findViewById(R.id.highBatteryTV);
        middleBatteryTV = (TextView)findViewById(R.id.middleBatteryTV);
        lowBatteryTV = (TextView)findViewById(R.id.lowBatteryTV);
        auditTV = (TextView)findViewById(R.id.auditTV);
        backTV = (TextView)findViewById(R.id.backTV);

        gsonData = intent.getStringExtra("detailInfo");
        battery = gson.fromJson(gsonData, new TypeToken<Battery>() {
        }.getType());

        idTV.setText(String.valueOf(battery.getId()));
        deviceNameACTV.setText(battery.getDeviceName());
        submitPersonTV.setText(battery.getSubmitPerson());
        copyDateTV.setText(battery.getCopyDate());
        workerTV.setText(battery.getWorker().getWorkerId());
        recordDateTV.setText(battery.getRecordDate());
        highBatteryTV.setText(battery.getHighBattery());
        middleBatteryTV.setText(battery.getMiddleBattery());
        lowBatteryTV.setText(battery.getLowBattery());

        if(battery.getAudit() == 'N'){
            auditTV.setText("通过");
            auditTV.setTextColor(Color.BLUE);
            auditTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "http://192.168.56.1:8080/save_battery";
                    battery.setAudit('Y');
                    String postData = gson.toJson(battery);
                    Log.v("detail" , postData);
                    HttpUtil.postHttpRequest(url, postData, new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            if ("200".equals(response)) {
                                Intent intent = new Intent(BatteryDetailActivity.this, BatteryAdministratorActivity.class);
                                startActivity(intent);
                                //设置切换动画，从左边进入，右边退出
                                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                            }
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.v("onError", "error");
                        }
                    });
                }
            });
        }
        else auditTV.setText("已审核");

        backTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  finish();
                //设置切换动画，从左边进入，右边退出
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            }
        });



    }
}
