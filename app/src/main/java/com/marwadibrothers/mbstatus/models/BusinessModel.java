package com.marwadibrothers.mbstatus.models;

public class BusinessModel {
    String business_name, business_contact, business_address;
    int img;

        public BusinessModel(String business_name, String business_contact, String business_address, int img) {
        this.business_name = business_name;
        this.business_contact = business_contact;
        this.business_address = business_address;
        this.img = img;
    }

    public int getImg() {
        return img;
    }

    public String getBusiness_address() {
        return business_address;
    }

    public String getBusiness_contact() {
        return business_contact;
    }

    public String getBusiness_name() {
        return business_name;
    }
}
