package com.example.eungapp;

public class LoginItem {
    private String email;
    private String password;

    public LoginItem() {}
    public LoginItem(String email, String password) {
        this.email = email; this.password = password;
    }

    void setEmail(String email) { this.email = email; }
    void setPassword(String password) { this.password = password; }

    String getEmail() { return email; }
    String getPassword() { return password; }
}
