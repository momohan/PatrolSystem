package com.legolas.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by legolas on 2016/3/30.
 */
@Entity
@Table(name="on_duty")
public class OnDuty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 16)
    @NotNull
    private String documentName;

    @Column(length = 16)
    @NotNull
    private String deviceName;

    @Column
    @NotNull
    private String recordDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "administratorId")
    private Administrator administrator;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "workerId")
    private Worker worker;

    @Column(length = 16)
    @NotNull
    private String apartment;

    @Lob
    @NotNull
    private String content;

    @Column(length = 2)
    @NotNull
    private char audit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
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

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public char getAudit() {
        return audit;
    }

    public void setAudit(char audit) {
        this.audit = audit;
    }
}
