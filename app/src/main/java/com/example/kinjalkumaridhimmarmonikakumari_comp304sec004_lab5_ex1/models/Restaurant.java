package com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1.models;

public class Restaurant {
    private String name;
    private String address;
    private Double ratings;
    private int totalUserRatings;
    private Double latitude;
    private Double longitude;
    private boolean openNow;

    public Restaurant(String name, String address, Double ratings, int totalUserRatings, Double latitude,
                      Double longitude, boolean openNow) {
        this.name = name;
        this.address = address;
        this.ratings = ratings;
        this.totalUserRatings = totalUserRatings;
        this.latitude = latitude;
        this.longitude = longitude;
        this.openNow = openNow;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Double getRatings() {
        return ratings;
    }

    public int getTotalUserRatings() {
        return totalUserRatings;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public boolean getOpenNow() {
        return openNow;
    }
}
