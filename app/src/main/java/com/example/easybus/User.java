package com.example.easybus;

public class User {
    String email;
    String password;
    String fullName;
    String imageURL;

    public User() {
    }

    public User(String email, String password, String fullName,String imageURL) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.imageURL = imageURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImageURL() { return imageURL; }

    public void setImageURL(String imageURL) { this.imageURL = imageURL; }
}
