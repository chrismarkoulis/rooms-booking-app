package com.example.rentaroom.models;

public class User {
    private int _id;
    private String email, name;

    public User(int id, String email, String name) {
        this._id = id;
        this.email = email;
        this.name = name;
    }

    public int getId() {
        return _id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
