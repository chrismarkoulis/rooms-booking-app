package com.example.rentaroom.models;

public class LoginResponse {
    private final User user;

    public LoginResponse(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }
}
