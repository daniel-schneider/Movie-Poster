package com.popular.movies.popularmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.popular.movies.popularmovies.data.Database;
import com.popular.movies.popularmovies.data.Movie;

/**
 * Created by danielschneider on 7/12/18.
 */

public class MovieDetailViewModel extends ViewModel {

    private LiveData<Movie> movie;


    public LiveData<Movie> getMovie() {
        return movie;
    }

    public MovieDetailViewModel(Database db, String id) {
        movie = db.movieDao().getMovie(id);
    }

}
