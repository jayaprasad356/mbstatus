package com.marwadibrothers.mbstatus.models.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeResponseData {

    @SerializedName("sub_category_id")
    @Expose
    private String subCategoryId;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("sub_category_name")
    @Expose
    private String subCategoryName;
    @SerializedName("sub_category_date")
    @Expose
    private String subCategoryDate = "";
    @SerializedName("sub_category_image")
    @Expose
    private String subCategoryImage;
    @SerializedName("sub_category_status")
    @Expose
    private String sub_category_status;

    public String getSub_category_status() {
        return sub_category_status;
    }

    public void setSub_category_status(String sub_category_status) {
        this.sub_category_status = sub_category_status;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getSubCategoryDate() {
        // Get date from string

        return subCategoryDate;
    }

    public void setSubCategoryDate(String subCategoryDate) {
        this.subCategoryDate = subCategoryDate;
    }

    public String getSubCategoryImage() {
        return subCategoryImage;
    }

    public void setSubCategoryImage(String subCategoryImage) {
        this.subCategoryImage = subCategoryImage;
    }

}

