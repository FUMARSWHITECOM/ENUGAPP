package com.example.eungapp;

import com.google.gson.annotations.SerializedName;

public class UserItem {
    @SerializedName("username")
    private String username;

    private String email;
    private int id;

    public UserItem() {}
    public UserItem(String username, String email) {
        this.username = username; this.email = email;
    }

    void setUserName(String username) { this.username = username; }
    void setEmail(String email) { this.email = email; }
    void setId(int id) { this.id = id; }

    String getUserName() { return this.username; }
    String getEmail() { return this.email; }
    int getId() { return this.id; }
}

