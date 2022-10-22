package edu.northeastern.numad22fa_team23.model;

import com.google.gson.annotations.SerializedName;

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

        public String getPlacename() {
            return placename;
        }

        public void setPlacename(String placename) {
            this.placename = placename;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getSa() {
            return sa;
        }

        public void setSa(String sa) {
            this.sa = sa;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

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
