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
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.legolas.VO.Fix;
import com.legolas.VO.ListViewVO;
import com.legolas.utils.HttpCallbackListener;
import com.legolas.utils.HttpUtil;
import com.legolas.utils.ListAdapter;
import com.legolas.utils.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by legolas on 2016/3/28.
 */
public class FixAdministratorActivity extends AppCompatActivity {
    private int mYear;
    private int mMonth;
    private int mDay;
    private TextView searchBeginDateTV;
    private TextView searchFinishDateTV;
    private ImageButton documentSearchIB;
    private TextView backTV;
    private EditText searchIdET;
    private AutoCompleteTextView searchApartmentACTV;
    private TextView auditTV;
    private ListView listView;
    private final int SEARCH = 3;
    private final int AUDITN = 2;
    private final int AUDITY = 1;
    private final int FAILED = 0;
    List<ListViewVO> adapterList = new ArrayList<ListViewVO>();
    List<Fix> fixList = new ArrayList<Fix>();
    List<Fix> auditList = new ArrayList<Fix>();
    Gson gson = new GsonBuilder().create();
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
                    Toast.makeText(FixAdministratorActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                    break;
                }
                case AUDITN:{
                    String str = (String)message.obj;
                    handlerMethod(str , 'Y');
                    break;
                }
                case SEARCH: {
                    String str = (String)message.obj;
                    handlerMethod(str, searchIdET.getText().toString(),searchApartmentACTV.getText().toString(), searchBeginDateTV.getText().toString(), searchFinishDateTV.getText().toString());
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.fix_administrator);
        auditTV = (TextView)findViewById(R.id.auditTV);
        documentSearchIB = (ImageButton)findViewById(R.id.documentSearchIB);
        searchIdET = (EditText)findViewById(R.id.searchIdET);
        searchApartmentACTV = (AutoCompleteTextView)findViewById(R.id.searchApartmentACTV);
        backTV = (TextView)findViewById(R.id.backTV);
        listView = (ListView)findViewById(R.id.listView);
        searchBeginDateTV = (TextView)findViewById(R.id.searchBeginDateTV);
        searchFinishDateTV = (TextView)findViewById(R.id.searchFinishDateTV);
        searchBeginDateTV.setOnClickListener(new TVBeginDateOnClickListener());
        searchFinishDateTV.setOnClickListener(new TVFinishDateOnClickListener());

        setDate();
        initListView(AUDITN);
        backTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FixAdministratorActivity.this, MainActivity.class);
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

    }//onCreate
    //普通方法
    public void handlerMethod(String str , char audit) {
        fixList = gson.fromJson(str, new TypeToken<List<Fix>>() {
        }.getType());
        for (int i = 0; i < fixList.size(); i++) {
            if (fixList.get(i).getAudit() != audit) {
                auditList.add(fixList.get(i));
            }
        }
        if (auditList != null) {
            for (int i = 0; i < auditList.size(); i++) {
                ListViewVO listViewVO = new ListViewVO();
                listViewVO.setName(String.valueOf(auditList.get(i).getId()));
                listViewVO.setId(auditList.get(i).getId());
                listViewVO.setAudit(auditList.get(i).getAudit());
                listViewVO.setDate(auditList.get(i).getFinishDate());
                adapterList.add(listViewVO);
            }
            Util.sortMethod(adapterList);
            ListAdapter adapter = new ListAdapter(FixAdministratorActivity.this, R.layout.listview_item, adapterList);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ListViewVO listViewVO = adapterList.get(position);
                    Intent intent = new Intent(FixAdministratorActivity.this, FixDetailActivity.class);
                    intent.putExtra("detailInfo", gson.toJson(findById(listViewVO, auditList)));
                    startActivity(intent);
                    //设置切换动画，从右边进入，左边退出
                    overridePendingTransition(R.anim.out_to_left, R.anim.in_from_right);
                }
            });

        }
        else Toast.makeText(FixAdministratorActivity.this, "暂无信息", Toast.LENGTH_SHORT).show();
    }
    //搜索处理方法
    public void handlerMethod(String str , String searchId ,String searchApartment , String beginDate , String finishDate){
        fixList = gson.fromJson(str, new TypeToken<List<Fix>>() {
        }.getType());
        if(searchId.trim().length()!=0|searchApartment.trim().length()!=0)
            for(int i = 0;i<fixList.size();i++){
                if(searchId.equals(String.valueOf(fixList.get(i).getId()))|searchApartment.equals(fixList.get(i).getApartment()))
                {
                    auditList.add(fixList.get(i));
                }
            }
        else for(int i = 0;i<fixList.size();i++){
            //此处注意&与&&；|与||的区别；&&与||均有短路的功能
            if(Util.stringtoDate(fixList.get(i).getFinishDate()).after(Util.stringtoDate(beginDate))
                    |Util.stringtoDate(fixList.get(i).getFinishDate()).equals(Util.stringtoDate(beginDate))
                    |Util.stringtoDate(fixList.get(i).getFinishDate()).equals(Util.stringtoDate(finishDate))
                    &&Util.stringtoDate(fixList.get(i).getFinishDate()).before(Util.stringtoDate(finishDate)))
            {
                auditList.add(fixList.get(i));
            }
        }
        if(auditList!=null){
            for (int i = 0; i < auditList.size(); i++) {
                ListViewVO listViewVO = new ListViewVO();
                listViewVO.setName(String.valueOf(auditList.get(i).getId()));
                listViewVO.setId(auditList.get(i).getId());
                listViewVO.setAudit(auditList.get(i).getAudit());
                listViewVO.setDate(auditList.get(i).getFinishDate());
                adapterList.add(listViewVO);
            }
            Util.sortMethod(adapterList);
            ListAdapter adapter = new ListAdapter(FixAdministratorActivity.this, R.layout.listview_item, adapterList);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ListViewVO listViewVO = adapterList.get(position);
                    Intent intent = new Intent(FixAdministratorActivity.this, FixDetailActivity.class);
                    intent.putExtra("detailInfo", gson.toJson(findById(listViewVO, auditList)));
                    startActivity(intent);
                    //设置切换动画，从右边进入，左边退出
                    overridePendingTransition(R.anim.out_to_left, R.anim.in_from_right);
                }
            });
        }
        else Toast.makeText(FixAdministratorActivity.this, "暂无信息", Toast.LENGTH_SHORT).show();

            /*adapter.notifyDataSetChanged();*/


    }

    /*-----onResume()------*/
/*    @Override
    protected void onResume(){
        super.onResume();
        initListView();
    }*/
    /*-----------*/

    //通过被点击的ListViewVO中的Id，在list中查询包含此条Id的Fix模型
    public static Fix findById(ListViewVO listViewVO , List<Fix> list){
        Fix fix ;
        for (int i=0 ; i<list.size();i++){
            if(list.get(i).getId() == listViewVO.getId())
            {
                fix = list.get(i);
                return fix;
            }
        }
        return null;
    }

    /*初始化ListView向服务端请求ListItem中的数据交给handler处理*/
    public void initListView(final int flag){
        adapterList.clear();
        fixList.clear();
        auditList.clear();
        HttpUtil.getHttpResponse("http://192.168.56.1:8080/get_fix", new HttpCallbackListener() {
            Message message = new Message();

            @Override
            public void onFinish(String response){
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

