package com.popular.movies.popularmovies.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.popular.movies.popularmovies.model.MovieListItem;

/**
 * Created by danielschneider on 6/19/18.
 */

//{
//        "vote_count": 792,
//        "id": 351286,
//        "video": false,
//        "vote_average": 6.7,
//        "title": "Jurassic World: Fallen Kingdom",
//        "popularity": 294.688946,
//        "poster_path": "/c9XxwwhPHdaImA2f1WEfEsbhaFB.jpg",
//        "original_language": "en",
//        "original_title": "Jurassic World: Fallen Kingdom",
//        "genre_ids": [
//        28,
//        12,
//        878
//        ],
//        "backdrop_path": "/gBmrsugfWpiXRh13Vo3j0WW55qD.jpg",
//        "adult": false,
//        "overview": "A volcanic eruption threatens the remaining dinosaurs on the island of Isla Nublar, where the creatures have freely roamed for several years after the demise of an animal theme park known as Jurassic World. Claire Dearing, the former park manager, has now founded the Dinosaur Protection Group, an organization dedicated to protecting the dinosaurs. To help with her cause, Claire has recruited Owen Grady, a former dinosaur trainer who worked at the park, to prevent the extinction of the dinosaurs once again.",
//        "release_date": "2018-06-06"
//        }

@Entity(tableName = "movies")
public class Movie {

    public Movie (String movieId, String movieName, int voteCount, double voteAverage,
                  double popularity, String posterPath,  String overview,
                  String releaseDate, boolean favorited) {

        this.movieId = movieId;
        this.movieName = movieName;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.favorited = favorited;
    }

    public Movie (MovieListItem movieListItem, boolean favorited) {
        this.movieId = movieListItem.getId();
        this.movieName = movieListItem.getTitle();
        this.voteCount = movieListItem.getVoteCount();
        this.voteAverage = movieListItem.getVoteAverage();
        this.popularity = movieListItem.getVotePopularity();
        this.posterPath = movieListItem.getImageUrl();
        this.overview = movieListItem.getOverview();
        this.releaseDate = movieListItem.getReleaseDate();
        this.favorited = favorited;
    }

    @NonNull
    @PrimaryKey
    private String movieId;

    @ColumnInfo(name = "movie_name")
    private String movieName;

    @ColumnInfo(name = "vote_count")
    private int voteCount;

    @ColumnInfo(name = "vote_average")
    private double voteAverage;

    @ColumnInfo(name = "popularity")
    private double popularity;

    @ColumnInfo(name = "poster_path")
    private String posterPath;

    @ColumnInfo(name = "overview")
    private String overview;

    @ColumnInfo(name = "release_date")
    private String releaseDate;

    @ColumnInfo(name = "favorited")
    private boolean favorited;

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @NonNull
    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(@NonNull String movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(int voteAverage) {
        this.voteAverage = voteAverage;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(long popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean getFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }
}
