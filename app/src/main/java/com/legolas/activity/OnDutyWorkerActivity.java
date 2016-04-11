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
public class OnDutyWorkerActivity extends AppCompatActivity {
    private final Gson gson =new GsonBuilder().create();
    private final int SUCCESS = 1;
    private final int FAILED  = 0;
    private int mYear;
    private int mMonth;
    private int mDay;
    private TextView backTV;
    private TextView submitTV;
    private AutoCompleteTextView apartmentACTV;
    private AutoCompleteTextView deviceNameACTV;
    private EditText administratorIdET;
    private EditText workerIdET;
    private EditText documentNameET;
    private EditText contentET;
    private TextView recordDateTV;
    private Handler handler = new Handler(){
        @Override
    public void handleMessage(Message message){
            switch(message.what){
                case SUCCESS:
                {
                    administratorIdET.setText(null);
                    workerIdET.setText(null);
                    apartmentACTV.setText(null);
                    contentET.setText(null);
                    deviceNameACTV.setText(null);
                    documentNameET.setText(null);
                    Toast.makeText(OnDutyWorkerActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                    break;
                }
                case FAILED:
                {
                    Toast.makeText(OnDutyWorkerActivity.this, "请检查输入信息是否有误", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.onduty_worker);
        backTV = (TextView)findViewById(R.id.backTV);
        submitTV = (TextView)findViewById(R.id.submitTV);
        apartmentACTV = (AutoCompleteTextView)findViewById(R.id.apartmentACTV);
        deviceNameACTV = (AutoCompleteTextView)findViewById(R.id.deviceNameACTV);
        administratorIdET = (EditText)findViewById(R.id.administratorIdET);
        workerIdET = (EditText)findViewById(R.id.workerIdET);
        documentNameET = (EditText)findViewById(R.id.documentNameET);
        contentET = (EditText)findViewById(R.id.contentET);
        recordDateTV = (TextView)findViewById(R.id.recordDateTV);
        setDate();
        recordDateTV.setOnClickListener(new TVDateOnClickListener());

        backTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submitTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(documentNameET.getText().toString().trim().length()<2||
                        apartmentACTV.getText().toString().trim().length()<2||
                        deviceNameACTV.getText().toString().trim().length()<2||
                        administratorIdET.getText().toString().trim().length()<5||
                        workerIdET.getText().toString().trim().length()<5||
                        contentET.getText().toString().trim().length()<2){
                    Toast.makeText(OnDutyWorkerActivity.this, "输入不合法", Toast.LENGTH_SHORT).show();
                }
                else{
                    OnDuty onduty = new OnDuty();
                    Administrator administrator = new Administrator();
                    Worker worker =new Worker();
                    administrator.setAdministratorId(administratorIdET.getText().toString());
                    worker.setWorkerId(workerIdET.getText().toString());
                    onduty.setAdministrator(administrator);
                    onduty.setWorker(worker);
                    onduty.setApartment(apartmentACTV.getText().toString());
                    onduty.setContent(contentET.getText().toString());
                    onduty.setDeviceName(deviceNameACTV.getText().toString());
                    onduty.setDocumentName(documentNameET.getText().toString());
                    onduty.setRecordDate(recordDateTV.getText().toString());
                    onduty.setAudit('N');
                    String data = gson.toJson(onduty);

                    HttpUtil.postHttpRequest("http://192.168.56.1:8080/save_onduty", data, new HttpCallbackListener() {
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

        recordDateTV.setText(new StringBuilder().append(mYear).append("/").append(

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
    }/*DateButtonOnClickListener*/
}

