package com.marwadibrothers.mbstatus.models.home;

import java.util.ArrayList;
import java.util.List;


public class FearuredImage {

    private ArrayList<FearuredImageData> dataList = new ArrayList<>();
    private  String category_section;

    public String getCategory_section() {
        return category_section;
    }

    public void setCategory_section(String category_section) {
        this.category_section = category_section;
    }

    public ArrayList<FearuredImageData> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<FearuredImageData> dataList) {
        this.dataList = dataList;
    }
}

