package com.legolas.controller;

import com.legolas.dao.BatteryDao;
import com.legolas.dao.DutyLogDao;
import com.legolas.entity.Battery;
import com.legolas.entity.DutyLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by legolas on 2016/4/6.
 */
@Controller
public class BatteryController {
    @Autowired
    BatteryDao batteryDao;

    @RequestMapping(value = "save_battery" , method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody Battery battery){
        batteryDao.save(battery);
    }

    @RequestMapping(value = "/get_battery" , method = RequestMethod.GET)
    @ResponseBody
    public List<Battery> get(){
        List<Battery> batteryList = batteryDao.findAll();
        return batteryList;
    }
}
