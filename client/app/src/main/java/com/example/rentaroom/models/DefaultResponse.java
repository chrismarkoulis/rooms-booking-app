package com.example.rentaroom.models;

import com.google.gson.annotations.SerializedName;

public class DefaultResponse {
    @SerializedName("message")
    private String errorMsg;

    public DefaultResponse(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
