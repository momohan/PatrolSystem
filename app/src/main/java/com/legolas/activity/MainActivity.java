package com.legolas.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.legolas.VO.Administrator;
import com.legolas.utils.HttpCallbackListener;
import com.legolas.utils.HttpUtil;
import com.legolas.utils.MyPagerAdapter;
import java.util.ArrayList;
import java.util.List;
import android.os.Handler;
import android.widget.Toast;

import com.legolas.VO.Worker;

/**
 * Created by legolas on 2016/3/23.
 */
public class MainActivity extends AppCompatActivity{
    private TextView titleFunction;
    private TextView titlePersonal;
    private ViewPager mPager;//页卡内容
    private List<View> listViews; // Tab页面列表
    private ImageView imageView;
    private ImageView functionIV1;
    private ImageView functionIV2;
    private ImageView functionIV3;
    private ImageView functionIV4;
    private EditText userIdET;
    private EditText nameET;
    private EditText telET;
    private RadioGroup sexRG;
    private EditText passwordET;
    private TextView editTV;
    private Button saveBT;
    private EditText oldPwdET;
    private EditText newPwdET;
    private TextView oldPwdTV;
    private TextView newPwdTV;
    private EditText sexET;
    private Intent flagIntent;
    private String identify;
    private String userId;
    private Bitmap cursor;
    private int offSet;
    private Matrix matrix = new Matrix();
    private int bmWidth;
    private Animation animation;
    private final int SHOW_RESPONSE = 0;
    private final int SEND_REQUEST = 1;
    private final Gson gson = new GsonBuilder().create();
    private String url;

    private Handler handler= new Handler(){
        public void handleMessage(Message message){
            switch(message.what){
                case SHOW_RESPONSE:{
                    String response = (String) message.obj;
                    if("worker".equals(identify))
                    {
                        Worker worker = gson.fromJson(response,new TypeToken<Worker>(){}.getType());
                        userIdET.setText(worker.getWorkerId());
                        nameET.setText(worker.getName());
                        passwordET.setText(worker.getPassword());
                        telET.setText(worker.getTel());
                        if("男".equals(worker.getSex())){
                            sexRG.check(R.id.sex_YRB);
                            sexET.setText("男");
                        }else {
                            sexRG.check(R.id.sex_XRB);
                            sexET.setText("女");
                        }
                    }
                    else
                    {
                        Administrator administrator = gson.fromJson(response,new TypeToken<Administrator>(){}.getType());
                        userIdET.setText(administrator.getAdministratorId());
                        nameET.setText(administrator.getName());
                        passwordET.setText(administrator. getPassword());
                        telET.setText(administrator.getTel());
                        if("男".equals(administrator.getSex())){
                            sexRG.check(R.id.sex_YRB);
                            sexET.setText("男");
                        }else {
                            sexRG.check(R.id.sex_XRB);
                            sexET.setText("女");
                        }
                    }
                    break;
                }//向服务端请求登陆的用户信息

                case SEND_REQUEST:{
                    String data = gson.toJson(message.obj);
                    if("worker".equals(identify))
                    {
                        url ="http://192.168.56.1:8080/update_worker";
                    }
                    else {
                        url ="http://192.168.56.1:8080/update_administrator";
                    }
                    HttpUtil.postHttpRequest(url , data, new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            Log.v("onFinish","信息修改成功");
                        }

                        @Override
                        public void onError(Exception e) {
                        }
                    });
                    break;
                }//向服务端发送修改的用户信息
            }
        }
    };

    @Override
    protected void onCreate(Bundle safeInstanceState){
        super.onCreate(safeInstanceState);
        setContentView(R.layout.main_view);
        flagIntent = getIntent();
        identify = flagIntent.getStringExtra("identify");
        userId = flagIntent.getStringExtra("userId");
        InitViewPager();
        InitTextView();
        InitCursor();
        if("worker".equals(identify))
        {
            url = "http://192.168.56.1:8080/find_worker?workerId="+userId;
        }
        else {
            url ="http://192.168.56.1:8080/find_administrator?administratorId="+userId;
        }
                    HttpUtil.getHttpResponse(url, new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            Message message = new Message();
                            message.what = SHOW_RESPONSE;
                            message.obj = response;
                            handler.sendMessage(message);
                        }
                        @Override
                        public void onError(Exception e) {
                            Log.v("onError" , "=====");
                        }
                    });

    }//onCreate

    private void InitCursor()
    {
        imageView = (ImageView) findViewById (R.id.cursor);
        cursor = BitmapFactory.decodeResource(getResources(), R.mipmap.cursor);
        bmWidth = cursor.getWidth();
        DisplayMetrics dm;
        dm = getResources().getDisplayMetrics();
        offSet = (dm.widthPixels - 2 * bmWidth) / 4;
        matrix.setTranslate(offSet, 0);
        imageView.setImageMatrix(matrix); //需要iamgeView的scaleType为matrix
    }

    private void InitTextView() {
        titleFunction = (TextView) findViewById(R.id.titleFunction);
        titlePersonal = (TextView) findViewById(R.id.titlePersonal);
        titleFunction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(0);
            }
        });
        titlePersonal.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(1);
            }
        });
    }//初始化Tab页头

    private void InitViewPager(){
        mPager = (ViewPager) findViewById(R.id.vPager);
        listViews = new ArrayList<View>();
        LayoutInflater mInflater = getLayoutInflater();
        //inflate方法实际上是将xml文件解析为一个view对象
        View functionView = mInflater.inflate(R.layout.function_view, null);
        final View personalView = mInflater.inflate(R.layout.person_view, null);
        //************对function_view下的控件事件绑定******************
        functionIV1 = (ImageView)functionView.findViewById(R.id.functionIV1);
        functionIV2 = (ImageView)functionView.findViewById(R.id.functionIV2);
        functionIV3 = (ImageView)functionView.findViewById(R.id.functionIV3);
        functionIV4 = (ImageView)functionView.findViewById(R.id.functionIV4);
        functionIV1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if("worker".equals(identify)){
                Intent intent = new Intent(MainActivity.this,PatrolWorkerActivity.class);
                startActivity(intent);
                }
               else if("administrator".equals(identify))
                {
                    Intent intent = new Intent(MainActivity.this,PatrolAdministratorActivity.class);
                    startActivity(intent);
                }
            }
        });
        functionIV2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,OnDutyActivity.class);
                    intent.putExtra("identify", identify);
                    startActivity(intent);
                }
        });
        functionIV3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if("worker".equals(identify)){
                    Intent intent = new Intent(MainActivity.this,FixWorkerActivity.class);
                    startActivity(intent);
                }
                else if("administrator".equals(identify)){
                    Intent intent = new Intent(MainActivity.this,FixAdministratorActivity.class);
                    startActivity(intent);}
            }
        });
        functionIV4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("worker".equals(identify)) {
                    Intent intent = new Intent(MainActivity.this, ContractsActivity.class);
                    startActivity(intent);
                }
                else{
                    //不执行动作
                 }
            }

        });

        //为personal_view添加事件
        userIdET = (EditText)personalView.findViewById(R.id.user_idET);
        nameET = (EditText)personalView.findViewById(R.id.nameET);
        telET = (EditText)personalView.findViewById(R.id.telET);
        passwordET = (EditText)personalView.findViewById(R.id.passwordET);
        sexRG = (RadioGroup)personalView.findViewById(R.id.sexRG);
        editTV = (TextView)personalView.findViewById(R.id.editTV);
        saveBT = (Button)personalView.findViewById(R.id.saveBT);
        oldPwdET = (EditText)personalView.findViewById(R.id.oldPwdET);
        newPwdET = (EditText)personalView.findViewById(R.id.newPwdET);
        oldPwdTV = (TextView)personalView.findViewById(R.id.oldPwdTV);
        newPwdTV = (TextView)personalView.findViewById(R.id.newPwdTV);
        sexET = (EditText)personalView.findViewById(R.id.sexET);

        editTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                nameET.setEnabled(true);
                telET.setEnabled(true);
                saveBT.setVisibility(View.VISIBLE);
                oldPwdTV.setVisibility(View.VISIBLE);
                newPwdTV.setVisibility(View.VISIBLE);
                oldPwdET.setVisibility(View.VISIBLE);
                newPwdET.setVisibility(View.VISIBLE);
            }
        });
        saveBT.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                if ("worker".equals(identify)) {
                    Worker worker = new Worker();
                    worker.setName(nameET.getText().toString());
                    worker.setTel(telET.getText().toString());
                    worker.setWorkerId(userIdET.getText().toString());
                    if (oldPwdET.getText().toString().trim().length() != 0 && oldPwdET.getText().toString().equals(passwordET.getText().toString())) {
                        if (newPwdET.getText().toString().trim().length() != 0) {
                            worker.setPassword(newPwdET.getText().toString());
                            passwordET.setText(newPwdET.getText().toString());
                        } else {
                            Toast.makeText(MainActivity.this, "请输入新密码", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } else if (oldPwdET.getText().toString().trim().length() != 0 && !oldPwdET.getText().toString().equals(passwordET.getText().toString())) {
                        Toast.makeText(MainActivity.this, "原密码有误", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (oldPwdET.getText().toString().trim().length() == 0 && newPwdET.getText().toString().trim().length() != 0) {
                        Toast.makeText(MainActivity.this, "请输入原密码", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        worker.setPassword(passwordET.getText().toString());
                    }
                    worker.setSex(sexET.getText().toString());
                    message.obj = worker;
                }
                if ("administrator".equals(identify)) {
                    Administrator administrator = new Administrator();
                    administrator.setName(nameET.getText().toString());
                    administrator.setTel(telET.getText().toString());
                    administrator.setAdministratorId(userIdET.getText().toString());
                    if (oldPwdET.getText().toString().trim().length() != 0 && oldPwdET.getText().toString().equals(passwordET.getText().toString())) {
                        if (newPwdET.getText().toString().trim().length() != 0) {
                            administrator.setPassword(newPwdET.getText().toString());
                            passwordET.setText(newPwdET.getText().toString());
                        } else {
                            Toast.makeText(MainActivity.this, "请输入新密码", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } else if (oldPwdET.getText().toString().trim().length() != 0 && !oldPwdET.getText().toString().equals(passwordET.getText().toString())) {
                        Toast.makeText(MainActivity.this, "原密码有误", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (oldPwdET.getText().toString().trim().length() == 0 && newPwdET.getText().toString().trim().length() != 0) {
                        Toast.makeText(MainActivity.this, "请输入原密码", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        administrator.setPassword(passwordET.getText().toString());
                    }
                    administrator.setSex(sexET.getText().toString());
                    message.obj = administrator;
                }
                    message.what = SEND_REQUEST;
                    handler.sendMessage(message);
                    nameET.setEnabled(false);
                    telET.setEnabled(false);
                    saveBT.setVisibility(View.GONE);
                    oldPwdTV.setVisibility(View.INVISIBLE);
                    newPwdTV.setVisibility(View.INVISIBLE);
                    oldPwdET.setText(null);
                    newPwdET.setText(null);
                    oldPwdET.setVisibility(View.GONE);
                    newPwdET.setVisibility(View.GONE);

                }
            }

            );
            //将function_view视图添加进listViews
            listViews.add(functionView);
            listViews.add(personalView);

            mPager.setAdapter(new

            MyPagerAdapter(listViews)

            );

            mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()

                                           {
                                               @Override
                                               public void onPageSelected(int arg0) {
                                                   //当滑动时，顶部的imageView是通过animation缓慢的滑动
                                                   // TODO Auto-generated method stub
                                                   switch (arg0) {
                                                       case 0:
                                                           animation = new TranslateAnimation(offSet * 2 + bmWidth, 0, 0, 0);
                                                           break;
                                                       case 1:
                                                           animation = new TranslateAnimation(0, offSet * 2 + bmWidth, 0, 0);
                                                           break;
                                                   }

                                                   animation.setDuration(500);
                                                   animation.setFillAfter(true);
                                                   imageView.startAnimation(animation);
                                               }

                                               @Override
                                               public void onPageScrolled(int arg0, float arg1, int arg2) {
                                                   // TODO Auto-generated method stub

                                               }

                                               @Override
                                               public void onPageScrollStateChanged(int arg0) {
                                                   // TODO Auto-generated method stub

                                               }
                                           }

            );
            mPager.setCurrentItem(0);
        }//InitViewPager()初始化滑动界面


}
