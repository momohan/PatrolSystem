package com.legolas.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;

import com.legolas.VO.ListViewVO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by legolas on 2016/3/25.
 */
public class Util {
    public static Dialog createDatePickerDialog(Context context, DatePickerDialog.OnDateSetListener callback){
        final Calendar c = Calendar.getInstance();
        return  new DatePickerDialog(context,callback, c.get(Calendar.YEAR),
               c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
    }//日期Dialog控件
    public static Dialog createTimePickerDialog(Context context ,TimePickerDialog.OnTimeSetListener callback){
        final Calendar c = Calendar.getInstance();
        return new TimePickerDialog(context,callback,c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE), true);
    }//时间Dialog控件
        /*context：返回Dialog的上下文环境
        *callback：用户选择好事件后，通知应用的回调函数
        * 最后一个参数is24HourView：是否使用24小时制
        *
        *
    ==============================================================
        submitTV.setOnClickListener(new TVSubmitOnClickListener());

        class TVSubmitOnClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            Util.createTimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hour, int minute) {
                    mHour = hour;
                    mMinute = minute;
                    updateTime();
                }
            }).show();
        }
    }
    private void updateTime() {

        tvDate.setText(new StringBuilder().append(mHour).append(":").append(mMinute));

    }
     private void setTime() {

        final Calendar c = Calendar.getInstance();

        mHour = c.get(Calendar.HOUR_OF_DAY);

        mMinute = c.get(Calendar.MINUTE);

        updateTime();

    }
    ===========================================================
        *
        */
    //格式化日期控件
    public static java.util.Date stringtoDate(String dateStr) {
        Date d = null;
        SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
        try {
            formater.setLenient(false);
            d = formater.parse(dateStr);
        } catch (Exception e) {
            d = null;
        }
        return d;
    }
    //对list中的时间降序排列
    @SuppressWarnings("unchecked")
    public static void sortMethod(List<ListViewVO> list){
            Collections.sort(list, new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    ListViewVO l1 = (ListViewVO)o1;
                    ListViewVO l2 = (ListViewVO) o2;
                    if (stringtoDate(l1.getDate()).after(stringtoDate(l2.getDate()))) {
                        return 1;
                    } else if (stringtoDate(l1.getDate()).equals(stringtoDate(l2.getDate()))) {
                        return 0;
                    } else {
                        return -1;
                    }
                }
            });
        }
}
