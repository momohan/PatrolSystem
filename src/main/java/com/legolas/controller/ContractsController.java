package com.legolas.controller;

import com.legolas.dao.ContractsDao;
import com.legolas.entity.Contracts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by legolas on 2016/4/7.
 */
@Controller
public class ContractsController {
    @Autowired
    ContractsDao contractsDao;
    @RequestMapping(value = "save_contracts" , method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody Contracts contracts){
        contractsDao.save(contracts);
    }
}
