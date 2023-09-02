package com.example.rentaroom.api;

import com.example.rentaroom.models.Room;
import com.example.rentaroom.models.RoomsResponse;
import com.example.rentaroom.models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

    @FormUrlEncoded
    @PUT("users/{id}")
    Call<User> updateUser(
            @Path("id") int id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("isAdmin") boolean isAdmin
    );

    @GET("rooms")
    Call<RoomsResponse> getRooms();

    @FormUrlEncoded
    @POST("rooms")
    Call<ResponseBody> createRoom(
            @Field("name") String name,
            @Field("location") String location,
            @Field("description") String description,
            @Field("price") int price

    );
}
