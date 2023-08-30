package com.example.rentaroom.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class Utils {
    public static String json (String target, String prop) {
        JSONObject jsonObject;
        String value;
        try {
            jsonObject = new JSONObject(target);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        try {
            value = jsonObject.getString(prop);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return value;
    }
}
