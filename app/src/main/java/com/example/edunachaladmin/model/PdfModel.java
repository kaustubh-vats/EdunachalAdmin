package com.example.edunachaladmin.model;

public class PdfModel {
    String name, url, storagename;

    public PdfModel(String name, String url, String storagename) {
        this.name = name;
        this.url = url;
        this.storagename = storagename;
    }

    public PdfModel() {
    }

    public String getStoragename() {
        return storagename;
    }

    public void setStoragename(String storagename) {
        this.storagename = storagename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
