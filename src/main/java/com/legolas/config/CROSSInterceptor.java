package com.legolas.config;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.Object;
import java.lang.Override;

/**
 * Created by legolas on 2016/3/16.
 */
//为响应添加一个Header信息，允许请求跨域访问
public class CROSSInterceptor extends HandlerInterceptorAdapter{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        response.setHeader("Access-Control-Allow-Origin","*");
        return true;
    }

}
