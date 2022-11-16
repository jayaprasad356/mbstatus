package com.marwadibrothers.mbstatus.models.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BannerSection {

    @SerializedName("banner_id")
    @Expose
    private String bannerId;
    @SerializedName("sub_category_id")
    @Expose
    private String subCategoryId;
    @SerializedName("banner_image")
    @Expose
    private String bannerImage;
    @SerializedName("sub_category_name")
    @Expose
    private String sub_category_name;
    @SerializedName("external_link")
    @Expose
    private String external_link;

    public String getExternal_link() {
        return external_link;
    }

    public void setExternal_link(String external_link) {
        this.external_link = external_link;
    }

    public String getSub_category_name() {
        return sub_category_name;
    }

    public void setSub_category_name(String sub_category_name) {
        this.sub_category_name = sub_category_name;
    }

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

}
