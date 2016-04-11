package com.legolas.VO;

/**
 * Created by legolas on 2016/3/30.
 */

public class Battery {
    private Integer id;

    private String deviceName;

    private String recordDate;

    private Worker worker;

    private String submitPerson;

    private String copyDate;

    private String highBattery;

    private String middleBattery;

    private String lowBattery;

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
