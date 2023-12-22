package com.example.familytreeapp;

public class MyDbDataModelFamily {

    String userId;
    String userName;
    String userDob;
    String userImage;
    String userParentId;

    public MyDbDataModelFamily(String userId, String userName, String userDob, String userImage, String userParentId) {
        this.userId = userId;
        this.userName = userName;
        this.userDob = userDob;
        this.userImage = userImage;
        this.userParentId = userParentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDob() {
        return userDob;
    }

    public void setUserDob(String userDob) {
        this.userDob = userDob;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserParentId() {
        return userParentId;
    }

    public void setUserParentId(String userParentId) {
        this.userParentId = userParentId;
    }
}
