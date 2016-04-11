package com.legolas.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.legolas.VO.Administrator;
import com.legolas.VO.Fix;
import com.legolas.VO.Worker;
import com.legolas.utils.HttpCallbackListener;
import com.legolas.utils.HttpUtil;
import com.legolas.utils.Util;

import java.util.Calendar;

/**
 * Created by legolas on 2016/3/28.
 */
public class FixWorkerActivity extends AppCompatActivity{
    private int mYear;
    private int mMonth;
    private int mDay;
    private TextView reportDateTV;
    private TextView receiveDateTV;
    private TextView finishDateTV;
    private TextView backTV;
    private TextView submitTV;
    private AutoCompleteTextView apartmentACTV;
    private EditText workerIdET;
    private EditText administratorIdET;
    private EditText areaET;
    private EditText telET;
    private EditText receivePersonET;
    private EditText fixPersonET;
    private EditText fixMaterialET;
    private EditText reportContentET;
    private EditText fixConditionET;
    private EditText unfinishedReasonET;
    private EditText apartmentCommentET;
    private EditText remarkET;
    private final int SUCCESS = 1;
    private final int FAILED = 0;
    private Gson gson = new GsonBuilder().create();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case SUCCESS: {
                    administratorIdET.setText(null);
                    workerIdET.setText(null);
                    apartmentACTV.setText(null);
                    areaET.setText(null);
                    apartmentCommentET.setText(null);
                    fixConditionET.setText(null);
                    fixMaterialET.setText(null);
                    fixPersonET.setText(null);
                    receivePersonET.setText(null);
                    reportContentET.setText(null);
                    telET.setText(null);
                    unfinishedReasonET.setText(null);
                    remarkET.setText(null);
                    Toast.makeText(FixWorkerActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                    break;
                }
                case FAILED:
                    Toast.makeText(FixWorkerActivity.this, "请检查输入信息是否有误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.fix_worker);
        backTV = (TextView)findViewById(R.id.backTV);
        submitTV = (TextView)findViewById(R.id.submitTV);
        reportDateTV = (TextView)findViewById(R.id.reportDateTV);
        receiveDateTV = (TextView)findViewById(R.id.receiveDateTV);
        finishDateTV = (TextView)findViewById(R.id.finishDateTV);
        apartmentACTV = (AutoCompleteTextView)findViewById(R.id.apartmentACTV);
        workerIdET = (EditText)findViewById(R.id.workerIdET);
        administratorIdET = (EditText)findViewById(R.id.administratorIdET);
        areaET = (EditText)findViewById(R.id.areaET);
        telET = (EditText)findViewById(R.id.telET);
        receivePersonET = (EditText)findViewById(R.id.receivePersonET);
        fixPersonET = (EditText)findViewById(R.id.fixPersonET);
        fixMaterialET = (EditText)findViewById(R.id.fixMaterialET);
        reportContentET = (EditText)findViewById(R.id.reportContentET);
        fixConditionET = (EditText)findViewById(R.id.fixConditionET);
        unfinishedReasonET = (EditText)findViewById(R.id.unfinishedReasonET);
        apartmentCommentET = (EditText)findViewById(R.id.apartmentCommentET);
        remarkET = (EditText) findViewById(R.id.remarkET);

        backTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FixWorkerActivity.this.finish();
            }
        });
        reportDateTV.setOnClickListener(new ReportDateOnClickListener());
        receiveDateTV.setOnClickListener(new ReceiveDateOnClickListener());
        finishDateTV.setOnClickListener(new FinishDateOnClickListener());
        setDate();
        submitTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(apartmentACTV.getText().toString().trim().length() < 2||
                            workerIdET.getText().toString().trim().length() < 5||
                            administratorIdET.getText().toString().trim().length() < 5||
                            areaET.getText().toString().trim().length() < 2||
                            telET.getText().toString().trim().length() < 2||
                            receivePersonET.getText().toString().trim().length() < 2||
                            fixPersonET.getText().toString().trim().length() < 2||
                            fixMaterialET.getText().toString().trim().length() < 1
                            ){
                        Toast.makeText(FixWorkerActivity.this, "输入不合法", Toast.LENGTH_SHORT).show();
                    }
                else{
                        Administrator administrator = new Administrator();
                        Worker worker = new Worker();
                        administrator.setAdministratorId(administratorIdET.getText().toString());
                        worker.setWorkerId(workerIdET.getText().toString());
                        Fix fix = new Fix();
                        fix.setApartment(apartmentACTV.getText().toString());
                        fix.setAdministrator(administrator);
                        fix.setWorker(worker);
                        fix.setArea(areaET.getText().toString());
                        fix.setFinishDate(finishDateTV.getText().toString());
                        fix.setReportDate(reportDateTV.getText().toString());
                        fix.setReceiveDate(receiveDateTV.getText().toString());
                        fix.setApartmentComment(apartmentCommentET.getText().toString());
                        fix.setFixCondition(fixConditionET.getText().toString());
                        fix.setFixMaterial(fixMaterialET.getText().toString());
                        fix.setFixPerson(fixPersonET.getText().toString());
                        fix.setReceivePerson(receivePersonET.getText().toString());
                        fix.setReportContent(reportContentET.getText().toString());
                        fix.setTel(telET.getText().toString());
                        fix.setUnfinishedReason(unfinishedReasonET.getText().toString());
                        fix.setRemark(remarkET.getText().toString());
                        fix.setAudit('N');

                        String data = gson.toJson(fix);

                        HttpUtil.postHttpRequest("http://192.168.56.1:8080/save_fix", data, new HttpCallbackListener() {
                            Message message = new Message();
                            @Override
                            public void onFinish(String response) {
                                Log.v("response", response);
                                if ("200".equals(response))
                                    message.what = SUCCESS;
                                else
                                    message.what = FAILED;
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


    }



    private void setDate() {

        final Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);

        mMonth = c.get(Calendar.MONTH);

        mDay = c.get(Calendar.DAY_OF_MONTH);

        updateReportDate();
        updateReceiveDate();
        updateFinishDate();

    }

    /**
     * 报修日期
     */
    private void updateReportDate() {

        reportDateTV.setText(new StringBuilder().append(mYear).append("/").append(

                (mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("/").append(

                (mDay < 10) ? "0" + mDay : mDay));
    }

    class ReportDateOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Util.createDatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateReportDate();
                }
            }).show();
        }
    }
    /**
     * 接单日期
     */
    private void updateReceiveDate() {

        receiveDateTV.setText(new StringBuilder().append(mYear).append("/").append(

                (mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("/").append(

                (mDay < 10) ? "0" + mDay : mDay));
    }

    class ReceiveDateOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Util.createDatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateReceiveDate();
                }
            }).show();
        }
    }
    /**
     * 完成日期
     */
    private void updateFinishDate() {

        finishDateTV.setText(new StringBuilder().append(mYear).append("/").append(

                (mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("/").append(

                (mDay < 10) ? "0" + mDay : mDay));
    }

    class FinishDateOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Util.createDatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateFinishDate();
                }
            }).show();
        }
    }
}
