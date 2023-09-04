package com.example.rentaroom.store;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.rentaroom.LoginActivity;
import com.example.rentaroom.MainActivity;
import com.example.rentaroom.models.Owner;
import com.example.rentaroom.models.Room;
import com.example.rentaroom.models.User;
import com.example.rentaroom.utils.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StoreManager {
    private static final String APP_STORE_NAME = "room_rental_store";
    private static StoreManager mInstance;
    private final Context mCtx;

    private StoreManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    public static synchronized StoreManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new StoreManager(mCtx);
        }
        return mInstance;
    }

    public void saveUser(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(APP_STORE_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (user != null) {
            editor.putString("id", user.getId());
            editor.putString("name", user.getName());
            editor.putString("email", user.getEmail());
            editor.putBoolean("isAdmin", user.isAdmin());
            editor.putString("token", user.getToken());
            editor.apply();
        }
    }

    public void saveToken(String token) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(APP_STORE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (token != null) {
            editor.putString("token",token);
            editor.apply();
        }
    }

    public void setCurrentRoom(Room room, Owner owner) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(APP_STORE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String ownerJson = gson.toJson(owner);

        if (room != null) {
            editor.putString("room_id", room.getId());
            editor.putString("room_name", room.getName());
            editor.putString("room_location", room.getLocation());
            editor.putString("room_description", room.getDescription());
            editor.putInt("room_price", room.getPrice());
            editor.putString("room_userId", room.getUserId());
            editor.putString("room_owner", ownerJson);
            editor.apply();
        }
    }

    public Room getCurrentRoom() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(APP_STORE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String ownerJson = sharedPreferences.getString("room_owner", null);
        Owner ownerObj = gson.fromJson(ownerJson, Owner.class);

        return new Room(
                sharedPreferences.getString("room_id", "-1"),
                sharedPreferences.getString("room_name", null),
                sharedPreferences.getString("room_location", null),
                sharedPreferences.getString("room_description", null),
                sharedPreferences.getInt("room_price", 0),
                sharedPreferences.getString("room_userId", null),
                ownerObj
        );
    }

    public Object[] getRoomInfo() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(APP_STORE_NAME, Context.MODE_PRIVATE);
        Object[] info = {
                sharedPreferences.getString("room_id", "-1"),
                sharedPreferences.getString("room_name", null),
                sharedPreferences.getString("room_location", null),
                sharedPreferences.getString("room_description", null),
                sharedPreferences.getInt("room_price", 0),
                sharedPreferences.getString("room_ownerName", null),
                sharedPreferences.getString("room_ownerEmail", null)
        };
        return info;
    }

    public String getToken() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(APP_STORE_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        return token;
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(APP_STORE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("id", "-1") != "-1";
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(APP_STORE_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString("id", "-1"),
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getBoolean("isAdmin", false),
                sharedPreferences.getString("token", null)
        );
    }

    public void clearStore() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(APP_STORE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
