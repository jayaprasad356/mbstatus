package com.marwadibrothers.mbstatus.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PopUpModel {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("data")
    @Expose
    public PopUpData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PopUpData getData() {
        return data;
    }

    public void setData(PopUpData data) {
        this.data = data;
    }
}
