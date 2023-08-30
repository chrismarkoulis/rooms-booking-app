package com.example.rentaroom.api;

import com.example.rentaroom.models.LoginResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {
    @FormUrlEncoded
    @POST("users")
    Call<ResponseBody> createUser(
            @Field("email") String email,
            @Field("password") String password,
            @Field("name") String name
    );

    @FormUrlEncoded
    @POST("users/auth")
    Call<LoginResponse> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );
}
