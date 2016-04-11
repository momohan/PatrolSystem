package com.legolas.controller;

import com.legolas.dao.OnDuttyDao;
import com.legolas.entity.OnDuty;
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
public class OnDutyController {
    @Autowired
    OnDuttyDao onDuttyDao;
    @RequestMapping(value = "save_onduty" , method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody OnDuty onDuty){
        onDuttyDao.save(onDuty);
    }

    @RequestMapping(value = "/get_onduty" , method = RequestMethod.GET)
    @ResponseBody
    public List<OnDuty> get(){
        List<OnDuty> onDutyList = onDuttyDao.findAll();
        return onDutyList;
    }
}
