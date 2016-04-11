package com.legolas.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.legolas.dao.WorkerDao;
import com.legolas.entity.Worker;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by legolas on 2016/3/30.
 */
@Controller
public class WorkerController {
    private final Logger log = Logger.getLogger(WorkerController.class);
    private final Gson gson = new GsonBuilder().create();

    @Autowired
    private WorkerDao workerDao;

    @RequestMapping(value = "/find_worker" , method = RequestMethod.GET)
    @ResponseBody
    public Worker findWorker(String workerId){
        Worker worker  = workerDao.findByWorkerId(workerId);
        return worker;
    }


    @RequestMapping(value = "/check_worker" , method = RequestMethod.GET)
    @ResponseBody
    public int checkWorker(String workerId, String password){
        Worker worker = workerDao.findByWorkerIdAndPassword(workerId, password);
        if(worker!=null) return 1;
        else return 0;
    }

    @RequestMapping(value = "/update_worker", method = RequestMethod.POST)
    @ResponseBody
    public void saveWorker(@RequestBody Worker worker){
        workerDao.save(worker);
    }

}