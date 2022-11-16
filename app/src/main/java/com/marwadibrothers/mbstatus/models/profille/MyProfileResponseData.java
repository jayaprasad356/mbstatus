package com.marwadibrothers.mbstatus.models.profille;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyProfileResponseData {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("profile_pic")
    @Expose
    private String profilePic;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("company_org_name")
    @Expose
    private String companyOrgName;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("facebook_username")
    @Expose
    private String facebookUsername;
    @SerializedName("twitter_username")
    @Expose
    private String twitterUsername;
    @SerializedName("instagram_username")
    @Expose
    private String instagramUsername;
    @SerializedName("linkedin_username")
    @Expose
    private String linkedinUsername;
    @SerializedName("whatsapp_username")
    @Expose
    private String whatsappUsername;
    @SerializedName("telegram_username")
    @Expose
    private String telegramUsername;
    @SerializedName("youtube_username")
    @Expose
    private String youtubeUsername;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return "+91"+mobileNumber;
    }
    public String getMobileNumberwithoutcode() {
        return mobileNumber;
    }
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyOrgName() {
        return companyOrgName;
    }

    public void setCompanyOrgName(String companyOrgName) {
        this.companyOrgName = companyOrgName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getFacebookUsername() {
        return facebookUsername;
    }

    public void setFacebookUsername(String facebookUsername) {
        this.facebookUsername = facebookUsername;
    }

    public String getTwitterUsername() {
        return twitterUsername;
    }

    public void setTwitterUsername(String twitterUsername) {
        this.twitterUsername = twitterUsername;
    }

    public String getInstagramUsername() {
        return instagramUsername;
    }

    public void setInstagramUsername(String instagramUsername) {
        this.instagramUsername = instagramUsername;
    }

    public String getLinkedinUsername() {
        return linkedinUsername;
    }

    public void setLinkedinUsername(String linkedinUsername) {
        this.linkedinUsername = linkedinUsername;
    }

    public String getWhatsappUsername() {
        return whatsappUsername;
    }

    public void setWhatsappUsername(String whatsappUsername) {
        this.whatsappUsername = whatsappUsername;
    }

    public String getTelegramUsername() {
        return telegramUsername;
    }

    public void setTelegramUsername(String telegramUsername) {
        this.telegramUsername = telegramUsername;
    }

    public String getYoutubeUsername() {
        return youtubeUsername;
    }

    public void setYoutubeUsername(String youtubeUsername) {
        this.youtubeUsername = youtubeUsername;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
