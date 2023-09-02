package com.example.rentaroom.utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    private String token;
    public TokenInterceptor(String jwtToken) {
        this.token = jwtToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request newRequest = chain.request().newBuilder()
                .header("Authorization","Bearer "+ token)
                .build();

        return chain.proceed(newRequest);
    }
}
