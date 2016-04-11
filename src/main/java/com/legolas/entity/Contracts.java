package com.legolas.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by legolas on 2016/3/31.
 */
@Entity
@Table(name = "contracts")
public class Contracts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column (length = 16)
    @NotNull
    private String name;

    @Column (length = 16)
    @NotNull
    private String type;

    @Column
    @NotNull
    private String date;

    @ManyToOne
    @JoinColumn(name ="workerId")
    private Worker worker;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}
