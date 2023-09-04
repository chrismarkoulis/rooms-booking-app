package com.example.rentaroom.models;

import com.google.gson.annotations.SerializedName;

public class Owner {
    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    public Owner(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
