package com.marwadibrothers.mbstatus.models;

public class NotificationModel {
    String txt_Title, txt_time;

    public NotificationModel(String txt_time, String txt_Title) {
        this.txt_time = txt_time;
        this.txt_Title = txt_Title;
    }

    public String getTxt_time() {
        return txt_time;
    }

    public String getTxt_Title() {
        return txt_Title;
    }
}
