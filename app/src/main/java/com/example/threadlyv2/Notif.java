package com.example.threadlyv2;

public class Notif {

    private String userName, postTitle;

    public Notif(String userName, String postTitle){
        this.userName = userName;
        this.postTitle = postTitle;
    }

    public String getUserName(){return userName;}
    public void setUserName(String userName) {this.userName = userName;}

    public String getPostTitle(){return postTitle;}
    public void setPostTitle (String postTitle) {this.postTitle = postTitle;}
}
