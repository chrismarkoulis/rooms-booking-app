package com.example.rentaroom.api;

import com.example.rentaroom.models.Room;
import com.example.rentaroom.models.RoomsResponse;
import com.example.rentaroom.models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {
    @FormUrlEncoded
    @POST("users")
    Call<ResponseBody> createUser(
            @Field("email") String email,
            @Field("password") String password,
            @Field("name") String name,
            @Field("isAdmin") boolean isAdmin
    );

    @FormUrlEncoded
    @POST("users/auth")
    Call<User> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("rooms")
    Call<RoomsResponse> getRooms();
}
