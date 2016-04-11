package com.legolas.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.legolas.VO.Administrator;
import com.legolas.VO.DutyLog;
import com.legolas.VO.SignContract;
import com.legolas.VO.Worker;
import com.legolas.utils.HttpCallbackListener;
import com.legolas.utils.HttpUtil;
import com.legolas.utils.Util;

import java.util.Calendar;

/**
 * Created by legolas on 2016/4/6.
 */
public class SignContractsWorkerActivity extends AppCompatActivity {
    private final Gson gson =new GsonBuilder().create();
    private final int SUCCESS = 1;
    private final int FAILED  = 0;
    private TextView backTV;
    private TextView submitTV;
    private EditText nameET;
    private EditText workerIdET;
    private EditText typeET;
    private EditText apartmentET;
    private EditText contractorET;
    private EditText contentET;
    private EditText moneyET;
    private EditText remarkET;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message message){
            switch(message.what){
                case SUCCESS:
                {nameET.setText(null);
                        workerIdET.setText(null);
                        typeET.setText(null);
                        contentET.setText(null);
                        apartmentET.setText(null);
                        remarkET.setText(null);
                        contractorET.setText(null);
                        moneyET.setText(null);
                    Toast.makeText(SignContractsWorkerActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                    break;
                }
                case FAILED:
                {
                    Toast.makeText(SignContractsWorkerActivity.this, "请检查输入信息是否有误", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.signcontracts_worker);
        backTV = (TextView)findViewById(R.id.backTV);
        submitTV = (TextView)findViewById(R.id.submitTV);
        nameET = (EditText)findViewById(R.id.nameET);
        workerIdET = (EditText)findViewById(R.id.workerIdET);
        typeET = (EditText)findViewById(R.id.typeET);
        apartmentET = (EditText)findViewById(R.id.apartmentET);
        contractorET  = (EditText)findViewById(R.id.contractorET);
        contentET = (EditText)findViewById(R.id.contentET);
        moneyET = (EditText)findViewById(R.id.moneyET);
        remarkET = (EditText)findViewById(R.id.remarkET);
        backTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        moneyET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    // 此处为失去焦点时的处理内容
                    if(moneyET.getText().toString().trim().length()!=0&&!IsDouble(moneyET.getText().toString())){
                        moneyET.setText(null);
                        Toast.makeText(SignContractsWorkerActivity.this, "请输入正确金额", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        submitTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if( nameET.getText().toString().trim().length()<2||
                typeET.getText().toString().trim().length()<2||
                workerIdET.getText().toString().trim().length()<5||
                apartmentET.getText().toString().trim().length()<2||
                contentET.getText().toString().trim().length()<1||
                contractorET.getText().toString().trim().length()<1||
                moneyET.getText().toString().trim().length()<1
                   ) {
                    Toast.makeText(SignContractsWorkerActivity.this, "输入不合法", Toast.LENGTH_SHORT).show();
                }
                else{
                    SignContract signContract = new SignContract();
                    Worker worker = new Worker();
                    worker.setWorkerId(workerIdET.getText().toString());
                signContract.setWorker(worker);
                signContract.setName(nameET.getText().toString());
                signContract.setType(typeET.getText().toString());
                signContract.setApartment(apartmentET.getText().toString());
                signContract.setContent(contentET.getText().toString());
                signContract.setContractor(contractorET.getText().toString());
                signContract.setRemark(remarkET.getText().toString());
                signContract.setMoney(Double.valueOf(moneyET.getText().toString()));

                    String data = gson.toJson(signContract);

                    HttpUtil.postHttpRequest("http://192.168.56.1:8080/save_signcontract", data, new HttpCallbackListener() {
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
    private boolean IsDouble(String num)
    {
        try
        {
            Double.parseDouble(num);
        }
        catch(Exception e)
        {
            return false;
        }
        return true;
    }


}

