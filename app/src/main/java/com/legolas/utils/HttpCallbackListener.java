package com.legolas.utils;

/**
 * Created by legolas on 2016/4/2.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
