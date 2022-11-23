package com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1.models;

public class Cuisine {
    private String cuisineName;
    private int drawableImageResource;

    public Cuisine(String cuisineName, int drawableImageResource) {
        this.cuisineName = cuisineName;
        this.drawableImageResource = drawableImageResource;
    }

    public String getCuisineName() {
        return cuisineName;
    }

    public void setCuisineName(String cuisineName) {
        this.cuisineName = cuisineName;
    }

    public int getDrawableImageResource() {
        return drawableImageResource;
    }

    public void setDrawableImageResource(int drawableImageResource) {
        this.drawableImageResource = drawableImageResource;
    }
}
