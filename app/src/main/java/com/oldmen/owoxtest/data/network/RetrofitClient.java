package com.oldmen.owoxtest.data.network;

import com.google.gson.Gson;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://api.unsplash.com/";

    private static Interceptor mTokenInterceptor = chain -> {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder()
                .addHeader("Accept-Version", "v1")
                .addHeader("Authorization",
                        "Client-ID 176a609651fb9ae514b26ceade8b6e2df8f82479213a4fb1332d4d383e9640ff");

        Request newRequest = requestBuilder.build();
        return chain.proceed(newRequest);
    };

    private static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(mTokenInterceptor)
            .build();

    private static Retrofit getRetrofitInstance(Gson gson) {

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(gson == null
                        ? GsonConverterFactory.create()
                        : GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static ApiService getApiService(Gson gson) {
        return getRetrofitInstance(gson).create(ApiService.class);
    }

}