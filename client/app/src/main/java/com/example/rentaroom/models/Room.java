package com.example.rentaroom.models;

import com.google.gson.annotations.SerializedName;

public class Room {

    @SerializedName("_id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("location")
    private String location;

    @SerializedName("description")
    private String description;

    @SerializedName("price")
    private int price;

    public Room(String id, String name, String location, String description, int price) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.description = description;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }
}
