package com.marwadibrothers.mbstatus.models.subscriptionplan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlanResponseData {

    @SerializedName("business_plan_id")
    @Expose
    private String businessPlanId;
    @SerializedName("plan_name")
    @Expose
    private String planName;
    @SerializedName("plan_description")
    @Expose
    private String planDescription;
    @SerializedName("plan_type")
    @Expose
    private String planType;
    @SerializedName("business_limit")
    @Expose
    private String businessLimit;
    @SerializedName("plan_duration")
    @Expose
    private String planDuration;
    @SerializedName("plan_duration_type")
    @Expose
    private String planDurationType;
    @SerializedName("plan_amount")
    @Expose
    private String planAmount;
    @SerializedName("plan_image")
    @Expose
    private String planImage;

    public String getBusinessPlanId() {
        return businessPlanId;
    }

    public void setBusinessPlanId(String businessPlanId) {
        this.businessPlanId = businessPlanId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getBusinessLimit() {
        return businessLimit;
    }

    public void setBusinessLimit(String businessLimit) {
        this.businessLimit = businessLimit;
    }

    public String getPlanDuration() {
        return planDuration;
    }

    public void setPlanDuration(String planDuration) {
        this.planDuration = planDuration;
    }

    public String getPlanDurationType() {
        return planDurationType;
    }

    public void setPlanDurationType(String planDurationType) {
        this.planDurationType = planDurationType;
    }

    public String getPlanAmount() {
        return planAmount;
    }

    public void setPlanAmount(String planAmount) {
        this.planAmount = planAmount;
    }

    public String getPlanImage() {
        return planImage;
    }

    public void setPlanImage(String planImage) {
        this.planImage = planImage;
    }

}
