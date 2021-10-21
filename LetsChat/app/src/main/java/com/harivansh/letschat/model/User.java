package com.harivansh.letschat.model;

public class User {

    private String userId;
    private String userName;
    private String userEmail;
    private String userProfileImage;
    private String userLastMessage;

    public User() {
    }

    public User(String userId, String userName, String userEmail, String userProfileImage, String userLastMessage) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userProfileImage = userProfileImage;
        this.userLastMessage = userLastMessage;
    }

    public User(String userName, String userEmail) {

        this.userName = userName;
        this.userEmail = userEmail;

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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(String userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    public String getUserLastMessage() {
        return userLastMessage;
    }

    public void setUserLastMessage(String userLastMessage) {
        this.userLastMessage = userLastMessage;
    }

}
