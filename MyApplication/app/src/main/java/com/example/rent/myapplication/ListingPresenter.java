package com.example.rent.myapplication;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import nucleus.presenter.Presenter;

/**
 * Created by RENT on 2017-03-07.
 */

public class ListingPresenter extends Presenter<ListingActivity> {

    public void getDataAsync(String title) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String result = getData(title);
                    SearchResult searchResult = new Gson().fromJson(result, SearchResult.class);
                    getView().setDataOnUiThread(searchResult);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public String getData(String title) throws IOException {
        String string_url ="http://www.omdbapi.com/?s=" + title;
        URL url = new URL(string_url);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        return convertStreamToString(inputStream);
    }

    private String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
