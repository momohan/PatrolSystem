package com.legolas;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by legolas on 2016/4/8.
 */
public class Test {
    public static void main(String args[]){
        String str = "2016/4/4";

        System.out.println(stringtoDate(str , "yyyy-MM-dd")+"");
    }
    public static java.util.Date stringtoDate(String dateStr, String format) {
        Date d = null;
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            formater.setLenient(false);
            d = formater.parse(dateStr);
        } catch (Exception e) {
            d = null;
        }
        return d;
    }
}
