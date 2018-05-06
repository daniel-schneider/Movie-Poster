package com.popular.movies.popularmovies.model;

/**
 * Created by danielschneider on 5/6/18.
 */

public class MovieListItem {

    private String uri;
    private String imageUrl;
    private String title;
    private int voteCount;
    private float voteAverage;
    private float popularity;
    private String id;
    private boolean hasVideo;
    private String overView;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int count) {
        this.voteCount = voteCount;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double average) {
        this.voteAverage = voteAverage;
    }

    public double getVotePopularity() {
        return popularity;
    }

    public void setVotePopularity(double popularityNumber) {
        this.popularity = popularity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



}
