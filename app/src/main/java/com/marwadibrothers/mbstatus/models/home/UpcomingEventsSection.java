package com.marwadibrothers.mbstatus.models.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpcomingEventsSection {
    @SerializedName("sub_category_id")
    @Expose
    private String subCategoryId;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("sub_category_name")
    @Expose
    private String subCategoryName;
    @SerializedName("sub_category_image")
    @Expose
    private String subCategoryImage;
    @SerializedName("sub_category_date")
    @Expose
    private String sub_category_date;

    public String getSub_category_date() {
        // Get date from string
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormatter.parse(sub_category_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
// Get time from date
        SimpleDateFormat timeFormatter = new SimpleDateFormat("dd MMM");
        sub_category_date = timeFormatter.format(date);
// Done!

        return sub_category_date;
    }

    public void setSub_category_date(String sub_category_date) {
        this.sub_category_date = sub_category_date;
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

    public String getSubCategoryImage() {
        return subCategoryImage;
    }

    public void setSubCategoryImage(String subCategoryImage) {
        this.subCategoryImage = subCategoryImage;
    }

}
