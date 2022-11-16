package com.marwadibrothers.mbstatus.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PopUpData {

    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("image")
    @Expose
    public String image;

    @SerializedName("sub_category_id")
    @Expose
    public String sub_category_id;

    @SerializedName("link")
    @Expose
    public String link;

    @SerializedName("link_type")
    @Expose
    public String link_type;

    @SerializedName("popup_status")
    @Expose
    public String popup_status;

    @SerializedName("sub_category_name")
    @Expose
    public String sub_category_name;

    public String getSub_category_name() {
        return sub_category_name;
    }

    public void setSub_category_name(String sub_category_name) {
        this.sub_category_name = sub_category_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(String sub_category_id) {
        this.sub_category_id = sub_category_id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink_type() {
        return link_type;
    }

    public void setLink_type(String link_type) {
        this.link_type = link_type;
    }

    public String getPopup_status() {
        return popup_status;
    }

    public void setPopup_status(String popup_status) {
        this.popup_status = popup_status;
    }
}
