package com.marwadibrothers.mbstatus.models.subscriptionplan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlanPrice {

    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("price")
    @Expose
    private Integer price;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
