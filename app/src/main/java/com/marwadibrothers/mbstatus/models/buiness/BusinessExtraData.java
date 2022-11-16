package com.marwadibrothers.mbstatus.models.buiness;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusinessExtraData {
    @SerializedName("business_limit")
    @Expose
    private String business_limit;
    @SerializedName("business_added")
    @Expose
    private Integer business_added;
    @SerializedName("plan_status")
    @Expose
    private Boolean plan_status;

    public String getBusiness_limit() {
        return business_limit;
    }

    public void setBusiness_limit(String business_limit) {
        this.business_limit = business_limit;
    }

    public Integer getBusiness_added() {
        return business_added;
    }

    public void setBusiness_added(Integer business_added) {
        this.business_added = business_added;
    }

    public Boolean getPlan_status() {
        return plan_status;
    }

    public void setPlan_status(Boolean plan_status) {
        this.plan_status = plan_status;
    }
}
