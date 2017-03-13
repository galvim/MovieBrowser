package com.example.rent.myapplication.detail;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.rent.myapplication.RetrofitProvider;

import nucleus.presenter.Presenter;
import retrofit2.Retrofit;
import io.reactivex.Observable;


public class DetailPresenter extends Presenter<DetailActivity> {
    private Retrofit retrofit;

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }



    public Observable<MovieItem> loadDetail(String imdbID){
     DetailService detailService = retrofit.create(DetailService.class);
        return detailService.getDetailsInfo(imdbID);
    }
}
