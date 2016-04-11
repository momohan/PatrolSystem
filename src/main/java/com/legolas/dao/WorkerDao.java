package com.legolas.dao;

import com.legolas.entity.Worker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by legolas on 2016/3/30.
 */
@Transactional
public interface WorkerDao extends CrudRepository<Worker , String> {
     Worker findByWorkerIdAndPassword(String workerId,String password);
     Worker findByWorkerId(String workerId);
}
