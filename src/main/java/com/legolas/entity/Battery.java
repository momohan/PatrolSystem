package com.legolas.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by legolas on 2016/3/30.
 */
@Entity
@Table(name = "battery")
public class Battery {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 16)
    @NotNull
    private String deviceName;

    @Column
    @NotNull
    private String recordDate;

    @ManyToOne
    @JoinColumn(name = "workerId")
    @NotNull
    private Worker worker;

    @Column(length = 16)
    @NotNull
    private String submitPerson;

    @Column
    @NotNull
    private String copyDate;

    @Column(length = 16)
    @NotNull
    private String highBattery;

    @Column(length = 16)
    @NotNull
    private String middleBattery;

    @Column(length = 16)
    @NotNull
    private String lowBattery;

    @Column(length = 2)
    @NotNull
    private char audit;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public String getSubmitPerson() {
        return submitPerson;
    }

    public void setSubmitPerson(String submitPerson) {
        this.submitPerson = submitPerson;
    }

    public String getCopyDate() {
        return copyDate;
    }

    public void setCopyDate(String copyDate) {
        this.copyDate = copyDate;
    }

    public String getHighBattery() {
        return highBattery;
    }

    public void setHighBattery(String highBattery) {
        this.highBattery = highBattery;
    }

    public String getMiddleBattery() {
        return middleBattery;
    }

    public void setMiddleBattery(String middleBattery) {
        this.middleBattery = middleBattery;
    }

    public String getLowBattery() {
        return lowBattery;
    }

    public void setLowBattery(String lowBattery) {
        this.lowBattery = lowBattery;
    }

    public char getAudit() {
        return audit;
    }

    public void setAudit(char audit) {
        this.audit = audit;
    }
}
