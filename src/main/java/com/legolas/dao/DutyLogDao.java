package com.legolas.dao;

import com.legolas.entity.DutyLog;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by legolas on 2016/3/31.
 */
public interface DutyLogDao extends CrudRepository<DutyLog,Integer> {
    List<DutyLog> findAll();
}
