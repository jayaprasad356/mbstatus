package com.marwadibrothers.mbstatus.models.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GreetingReponse {

    private String greeting_section;
    private List<GreetingListResponse> greetingListResponses = new ArrayList<>();

    public String getGreeting_section() {
        return greeting_section;
    }

    public void setGreeting_section(String greeting_section) {
        this.greeting_section = greeting_section;
    }

    public List<GreetingListResponse> getGreetingListResponses() {
        return greetingListResponses;
    }

    public void setGreetingListResponses(List<GreetingListResponse> greetingListResponses) {
        this.greetingListResponses = greetingListResponses;
    }
}
