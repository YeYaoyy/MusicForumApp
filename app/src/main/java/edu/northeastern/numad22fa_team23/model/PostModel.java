package edu.northeastern.numad22fa_team23.model;

import com.google.gson.annotations.SerializedName;

public class PostModel {
    public int getZipCode() {
        return zipCode;
    }
//
//    @SerializedName("body")
//    private String text;

    public PostModel(int zipCode) {
        this.zipCode = zipCode;
//        this.text = text;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    private int zipCode;




}
