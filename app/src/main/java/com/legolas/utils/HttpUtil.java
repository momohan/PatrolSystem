package com.legolas.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by legolas on 2016/4/2.
 */
public class HttpUtil {
    public static void getHttpResponse(final String address, final HttpCallbackListener listener){
        new Thread(new Runnable(){
            @Override
                public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection)
                    url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);
                    connection.setDoInput(true);
                    /*connection.setDoOutput(true);*/
                    /*4.0中设置httpCon.setDoOutput(true),将导致请求以post方式提交,
                    即使设置了httpCon.setRequestMethod("GET");
                    将代码中的httpCon.setDoOutput(true);删除即可*/
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }if(listener != null){
                        //回调onFinish方法；
                    listener.onFinish(response.toString());
                    }

                } catch (Exception e) {
                  if(listener != null){
                      listener.onError(e);
                  }
                } finally {
                    if (connection != null)
                        connection.disconnect();
                }
            }
        }).start();
    }

    public static void postHttpRequest(final String address,final String data, final HttpCallbackListener listener){
        new Thread(new Runnable(){
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    OutputStream os = connection.getOutputStream();
                    os.write(data.getBytes());
                    os.flush();
                    os.close();
                    int code = connection.getResponseCode();
                    if(listener != null){
                        //回调onFinish方法；
                        listener.onFinish(String.valueOf(code));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    if(listener != null){
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null)
                        connection.disconnect();
                }
            }
        }).start();
    }

}
