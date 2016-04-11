package com.legolas.dao;

import com.legolas.entity.Fix;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by legolas on 2016/3/31.
 */
public interface FixDao extends CrudRepository<Fix,Integer> {
    List<Fix> findAll();
}
