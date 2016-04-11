package com.legolas.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * Created by legolas on 2016/3/30.
 */
@Entity
@Table(name = "patrol_process")
public class PatrolProcess {
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

    @ManyToOne
    @JoinColumn(name ="administratorId")
    @NotNull
    private Administrator administrator;

    @ManyToOne
    @JoinColumn(name = "workerId")
    @NotNull
    private Worker worker;

    @Column(length = 20)
    @NotNull
    private String apartment;

    @Column(length = 16)
    @NotNull
    private String area;

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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public char getAudit() {
        return audit;
    }

    public void setAudit(char audit) {
        this.audit = audit;
    }
}
