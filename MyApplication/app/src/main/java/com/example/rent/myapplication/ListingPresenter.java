package com.example.rent.myapplication;


import java.util.List;

import io.reactivex.Observable;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import nucleus.presenter.Presenter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;



public class ListingPresenter extends Presenter<ListingActivity> implements  OnLoadNextPageListener{
    private List<MovieListingItem> allResults;
    private Retrofit retrofit;
    private String title;
    private String stringYear;
    private String type;

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public ListingPresenter() {
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.omdbapi.com")
                .build();
    }

    public Observable<SearchResult> getDataAsync(int page,String title, int year, String type) {
        this.title = title;

        this.type = type;

        stringYear = year == ListingActivity.NO_YEAR_SELECTED ? null : String.valueOf(year);
        return retrofit.create(SearchService.class).search(page, title, stringYear, type);
    }

    @Override
    public void loadNextPage(int page) {
        retrofit.create(SearchService.class).search(page ,title,stringYear,type)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                searchResult -> {
        getView().appendItems(searchResult);
        },throwable -> {
                            //nop
                        });

    }
}

      /*  new Thread() {
            @Override
            public void run() {
                try {
                    String result = getData(title);
                    SearchResult searchResult = new Gson().fromJson(result, SearchResult.class);
                    getView().setDataOnUiThread(searchResult, false);
                } catch (IOException e) {
                    getView().setDataOnUiThread(null, true);
                }
            }
        }.start();*/


    /*public String getData(String title) throws IOException {
        String string_url = "http://www.omdbapi.com/?s=" + title;
        URL url = new URL(string_url);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setConnectTimeout(3000);
        InputStream inputStream = urlConnection.getInputStream();
        return convertStreamToString(inputStream);
    }

    private String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }*/

