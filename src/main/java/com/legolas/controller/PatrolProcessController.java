package com.legolas.controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.legolas.dao.PatrolProcessDao;
import com.legolas.entity.PatrolProcess;
import org.apache.juli.logging.Log;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by legolas on 2016/4/5.
 */
@Controller
public class PatrolProcessController {
    private final Logger Log = Logger.getLogger(PatrolProcessController.class);
    private final Gson gson =new GsonBuilder().create();
    @Autowired
    private PatrolProcessDao patrolProcessDao;

    @RequestMapping(value = "/save_patrolprocess" ,method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody PatrolProcess patrolProcess){
        patrolProcessDao.save(patrolProcess);
    }

    @RequestMapping(value = "/get_patorlprocess" , method = RequestMethod.GET)
    @ResponseBody
    public List<PatrolProcess> get(){
        List<PatrolProcess> patrolProcessList = patrolProcessDao.findAll();
        return patrolProcessList;
    }
}
