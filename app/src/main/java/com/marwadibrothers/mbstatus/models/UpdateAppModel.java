package com.marwadibrothers.mbstatus.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateAppModel {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("message")
    @Expose
    public String message;


    @SerializedName("data")
    @Expose
    public UpdateAppData data;

    public UpdateAppData getData() {
        return data;
    }

    public void setData(UpdateAppData data) {
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
