package com.legolas.controller;

import com.legolas.dao.SignContractDao;
import com.legolas.entity.SignContract;
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
public class SignContractController {
    @Autowired
    SignContractDao signContractDao;

    @RequestMapping(value = "/save_signcontract", method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody SignContract signContract) {
        signContractDao.save(signContract);
    }
}
