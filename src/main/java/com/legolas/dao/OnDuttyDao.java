package com.legolas.dao;

import com.legolas.entity.OnDuty;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by legolas on 2016/3/31.
 */
public interface OnDuttyDao extends CrudRepository<OnDuty,Integer> {
    List<OnDuty> findAll();
}
