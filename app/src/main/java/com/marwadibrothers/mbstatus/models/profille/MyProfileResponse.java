package com.marwadibrothers.mbstatus.models.profille;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyProfileResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private MyProfileResponseData data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MyProfileResponseData getData() {
        return data;
    }

    public void setData(MyProfileResponseData data) {
        this.data = data;
    }

}
