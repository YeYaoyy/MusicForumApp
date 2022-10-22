package edu.northeastern.numad22fa_team23.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Responses {
    @SerializedName("post code")
    private String postCode;

    private String country;

    @SerializedName("country abbreviation")
    private String abb;

    private List<Places> places;

    public class Places {
        @SerializedName("place name")
        private String placename;
        private String longitude;
        private String state;
        @SerializedName("state abbreviation")
        private String sa;
        private String latitude;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getCountry() {
        return country;
    }

    public String getAbb() {
        return abb;
    }

    public List<Places> getPlace() {
        return places;
    }


}
