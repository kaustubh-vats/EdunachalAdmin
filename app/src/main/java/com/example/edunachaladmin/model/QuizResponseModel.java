package com.example.edunachaladmin.model;

public class QuizResponseModel {
    String uid;
    String name;
    int obtained;
    int total;
    float percentage;

    public QuizResponseModel(String uid, String name, int obtained, int total, float percentage) {
        this.uid = uid;
        this.name = name;
        this.obtained = obtained;
        this.total = total;
        this.percentage = percentage;
    }

    public QuizResponseModel() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getObtained() {
        return obtained;
    }

    public void setObtained(int obtained) {
        this.obtained = obtained;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }
}
