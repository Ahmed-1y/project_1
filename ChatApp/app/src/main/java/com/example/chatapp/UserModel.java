package com.example.chatapp;

public class UserModel {

    String userId , userName ,userPassword, useremail;

    public UserModel() {
    }

    public UserModel(String userId, String userName, String useremail, String userPassword) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.useremail = useremail;

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

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
