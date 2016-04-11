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
import com.legolas.VO.Fix;
import com.legolas.VO.PatrolProcess;
import com.legolas.utils.HttpCallbackListener;
import com.legolas.utils.HttpUtil;


/**
 * Created by legolas on 2016/3/28.
 */
public class FixDetailActivity extends AppCompatActivity{
    private TextView idTV;
    private TextView apartmentTV;
    private TextView reportDateTV;
    private TextView receiveDateTV;
    private TextView finishDateTV;
    private TextView workerIdTV;
    private TextView administratorIdTV;
    private TextView areaTV;
    private TextView telTV;
    private TextView receivePersonTV;
    private TextView fixPersonTV;
    private TextView fixMaterialTV;
    private TextView reportContentTV;
    private TextView fixConditionTV;
    private TextView unfinishedReasonTV;
    private TextView apartmentCommentTV;
    private TextView remarkTV;
    private TextView backTV;
    private TextView auditTV;
    private Intent intent;
    private final Gson gson = new GsonBuilder().create();
    private  Fix fix;
    private  String gsonData;


    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.fix_detail);
        intent =getIntent();
        idTV = (TextView)findViewById(R.id.idTV);
        apartmentTV = (TextView)findViewById(R.id.apartmentTV);
        reportDateTV = (TextView)findViewById(R.id.reportDateTV);
        receiveDateTV = (TextView)findViewById(R.id.receiveDateTV);
        finishDateTV = (TextView)findViewById(R.id.finishDateTV);
        workerIdTV = (TextView)findViewById(R.id.workerIdTV);
        administratorIdTV = (TextView)findViewById(R.id.administratorIdTV);
        areaTV = (TextView)findViewById(R.id.areaTV);
        telTV = (TextView)findViewById(R.id.telTV);
        receivePersonTV = (TextView)findViewById(R.id.receivePersonTV);
        fixPersonTV = (TextView)findViewById(R.id.fixPersonTV);
        fixMaterialTV = (TextView)findViewById(R.id.fixMaterialTV);
        reportContentTV = (TextView)findViewById(R.id.reportContentTV);
        fixConditionTV = (TextView)findViewById(R.id.fixConditionTV);
        unfinishedReasonTV = (TextView)findViewById(R.id.unfinishedReasonTV);
        apartmentCommentTV = (TextView)findViewById(R.id.apartmentCommentTV);
        remarkTV = (TextView)findViewById(R.id.remarkTV);
        backTV = (TextView)findViewById(R.id.backTV);
        auditTV = (TextView)findViewById(R.id.auditTV);
        gsonData = intent.getStringExtra("detailInfo");
        fix = gson.fromJson(gsonData, new TypeToken<Fix>() {
        }.getType());

        idTV.setText(String.valueOf(fix.getId()));
        apartmentTV.setText(fix.getApartment());
        reportDateTV.setText(fix.getReportDate());
        receiveDateTV.setText(fix.getReceiveDate());
        finishDateTV.setText(fix.getFinishDate());
        workerIdTV.setText(fix.getWorker().getWorkerId());
        administratorIdTV.setText(fix.getAdministrator().getAdministratorId());
        areaTV.setText(fix.getArea());
        telTV.setText(fix.getTel());
        receivePersonTV.setText(fix.getReceivePerson());
        fixPersonTV.setText(fix.getFixPerson());
        fixMaterialTV.setText(fix.getFixMaterial());
        reportContentTV.setText(fix.getReportContent());
        fixConditionTV.setText(fix.getFixCondition());
        unfinishedReasonTV.setText(fix.getUnfinishedReason());
        apartmentCommentTV.setText(fix.getApartmentComment());
        remarkTV.setText(fix.getRemark());

        if(fix.getAudit() == 'N'){
            auditTV.setText("通过");
            auditTV.setTextColor(Color.BLUE);
            auditTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "http://192.168.56.1:8080/save_fix";
                    fix.setAudit('Y');
                    String postData = gson.toJson(fix);
                    Log.v("detail" , postData);
                    HttpUtil.postHttpRequest(url, postData, new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            if ("200".equals(response)) {
                                Intent intent = new Intent(FixDetailActivity.this, FixAdministratorActivity.class);
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
