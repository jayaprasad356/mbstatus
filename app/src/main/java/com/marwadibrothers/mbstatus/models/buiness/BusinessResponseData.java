package com.marwadibrothers.mbstatus.models.buiness;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusinessResponseData {

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
    private boolean setselectedAcc = false;

    public boolean isSetselectedAcc() {
        return setselectedAcc;
    }

    public void setSetselectedAcc(boolean setselectedAcc) {
        this.setselectedAcc = setselectedAcc;
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
        return "+91 "+businessPhoneNo;
    }

    public String getBusinessPhoneNowithoutcode() {
        return businessPhoneNo;
    }

    public void setBusinessPhoneNo(String businessPhoneNo) {
        this.businessPhoneNo = businessPhoneNo;
    }


}
