package com.legolas.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.util.Calendar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.legolas.VO.Administrator;
import com.legolas.VO.PatrolProcess;
import com.legolas.VO.Worker;
import com.legolas.utils.HttpCallbackListener;
import com.legolas.utils.HttpUtil;
import com.legolas.utils.Util;
import android.os.Handler;


/**
 * Created by legolas on 2016/3/23.
 */
public class PatrolWorkerActivity extends AppCompatActivity {
    private final Gson gson = new GsonBuilder().create();
    private TextView backTV;
    private TextView submitTV;
    private TextView recordDateTV;
    private EditText documentNameET;
    private AutoCompleteTextView apartmentACTV;
    private AutoCompleteTextView deviceNameACTV;
    private EditText workerIdET;
    private EditText administratorIdET;
    private EditText areaET;
    private int mYear;
    private int mMonth;
    private int mDay;
    private final int SUCCESS = 1;
    private final int FAILED = 0;
    private String[] aptStr = {"管理部门","指挥部门","财务部门"};
    private String[] equipStr = {"变压器","射灯","水箱","排水管","机箱"};
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case SUCCESS:
                    administratorIdET.setText(null);
                    apartmentACTV.setText(null);
                    areaET.setText(null);
                    deviceNameACTV.setText(null);
                    documentNameET.setText(null);
                    workerIdET.setText(null);
                    Toast.makeText(PatrolWorkerActivity.this, "提交成功", Toast.LENGTH_SHORT).show();

                case FAILED:
                    Toast.makeText(PatrolWorkerActivity.this, "请检查输入信息是否有误", Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patrol_worker);
        backTV = (TextView)findViewById(R.id.backTV);
        submitTV = (TextView)findViewById(R.id.submitTV);
        recordDateTV = (TextView)findViewById(R.id.recordDateTV);
        apartmentACTV = (AutoCompleteTextView)findViewById(R.id.apartmentACTV);
        deviceNameACTV = (AutoCompleteTextView)findViewById(R.id.deviceNameACTV);
        documentNameET = (EditText)findViewById(R.id.documentNameET);
        workerIdET = (EditText)findViewById(R.id.workerIdET);
        administratorIdET = (EditText)findViewById(R.id.administratorIdET);
        areaET = (EditText)findViewById(R.id.areaET);
        ArrayAdapter<String> aptAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,aptStr);
        ArrayAdapter<String> equipAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,equipStr);
        apartmentACTV.setAdapter(aptAdapter);
        deviceNameACTV.setAdapter(equipAdapter);
        recordDateTV.setOnClickListener(new TVDateOnClickListener());
        setDate();

        backTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submitTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (documentNameET.getText().toString().trim().length() < 2
                    ||administratorIdET.getText().toString().trim().length() < 5
                    ||apartmentACTV.getText().toString().trim().length() < 2
                        ||deviceNameACTV.getText().toString().trim().length() < 2
                        ||workerIdET.getText().toString().trim().length() < 5
                        ||areaET.getText().toString().trim().length() < 2)
                {
                    Toast.makeText(PatrolWorkerActivity.this, "输入不合法", Toast.LENGTH_SHORT).show();
                }
                else {
                    PatrolProcess patrolprocess = new PatrolProcess();
                    Administrator administrator = new Administrator();
                    administrator.setAdministratorId(administratorIdET.getText().toString());
                    Worker worker = new Worker();
                    worker.setWorkerId(workerIdET.getText().toString());
                    patrolprocess.setAdministrator(administrator);
                    patrolprocess.setWorker(worker);
                    patrolprocess.setApartment(apartmentACTV.getText().toString());
                    patrolprocess.setArea(areaET.getText().toString());
                    patrolprocess.setDeviceName(deviceNameACTV.getText().toString());
                    patrolprocess.setDocumentName(documentNameET.getText().toString());
                    patrolprocess.setRecordDate(recordDateTV.getText().toString());
                    patrolprocess.setAudit('N');

                    String data = gson.toJson(patrolprocess);
                    HttpUtil.postHttpRequest("http://192.168.56.1:8080/save_patrolprocess", data, new HttpCallbackListener() {
                        Message message = new Message();
                        @Override
                        public void onFinish(String response) {
                            if("200".equals(response))
                                message.what = SUCCESS;
                            else message.what = FAILED;

                            handler.sendMessage(message);
                        }

                        @Override
                        public void onError(Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });


    }/*==onCreate方法==*/


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

    class TVDateOnClickListener implements OnClickListener {
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
