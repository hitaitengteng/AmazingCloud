package com.whut.zuul.domain;

import javax.persistence.*;

/**
 * 用来封装用户登陆后提交的用户信息，比如用户名、密码等
 */
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(nullable = false)
    private String username;//对应登陆界面的用户名
    @Column(nullable = false)
    private String password;//对应登陆界面的密码
    @Column(nullable = false)
    private String role;//用户的角色
    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(String name, String password, String loginkeeping) {
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
