package com.example.rent.myapplication;

import com.google.gson.annotations.SerializedName;

import java.util.List;


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

    public void setItems(List<MovieListingItem> items) {
        this.items = items;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }
}
