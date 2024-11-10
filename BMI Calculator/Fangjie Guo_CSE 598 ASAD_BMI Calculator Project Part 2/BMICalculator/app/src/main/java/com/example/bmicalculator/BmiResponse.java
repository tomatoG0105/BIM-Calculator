package com.example.bmicalculator;

import com.google.gson.annotations.SerializedName;
import java.util.List;

//Handle API response
public class BmiResponse {
    @SerializedName("bmi")
    private double bmi;    // Shown in Label

    @SerializedName("risk")
    private String risk;   //Shown in "Message"

    @SerializedName("more")
    private List<String> more;  //more links

    //Getter and setters

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public List<String> getMore() {
        return more;
    }

    public void setMore(List<String> more) {
        this.more = more;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }



}
