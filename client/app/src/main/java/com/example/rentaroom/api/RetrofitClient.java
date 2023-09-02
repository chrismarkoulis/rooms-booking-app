package com.example.rentaroom.api;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://192.168.1.103:5000/api/";
    private static Retrofit retrofit = null;

    public static Retrofit makeRequest(String token) {
            if (token != null) {
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(
                                chain -> {
                                    Request original = chain.request();
                                    Request.Builder requestBuilder = original.newBuilder()
                                            .header("Authorization", String.format("Bearer %s", token))
                                            .method(original.method(), original.body());

                                    Request request = requestBuilder.build();
                                    return chain.proceed(request);
                                }
                        ).build();

                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient)
                        .build();
            } else {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        return retrofit;
    }
}
