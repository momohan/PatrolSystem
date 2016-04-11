package com.legolas.dao;

import com.legolas.entity.PatrolProcess;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by legolas on 2016/3/30.
 */
@Transactional
public interface PatrolProcessDao extends CrudRepository<PatrolProcess,Integer> {
    List<PatrolProcess> findAll();
}
