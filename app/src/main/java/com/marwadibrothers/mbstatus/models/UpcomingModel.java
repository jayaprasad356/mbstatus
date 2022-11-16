package com.marwadibrothers.mbstatus.models;

public class UpcomingModel {
    String title, date;
    int img;

    public UpcomingModel(String title, String date, int img) {
        this.date = date;
        this.title = title;
        this.img = img;
    }

    public int getImg() {
        return img;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }
}
