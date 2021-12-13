package com.help.serviceidentities.repo.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public final class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String username;
    private String fullName;
    private String contactInfo;
    private String userType;

    public User() {
    }

    public User(String username, String fullName, String contactInfo, String userType) {
        this.username = username;
        this.fullName = fullName;
        this.contactInfo = contactInfo;
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public long getId() {
        return id;
    }
}
