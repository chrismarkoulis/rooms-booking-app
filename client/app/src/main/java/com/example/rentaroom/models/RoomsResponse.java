package com.example.rentaroom.models;

import java.util.List;

public class RoomsResponse {

    private List<Room> rooms;
//    private int page;
//    private int pages;

    public RoomsResponse(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Room> getRooms() {
        return rooms;
    }

//    public int getPage() {
//        return page;
//    }
//
//    public int getPages() {
//        return pages;
//    }
}
