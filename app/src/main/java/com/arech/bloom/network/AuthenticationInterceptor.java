package com.arech.bloom.network;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Pili Arancibia on 5/11/19
 */
public class AuthenticationInterceptor implements Interceptor {

    private String authToken;

    public AuthenticationInterceptor(String token) {
        this.authToken = token;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json");

        if (!this.authToken.equals("")) builder.addHeader("Authorization", this.authToken);

        Request request = builder.build();
        Log.d("url", "Request to url: " + request.url().toString());
        Log.d("header", "Header: " + request.headers().toString());
        if (request.body() != null) Log.d("body", "Body: " + request.body().toString());
        return chain.proceed(request);
    }
}