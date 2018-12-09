package com.opendownloader.opendownloadersample.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileImage {

    @Expose
    @SerializedName("small")
    private String smallImg;

    @Expose
    @SerializedName("medium")
    private String mediumImg;

    @Expose
    @SerializedName("large")
    private String largeImg;

    public String getSmallImg() {
        return smallImg;
    }

    public void setSmallImg(String smallImg) {
        this.smallImg = smallImg;
    }

    public String getMediumImg() {
        return mediumImg;
    }

    public void setMediumImg(String mediumImg) {
        this.mediumImg = mediumImg;
    }

    public String getLargeImg() {
        return largeImg;
    }

    public void setLargeImg(String largeImg) {
        this.largeImg = largeImg;
    }
}
