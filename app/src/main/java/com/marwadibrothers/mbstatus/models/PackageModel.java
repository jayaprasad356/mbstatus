package com.marwadibrothers.mbstatus.models;

import java.util.ArrayList;

public class PackageModel {
    String txt_expire,txt_title,txt_price,txt_duration;
    ArrayList<String> PrimiuimFeatureList = new ArrayList<>();

    public PackageModel(String txt_expire,String txt_title,String txt_price,String txt_duration,ArrayList<String> PrimiuimFeatureList){

        this.txt_expire = txt_expire;
        this.txt_title = txt_title;
        this.txt_price = txt_price;
        this.txt_duration = txt_duration;
        this.PrimiuimFeatureList = PrimiuimFeatureList;
    }

    public ArrayList<String> getPrimiuimFeatureList() {
        return PrimiuimFeatureList;
    }

    public String getTxt_duration() {
        return txt_duration;
    }

    public String getTxt_expire() {
        return txt_expire;
    }

    public String getTxt_price() {
        return txt_price;
    }

    public String getTxt_title() {
        return txt_title;
    }

}
