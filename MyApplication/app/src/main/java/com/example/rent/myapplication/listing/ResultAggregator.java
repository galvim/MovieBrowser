package com.example.rent.myapplication.listing;

import com.example.rent.myapplication.detail.MovieItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RENT on 2017-03-15.
 */

public class ResultAggregator {

    private List<MovieListingItem> movieItems = new ArrayList<>();
    private int totalItemResults ;
    private String response;

    public void setMovieItems(List<MovieListingItem> movieItems) {
        this.movieItems = movieItems;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<MovieListingItem> getMovieItems() {
        return movieItems;
    }

    public int getTotalItemResults() {
        return totalItemResults;
    }

    public String getResponse() {
        return response;
    }

    public void setTotalItemResults(int totalItemResults) {
        this.totalItemResults = totalItemResults;
    }

    public void addNewItems(List<MovieListingItem> newItems){
        movieItems.addAll(newItems);
    }
}
