package com.legolas.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.legolas.VO.Battery;
import com.legolas.VO.Worker;
import com.legolas.utils.HttpCallbackListener;
import com.legolas.utils.HttpUtil;
import com.legolas.utils.Util;
import android.os.Handler;

import java.util.Calendar;

/**
 * Created by legolas on 2016/4/6.
 */
public class BatteryWorkerActivity extends AppCompatActivity {
    private final Gson gson =new GsonBuilder().create();
    private final int SUCCESS = 1;
    private final int FAILED  = 0;
    private int mYear;
    private int mMonth;
    private int mDay;
    private TextView backTV;
    private TextView submitTV;
    private AutoCompleteTextView deviceNameACTV;
    private EditText submitPersonET;
    private EditText workerIdET;
    private TextView recordDateTV;
    private TextView copyDateTV;
    private EditText highBatteryET;
    private EditText middleBatteryET;
    private EditText lowBatteryET;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message message){
            switch(message.what){
                case SUCCESS:
                {
                    deviceNameACTV.setText(null);
                            submitPersonET.setText(null);
                            workerIdET.setText(null);
                            highBatteryET.setText(null);
                            middleBatteryET.setText(null);
                            lowBatteryET.setText(null);
                    Toast.makeText(BatteryWorkerActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                    break;
                }
                case FAILED:
                {
                    Toast.makeText(BatteryWorkerActivity.this, "请检查输入信息是否有误", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.battery_worker);
        backTV = (TextView)findViewById(R.id.backTV);
        submitTV = (TextView)findViewById(R.id.submitTV);
        deviceNameACTV = (AutoCompleteTextView)findViewById(R.id.deviceNameACTV);
        submitPersonET = (EditText)findViewById(R.id.submitPersonET);
        workerIdET = (EditText)findViewById(R.id.workerIdET);
        recordDateTV = (TextView)findViewById(R.id.recordDateTV);
        copyDateTV = (TextView)findViewById(R.id.copyDateTV);
        highBatteryET = (EditText)findViewById(R.id.highBatteryET);
        middleBatteryET = (EditText)findViewById(R.id.middleBatteryET);
        lowBatteryET = (EditText)findViewById(R.id.lowBatteryET);
        recordDateTV.setOnClickListener(new TVRecordDateOnClickListener());
        copyDateTV.setOnClickListener(new TVCopyDateOnClickListener());
        setDate();

        backTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submitTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deviceNameACTV.getText().toString().trim().length()<2||
                        submitPersonET.getText().toString().trim().length()<2||
                        workerIdET.getText().toString().trim().length()<5||
                        highBatteryET.getText().toString().trim().length()<1||
                        middleBatteryET.getText().toString().trim().length()<1||
                        lowBatteryET.getText().toString().trim().length()<1
                        ){
                    Toast.makeText(BatteryWorkerActivity.this, "输入不合法", Toast.LENGTH_SHORT).show();
                }
                else{
                    Battery battery = new Battery();
                    Worker worker = new Worker();
                    worker.setWorkerId(workerIdET.getText().toString());
                    battery.setWorker(worker);
                    battery.setRecordDate(recordDateTV.getText().toString());
                    battery.setDeviceName(deviceNameACTV.getText().toString());
                    battery.setCopyDate(copyDateTV.getText().toString());
                    battery.setSubmitPerson(submitPersonET.getText().toString());
                    battery.setHighBattery(highBatteryET.getText().toString());
                    battery.setMiddleBattery(middleBatteryET.getText().toString());
                    battery.setLowBattery(lowBatteryET.getText().toString());
                    battery.setAudit('N');

                    String data = gson.toJson(battery);

                    HttpUtil.postHttpRequest("http://192.168.56.1:8080/save_battery", data, new HttpCallbackListener() {
                        Message message =new Message();
                        @Override
                        public void onFinish(String response) {
                            if("200".equals(response)){
                                message.what = SUCCESS;
                            }
                            else message.what = FAILED;
                            handler.sendMessage(message);
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
                }

            }
        });


    }//OnCreate()

    private void setDate() {

        final Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);

        mMonth = c.get(Calendar.MONTH);

        mDay = c.get(Calendar.DAY_OF_MONTH);

        updateRecordDate();
        updateCopyDate();

    }

    /**
     * 更新日期
     */
    private void updateRecordDate() {

        recordDateTV.setText(new StringBuilder().append(mYear).append("/").append(

                (mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("/").append(

                (mDay < 10) ? "0" + mDay : mDay));
    }

    private void updateCopyDate() {

        copyDateTV.setText(new StringBuilder().append(mYear).append("/").append(

                (mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("/").append(

                (mDay < 10) ? "0" + mDay : mDay));
    }

    class TVRecordDateOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Util.createDatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateRecordDate();
                }
            }).show();
        }
    }/*TVRecordDateOnClickListener*/

    class TVCopyDateOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Util.createDatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateCopyDate();
                }
            }).show();
        }/*TVCopyDateOnClickListener*/
    }

}

