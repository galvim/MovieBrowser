package com.example.rent.myapplication.detail;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface DetailService {
    @GET("/")
    Observable<MovieItem> getDetailsInfo(@Query("i") String id);
}
