package com.example.rent.myapplication;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;


public interface SearchService {

    @GET("/")
    Observable<SearchResult> search(@Field("s") String title);
}
