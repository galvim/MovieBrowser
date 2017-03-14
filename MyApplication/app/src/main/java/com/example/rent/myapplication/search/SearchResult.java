package com.example.rent.myapplication.search;

import com.example.rent.myapplication.listing.MovieListingItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class SearchResult {
    @SerializedName("Search")
    private List<MovieListingItem> items;
    private String totalResults;
    @SerializedName("Response")
    private String response;

    public List<MovieListingItem> getItems() {
        return items;
    }

    public String getResposne() {
        return response;
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
