package com.legolas.dao;

import com.legolas.entity.Battery;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by legolas on 2016/3/31.
 */
public interface BatteryDao extends CrudRepository<Battery,Integer>{
    List<Battery> findAll();
}
