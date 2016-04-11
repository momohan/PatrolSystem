package com.legolas.activity;


import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.legolas.utils.HttpCallbackListener;
import com.legolas.utils.HttpUtil;
import android.os.Handler;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    private Button loginBT;
    private RadioGroup loginRG;
    private String url;
    private String identify = "worker";
    private final int SHOW_RESPONSE = 0;
    private EditText userET;
    private EditText pwdET;
    private Gson gson = new GsonBuilder().create();
    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case SHOW_RESPONSE: {
                    String response = (String) message.obj;
                    if("1".equals(response))
                    {
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("identify", identify);
                    intent.putExtra("userId", userET.getText().toString());
                    startActivity(intent);
                    }
                    else{
                        userET.setText(null);
                        pwdET.setText(null);
                        Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        }
    };//向服务端请求登陆的用户信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);
        loginRG = (RadioGroup)findViewById(R.id.loginRG);
        loginRG.setOnCheckedChangeListener(this);
        loginBT = (Button)findViewById(R.id.loginBT);
        userET = (EditText)findViewById(R.id.userET);
        pwdET = (EditText)findViewById(R.id.pwdET);
        loginBT.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view){
                if("worker".equals(identify))
                {
                    url="http://192.168.56.1:8080/check_worker?workerId="+userET.getText().toString()+"&password="+pwdET.getText().toString();
                            /*check_worker?workerId="+userET.getText().toString()+"&password="+pwdET.getText().toString();*/
                }
                else {
                    url ="http://192.168.56.1:8080/check_administrator?administratorId="+userET.getText().toString()+"&password="+pwdET.getText().toString();
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
                        e.printStackTrace();
                    }
                });
            }
        });
    }
    //重写RadioGroup.OnCheckedChangeListener接口中的onCheckedChanged方法
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch(checkedId){
            case R.id.worker:
                identify = "worker";
                break;
            case R.id.administrator:
                identify = "administrator";
        }
    }
}
