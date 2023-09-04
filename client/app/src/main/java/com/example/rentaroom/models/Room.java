package com.example.rentaroom.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Room implements Serializable {

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

    @SerializedName("user")
    private String userId;

    @SerializedName("userObject")
    private Owner owner;


    public Room(
            String id,
            String name,
            String location,
            String description,
            int price,
            String userId,
            Owner owner) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.description = description;
        this.price = price;
        this.userId = userId;
        this.owner = owner;
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

    public String getUserId() {
        return userId;
    }

    public Owner getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", userId='" + userId + '\'' +
                ", owner=" + owner +
                '}';
    }
}
