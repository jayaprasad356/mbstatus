package com.marwadibrothers.mbstatus.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserBusinessListResponse {

    @SerializedName("business_id")
    @Expose
    private String businessId;
    @SerializedName("business_name")
    @Expose
    private String businessName;
    @SerializedName("business_website")
    @Expose
    private String businessWebsite;
    @SerializedName("business_logo")
    @Expose
    private String businessLogo;
    @SerializedName("business_address")
    @Expose
    private String businessAddress;
    @SerializedName("business_email")
    @Expose
    private String businessEmail;
    @SerializedName("business_phone_no")
    @Expose
    private String businessPhoneNo;
    @SerializedName("business_owner")
    @Expose
    private String businessOwner;
    @SerializedName("business_category")
    @Expose
    private String businessCategory;
    @SerializedName("business_category_name")
    @Expose
    private String businessCategoryName;
    @SerializedName("other_text")
    @Expose
    private String otherText;
    private boolean SelectedAcc = false;


    public void setSelectedAcc(boolean selectedAcc) {
        SelectedAcc = selectedAcc;
    }

    public boolean isSelectedAcc() {
        return SelectedAcc;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessWebsite() {
        return businessWebsite;
    }

    public void setBusinessWebsite(String businessWebsite) {
        this.businessWebsite = businessWebsite;
    }

    public String getBusinessLogo() {
        return businessLogo;
    }

    public void setBusinessLogo(String businessLogo) {
        this.businessLogo = businessLogo;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getBusinessEmail() {
        return businessEmail;
    }

    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }

    public String getBusinessPhoneNo() {
        return businessPhoneNo;
    }

    public void setBusinessPhoneNo(String businessPhoneNo) {
        this.businessPhoneNo = businessPhoneNo;
    }

    public String getBusinessOwner() {
        return businessOwner;
    }

    public void setBusinessOwner(String businessOwner) {
        this.businessOwner = businessOwner;
    }

    public String getBusinessCategory() {
        return businessCategory;
    }

    public void setBusinessCategory(String businessCategory) {
        this.businessCategory = businessCategory;
    }

    public String getBusinessCategoryName() {
        return businessCategoryName;
    }

    public void setBusinessCategoryName(String businessCategoryName) {
        this.businessCategoryName = businessCategoryName;
    }

    public String getOtherText() {
        return otherText;
    }

    public void setOtherText(String otherText) {
        this.otherText = otherText;
    }


}
