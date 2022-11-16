package com.marwadibrothers.mbstatus.models.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FearuredImageData {

    @SerializedName("sub_category_id")
    @Expose
    private String sub_category_id;

    @SerializedName("product_id")
    @Expose
    private String product_id;

    @SerializedName("product_type")
    @Expose
    private String product_type;

    @SerializedName("product_image")
    @Expose
    private String product_image;

    @SerializedName("foreground_image")
    @Expose
    private String foreground_image;

    public String getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(String sub_category_id) {
        this.sub_category_id = sub_category_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getForeground_image() {
        return foreground_image;
    }

    public void setForeground_image(String foreground_image) {
        this.foreground_image = foreground_image;
    }
}
