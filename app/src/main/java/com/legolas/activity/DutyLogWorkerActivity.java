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
import com.legolas.VO.Administrator;
import com.legolas.VO.Battery;
import com.legolas.VO.DutyLog;
import com.legolas.VO.OnDuty;
import com.legolas.VO.Worker;
import com.legolas.utils.HttpCallbackListener;
import com.legolas.utils.HttpUtil;
import com.legolas.utils.Util;
import android.os.Handler;

import java.util.Calendar;

/**
 * Created by legolas on 2016/4/6.
 */
public class DutyLogWorkerActivity extends AppCompatActivity {
    private final Gson gson =new GsonBuilder().create();
    private final int SUCCESS = 1;
    private final int FAILED  = 0;
    private int mYear;
    private int mMonth;
    private int mDay;
    private TextView backTV;
    private TextView submitTV;
    private EditText shiftET;
    private EditText workerIdET;
    private EditText administratorIdET;
    private TextView dateTV;
    private EditText areaET;
    private EditText contentET;
    private EditText affairsET;
    private EditText remarkET;
    private EditText equipET;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message message){
            switch(message.what){
                case SUCCESS:
                {shiftET.setText(null);
                        areaET.setText(null);
                        workerIdET.setText(null);
                        administratorIdET.setText(null);
                        contentET.setText(null);
                        affairsET.setText(null);
                        remarkET.setText(null);
                        equipET.setText(null);
                    Toast.makeText(DutyLogWorkerActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                    break;

                }
                case FAILED:
                {
                    Toast.makeText(DutyLogWorkerActivity.this, "请检查输入信息是否有误", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.dutylog_worker);
        backTV = (TextView)findViewById(R.id.backTV);
        submitTV = (TextView)findViewById(R.id.submitTV);
        shiftET = (EditText)findViewById(R.id.shiftET);
        workerIdET = (EditText)findViewById(R.id.workerIdET);
        administratorIdET = (EditText)findViewById(R.id.administratorIdET);
        dateTV = (TextView)findViewById(R.id.dateTV);
        areaET = (EditText)findViewById(R.id.areaET);
        contentET = (EditText)findViewById(R.id.contentET);
        affairsET = (EditText)findViewById(R.id.affairsET);
        remarkET = (EditText)findViewById(R.id.remarkET);
        equipET = (EditText)findViewById(R.id.equipET);
        setDate();
        dateTV.setOnClickListener(new TVDateOnClickListener());

        backTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submitTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if( shiftET.getText().toString().trim().length()<2||
                areaET.getText().toString().trim().length()<2||
                workerIdET.getText().toString().trim().length()<5||
                administratorIdET.getText().toString().trim().length()<5||
                contentET.getText().toString().trim().length()<1||
                affairsET.getText().toString().trim().length()<1||
                equipET.getText().toString().trim().length()<1
                   ) {
                    Toast.makeText(DutyLogWorkerActivity.this, "输入不合法", Toast.LENGTH_SHORT).show();
                }
                else{
                    DutyLog dutyLog = new DutyLog();
                    Administrator administrator = new Administrator();
                    Worker worker = new Worker();
                    worker.setWorkerId(workerIdET.getText().toString());
                    administrator.setAdministratorId(administratorIdET.getText().toString());
                    dutyLog.setWorker(worker);
                    dutyLog.setAdministrator(administrator);
                    dutyLog.setShift(shiftET.getText().toString());
                    dutyLog.setDate(dateTV.getText().toString());
                    dutyLog.setArea(areaET.getText().toString());
                    dutyLog.setContent(contentET.getText().toString());
                    dutyLog.setAffairs(affairsET.getText().toString());
                    dutyLog.setRemark(remarkET.getText().toString());
                    dutyLog.setEquip(equipET.getText().toString());
                    dutyLog.setAudit('N');

                    String data = gson.toJson(dutyLog);

                    HttpUtil.postHttpRequest("http://192.168.56.1:8080/save_dutylog", data, new HttpCallbackListener() {
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

        updateDate();


    }

    /**
     * 更新日期
     */
    private void updateDate() {

        dateTV.setText(new StringBuilder().append(mYear).append("/").append(

                (mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("/").append(

                (mDay < 10) ? "0" + mDay : mDay));
    }


    class TVDateOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Util.createDatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDate();
                }
            }).show();
        }
    }

}

