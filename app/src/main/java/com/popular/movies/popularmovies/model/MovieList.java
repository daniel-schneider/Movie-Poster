package com.popular.movies.popularmovies.model;

import java.util.List;

/**
 * Created by danielschneider on 5/6/18.
 */

public class MovieList {

    private String url;
    private List<MovieListItem> movielists;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<MovieListItem> getPlaylists() {
        return movielists;
    }

    public void setPlaylists(List<MovieListItem> playlists) {
        this.movielists = movielists;
    }

}
