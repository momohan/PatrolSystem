package com.legolas.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.legolas.VO.OnDuty;
import com.legolas.VO.PatrolProcess;
import com.legolas.utils.HttpCallbackListener;
import com.legolas.utils.HttpUtil;


/**
 * Created by legolas on 2016/3/28.
 */
public class OnDutyDetailActivity extends AppCompatActivity{
    private TextView idTV;
    private TextView documentNameTV;
    private TextView apartmentTV;
    private TextView deviceNameTV;
    private TextView workerTV;
    private TextView administratorTV;
    private TextView recordDateTV;
    private TextView contentTV;
    private TextView auditTV;
    private TextView backTV;
    private Intent intent;
    private final Gson gson = new GsonBuilder().create();
    private  OnDuty onDuty;
    private  String gsonData;


    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.onduty_detail);
        intent =getIntent();
        idTV = (TextView)findViewById(R.id.idTV);
        documentNameTV = (TextView)findViewById(R.id.documentNameTV);
        apartmentTV = (TextView)findViewById(R.id.apartmentTV);
        deviceNameTV = (TextView)findViewById(R.id.deviceNameTV);
        workerTV = (TextView)findViewById(R.id.workerTV);
        administratorTV = (TextView)findViewById(R.id.administratorTV);
        recordDateTV = (TextView)findViewById(R.id.recordDateTV);
        contentTV = (TextView)findViewById(R.id.contentTV);
        auditTV = (TextView)findViewById(R.id.auditTV);
        backTV = (TextView)findViewById(R.id.backTV);

        gsonData = intent.getStringExtra("detailInfo");
        onDuty = gson.fromJson(gsonData, new TypeToken<OnDuty>() {
        }.getType());

        idTV.setText(String.valueOf(onDuty.getId()));
        documentNameTV.setText(onDuty.getDocumentName());
        apartmentTV.setText(onDuty.getApartment());
        deviceNameTV.setText(onDuty.getDeviceName());
        workerTV.setText(onDuty.getWorker().getWorkerId());
        administratorTV.setText(onDuty.getAdministrator().getAdministratorId());
        recordDateTV.setText(onDuty.getRecordDate());
        contentTV.setText(onDuty.getContent());

        if(onDuty.getAudit() == 'N'){
            auditTV.setText("通过");
            auditTV.setTextColor(Color.BLUE);
            auditTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "http://192.168.56.1:8080/save_onduty";
                    onDuty.setAudit('Y');
                    String postData = gson.toJson(onDuty);
                    Log.v("detail" , postData);
                    HttpUtil.postHttpRequest(url, postData, new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            if ("200".equals(response)) {
                                Intent intent = new Intent(OnDutyDetailActivity.this, OnDutyAdministratorActivity.class);
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
