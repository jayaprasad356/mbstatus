package com.marwadibrothers.mbstatus.models.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductResponseData {
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("foreground_image")
    @Expose
    private String foreground_image;
    @SerializedName("product_type")
    @Expose
    private String product_type;
    @SerializedName("display_section")
    @Expose
    private String display_section;

    public String getForeground_image() {
        return foreground_image;
    }

    public void setForeground_image(String foreground_image) {
        this.foreground_image = foreground_image;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getDisplay_section() {
        return display_section;
    }

    public void setDisplay_section(String display_section) {
        this.display_section = display_section;
    }
}
