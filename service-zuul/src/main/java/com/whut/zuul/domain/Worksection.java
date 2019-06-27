package com.whut.zuul.domain;



import javax.persistence.*;

@Entity
public class Worksection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column
    private String worksectionname;

    @Column
    private String worksectioncode;

    @Column
    private String num;

    public Worksection() {
    }

    public String getId() {
        return id;
    }

    public Worksection(String worksectionname, String worksectioncode, String id) {
        this.worksectionname = worksectionname;
        this.worksectioncode = worksectioncode;
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorksectionname() {
        return worksectionname;
    }

    public void setWorksectionname(String worksectionname) {
        this.worksectionname = worksectionname;
    }

    public String getWorksectioncode() {
        return worksectioncode;
    }

    public void setWorksectioncode(String worksectioncode) {
        this.worksectioncode = worksectioncode;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
