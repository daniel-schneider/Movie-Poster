package com.popular.movies.popularmovies;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.popular.movies.popularmovies.data.Database;

/**
 * Created by danielschneider on 7/12/18.
 */

public class MovieDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Database mDb;
    private final String mMovieId;

    public MovieDetailViewModelFactory(Database db, String movieId) {
        mDb = db;
        mMovieId = movieId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MovieDetailViewModel(mDb, mMovieId);
    }

}
