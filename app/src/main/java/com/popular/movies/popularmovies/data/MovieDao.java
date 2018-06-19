package com.popular.movies.popularmovies.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by danielschneider on 6/19/18.
 */
@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies")
    List<Movie> getAll();

    @Query("SELECT * FROM movies WHERE favorited = TRUE")
    List<Movie> getFavorites();

    @Update
    void updateMovie (Movie movie);
}
