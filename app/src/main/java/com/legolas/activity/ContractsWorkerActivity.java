package com.legolas.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
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
import com.legolas.VO.Contracts;
import com.legolas.VO.OnDuty;
import com.legolas.VO.Worker;
import com.legolas.utils.HttpCallbackListener;
import com.legolas.utils.HttpUtil;
import com.legolas.utils.Util;

import java.util.Calendar;

/**
 * Created by legolas on 2016/4/6.
 */
public class ContractsWorkerActivity extends AppCompatActivity {
    private final Gson gson =new GsonBuilder().create();
    private final int SUCCESS = 1;
    private final int FAILED  = 0;
    private int mYear;
    private int mMonth;
    private int mDay;
    private TextView backTV;
    private TextView submitTV;
    private EditText workerIdET;
    private EditText nameET;
    private EditText typeET;
    private TextView dateTV;
    private Handler handler = new Handler(){
        @Override
    public void handleMessage(Message message){
            switch(message.what){
                case SUCCESS:
                {
                    nameET.setText(null);
                    workerIdET.setText(null);
                    typeET.setText(null);
                    Toast.makeText(ContractsWorkerActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                    break;
                }
                case FAILED:
                {
                    Toast.makeText(ContractsWorkerActivity.this, "请检查输入信息是否有误", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.contracts_worker);
        backTV = (TextView)findViewById(R.id.backTV);
        submitTV = (TextView)findViewById(R.id.submitTV);
        nameET = (EditText)findViewById(R.id.nameET);
        workerIdET = (EditText)findViewById(R.id.workerIdET);
        typeET = (EditText)findViewById(R.id.typeET);
        dateTV = (TextView)findViewById(R.id.dateTV);
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
                if(nameET.getText().toString().trim().length()<2||
                        typeET.getText().toString().trim().length()<2||
                        workerIdET.getText().toString().trim().length()<5){
                    Toast.makeText(ContractsWorkerActivity.this, "输入不合法", Toast.LENGTH_SHORT).show();
                }
                else{
                    Contracts contracts = new Contracts();
                    Worker worker =new Worker();
                    worker.setWorkerId(workerIdET.getText().toString());
                    contracts.setWorker(worker);
                    contracts.setName(nameET.getText().toString());
                    contracts.setDate(dateTV.getText().toString());
                    contracts.setType(typeET.getText().toString());

                    String data = gson.toJson(contracts);

                    HttpUtil.postHttpRequest("http://192.168.56.1:8080/save_contracts", data, new HttpCallbackListener() {
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
    }/*DateButtonOnClickListener*/
}

