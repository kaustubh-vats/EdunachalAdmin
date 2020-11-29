package com.example.edunachaladmin.model;

public class CurrentAffairsModel {
    String curId;
    String date;
    long timestamp;
    String title;
    String content;
    String tag;

    public CurrentAffairsModel(String curId, String date, long timestamp, String title, String content, String tag) {
        this.curId = curId;
        this.date = date;
        this.timestamp = timestamp;
        this.title = title;
        this.content = content;
        this.tag = tag;
    }

    public CurrentAffairsModel() {
    }

    public String getCurId() {
        return curId;
    }

    public void setCurId(String curId) {
        this.curId = curId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
