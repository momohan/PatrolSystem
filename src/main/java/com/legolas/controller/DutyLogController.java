package com.legolas.controller;

import com.legolas.dao.DutyLogDao;
import com.legolas.entity.DutyLog;
import com.legolas.entity.PatrolProcess;
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
public class DutyLogController{
    @Autowired
    DutyLogDao dutyLogDao;

    @RequestMapping(value = "save_dutylog" , method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody DutyLog dutyLog){
        dutyLogDao.save(dutyLog);
    }

    @RequestMapping(value = "/get_dutylog" , method = RequestMethod.GET)
    @ResponseBody
    public List<DutyLog> get(){
        List<DutyLog> dutyLogList = dutyLogDao.findAll();
        return dutyLogList;
    }
}
