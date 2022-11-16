package com.marwadibrothers.mbstatus.models.buiness;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusinessResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<BusinessResponseData> data = null;
    @SerializedName("extra_data")
    @Expose
    private BusinessExtraData extraData = null;

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

    public List<BusinessResponseData> getData() {
        return data;
    }

    public void setData(List<BusinessResponseData> data) {
        this.data = data;
    }

    public BusinessExtraData getExtraData() {
        return extraData;
    }

    public void setExtraData(BusinessExtraData extraData) {
        this.extraData = extraData;
    }
}
