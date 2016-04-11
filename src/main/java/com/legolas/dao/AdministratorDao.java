package com.legolas.dao;

import com.legolas.entity.Administrator;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by legolas on 2016/3/30.
 */
@Transactional
public interface AdministratorDao extends CrudRepository<Administrator,String> {
    Administrator findByAdministratorIdAndPassword(String administratorId,String password);
    Administrator findByAdministratorId(String administratorId);
}
