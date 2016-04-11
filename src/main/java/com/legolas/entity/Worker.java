package com.legolas.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
/**
 * Created by legolas on 2016/3/30.
 */
//工作人员表
@Entity
@Table(name = "worker")
public class Worker {
    @Id
    @Column( length=16)
    private String workerId;

    @Column(length = 16)
    @NotNull
    private String name;

    @Column(length = 16)
    @NotNull
    private String password;

    @Column(length = 2)
    @NotNull
    private String sex;

    @Column(length = 16)
    @NotNull
    private String tel;


    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
