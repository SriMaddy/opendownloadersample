package com.opendownloader.opendownloadersample.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerObject {

    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("createdAt")
    private String createdAt;

    @Expose
    @SerializedName("width")
    private long width;

    @Expose
    @SerializedName("height")
    private long height;

    @Expose
    @SerializedName("color")
    private String color;

    @Expose
    @SerializedName("likes")
    private long likes;

    @Expose
    @SerializedName("liked_by_user")
    private boolean likedByUser;

    @Expose
    @SerializedName("user")
    private User user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public long getWidth() {
        return width;
    }

    public void setWidth(long width) {
        this.width = width;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public boolean isLikedByUser() {
        return likedByUser;
    }

    public void setLikedByUser(boolean likedByUser) {
        this.likedByUser = likedByUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
