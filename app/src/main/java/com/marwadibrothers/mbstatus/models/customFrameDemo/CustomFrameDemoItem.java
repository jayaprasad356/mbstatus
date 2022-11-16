package com.marwadibrothers.mbstatus.models.customFrameDemo;

import com.google.gson.annotations.SerializedName;

public class CustomFrameDemoItem {

    @SerializedName("image")
    private String image;

    @SerializedName("caption")
    private String caption;

    public void setImage(String image){
        this.image = image;
    }

    public String getImage(){
        return image;
    }

    public void setCaption(String caption){
        this.caption = caption;
    }

    public String getCaption(){
        return caption;
    }
}