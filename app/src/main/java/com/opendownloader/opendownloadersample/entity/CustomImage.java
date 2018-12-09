package com.opendownloader.opendownloadersample.entity;

public class CustomImage {

    private String url;
    private String smallUrl;


    public CustomImage(String url, String smallUrl) {
        this.url = url;
        this.smallUrl = smallUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSmallUrl() {
        return smallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl;
    }
}
