package com.marwadibrothers.mbstatus.models.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class HomeResponse {

    private  String category_section;
    private List<HomeResponseData> dataList = new ArrayList<>();
    public String getCategory_section() {
        return category_section;
    }

    public void setCategory_section(String category_section) {
        this.category_section = category_section;
    }

    public List<HomeResponseData> getDataList() {
        return dataList;
    }

    public void setDataList(List<HomeResponseData> dataList) {
        this.dataList = dataList;
    }
}

