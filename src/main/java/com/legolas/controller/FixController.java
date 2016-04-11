package com.legolas.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.legolas.dao.FixDao;
import com.legolas.entity.Fix;
import com.legolas.entity.PatrolProcess;
import org.apache.log4j.Logger;
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
public class FixController {
    private final Gson gson =   new GsonBuilder().create();
    private final Logger Log = Logger.getLogger(FixController.class);

    @Autowired
    FixDao fixDao;

    @RequestMapping(value = "/save_fix" ,method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody Fix fix){
        fixDao.save(fix);
    }

    @RequestMapping(value = "/get_fix" , method = RequestMethod.GET)
    @ResponseBody
    public List<Fix> get(){
        List<Fix> fixList = fixDao.findAll();
        return fixList;
    }
}
