package com.oldmen.owoxtest.network;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://api.unsplash.com/";
    private static String mToken;

    private static Interceptor mTokenInterceptor = new Interceptor() {
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();
            Request.Builder requestBuilder = request.newBuilder()
                    .addHeader("Accept-Version", "v1")
                    .addHeader("Authorization",
                            "Client-ID 176a609651fb9ae514b26ceade8b6e2df8f82479213a4fb1332d4d383e9640ff");

            Request newRequest = requestBuilder.build();
            return chain.proceed(newRequest);
        }
    };

    private static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(mTokenInterceptor)
            .build();

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static ApiService getApiService() {
        return getRetrofitInstance().create(ApiService.class);
    }

}