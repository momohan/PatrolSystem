package com.legolas.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.legolas.dao.AdministratorDao;
import com.legolas.entity.Administrator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by legolas on 2016/3/30.
 */
@Controller
public class AdministratorController {
    private final Logger log = Logger.getLogger(AdministratorController.class);
    private final Gson gson = new GsonBuilder().create();

    @Autowired
    private AdministratorDao administratorDao;

    @RequestMapping(value = "/find_administrator" , method = RequestMethod.GET)
    @ResponseBody
    public Administrator findAdministrator(String administratorId){
        Administrator administrator  = administratorDao.findByAdministratorId(administratorId);
        return administrator;
    }


    @RequestMapping(value = "/check_administrator" , method = RequestMethod.GET)
    @ResponseBody
    public int checkAdministrator(String administratorId, String password){
        Administrator administrator  = administratorDao.findByAdministratorIdAndPassword(administratorId, password);
        if(administrator!=null) return 1;
        else return 0;
    }

    @RequestMapping(value = "/update_administrator", method = RequestMethod.POST)
    @ResponseBody
    public void saveAdministrator(@RequestBody Administrator administrator){
        administratorDao.save(administrator);
    }

}