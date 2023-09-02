package com.example.rentaroom.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("_id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("isAdmin")
    private boolean isAdmin;

    private final String token;

    public User(String id, String name, String email, boolean isAdmin, String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.isAdmin = isAdmin;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", token='" + token + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
