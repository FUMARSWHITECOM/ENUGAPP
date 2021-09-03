package com.example.eungapp;

public class AuthenticateItem {
    private String code;
    private String message;
    private String username;
    private int user_id;

    public AuthenticateItem() {}
    public AuthenticateItem(String code, String message, String username) {
        this.code = code; this.message = message; this.username = username;
    }

    void setCode(String code) { this.code = code; }
    void setMessage(String message) { this.message = message; }
    void setUserName(String username) { this.username = username; }

    String getCode() { return this.code; }
    String getMessage() { return this.message; }
    String getUserName() { return this.username; }
    int getUserId() { return user_id; }
}
