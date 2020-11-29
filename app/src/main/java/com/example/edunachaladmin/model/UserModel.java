package com.example.edunachaladmin.model;

public class UserModel {
    String name;
    String email;
    String phone;
    String domain;

    public UserModel(String name, String email, String phone, String domain) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.domain = domain;
    }

    public UserModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
