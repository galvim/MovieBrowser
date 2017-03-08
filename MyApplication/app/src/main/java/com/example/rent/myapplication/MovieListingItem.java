package com.example.rent.myapplication;

import com.google.gson.annotations.SerializedName;

public class MovieListingItem {
    @SerializedName("Title")
    private String title;
    @SerializedName("Year")
    private String year;
    @SerializedName("imdbID")
    private String imdbID;
    @SerializedName("Type")
    private String type;
    @SerializedName("Poster")
    private String poster;

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getType() {
        return type;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getYear() {
        return year;
    }
}

