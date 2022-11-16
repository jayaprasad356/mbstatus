package com.marwadibrothers.mbstatus.models.customFrame;

import com.google.gson.annotations.SerializedName;

public class CustomFrameItem {

    @SerializedName("frame")
    private String frame;

    public void setFrame(String frame){
        this.frame = frame;
    }

    public String getFrame(){
        return frame;
    }
}