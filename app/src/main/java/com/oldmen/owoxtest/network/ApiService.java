package com.oldmen.owoxtest.network;

import com.oldmen.owoxtest.domain.models.ImageInfo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {


    @GET("photos")
    Observable<Response<List<ImageInfo>>> getAllPhotos(@Query("page") int page);

    @GET("photos")
    Observable<Response<List<ImageInfo>>> getSearchPhotos(@Query("page") int page, @Query("query") String searchMsg);

}
