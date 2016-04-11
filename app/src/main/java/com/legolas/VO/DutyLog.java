package com.legolas.VO;

/**
 * Created by legolas on 2016/3/30.
 */
public class DutyLog {
    private Integer id;

    private String date;

    private String shift;

    private String area;

    private Administrator administrator;

    private Worker worker;

    private String content;

    private String affairs;

    private String remark;

    private String equip;

    private char audit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAffairs() {
        return affairs;
    }

    public void setAffairs(String affairs) {
        this.affairs = affairs;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEquip() {
        return equip;
    }

    public void setEquip(String equip) {
        this.equip = equip;
    }

    public char getAudit() {
        return audit;
    }

    public void setAudit(char audit) {
        this.audit = audit;
    }
}
