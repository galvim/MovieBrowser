package com.example.rent.myapplication;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface SearchService {

    @GET("/")
    Observable<SearchResult> search(@Query("page") int page,@Query("s") String title, @Query("y") String year, @Query("type") String type);
}
