package com.fitnessfriends.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Users") 
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int userId;

    @Column(nullable = false, unique = true) 
    private String username;

    @Column(nullable = false) 
    private String password;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}