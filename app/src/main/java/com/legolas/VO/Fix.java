package com.legolas.VO;

/**
 * Created by legolas on 2016/3/31.
 */

public class Fix {
    private Integer id;

    private String apartment;

    private String reportDate;

    private String receiveDate;

    private String finishDate;

    private Worker worker;

    private Administrator administrator ;

    private String area;

    private String tel;

    private String receivePerson;

    private String fixPerson;

    private String fixMaterial;

    private String reportContent;

    private String fixCondition;

    private String unfinishedReason;

    private String apartmentComment;

    private String remark;

    private char audit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getReceivePerson() {
        return receivePerson;
    }

    public void setReceivePerson(String receivePerson) {
        this.receivePerson = receivePerson;
    }

    public String getFixPerson() {
        return fixPerson;
    }

    public void setFixPerson(String fixPerson) {
        this.fixPerson = fixPerson;
    }

    public String getFixMaterial() {
        return fixMaterial;
    }

    public void setFixMaterial(String fixMaterial) {
        this.fixMaterial = fixMaterial;
    }

    public String getReportContent() {
        return reportContent;
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    public String getFixCondition() {
        return fixCondition;
    }

    public void setFixCondition(String fixCondition) {
        this.fixCondition = fixCondition;
    }

    public String getUnfinishedReason() {
        return unfinishedReason;
    }

    public void setUnfinishedReason(String unfinishedReason) {
        this.unfinishedReason = unfinishedReason;
    }

    public String getApartmentComment() {
        return apartmentComment;
    }

    public void setApartmentComment(String apartmentComment) {
        this.apartmentComment = apartmentComment;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public char getAudit() {
        return audit;
    }

    public void setAudit(char audit) {
        this.audit = audit;
    }
}
