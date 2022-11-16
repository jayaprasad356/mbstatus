package com.marwadibrothers.mbstatus.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WaterMark {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("data")
    @Expose
    public WatermarkData data;

    public WatermarkData getData() {
        return data;
    }

    public void setData(WatermarkData data) {
        this.data = data;
    }

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
}
