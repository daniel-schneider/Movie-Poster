package com.popular.movies.popularmovies.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

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

    @NonNull
    @PrimaryKey
    private String movieId;

    @ColumnInfo(name = "movie_name")
    private String movieName;

    @ColumnInfo(name = "vote_count")
    private int voyeCount;

    @ColumnInfo(name = "vote_average")
    private int votAverage;

    @ColumnInfo(name = "popularity")
    private long popularity;

    @ColumnInfo(name = "poster_path")
    private String posterPath;

    @ColumnInfo(name = "overview")
    private String overview;

    @ColumnInfo(name = "release_date")
    private String releaseDate;

    @ColumnInfo(name = "favorited")
    private boolean favorited;

}
