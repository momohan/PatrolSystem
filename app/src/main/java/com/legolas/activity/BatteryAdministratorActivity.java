package com.legolas.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.legolas.VO.Battery;
import com.legolas.VO.ListViewVO;
import com.legolas.VO.PatrolProcess;
import com.legolas.utils.HttpCallbackListener;
import com.legolas.utils.HttpUtil;
import com.legolas.utils.ListAdapter;
import com.legolas.utils.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by legolas on 2016/3/26.
 */
public class BatteryAdministratorActivity extends AppCompatActivity {
    private final Gson gson = new GsonBuilder().create();
    private TextView searchBeginDateTV;
    private TextView searchFinishDateTV;
    private ImageButton documentSearchIB;
    private TextView backTV;
    private AutoCompleteTextView deviceNameACTV;
    private TextView auditTV;
    private ListView listView;
    private int mYear;
    private int mMonth;
    private int mDay;
    private final int SEARCH = 3;
    private final int AUDITN = 2;
    private final int AUDITY = 1;
    private final int FAILED = 0;
    List<ListViewVO> adapterList = new ArrayList<ListViewVO>();
    List<Battery> batteryList = new ArrayList<Battery>();
    List<Battery> auditList = new ArrayList<Battery>();
    private String[] documentSearchNameStr = {"asd","asdf","asdfg"};
    private Handler handler = new Handler(){
        @Override
        public  void handleMessage(Message message){
            switch(message.what){
                case AUDITY:{
                    String str = (String)message.obj;
                    handlerMethod(str , 'N');
                    break;
                }
                case FAILED:{
                    Toast.makeText(BatteryAdministratorActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                    break;
                }
                case AUDITN:{
                    String str = (String)message.obj;
                    handlerMethod(str , 'Y');
                    break;
                }
                case SEARCH: {
                    String str = (String)message.obj;
                    handlerMethod(str, deviceNameACTV.getText().toString(), searchBeginDateTV.getText().toString(), searchFinishDateTV.getText().toString());
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.patorl_administrator);
        deviceNameACTV =(AutoCompleteTextView)findViewById(R.id.documentSearchNameACTV);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,documentSearchNameStr);
        deviceNameACTV.setAdapter(Adapter);
        searchBeginDateTV = (TextView)findViewById(R.id.searchBeginDateTV);
        searchFinishDateTV = (TextView)findViewById(R.id.searchFinishDateTV);
        documentSearchIB = (ImageButton)findViewById(R.id.documentSearchIB);
        backTV = (TextView)findViewById(R.id.backTV);
        auditTV = (TextView)findViewById(R.id.auditTV);
        listView = (ListView) findViewById(R.id.listView);
        searchBeginDateTV.setOnClickListener(new TVBeginDateOnClickListener());
        searchFinishDateTV.setOnClickListener(new TVFinishDateOnClickListener());
        setDate();
        backTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BatteryAdministratorActivity.this, OnDutyActivity.class);
                //重新启动MainActivity时不再调用onCreate，直接调用onStart,保证之前活动的数据不被更改。
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        auditTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if ("已审核".equals(auditTV.getText().toString())) {
                        initListView(AUDITY);
                        auditTV.setText("未审核");
                    } else {
                        initListView(AUDITN);
                        auditTV.setText("已审核");
                    }
                }

        });
        documentSearchIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initListView(SEARCH);
            }
        });
        initListView(AUDITN);

    }//onCreate

    public void handlerMethod(String str , char audit){
        batteryList = gson.fromJson(str, new TypeToken<List<Battery>>() {
        }.getType());
            for(int i = 0;i<batteryList.size();i++){
                if(batteryList.get(i).getAudit()!=audit)
                {
                    auditList.add(batteryList.get(i));
                }
            }
            if(auditList!=null) {
                for (int i = 0; i < auditList.size(); i++) {
                    ListViewVO listViewVO = new ListViewVO();
                    listViewVO.setName(auditList.get(i).getDeviceName());
                    listViewVO.setId(auditList.get(i).getId());
                    listViewVO.setAudit(auditList.get(i).getAudit());
                    listViewVO.setDate(auditList.get(i).getRecordDate());
                    adapterList.add(listViewVO);
                }
                Util.sortMethod(adapterList);
                ListAdapter adapter = new ListAdapter(BatteryAdministratorActivity.this, R.layout.listview_item, adapterList);
                listView.setAdapter(adapter);
            /*adapter.notifyDataSetChanged();*/
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ListViewVO listViewVO = adapterList.get(position);
                        Intent intent = new Intent(BatteryAdministratorActivity.this, PatrolDetailActivity.class);
                        intent.putExtra("detailInfo", gson.toJson(findById(listViewVO, auditList)));
                        startActivity(intent);
                        //设置切换动画，从右边进入，左边退出
                        overridePendingTransition(R.anim.out_to_left, R.anim.in_from_right);
                    }
                });
            }
            else Toast.makeText(BatteryAdministratorActivity.this, "暂无信息", Toast.LENGTH_SHORT).show();
    }

    public void handlerMethod(String str , String flag , String beginDate , String finishDate){
        batteryList = gson.fromJson(str, new TypeToken<List<Battery>>() {
        }.getType());
        if(flag.trim().length()!=0)
        for(int i = 0;i<batteryList.size();i++){
            if(flag.equals(batteryList.get(i).getDeviceName()))
            {
                auditList.add(batteryList.get(i));
            }
        }
        else for(int i = 0;i<batteryList.size();i++){
            //此处注意&与&&；|与||的区别；&&与||均有短路的功能
            if(Util.stringtoDate(batteryList.get(i).getRecordDate()).after(Util.stringtoDate(beginDate))|Util.stringtoDate(batteryList.get(i).getRecordDate()).equals(Util.stringtoDate(beginDate))|Util.stringtoDate(batteryList.get(i).getRecordDate()).equals(Util.stringtoDate(finishDate))
            &&Util.stringtoDate(batteryList.get(i).getRecordDate()).before(Util.stringtoDate(finishDate)))
            {
                auditList.add(batteryList.get(i));
            }
        }
        if(auditList!=null){
        for (int i = 0; i < auditList.size(); i++) {
            ListViewVO listViewVO = new ListViewVO();
            listViewVO.setName(auditList.get(i).getDeviceName());
            listViewVO.setId(auditList.get(i).getId());
            listViewVO.setAudit(auditList.get(i).getAudit());
            listViewVO.setDate(auditList.get(i).getRecordDate());
            adapterList.add(listViewVO);
        }
        Util.sortMethod(adapterList);
        ListAdapter adapter = new ListAdapter(BatteryAdministratorActivity.this, R.layout.listview_item, adapterList);
        listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ListViewVO listViewVO = adapterList.get(position);
                    Intent intent = new Intent(BatteryAdministratorActivity.this, PatrolDetailActivity.class);
                    intent.putExtra("detailInfo", gson.toJson(findById(listViewVO, auditList)));
                    startActivity(intent);
                    //设置切换动画，从右边进入，左边退出
                    overridePendingTransition(R.anim.out_to_left, R.anim.in_from_right);
                }
            });
        }
        else Toast.makeText(BatteryAdministratorActivity.this, "暂无信息", Toast.LENGTH_SHORT).show();

            /*adapter.notifyDataSetChanged();*/


    }

    /*-----onResume()------*/
/*    @Override
    protected void onResume(){
        super.onResume();
        initListView();
    }*/
    /*-----------*/


    //通过被点击的ListViewVO中的Id，在list中查询包含此条Id的PatrolProcess模型
    public static Battery findById(ListViewVO listViewVO , List<Battery> list){
        Battery battery ;
        for (int i=0 ; i<list.size();i++){
            if(list.get(i).getId() == listViewVO.getId())
            {
                battery = list.get(i);
                return battery;
            }
        }
        return null;
    }
    /*初始化ListView向服务端请求ListItem中的数据交给handler处理*/
    public void initListView(final int flag){
        adapterList.clear();
        batteryList.clear();
        auditList.clear();
        HttpUtil.getHttpResponse("http://192.168.56.1:8080/get_battery", new HttpCallbackListener() {
            Message message = new Message();
            @Override
            public void onFinish(String response) {
                Log.v("onFinish", response);
                message.what = flag;
                message.obj = response;
                handler.sendMessage(message);
            }
            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Log.v("onError", "FAILED");
                message.what = FAILED;
                handler.sendMessage(message);
            }

        });
    }//initListView

    /*======================*/

    /*======================*/
    private void setDate() {

        final Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);

        mMonth = c.get(Calendar.MONTH);

        mDay = c.get(Calendar.DAY_OF_MONTH);

        updateBeginDate();
        updateFinishDate();

    }

    /**
     * 更新日期
     */
    private void updateBeginDate() {

        searchBeginDateTV.setText(new StringBuilder().append(mYear).append("/").append(

                (mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("/").append(

                (mDay < 10) ? "0" + mDay : mDay));
    }
    private void updateFinishDate() {

        searchFinishDateTV.setText(new StringBuilder().append(mYear).append("/").append(

                (mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("/").append(

                (mDay < 10) ? "0" + mDay : mDay));
    }
    class TVBeginDateOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Util.createDatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateBeginDate();
                }
            }).show();
        }
    }/*TVBeginDateOnClickListener*/
    class TVFinishDateOnClickListener implements View.OnClickListener {
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
    }/*TVFinishDateOnClickListener*/
}


