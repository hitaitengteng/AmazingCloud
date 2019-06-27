package com.whut.zuul.domain;


import javax.persistence.*;

@Entity
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column
    private String contentname;

    @Column
    private String contentcode;

    @Column
    private String content;

    public Content() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContentname() {
        return contentname;
    }

    public void setContentname(String contentname) {
        this.contentname = contentname;
    }

    public String getContentcode() {
        return contentcode;
    }

    public void setContentcode(String contentcode) {
        this.contentcode = contentcode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
