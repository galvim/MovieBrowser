package com.example.rent.myapplication;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by RENT on 2017-03-07.
 */

public class SearchResult {
    @SerializedName("Search")
    private List<MovieListingItem> items;
    private String totalResults;
    @SerializedName("Response")
    private String resposne;

    public List<MovieListingItem> getItems() {
        return items;
    }

    public String getResposne() {
        return resposne;
    }

    public String getTotalResults() {
        return totalResults;
    }
}
