package com.legolas.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by legolas on 2016/3/31.
 */
@Entity
@Table(name = "fix")
public class Fix {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 16)
    @NotNull
    private String apartment;

    @Column
    @NotNull
    private String reportDate;

    @Column
    @NotNull
    private String receiveDate;

    @Column
    @NotNull
    private String finishDate;

    @ManyToOne
    @JoinColumn(name = "workerId")
    private Worker worker;

    @ManyToOne
    @JoinColumn(name = "administratorId")
    private Administrator administrator ;

    @Column(length = 16)
    @NotNull
    private String area;

    @Column(length = 16)
    @NotNull
    private String tel;

    @Column(length = 16)
    @NotNull
    private String receivePerson;

    @Column(length = 16)
    @NotNull
    private String fixPerson;

    @Column(length = 25)
    private String fixMaterial;

    @Lob
    private String reportContent;

    @Lob
    private String fixCondition;

    @Lob
    private String unfinishedReason;

    @Lob
    private String apartmentComment;

    @Lob
    private String remark;

    @Column(length = 2)
    @NotNull
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
