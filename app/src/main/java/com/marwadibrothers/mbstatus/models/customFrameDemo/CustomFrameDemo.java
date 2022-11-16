package com.marwadibrothers.mbstatus.models.customFrameDemo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CustomFrameDemo{

    @SerializedName("data")
    private List<CustomFrameDemoItem> data;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private Boolean status;

    public void setData(List<CustomFrameDemoItem> data){
        this.data = data;
    }

    public List<CustomFrameDemoItem> getData(){
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