package com.example.rentaroom.store;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.rentaroom.LoginActivity;
import com.example.rentaroom.MainActivity;
import com.example.rentaroom.models.User;
import com.example.rentaroom.utils.Utils;

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
        }
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
