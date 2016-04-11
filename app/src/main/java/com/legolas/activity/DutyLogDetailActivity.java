package com.legolas.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.legolas.VO.DutyLog;
import com.legolas.VO.OnDuty;
import com.legolas.utils.HttpCallbackListener;
import com.legolas.utils.HttpUtil;


/**
 * Created by legolas on 2016/3/28.
 */
public class DutyLogDetailActivity extends AppCompatActivity{
    private TextView idTV;
    private TextView workerTV;
    private TextView administratorTV;
    private TextView areaTV;
    private TextView shiftTV;
    private TextView dateTV;
    private TextView contentTV;
    private TextView affairsTV;
    private TextView equipTV;
    private TextView remarkTV;
    private TextView auditTV;
    private TextView backTV;
    private Intent intent;
    private final Gson gson = new GsonBuilder().create();
    private DutyLog dutyLog;
    private  String gsonData;


    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.onduty_detail);
        intent =getIntent();
        idTV = (TextView)findViewById(R.id.idTV);
        areaTV = (TextView)findViewById(R.id.areaTV);
        shiftTV = (TextView)findViewById(R.id.shiftTV);
        dateTV = (TextView)findViewById(R.id.dateTV);
        workerTV = (TextView)findViewById(R.id.workerTV);
        administratorTV = (TextView)findViewById(R.id.administratorTV);
        affairsTV = (TextView)findViewById(R.id.affairsTV);
        equipTV = (TextView)findViewById(R.id.equipTV);
        remarkTV = (TextView)findViewById(R.id.remarkTV);
        contentTV = (TextView)findViewById(R.id.contentTV);
        auditTV = (TextView)findViewById(R.id.auditTV);
        backTV = (TextView)findViewById(R.id.backTV);

        gsonData = intent.getStringExtra("detailInfo");
        dutyLog = gson.fromJson(gsonData, new TypeToken<OnDuty>() {
        }.getType());

        idTV.setText(String.valueOf(dutyLog.getId()));
        areaTV.setText(dutyLog.getArea());
        shiftTV.setText(dutyLog.getShift());
        dateTV.setText(dutyLog.getDate());
        workerTV.setText(dutyLog.getWorker().getWorkerId());
        administratorTV.setText(dutyLog.getAdministrator().getAdministratorId());
        affairsTV.setText(dutyLog.getAffairs());
        equipTV.setText(dutyLog.getEquip());
        remarkTV.setText(dutyLog.getRemark());
        contentTV.setText(dutyLog.getContent());

        if(dutyLog.getAudit() == 'N'){
            auditTV.setText("通过");
            auditTV.setTextColor(Color.BLUE);
            auditTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "http://192.168.56.1:8080/save_dutylog";
                    dutyLog.setAudit('Y');
                    String postData = gson.toJson(dutyLog);
                    Log.v("detail" , postData);
                    HttpUtil.postHttpRequest(url, postData, new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            if ("200".equals(response)) {
                                Intent intent = new Intent(DutyLogDetailActivity.this, DutyLogAdministratorActivity.class);
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
