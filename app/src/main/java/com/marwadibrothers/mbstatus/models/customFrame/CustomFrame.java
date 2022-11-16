package com.marwadibrothers.mbstatus.models.customFrame;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CustomFrame {

    @SerializedName("data")
    private List<CustomFrameItem> data;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private Boolean status;

    public void setData(List<CustomFrameItem> data){
        this.data = data;
    }

    public List<CustomFrameItem> getData(){
        return data;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public void setStatus(Boolean status){
        this.status = status;
    }

    public boolean isStatus(){
        return status;
    }
}