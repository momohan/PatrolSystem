package com.legolas.VO;


/**
 * Created by legolas on 2016/4/7.
 */
public class ListViewVO {
    private Integer id;
    private String name;
    private String date;
    private char audit;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public char getAudit() {
        return audit;
    }

    public void setAudit(char audit) {
        this.audit = audit;
    }
}
