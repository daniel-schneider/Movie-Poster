package com.popular.movies.popularmovies.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by danielschneider on 6/19/18.
 */
@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies")
    List<Movie> getAll();

    @Query("SELECT * FROM movies WHERE favorited = 1")
    LiveData<List<Movie>> getFavorites();

    @Query("SELECT * FROM movies WHERE movieId = :id")
    LiveData<Movie> getMovie(String id);

    @Query("SELECT * FROM movies WHERE movieId = :id")
    Movie getMovieObject(String id);

    @Update
    void updateMovie (Movie movie);


    @Insert(onConflict = REPLACE)
    void addMovie(Movie movie);
}
