package com.whut.zuul.domain;

import javax.persistence.*;

@Entity
@Table(name = "y6item")
public class Y6Item {
    @Id
    @GeneratedValue
    private String tag;
    @Column(name = "desc")
    private String desc;
    @Column(name ="address")
    private String address;
    @Column(name ="type")
    private String type;
    @Column(name ="factory")
    private String factory;
    @Column(name ="conitem")
    private String conitem;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getConitem() {
        return conitem;
    }

    public void setConitem(String conitem) {
        this.conitem = conitem;
    }
}
