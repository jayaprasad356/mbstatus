package com.marwadibrothers.mbstatus.models.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentResponseData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("cred_title")
    @Expose
    private String credTitle;
    @SerializedName("cred_value")
    @Expose
    private String credValue;
    @SerializedName("image")
    @Expose
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCredTitle() {
        return credTitle;
    }

    public void setCredTitle(String credTitle) {
        this.credTitle = credTitle;
    }

    public String getCredValue() {
        return credValue;
    }

    public void setCredValue(String credValue) {
        this.credValue = credValue;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
